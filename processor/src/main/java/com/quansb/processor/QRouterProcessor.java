package com.quansb.processor;

import com.google.auto.service.AutoService;
import com.quansb.annotation.QRouter;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;
import com.squareup.javapoet.WildcardTypeName;

import com.quansb.processor.utils.ProcessorConfig;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedOptions;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic;

/**
 * author:quansb
 * Description:
 * Date:2020/5/15
 */
@AutoService(Processor.class)
@SupportedSourceVersion(SourceVersion.RELEASE_8)
@SupportedAnnotationTypes({"com.quansb.annotation.QRouter"})
@SupportedOptions({ProcessorConfig.MODULE_NAME, ProcessorConfig.APT_PACKAGE})
public class QRouterProcessor extends AbstractProcessor {

    // 操作Element的工具类（类，函数，属性，其实都是Element）
    private Elements elementTool;

    // type(类信息)的工具类，包含用于操作TypeMirror的工具方法
    private Types typeTool;

    // Message用来打印 日志相关信息
    private Messager messager;

    // 文件生成器， 类 资源 等，就是最终要生成的文件 是需要Filer来完成的
    private Filer filer;

    private String moduleName;  // （模块传递过来的）模块名  app，personal
    private String packageName; // （模块传递过来的） 包名

    private List<String> classPackageNameList = new ArrayList<>();
    private List<String> classSimpleNameList = new ArrayList<>();

    private List<ClassName> pathClassList = new ArrayList<>();


    private final String APT_JAVA_SAVE_LOCATION="com.quansb.qrouter";  //apt生成的.java文件统一存在哪个目录下面   暂时不支持自定义目录

    @Override

    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        elementTool = processingEnvironment.getElementUtils();
        messager = processingEnvironment.getMessager();
        filer = processingEnvironment.getFiler();
        typeTool = processingEnvironment.getTypeUtils();
        moduleName = processingEnvironment.getOptions().get(ProcessorConfig.MODULE_NAME);   //从gradle获取
      //  packageName = processingEnvironment.getOptions().get(ProcessorConfig.APT_PACKAGE);  //暂时不支持
        messager.printMessage(Diagnostic.Kind.NOTE, ">>>>>>>>>>>>>>>>>>>>>> QRouter路由搭建");
        messager.printMessage(Diagnostic.Kind.NOTE, ">>>>>>>>>>>>>>>>>>>>>> moduleName:" + moduleName);
        messager.printMessage(Diagnostic.Kind.NOTE, ">>>>>>>>>>>>>>>>>>>>>> packageName:" + packageName);

        packageName=APT_JAVA_SAVE_LOCATION; //暂时不支持自定义目录

