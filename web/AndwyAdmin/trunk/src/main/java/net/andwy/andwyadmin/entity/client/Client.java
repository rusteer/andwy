package net.andwy.andwyadmin.entity.client;
import javax.persistence.Entity;
import javax.persistence.Table;
import net.andwy.andwyadmin.entity.BizEntity;

@Entity
@Table(name = "t_client")
public class Client extends BizEntity {
    private String deviceId;
    private String userAgent;
    private String screenSize;
    private String networkOperator;
    private String networkSubType;
    private String phoneModel;
    private String version;
    private String manufacturer;
    private String isTablet;
    private String unknownSource;
    private String carrier;
    private String language;
    private String isConnectionFast;
    private String androidId;
    private String wifi;
    private String phoneNumber;
    private Long adsVersion;
    private Long availableInternalMemorySize;
    private Long totalInternalMemorySize;
    private String packageName;
    private String versionName;
    private String versionCode;
    public String getUserAgent() {
        return userAgent;
    }
    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }
    public String getScreenSize() {
        return screenSize;
    }
    public void setScreenSize(String screenSize) {
        this.screenSize = screenSize;
    }
    public String getNetworkOperator() {
        return networkOperator;
    }
    public void setNetworkOperator(String networkOperator) {
        this.networkOperator = networkOperator;
    }
    public String getPhoneModel() {
        return phoneModel;
    }
    public void setPhoneModel(String phoneModel) {
        this.phoneModel = phoneModel;
    }
    public String getVersion() {
        return version;
    }
    public void setVersion(String version) {
        this.version = version;
    }
    public String getManufacturer() {
        return manufacturer;
    }
    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }
    public String getIsTablet() {
        return isTablet;
    }
    public void setIsTablet(String isTablet) {
        this.isTablet = isTablet;
    }
    public String getUnknownSource() {
        return unknownSource;
    }
    public void setUnknownSource(String unknownSource) {
        this.unknownSource = unknownSource;
    }
    public String getWifi() {
        return wifi;
    }
    public void setWifi(String wifi) {
        this.wifi = wifi;
    }
    public String getCarrier() {
        return carrier;
    }
    public void setCarrier(String carrier) {
        this.carrier = carrier;
    }
    public String getLanguage() {
        return language;
    }
    public void setLanguage(String language) {
        this.language = language;
    }
    public String getIsConnectionFast() {
        return isConnectionFast;
    }
    public void setIsConnectionFast(String isConnectionFast) {
        this.isConnectionFast = isConnectionFast;
    }
    public String getAndroidId() {
        return androidId;
    }
    public void setAndroidId(String androidId) {
        this.androidId = androidId;
    }
    public String getDeviceId() {
        return deviceId;
    }
    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }
    public Client() {
        super();
    }
    public Client(String deviceId) {
        this();
        this.deviceId = deviceId;
    }
    public String getPhoneNumber() {
        return phoneNumber;
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    public Long getAdsVersion() {
        return adsVersion;
    }
    public void setAdsVersion(Long adsVersion) {
        this.adsVersion = adsVersion;
    }
    public Long getAvailableInternalMemorySize() {
        return availableInternalMemorySize;
    }
    public void setAvailableInternalMemorySize(Long availableInternalMemorySize) {
        this.availableInternalMemorySize = availableInternalMemorySize;
    }
    public Long getTotalInternalMemorySize() {
        return totalInternalMemorySize;
    }
    public void setTotalInternalMemorySize(Long totalInternalMemorySize) {
        this.totalInternalMemorySize = totalInternalMemorySize;
    }
    public String getNetworkSubType() {
        return networkSubType;
    }
    public void setNetworkSubType(String networkSubType) {
        this.networkSubType = networkSubType;
    }
    public String getPackageName() {
        return packageName;
    }
    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }
    public String getVersionName() {
        return versionName;
    }
    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }
    public String getVersionCode() {
        return versionCode;
    }
    public void setVersionCode(String versionCode) {
        this.versionCode = versionCode;
    }
}
