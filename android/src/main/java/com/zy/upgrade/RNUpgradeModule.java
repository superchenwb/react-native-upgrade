
package com.zy.upgrade;

import android.app.Application;
import android.util.Log;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.xuexiang.xupdate.XUpdate;
import com.xuexiang.xupdate.entity.UpdateError;
import com.xuexiang.xupdate.listener.OnUpdateFailureListener;
import com.xuexiang.xupdate.utils.UpdateUtils;
import com.zy.upgrade.custom.CustomUpdateParser;
import com.zy.upgrade.http.OKHttpUpdateHttpService;

import static com.xuexiang.xupdate.entity.UpdateError.ERROR.CHECK_NO_NEW_VERSION;

public class RNUpgradeModule extends ReactContextBaseJavaModule {

  private final ReactApplicationContext reactContext;

  public RNUpgradeModule(ReactApplicationContext reactContext) {
    super(reactContext);
    this.reactContext = reactContext;
  }

  @Override
  public String getName() {
    return "RNUpgrade";
  }

  public static void init(Application application, String cerPath) {
    XUpdate.get()
            .debug(true)
            .isWifiOnly(true)                                               //默认设置只在wifi下检查版本更新
            .isGet(true)                                                    //默认设置使用get请求检查版本
            .isAutoMode(false)                                              //默认设置非自动模式，可根据具体使用配置
            .param("versionCode", UpdateUtils.getVersionCode(application))         //设置默认公共请求参数
            .param("appKey", application.getPackageName())
            .setOnUpdateFailureListener(new OnUpdateFailureListener() {     //设置版本更新出错的监听
              @Override
              public void onFailure(UpdateError error) {
                if (error.getCode() != CHECK_NO_NEW_VERSION) {          //对不同错误进行处理
                  Log.e("RNUpgradeModule", error.getDetailMsg());
                }
              }
            })
            .supportSilentInstall(true)                                     //设置是否支持静默安装，默认是true
            .setIUpdateHttpService(new OKHttpUpdateHttpService(application, cerPath))           //这个必须设置！实现网络请求功能。
            .init(application);                                                    //这个必须初始化
  }

  @ReactMethod
  public void checkUpgrade(String updateUrl, Boolean show) {
      XUpdate.newBuild(getCurrentActivity())
              .updateUrl(updateUrl)
              .themeColor(reactContext.getResources().getColor(R.color.update_theme_color))
              .topResId(R.mipmap.bg_update_top)
              .updateParser(new CustomUpdateParser(reactContext, show))
              .update();
  }
}