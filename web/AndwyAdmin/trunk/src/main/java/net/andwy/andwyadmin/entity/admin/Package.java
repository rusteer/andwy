package net.andwy.andwyadmin.entity.admin;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import net.andwy.andwyadmin.entity.BizEntity;
import net.andwy.andwyadmin.entity.client.Config;

@Entity
@Table(name = "t_package")
public class Package extends BizEntity {
    private Product product;
    private MarketAccount marketAccount;
    private String packageName;
    private String packageProductName;// 每一个package的产品的名字可能不一致,如果同产品名称一致,则置为空
    private String versionDescription;
    // Build attributes
    private String injectAds;
    private String needBuild;
    private Long buildCount = 0L;
    private String buildStatus;
    /**
     * 是否被广告市场检测出广告
     */
    private String adsDetected;
    // Release attributes
    private String submitDate;
    private String publishDate;
    private String needPublish;
    private Long publishTryCount = 0L;
    private Long publishSuccessCount = 0L;
    private String publishStatus;// publish success or failure
    private String publishUrl;
    private String downloadUrl;
    private String failureDescription; // failure reason here
    private String marketVersion;
    private Config config;
    private String uid;
    private Integer publishingVersionCode;
    @ManyToOne
    @JoinColumn(name = "config_id")
    public Config getConfig() {
        return config;
    }
    public void setConfig(Config config) {
        this.config = config;
    }
    public String getBuildStatus() {
        return buildStatus;
    }
    public String getFailureDescription() {
        return failureDescription;
    }
    @ManyToOne
    @JoinColumn(name = "market_account_id")
    public MarketAccount getMarketAccount() {
        return marketAccount;
    }
    public String getPackageName() {
        return packageName;
    }
    @ManyToOne
    @JoinColumn(name = "product_id")
    public Product getProduct() {
        return product;
    }
    public String getVersionDescription() {
        return versionDescription;
    }
    public void setBuildStatus(String buildStatus) {
        this.buildStatus = buildStatus;
    }
    public void setFailureDescription(String packageFailureDescription) {
        failureDescription = packageFailureDescription;
    }
    public void setMarketAccount(MarketAccount marketAccount) {
        this.marketAccount = marketAccount;
    }
    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }
    public void setProduct(Product product) {
        this.product = product;
    }
    public void setVersionDescription(String versionDescription) {
        this.versionDescription = versionDescription;
    }
    public String getPublishStatus() {
        return publishStatus;
    }
    public void setPublishStatus(String publishStatus) {
        this.publishStatus = publishStatus;
    }
    public String getNeedPublish() {
        return needPublish;
    }
    public void setNeedPublish(String needPublish) {
        this.needPublish = needPublish;
    }
    public String getNeedBuild() {
        return needBuild;
    }
    public void setNeedBuild(String needBuild) {
        this.needBuild = needBuild;
    }
    public Long getBuildCount() {
        return buildCount;
    }
    public void setBuildCount(Long buildCount) {
        this.buildCount = buildCount;
    }
    public Long getPublishTryCount() {
        return publishTryCount;
    }
    public void setPublishTryCount(Long publishTryCount) {
        this.publishTryCount = publishTryCount;
    }
    public Long getPublishSuccessCount() {
        return publishSuccessCount;
    }
    public void setPublishSuccessCount(Long publishSuccessCount) {
        this.publishSuccessCount = publishSuccessCount;
    }
    public String getPublishUrl() {
        return publishUrl;
    }
    public void setPublishUrl(String publishUrl) {
        this.publishUrl = publishUrl;
    }
    public String getMarketVersion() {
        return marketVersion;
    }
    public void setMarketVersion(String marketVersion) {
        this.marketVersion = marketVersion;
    }
    public String getSubmitDate() {
        return submitDate;
    }
    public void setSubmitDate(String submitDate) {
        this.submitDate = submitDate;
    }
    public String getPublishDate() {
        return publishDate;
    }
    public void setPublishDate(String publishDate) {
        this.publishDate = publishDate;
    }
    public String getUid() {
        return uid;
    }
    public void setUid(String uid) {
        this.uid = uid;
    }
    public String getPackageProductName() {
        return packageProductName;
    }
    public void setPackageProductName(String packageProductName) {
        this.packageProductName = packageProductName;
    }
    public Integer getPublishingVersionCode() {
        return publishingVersionCode;
    }
    public void setPublishingVersionCode(Integer publishingVersionCode) {
        this.publishingVersionCode = publishingVersionCode;
    }
    public String getInjectAds() {
        return injectAds;
    }
    public void setInjectAds(String injectAds) {
        this.injectAds = injectAds;
    }
    public String getAdsDetected() {
        return adsDetected;
    }
    public void setAdsDetected(String adsDetected) {
        this.adsDetected = adsDetected;
    }
    public String getDownloadUrl() {
        return downloadUrl;
    }
    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }
}
