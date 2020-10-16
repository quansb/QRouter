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
  annotationProcessor 'com.github.quansb.QRouter:processor:1.0.0'
}
```


# Setting
在activity对应的module中配置
切记：必须写在defaultConfig节点下

```
   javaCompileOptions {  
            annotationProcessorOptions {  
                arguments = [moduleName: project.getName()]  
            }  
        }  
```

# Using way

在Activity中使用注解  @QRouter(path = "com/quansb/main", name = "MainActivity")

其中path是类的包名  name是类的名字 （！！注意不是类全限定名！！）


# Init
```
 /**
     * @param context      context
     * @param group        所在的module名字
     * @param activityName 在该module中存在的 Activity 名字
     */
    public void startActivity(Context context, String group, String activityName){
    
    }
```

# Example
```
@QRouter(path = "com/quansb/main", name = "MainActivity")
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RouterManager.getInstance().startActivity(this, "app", "MineActivity");
    }
}
```


     
