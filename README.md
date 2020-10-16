# QRouter
android component communication

版本功能  
支持不同的module间Activity组件通信  


# Download
use Gradle:
```
repositories {
  google()
  jcenter()
}
dependencies {
  implementation 'com.duiud.quansb:qrouter:1.0.0'
}
```




使用方式：

在需要的module中配置

切记：必须写在defaultConfig节点下

```
   javaCompileOptions {  
            annotationProcessorOptions {  
                arguments = [moduleName: project.getName()]  
            }  
        }  
```
     
