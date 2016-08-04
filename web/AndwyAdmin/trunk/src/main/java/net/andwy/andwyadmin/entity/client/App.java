package net.andwy.andwyadmin.entity.client;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "t_app")
public final class App extends UUIDEntity {
    private String name;
    private String hint;
    private String description;
    private String size;
    private String pkgName;
    private String pkgVersionName;
    private String pkgVersionCode;
    private Long type;
    private Long hot = 0L;
    private String push; //Y||N
    private String pushInterval;//"20,21,22,23"
    private Long pushStartHour;
    private Long pushEndHour;
    private String iconUrl;
    private String apkUrl;
    private String screen1Url;
    private String screen2Url;
    private String screen3Url;
    private String screen4Url;
    private Category category;
    private String advertiser;
    private String comments;
    private String sharingType;
    private Float price;
    private int dailyInstallLimit;//每天安装数限制
    private boolean privateApp;//私有APP
    public App() {}
    public App(Long id) {
        this.id = id;
    }
    public String getSize() {
        return size;
    }
    public void setSize(String size) {
        this.size = size;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getPkgName() {
        return pkgName;
    }
    public void setPkgName(String pkgName) {
        this.pkgName = pkgName;
    }
    @Column(name = "pkg_version_name")
    public String getPkgVersionName() {
        return pkgVersionName;
    }
    public void setPkgVersionName(String pkgVersionName) {
        this.pkgVersionName = pkgVersionName;
    }
    public String getPkgVersionCode() {
        return pkgVersionCode;
    }
    @Column(name = "pkg_version_code")
    public void setPkgVersionCode(String pkgVersionCode) {
        this.pkgVersionCode = pkgVersionCode;
    }
    public Long getType() {
        return type;
    }
    public void setType(Long type) {
        this.type = type;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public Long getHot() {
        return hot;
    }
    public void setHot(Long hot) {
        this.hot = hot;
    }
    public String getPush() {
        return push;
    }
    public void setPush(String push) {
        this.push = push;
    }
    @ManyToOne
    @JoinColumn(name = "category_id")
    public Category getCategory() {
        return category;
    }
    public void setCategory(Category category) {
        this.category = category;
    }
    public String getHint() {
        return hint;
    }
    public void setHint(String hint) {
        this.hint = hint;
    }
    public String getSharingType() {
        return sharingType;
    }
    public void setSharingType(String sharingType) {
        this.sharingType = sharingType;
    }
    public Float getPrice() {
        return price;
    }
    public void setPrice(Float price) {
        this.price = price;
    }
    public String getAdvertiser() {
        return advertiser;
    }
    public void setAdvertiser(String advertiser) {
        this.advertiser = advertiser;
    }
    public String getIconUrl() {
        return iconUrl;
    }
    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }
    public String getApkUrl() {
        return apkUrl;
    }
    public void setApkUrl(String apkUrl) {
        this.apkUrl = apkUrl;
    }
    @Column(name = "screen1_url")
    public String getScreen1Url() {
        return screen1Url;
    }
    public void setScreen1Url(String screen1Url) {
        this.screen1Url = screen1Url;
    }
    @Column(name = "screen2_url")
    public String getScreen2Url() {
        return screen2Url;
    }
    public void setScreen2Url(String screen2Url) {
        this.screen2Url = screen2Url;
    }
    @Column(name = "screen3_url")
    public String getScreen3Url() {
        return screen3Url;
    }
    public void setScreen3Url(String screen3Url) {
        this.screen3Url = screen3Url;
    }
    @Column(name = "screen4_url")
    public String getScreen4Url() {
        return screen4Url;
    }
    public void setScreen4Url(String screen4Url) {
        this.screen4Url = screen4Url;
    }
    public String getPushInterval() {
        return pushInterval;
    }
    public void setPushInterval(String pushInterval) {
        this.pushInterval = pushInterval;
    }
    public Long getPushStartHour() {
        return pushStartHour;
    }
    public void setPushStartHour(Long pushStartHour) {
        this.pushStartHour = pushStartHour;
    }
    public Long getPushEndHour() {
        return pushEndHour;
    }
    public void setPushEndHour(Long pushEndHour) {
        this.pushEndHour = pushEndHour;
    }
    public String getComments() {
        return comments;
    }
    public void setComments(String comments) {
        this.comments = comments;
    }
    public int getDailyInstallLimit() {
        return dailyInstallLimit;
    }
    public void setDailyInstallLimit(int dailyInstallLimit) {
        this.dailyInstallLimit = dailyInstallLimit;
    }
    public boolean isPrivateApp() {
        return privateApp;
    }
    public void setPrivateApp(boolean privateApp) {
        this.privateApp = privateApp;
    }
}
