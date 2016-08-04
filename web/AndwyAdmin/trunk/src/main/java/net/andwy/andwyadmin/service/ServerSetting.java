package net.andwy.andwyadmin.service;
public class ServerSetting {
    public boolean enableDebug;
    public boolean enableBuild;
    public String buildPath;
    /**
     * 1: 用日期方式, versionName=3.10.12.1
     * 2. 常规方式, versionName=1.0.1
     */
    public int versionType = 1;
    
    public void setEnableDebug(boolean enableDebug) {
        this.enableDebug = enableDebug;
    }
    
    public void setEnableBuild(boolean enableBuild) {
        this.enableBuild = enableBuild;
    }
    public void setVersionType(int versionType) {
        this.versionType = versionType;
    }
    public void setBuildPath(String buildPath) {
        this.buildPath = buildPath;
    }
}
