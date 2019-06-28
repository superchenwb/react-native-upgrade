package com.zy.upgrade.entity;


public class CustomResult {
    // 版本id
    private Integer id;

    // 0:无版本更新， 1:有版本更新，不需要强制升级，2:有版本更新，需要强制升级
    private Integer updatestatus;

    // 版本号[根据版本号来判别是否需要升级]
    private Integer versioncode;

    // 版本名称[用于展示的版本名]
    private String versionname;

    // 更新时间
    private String uploadtime;

    // apk大小
    private Integer apksize;

    //
    private String appkey;

    private String modifycontent;

    private String downloadurl;

    private String apkmd5;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    // 后端接口返回状态
    private Integer status;

    // 后端接口返回状态信息
    private String info;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUpdatestatus() {
        return updatestatus;
    }

    public void setUpdatestatus(Integer updatestatus) {
        this.updatestatus = updatestatus;
    }

    public Integer getVersioncode() {
        return versioncode;
    }

    public void setVersioncode(Integer versioncode) {
        this.versioncode = versioncode;
    }

    public String getVersionname() {
        return versionname;
    }

    public void setVersionname(String versionname) {
        this.versionname = versionname;
    }

    public String getUploadtime() {
        return uploadtime;
    }

    public void setUploadtime(String uploadtime) {
        this.uploadtime = uploadtime;
    }

    public Integer getApksize() {
        return apksize;
    }

    public void setApksize(Integer apksize) {
        this.apksize = apksize;
    }

    public String getAppkey() {
        return appkey;
    }

    public void setAppkey(String appkey) {
        this.appkey = appkey;
    }

    public String getModifycontent() {
        return modifycontent;
    }

    public void setModifycontent(String modifycontent) {
        this.modifycontent = modifycontent;
    }

    public String getDownloadurl() {
        return downloadurl;
    }

    public void setDownloadurl(String downloadurl) {
        this.downloadurl = downloadurl;
    }

    public String getApkmd5() {
        return apkmd5;
    }

    public void setApkmd5(String apkmd5) {
        this.apkmd5 = apkmd5;
    }
}

