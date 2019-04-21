package com.example.pc.lifeassistant.bean;

import com.avos.avoscloud.AVClassName;
import com.avos.avoscloud.AVObject;

/**
 * Created by pc on 2019/4/14.
 */
@AVClassName("APKVersion")
public class ApkVersionInfo extends AVObject {
    public String version;
    public String url;
    public String versionInformation;

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getVersionInformation() {
        return versionInformation;
    }

    public void setVersionInformation(String versionInformation) {
        this.versionInformation = versionInformation;
    }
}
