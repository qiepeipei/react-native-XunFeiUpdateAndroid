# react-native-XunFeiUpdateAndroid
react native 讯飞自动更新
## npm install react-native-xunfeiupdate
### 使用实例
	var Update = require('react-native-xunfeiupdate');
	
	let confg = {
      DebugMode:false,  //调试模式: true开启调试模式,false不开启
      NotificationType:1, //通知类型: 1通知栏提示,2对话框提示
      UpdateType:1,  //更新类型: 1自动更新,2强制更新

    };
   
    var update = new Update(confg);
    
    //回调事件
    update.updateEvent((event)=>{

      if(event['updateState'] == 0){
        console.log("已是最新版本");
      }else if(event['updateState'] == 1){
        console.log("有新版本发布");
      }else if(event['updateState'] == -1){
        console.log("请求更新失败,错误码:"+event['errorCode']);
      }


    });

####android配置
1. 设置 `android/setting.gradle`

    ```
    ...
	include ':xunfeiupdate'
	project(':xunfeiupdate').projectDir = new File(rootProject.projectDir, '../node_modules/react-native-xunfeiupdate/android/xunfeiupdate')
    ```

2. 设置 `android/app/build.gradle`

    ```
    ...
    dependencies {
        ...
        compile project(':xunfeiupdate')
    }
    ```
    
3. 注册模块 (到 MainApplication.java)

    ```
    import com.example.xunfeiupdate.XunFeiUpdatePackage;  // <--- 导入

    public class MainApplication extends Application implements ReactApplication {
      ......

        @Override
    	protected List<ReactPackage> getPackages() {
      		return Arrays.<ReactPackage>asList(
          			new MainReactPackage(),
          			new XunFeiUpdatePackage()      //<--- 添加
      		);
    	} 

      ......

    }
    ```

![Mou icon1](/assets/Image1.png)