        if (moduleName != null && packageName != null) {
            messager.printMessage(Diagnostic.Kind.NOTE, "QRouter 环境搭建完成....");
        } else {
            messager.printMessage(Diagnostic.Kind.NOTE, "QRouter 环境有问题，请检查 options 与 aptPackage 为null...");
        }
    }

    /**
     * 相当于main函数，开始处理注解
     * 注解处理器的核心方法，处理具体的注解，生成Java文件
     *
     * @param set              使用了支持处理注解的节点集合
     * @param roundEnvironment 当前或是之前的运行环境,可以通过该对象查找的注解。
     * @return true 表示后续处理器不会再处理（已经处理完成）
     */
    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        if (set.isEmpty()) {
            messager.printMessage(Diagnostic.Kind.NOTE, "并没有发现 被@QRouter注解的地方");
            return false;
        }
        createRouterClass(set, roundEnvironment);

        return true;
    }

    private void createRouterClass(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {

        Set<? extends Element> elements = roundEnvironment.getElementsAnnotatedWith(QRouter.class);
        for (Element element : elements) {
            // 获取类节点，获取包节点 （com.xiangxue.xxxxxx）
            String classPackageName = elementTool.getPackageOf(element).getQualifiedName().toString();
            // 获取简单类名，例如：MainActivity
            String className = element.getSimpleName().toString();
            messager.printMessage(Diagnostic.Kind.NOTE, "被@QRetuer注解的类有：" + classPackageName + "." + className); // 打印出 就证明APT没有问题
            classPackageNameList.add(classPackageName);
            classSimpleNameList.add(className);
            QRouter qRouter = element.getAnnotation(QRouter.class);
        }

        TypeSpec classPath = createPathClass();
        TypeSpec classGroup = createRouterClass();

        JavaFile javaFilePathClass = JavaFile.builder(packageName, classPath).build();

        try {
            javaFilePathClass.writeTo(filer);
        } catch (IOException e) {
            e.printStackTrace();
        }

        JavaFile javaFileRouterClass = JavaFile.builder(packageName, classGroup).build();
        try {
            javaFileRouterClass.writeTo(filer);
        } catch (IOException e) {
            e.printStackTrace();
        }

        messager.printMessage(Diagnostic.Kind.NOTE, "生成类：" + packageName + "." + classPath.name);
        messager.printMessage(Diagnostic.Kind.NOTE, "生成类：" + packageName + "." + classGroup.name);

    }


//        public class QRouter$$Group$$Admob implements RouterGroup {
//            @Override
//            public Map<String, Class<? extends RouterPath>> getGroup() {
//                Map<String, Class<? extends RouterPath>> map=new HashMap<>();
//                map.put("mine",QRouter$$Path$$Admob.class);
//                return map;
//            }
//        }

    /**
     *     APT生成代码样板
     *     public class QRouter$$Group$$Admob implements RouterGroup {
     *             public Map<String, Class<? extends RouterPath>> getGroup() {
     *                 Map<String, Class<? extends RouterPath>> map=new HashMap<>();
     *                 map.put("mine",QRouter$$Path$$Admob.class);
     *                 return map;
     *             }
     *         }
     */

    private TypeSpec createPathClass() {

        if (classPackageNameList.size() > 0) {

            for (int i = 0; i < classPackageNameList.size(); i++) {
                ClassName clazz = ClassName.get(classPackageNameList.get(i), classSimpleNameList.get(i));
                pathClassList.add(clazz);
            }
        }

        ClassName routerGroup = ClassName.get("com.quansb.qrouter", "RouterGroup");

        ClassName routerPath = ClassName.get("com.quansb.qrouter", "RouterPath");

        ClassName activity = ClassName.get("android.app", "Activity");

        ClassName map = ClassName.get("java.util", "Map");

        ClassName hashMap = ClassName.get("java.util", "HashMap");

        TypeName activityClass = ParameterizedTypeName.get(ClassName.get(Class.class), WildcardTypeName.subtypeOf(activity));

        TypeName pathMap = ParameterizedTypeName.get(ClassName.get(Map.class), ClassName.get(String.class),activityClass);

        TypeName clazz = ParameterizedTypeName.get(ClassName.get(Class.class),WildcardTypeName.subtypeOf(Object.class));

        TypeName pathMap2 = ParameterizedTypeName.get(ClassName.get(Map.class), ClassName.get(String.class), clazz);

        MethodSpec.Builder getGroupMapBuilder = MethodSpec.methodBuilder("getPathMap")
                                                          .addAnnotation(Override.class)
                                                          .addModifiers(Modifier.PUBLIC)
                                                          .returns(pathMap2)
                                                          .addStatement("$T<$T,$T<?>>map =new $T()", Map.class, String.class, Class.class, HashMap.class);

        if (classSimpleNameList.size() > 0) {
            for (int i = 0; i < classSimpleNameList.size(); i++) {
                getGroupMapBuilder.addStatement("map.put($S,$T.class)", classSimpleNameList.get(i), pathClassList.get(i));
            }
        }

        getGroupMapBuilder.addStatement("return map");

        MethodSpec getGroupMap = getGroupMapBuilder.build();

        TypeSpec typeSpec = TypeSpec.classBuilder(ProcessorConfig.PATH_CLASS_NAME + moduleName)
                                    .addModifiers(Modifier.PUBLIC)
                                    .addSuperinterface(routerPath)
                                    .addMethod(getGroupMap)
                                    .build();

        return typeSpec;
    }

    private TypeSpec createRouterClass() {

        ClassName routerGroup = ClassName.get("com.quansb.qrouter", "RouterGroup");

        ClassName routerPath = ClassName.get("com.quansb.qrouter", "RouterPath");

        ClassName router$$Path$$module = ClassName.get(packageName, ProcessorConfig.PATH_CLASS_NAME + moduleName);

        TypeName clazz = ParameterizedTypeName.get(ClassName.get(Class.class), WildcardTypeName.subtypeOf(routerPath));

        TypeName groupMap = ParameterizedTypeName.get(ClassName.get(Map.class), ClassName.get(String.class), clazz);

        MethodSpec getGroup = MethodSpec.methodBuilder("getGroupMap")
                                        .addAnnotation(Override.class)
                                        .addModifiers(Modifier.PUBLIC)
                                        .returns(groupMap)
                                        .addStatement("$T<$T,$T<? extends $T>>map =new $T()", Map.class, String.class, Class.class, routerPath,
                                                HashMap.class)
                                        .addStatement("map.put($S,$T.class)", moduleName, router$$Path$$module)
                                        .addStatement("return map")
                                        .build();

        TypeSpec typeSpec = TypeSpec.classBuilder(ProcessorConfig.GROUP_CLASS_NAME + moduleName)
                                    .addModifiers(Modifier.PUBLIC)
                                    .addSuperinterface(routerGroup)
                                    .addMethod(getGroup)
                                    .build();

        return typeSpec;
    }


}
