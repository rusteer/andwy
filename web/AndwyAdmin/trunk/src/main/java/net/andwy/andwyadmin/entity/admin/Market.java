package net.andwy.andwyadmin.entity.admin;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import net.andwy.andwyadmin.entity.BizEntity;
import net.andwy.andwyadmin.entity.client.Config;
import org.hibernate.validator.constraints.NotBlank;

@Entity
@Table(name = "t_market")
public class Market extends BizEntity {
    private String name;
    private String url;
    private String shortName;
    private Config config;
    @ManyToOne
    @JoinColumn(name = "config_id")
    public Config getConfig() {
        return config;
    }
    public void setConfig(Config config) {
        this.config = config;
    }
    public Market() {}
    public Market(Long id) {
        this.id = id;
    }
    @NotBlank
    public String getName() {
        return name;
    }
    @NotBlank
    public String getShortName() {
        return shortName;
    }
    @NotBlank
    public String getUrl() {
        return url;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setShortName(String shortName) {
        this.shortName = shortName;
    }
    public void setUrl(String url) {
        this.url = url;
    }
}