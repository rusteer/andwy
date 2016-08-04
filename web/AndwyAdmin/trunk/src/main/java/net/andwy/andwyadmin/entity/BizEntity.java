package net.andwy.andwyadmin.entity;
import java.util.Date;
import javax.persistence.MappedSuperclass;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.validator.constraints.NotBlank;
import com.fasterxml.jackson.annotation.JsonFormat;

@MappedSuperclass
public abstract class BizEntity extends IdEntity {
    final static String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    final static String TIME_ZONE = "GMT+08:00";
    private String status;
    private Date createTime;
    private Date updateTime;
    @JsonFormat(pattern = DATE_FORMAT, timezone = TIME_ZONE)
    public Date getCreateTime() {
        return createTime;
    }
    @NotBlank
    public String getStatus() {
        return status;
    }
    @JsonFormat(pattern = DATE_FORMAT, timezone = TIME_ZONE)
    public Date getUpdateTime() {
        return updateTime;
    }
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
