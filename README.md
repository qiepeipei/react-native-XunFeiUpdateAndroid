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
1. 设置 `android/setting.gradle` 注意: 如果node_modules前面有空格记得删除,不然无法读取lib

    ```
    ...
	include ':xunfeiupdate'
	project(':xunfeiupdate').projectDir = new File(rootProject.projectDir, '../	node_modules/react-native-xunfeiupdate/android/xunfeiupdate')
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
    
![Mou icon1](https://cl.ly/0Y183g3A1A3o/Image%202016-07-31%20at%202.41.18%20%E4%B8%8B%E5%8D%88.png)

