package net.andwy.andwyadmin.entity.client;
import javax.persistence.Entity;
import javax.persistence.Table;
import net.andwy.andwyadmin.entity.IdEntity;

@Entity
@Table(name = "t_client_setting")
public class ClientSetting extends IdEntity {
    private String updateHostName;
    private String adsHostName;
    private String webflowHostName;
    public String getUpdateHostName() {
        return updateHostName;
    }
    public String getAdsHostName() {
        return adsHostName;
    }
    public String getWebflowHostName() {
        return webflowHostName;
    }
    public void setUpdateHostName(String updateHostName) {
        this.updateHostName = updateHostName;
    }
    public void setAdsHostName(String adsHostName) {
        this.adsHostName = adsHostName;
    }
    public void setWebflowHostName(String webflowHostName) {
        this.webflowHostName = webflowHostName;
    }
}
