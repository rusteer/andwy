package net.andwy.andwyadmin.service;
public class Constants {
    public static final boolean OS_WIN = System.getProperty("os.name").toLowerCase().contains("win");
    public static final String WORKSPACE_BASE = OS_WIN ? "E:/workspace/android" : "/workspace/android";
    public static final String RELEASE_BASE = WORKSPACE_BASE + "/package";
    public static final String RESOURCES_BASE = WORKSPACE_BASE + "/resources";
    public static final String BASE = WORKSPACE_BASE + "/client";
    public static final String MAVEN_BASE = OS_WIN ? "E:/data/mavenRepository" : "/home/hike/.m2/repository";
    public final static String svnCmd = OS_WIN ? "svn" : "/usr/bin/svn";
    public final static String mvnCmd = OS_WIN ? "mvn.bat" : "mvn";
    public final static String apktoolCmd = OS_WIN ? "apktool.bat" : "apktool";
    public final static String jarsignerCmd = OS_WIN ? "jarsigner.exe" : "jarsigner";
}
