package net.andwy.andwyadmin.entity.admin;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import net.andwy.andwyadmin.entity.BizEntity;
import net.andwy.andwyadmin.entity.client.Config;
import org.hibernate.validator.constraints.NotBlank;

@Entity
@Table(name = "t_product")
public class Product extends BizEntity {
    private String projectName;
    private String productName;
    private String versionDate;
    private Long versionCount;
    private Long batchId;
    private Long publishInterval;
    private String umengId;
    private String category1;
    private String category2;
    private String category3;
    private String language;
    private String feeType;
    private String description;
    private String basePackage;
    private String priority;
    private Config config;
    private String projectType;
    private String iconResource;
    private String appNameResource;
    private String haveThirdPartyAds;
    public String getHaveThirdPartyAds() {
        return haveThirdPartyAds;
    }
    public void setHaveThirdPartyAds(String haveThirdPartyAds) {
        this.haveThirdPartyAds = haveThirdPartyAds;
    }
    public String getIconResource() {
        return iconResource;
    }
    public void setIconResource(String iconResource) {
        this.iconResource = iconResource;
    }
    public String getAppNameResource() {
        return appNameResource;
    }
    public void setAppNameResource(String appNameResource) {
        this.appNameResource = appNameResource;
    }
    @ManyToOne
    @JoinColumn(name = "config_id")
    public Config getConfig() {
        return config;
    }
    public void setConfig(Config config) {
        this.config = config;
    }
    public Product() {}
    public Product(Long id) {
        this.id = id;
    }
    @NotBlank
    public String getBasePackage() {
        return basePackage;
    }
    public Long getBatchId() {
        return batchId;
    }
    public String getCategory1() {
        return category1;
    }
    public String getCategory2() {
        return category2;
    }
    public String getCategory3() {
        return category3;
    }
    @NotBlank
    public String getDescription() {
        return description;
    }
    @NotBlank
    public String getFeeType() {
        return feeType;
    }
    @NotBlank
    public String getLanguage() {
        return language;
    }
    @NotBlank
    public String getProductName() {
        return productName;
    }
    @NotBlank
    public String getProjectName() {
        return projectName;
    }
    public Long getPublishInterval() {
        return publishInterval;
    }
    public Long getVersionCount() {
        return versionCount;
    }
    @NotBlank
    public String getVersionDate() {
        return versionDate;
    }
    public void setBasePackage(String basePackage) {
        this.basePackage = basePackage;
    }
    public void setBatchId(Long batchId) {
        this.batchId = batchId;
    }
    @NotBlank
    public void setCategory1(String category1) {
        this.category1 = category1;
    }
    public void setCategory2(String category2) {
        this.category2 = category2;
    }
    public void setCategory3(String category3) {
        this.category3 = category3;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public void setFeeType(String feeType) {
        this.feeType = feeType;
    }
    public void setLanguage(String language) {
        this.language = language;
    }
    public void setProductName(String productName) {
        this.productName = productName;
    }
    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }
    public void setPublishInterval(Long publishInterval) {
        this.publishInterval = publishInterval;
    }
    public void setVersionCount(Long versionCount) {
        this.versionCount = versionCount;
    }
    public void setVersionDate(String versionDate) {
        this.versionDate = versionDate;
    }
    public String getPriority() {
        return priority;
    }
    public void setPriority(String priority) {
        this.priority = priority;
    }
    public String getUmengId() {
        return umengId;
    }
    public void setUmengId(String umengId) {
        this.umengId = umengId;
    }
    public String getProjectType() {
        return projectType;
    }
    public void setProjectType(String projectType) {
        this.projectType = projectType;
    }
}