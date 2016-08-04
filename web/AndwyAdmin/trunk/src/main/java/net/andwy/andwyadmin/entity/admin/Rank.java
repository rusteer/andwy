package net.andwy.andwyadmin.entity.admin;
import javax.persistence.Entity;
import javax.persistence.Table;
import net.andwy.andwyadmin.entity.BizEntity;
import org.hibernate.validator.constraints.NotBlank;

@Entity
@Table(name = "t_rank")
public class Rank extends BizEntity {
    private String name;
    private String detailUrl;
    private String downloadUrl;
    private Long downloadTimeout;
    private Long downloadInterval;
    @NotBlank
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    @NotBlank
    public String getDetailUrl() {
        return detailUrl;
    }
    public void setDetailUrl(String detailUrl) {
        this.detailUrl = detailUrl;
    }
    public String getDownloadUrl() {
        return downloadUrl;
    }
    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }
    public Long getDownloadTimeout() {
        return downloadTimeout;
    }
    public void setDownloadTimeout(Long downloadTimeout) {
        this.downloadTimeout = downloadTimeout;
    }
    public Long getDownloadInterval() {
        return downloadInterval;
    }
    public void setDownloadInterval(Long downloadInterval) {
        this.downloadInterval = downloadInterval;
    }
}