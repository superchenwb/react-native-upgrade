
package com.zy.upgrade;

import android.util.Log;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.xuexiang.xupdate.XUpdate;
import com.xuexiang.xupdate.entity.UpdateError;
import com.xuexiang.xupdate.listener.OnUpdateFailureListener;
import com.xuexiang.xupdate.proxy.IUpdateHttpService;
import com.xuexiang.xupdate.utils.UpdateUtils;
import com.zy.upgrade.custom.CustomUpdateParser;
import com.zy.upgrade.http.OKHttpUpdateHttpService;
import com.zy.upgrade.listener.APKInstallListener;

import static com.xuexiang.xupdate.entity.UpdateError.ERROR.CHECK_NO_NEW_VERSION;

public class RNUpgradeModule extends ReactContextBaseJavaModule {

    private final ReactApplicationContext reactContext;

    IUpdateHttpService mIUpdateHttpService;

  public RNUpgradeModule(ReactApplicationContext reactContext) {
    super(reactContext);
    this.reactContext = reactContext;
  }

  @Override
  public String getName() {
    return "RNUpgrade";
  }

  @ReactMethod
  public void init(String url, String token, String cerPath) {

    if(cerPath == null) {
      cerPath = "cers/cert.cer";
    }
    mIUpdateHttpService = new OKHttpUpdateHttpService(reactContext, token, cerPath);
    int versionCode = UpdateUtils.getVersionCode(reactContext);
    Log.d("RNUpgradeModule init", url + "===" + token);
    XUpdate.get()
            .debug(BuildConfig.DEBUG)
            .isWifiOnly(false)                                               //默认设置只在wifi下检查版本更新
            .isGet(true)                                                    //默认设置使用get请求检查版本
            .isAutoMode(false)                                              //默认设置非自动模式，可根据具体使用配置
            .param("versionCode", versionCode)         //设置默认公共请求参数
            .param("appKey", reactContext.getPackageName())
            .setOnUpdateFailureListener(new OnUpdateFailureListener() {     //设置版本更新出错的监听
              @Override
              public void onFailure(UpdateError error) {
                if (error.getCode() != CHECK_NO_NEW_VERSION) {          //对不同错误进行处理
                  Log.e("RNUpgradeModule", error.getDetailMsg());
                }
              }
            })
            .supportSilentInstall(true)                                     //设置是否支持静默安装，默认是true
            .setIUpdateHttpService(mIUpdateHttpService)           //这个必须设置！实现网络请求功能。
            .setOnInstallListener(new APKInstallListener(mIUpdateHttpService, url))
            .init(reactContext);                                                    //这个必须初始化
  }

  @ReactMethod
  public void checkUpgrade(String updateUrl, Boolean show) {
      Log.d("checkUpgrade", updateUrl + "===" + show);
      XUpdate.newBuild(getCurrentActivity())
              .updateUrl(updateUrl)
              .themeColor(reactContext.getResources().getColor(R.color.update_theme_color))
              .topResId(R.mipmap.bg_update_top)
              .updateParser(new CustomUpdateParser(reactContext, show))
              .update();
  }
}