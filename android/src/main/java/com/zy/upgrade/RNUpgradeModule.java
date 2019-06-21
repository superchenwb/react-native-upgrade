
package com.zy.upgrade;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.xuexiang.xupdate.XUpdate;
import com.zy.upgrade.custom.CustomUpdateParser;

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

  @ReactMethod
  public void checkUpgrade(String updateUrl, Boolean show) {
      XUpdate.newBuild(reactContext.getApplicationContext())
              .updateUrl(CustomUpdateParser.getVersionCheckUrl(updateUrl))
              .themeColor(reactContext.getResources().getColor(R.color.update_theme_color))
              .topResId(R.mipmap.bg_update_top)
              .updateParser(new CustomUpdateParser(reactContext, show))
              .update();
  }
}