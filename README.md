# QRouter
android component communication

初始版本功能  
不同的module间Activity组件通信  





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
     
