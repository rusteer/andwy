package net.andwy.andwyadmin.bean;
public class Stat {
    private Long entityId;
    private Integer pushCount = 0;
    private Integer installCount = 0;
    private String statDate;
    private Float installEarning = 0.0f;
    private Bean bean;
    public Stat() {}
    public Stat(String statDate, Long entityId) {
        this.statDate = statDate;
        this.entityId = entityId;
    }
    public Long getEntityId() {
        return entityId;
    }
    public void setEntityId(Long entityId) {
        this.entityId = entityId;
    }
    public String getStatDate() {
        return statDate;
    }
    public void setStatDate(String statDate) {
        this.statDate = statDate;
    }
    public Integer getPushCount() {
        return pushCount;
    }
    public void setPushCount(Integer pushCount) {
        this.pushCount = pushCount;
    }
    public Integer getInstallCount() {
        return installCount;
    }
    public void setInstallCount(Integer installCount) {
        this.installCount = installCount;
    }
    public Float getInstallEarning() {
        return installEarning;
    }
    public void setInstallEarning(Float installEarning) {
        this.installEarning = installEarning;
    }
    public Bean getBean() {
        return bean;
    }
    public void setBean(Bean bean) {
        this.bean = bean;
    }
}
