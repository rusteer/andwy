package net.andwy.andwyadmin.entity.client;
import javax.persistence.Entity;
import javax.persistence.Table;
import net.andwy.andwyadmin.entity.BizEntity;

@Entity
@Table(name = "t_config")
public class Config extends BizEntity {
    private String name;
    private Long level; //1:Global,2:Market,3:Product,4:Package
    private String host1;
    private String host2;
    //Push related config;
    private String enablePush = "N";
    private Long devicePushInterval = Long.MAX_VALUE;
    private Long appPushInterval = Long.MAX_VALUE;//Can push the same app more than one times for same device;
    private Long pushRequestInterval = 3600 * 24L;
    private Long pushStartHour;
    private Long pushEndHour;
    /**
     * 1:Show detail,download apk on command; 
     * 2:show detail, download apk auto; 
     * 3:Don't show detail, click to install; 
     * 4: Directly install
     */
    private Long pushType;
    /**
     * 1. 按照热度的概率来推送
     * 2. 优先推送热度高的,然后热度低的
     */
    private int pushStrategy;
    //Recommend related config
    private String enableRecommend = "N";
    private Long recommendType;//1:one list,2:app&game,3:multiple types
    private Long recommendRequestInterval = 3600 * 24L;
    private Long recommendExpireTime = 3600L;
    public Config() {}
    public Config(Long id) {
        this.id = id;
    }
    public String getEnablePush() {
        return enablePush;
    }
    public void setEnablePush(String enablePush) {
        this.enablePush = enablePush;
    }
    public String getEnableRecommend() {
        return enableRecommend;
    }
    public void setEnableRecommend(String enableRecommend) {
        this.enableRecommend = enableRecommend;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Long getLevel() {
        return level;
    }
    public void setLevel(Long level) {
        this.level = level;
    }
    public Long getAppPushInterval() {
        return appPushInterval;
    }
    public void setAppPushInterval(Long appPushInterval) {
        this.appPushInterval = appPushInterval;
    }
    public Long getDevicePushInterval() {
        return devicePushInterval;
    }
    public void setDevicePushInterval(Long devicePushInterval) {
        this.devicePushInterval = devicePushInterval;
    }
    public Long getRecommendType() {
        return recommendType;
    }
    public void setRecommendType(Long recommendType) {
        this.recommendType = recommendType;
    }
    
    public Long getPushRequestInterval() {
        return pushRequestInterval;
    }
    public void setPushRequestInterval(Long pushRequestInterval) {
        this.pushRequestInterval = pushRequestInterval;
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
    public Long getRecommendRequestInterval() {
        return recommendRequestInterval;
    }
    public void setRecommendRequestInterval(Long recommendRequestInterval) {
        this.recommendRequestInterval = recommendRequestInterval;
    }
    public Long getRecommendExpireTime() {
        return recommendExpireTime;
    }
    public void setRecommendExpireTime(Long recommendExpireTime) {
        this.recommendExpireTime = recommendExpireTime;
    }
    public Long getPushType() {
        return pushType;
    }
    public void setPushType(Long pushType) {
        this.pushType = pushType;
    }
    public int getPushStrategy() {
        return pushStrategy;
    }
    public void setPushStrategy(int pushStrategy) {
        this.pushStrategy = pushStrategy;
    }
    public String getHost1() {
        return host1;
    }
    public void setHost1(String host1) {
        this.host1 = host1;
    }
    public String getHost2() {
        return host2;
    }
    public void setHost2(String host2) {
        this.host2 = host2;
    }
}