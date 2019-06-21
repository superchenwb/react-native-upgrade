package com.zy.upgrade.custom;

import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.facebook.react.bridge.ReactApplicationContext;
import com.xuexiang.xupdate.XUpdate;
import com.xuexiang.xupdate.entity.CheckVersionResult;
import com.xuexiang.xupdate.entity.UpdateEntity;
import com.xuexiang.xupdate.proxy.IUpdateParser;
import com.xuexiang.xupdate.utils.UpdateUtils;
import com.zy.upgrade.entity.CustomResult;

/**
 * 自定义更新解析器
 *
 * @author chenwenbin
 * @since 2019/3/13
 */
public class CustomUpdateParser implements IUpdateParser {
    private boolean showToast;
    private final ReactApplicationContext reactContext;
    public CustomUpdateParser(ReactApplicationContext reactContext) {
        this.showToast = false;
        this.reactContext = reactContext;
    }

    public CustomUpdateParser(ReactApplicationContext reactContext, boolean showToast) {
        this.showToast = showToast;
        this.reactContext = reactContext;
    }

    @Override
    public UpdateEntity parseJson(String json) throws Exception {
        CustomResult customResult = JSON.parseObject(json, CustomResult.class);
        if (customResult != null) {
            CustomResult result = doLocalCompare(customResult);
            UpdateEntity updateEntity = new UpdateEntity();
            if (result.getUpdatestatus() == CheckVersionResult.NO_NEW_VERSION) {
                updateEntity.setHasUpdate(false);
                if(showToast) {
                    Toast tost = Toast.makeText(reactContext, null, Toast.LENGTH_SHORT);
                    tost.setText("当前已是最新版本");
                }
            } else {
                if (result.getUpdatestatus() == CheckVersionResult.HAVE_NEW_VERSION_FORCED_UPLOAD) {
                    updateEntity.setForce(true);
                }
                updateEntity.setHasUpdate(true)
                        .setUpdateContent(result.getModifycontent().replaceAll("\\\\r\\\\n", "\r\n"))//兼容一下
                        .setVersionCode(result.getVersioncode())
                        .setVersionName(result.getVersionname())
                        .setDownloadUrl(result.getDownloadurl())
                        .setSize(result.getApksize())
                        .setMd5(result.getApkmd5());
            }
            return updateEntity;
        }
        return null;
    }

    /**
     * 进行本地版本判断[防止服务端出错，本来是不需要更新，但是服务端返回是需要更新]
     *
     * @param result
     * @return
     */
    private CustomResult doLocalCompare(CustomResult result) {
        if (result.getUpdatestatus() != CheckVersionResult.NO_NEW_VERSION) { //服务端返回需要更新
            int lastVersionCode = result.getVersioncode();
            if (lastVersionCode <= UpdateUtils.getVersionCode(XUpdate.getContext())) { //最新版本小于等于现在的版本，不需要更新
                result.setUpdatestatus(CheckVersionResult.NO_NEW_VERSION);
            }
        }
        return result;
    }

    /**
     *  返回更新apk地址
     * @return baseUrl
     */
    public static String getVersionCheckUrl(String url) {
        return url;
    }
}

