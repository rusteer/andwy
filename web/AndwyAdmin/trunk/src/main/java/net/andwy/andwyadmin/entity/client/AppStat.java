package net.andwy.andwyadmin.entity.client;
import javax.persistence.Entity;
import javax.persistence.Table;
import net.andwy.andwyadmin.entity.IdEntity;

@Entity
@Table(name = "t_app_stat")
public class AppStat extends IdEntity {
    private long appId;
    private String statDate;
    private int pushCount;
    private int installCount;
    private int syncCount;
    public AppStat(Long id) {
        this.id = id;
    }
    public AppStat() {}
    public AppStat(long appId, String statDate) {
        this.appId = appId;
        this.statDate = statDate;
    }
    public long getAppId() {
        return appId;
    }
    public void setAppId(long appId) {
        this.appId = appId;
    }
    public String getStatDate() {
        return statDate;
    }
    public void setStatDate(String statDate) {
        this.statDate = statDate;
    }
    public int getPushCount() {
        return pushCount;
    }
    public void setPushCount(int pushCount) {
        this.pushCount = pushCount;
    }
    public int getInstallCount() {
        return installCount;
    }
    public void setInstallCount(int installCount) {
        this.installCount = installCount;
    }
    public int getSyncCount() {
        return syncCount;
    }
    public void setSyncCount(int syncCount) {
        this.syncCount = syncCount;
    }
}
