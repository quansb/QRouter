# QRouter
android component communication

版本功能  
支持不同的module间Activity组件通信  

例如: 同一级别  A module  和  B module  都被上层app module依赖,
 A 与 B 没有依赖关系
 A 与 B 都依赖 QRouter 通过调用 QRouter的方法进行通信

# Download
Use project Gradle:
```
repositories {
  google()
  jcenter()
}

allprojects {
    repositories {
        google()
        jcenter()
        maven { url 'https://jitpack.io' }
    }
}
```

# Setting
需要通信的所有module都要配置 ！！！！ 所有需要通信的都要配置
app or module Gradle:
```
  dependencies {
      implementation 'com.github.quansb:QRouter:1.0.0'
      annotationProcessor 'com.github.quansb.QRouter:processor:1.0.0'
  }
```


```
android {
    compileOptions {
        sourceCompatibility JavaVersion.VERSION_1_8
        targetCompatibility JavaVersion.VERSION_1_8
    }
    defaultConfig {
        javaCompileOptions {
            annotationProcessorOptions {
                arguments = [moduleName: project.getName()]
            }
        }
    }
}
```


# Using way

在Activity中使用注解  @QRouter(name = "MainActivity")
    name是类的名字 （！！注意不是类全限定名！！）


# Init
```
   RouterManager.getInstance().startActivity();
```

```
 /**
     * @param context      context
     * @param group        所在的module名字
     * @param activityName 在该module中存在的 Activity 名字
     */
    public void startActivity(Context context, String group, String activityName){
    
    }
```

# Example   Module "home"   jump  to   Module "mine"
```
@QRouter(name = "HomeActivity")
public class HomeActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        RouterManager.getInstance().startActivity(this, "mine", "MineActivity");
    }
}
```


     
