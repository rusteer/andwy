package net.andwy.andwyadmin.entity.client;
import javax.persistence.Entity;
import javax.persistence.Table;
import net.andwy.andwyadmin.entity.IdEntity;

@Entity
@Table(name = "t_package_stat")
public class PackageStat extends IdEntity {
    private long packageId;
    private String statDate;
    //非私有统计数据
    private int pushCount;
    private int installCount;
    private float installEarning;
    //全部的统计数据
    private int allPushCount;
    private int allInstallCount;
    private float allInstallEarning;
    //
    public PackageStat(Long id) {
        this.id = id;
    }
    public PackageStat() {}
    public PackageStat(long packageId, String statDate) {
        setPackageId(packageId);
        this.statDate = statDate;
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
    public long getPackageId() {
        return packageId;
    }
    public void setPackageId(long packageId) {
        this.packageId = packageId;
    }
    public float getInstallEarning() {
        return installEarning;
    }
    public void setInstallEarning(float installEarning) {
        this.installEarning = installEarning;
    }
    public int getAllPushCount() {
        return allPushCount;
    }
    public void setAllPushCount(int allPushCount) {
        this.allPushCount = allPushCount;
    }
    public int getAllInstallCount() {
        return allInstallCount;
    }
    public void setAllInstallCount(int allInstallCount) {
        this.allInstallCount = allInstallCount;
    }
    public float getAllInstallEarning() {
        return allInstallEarning;
    }
    public void setAllInstallEarning(float allInstallEarning) {
        this.allInstallEarning = allInstallEarning;
    }
}
