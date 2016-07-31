package com.example.xunfeiupdate;

import android.support.annotation.Nullable;
import android.util.Log;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;
import com.iflytek.autoupdate.IFlytekUpdate;
import com.iflytek.autoupdate.IFlytekUpdateListener;
import com.iflytek.autoupdate.UpdateConstants;
import com.iflytek.autoupdate.UpdateErrorCode;
import com.iflytek.autoupdate.UpdateInfo;
import com.iflytek.autoupdate.UpdateType;

/**
 * Created by qiepeipei on 16/7/30.
 */
public class XunFeiUpdateModule extends ReactContextBaseJavaModule {

    IFlytekUpdate updManager;

    public XunFeiUpdateModule(ReactApplicationContext reactContext) {
        super(reactContext);
    }

    @Override
    public String getName() {
        return "XunFeiUpdate";
    }

    //初始化方法
    @ReactMethod
    public void initialise(ReadableMap objMap){

        //初始化自动更新对象
        updManager = IFlytekUpdate.getInstance(getReactApplicationContext());
        //开启调试模式,默认不开启
        updManager.setDebugMode(objMap.getBoolean("DebugMode"));
        //开启wifi环境下检测更新,仅对自动更新有效,强制更新则生效
        updManager.setParameter(UpdateConstants.EXTRA_WIFIONLY, "true");
        //设置通知栏使用应用icon,详情请见示例
        updManager.setParameter(UpdateConstants.EXTRA_NOTI_ICON, "true");

        int NotificationType = objMap.getInt("NotificationType");
        if(NotificationType == 1){
            //设置更新提示类型,为通知栏提示
            updManager.setParameter(UpdateConstants.EXTRA_STYLE,UpdateConstants.UPDATE_UI_NITIFICATION);

        }else{
            //设置更新提示类型,为对话框提示
            updManager.setParameter(UpdateConstants.EXTRA_STYLE,UpdateConstants.UPDATE_UI_DIALOG);
        }

        int UpdateType = objMap.getInt("NotificationType");
        if(UpdateType == 1){
            // 启动自动更新
            updManager.autoUpdate(getCurrentActivity(), updateListener);
        }else{
            // 启动强制更新
            updManager.forceUpdate(getCurrentActivity(), updateListener);
        }



    }

    //自动更新回调方法,详情参考demo
    IFlytekUpdateListener updateListener = new IFlytekUpdateListener() {

        @Override
        public void onResult(int errorcode, UpdateInfo result) {

            if(errorcode == UpdateErrorCode.OK && result!= null) {

                if(result.getUpdateType() == UpdateType.NoNeed) {

                    WritableMap params = Arguments.createMap();
                    params.putInt("updateState", 0);
                    sendEvent(getReactApplicationContext(), "updateEvent", params);
                    return;
                }

                WritableMap params = Arguments.createMap();
                params.putInt("updateState", 1);
                sendEvent(getReactApplicationContext(), "updateEvent", params);
                updManager.showUpdateInfo(getCurrentActivity(), result);
            }
            else
            {
                WritableMap params = Arguments.createMap();
                params.putInt("updateState", -1);
                params.putInt("errorCode", errorcode);
                sendEvent(getReactApplicationContext(), "updateEvent", params);
            }

        }
    };


    private void sendEvent(ReactContext reactContext,
                           String eventName,
                           @Nullable WritableMap params) {
        reactContext.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class).emit(eventName, params);
    }



}
