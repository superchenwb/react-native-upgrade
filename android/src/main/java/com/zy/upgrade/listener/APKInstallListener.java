package com.zy.upgrade.listener;

import android.util.Log;

import com.xuexiang.xupdate.listener.impl.DefaultInstallListener;
import com.xuexiang.xupdate.proxy.IUpdateHttpService;
import com.xuexiang.xupdate.utils.UpdateUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * apk安装监听
 *
 * @author xuexiang
 * @since 2018/7/1 下午11:58
 */
public class APKInstallListener extends DefaultInstallListener {

    IUpdateHttpService httpService;
    String url;

    public APKInstallListener(IUpdateHttpService httpService, String url) {
        this.httpService = httpService;
        this.url = url;
    }

    @Override
    public void onInstallApkSuccess() {
        Log.d("APKInstallListener", "安装成功");
//        Map<String, Object> params = new HashMap<>();
//        params.put("versioncode", UpdateUtils.getVersionCode(application))
        httpService.asyncGet(url, null, null);
    }
}
