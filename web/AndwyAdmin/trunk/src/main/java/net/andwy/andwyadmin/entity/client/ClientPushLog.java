package net.andwy.andwyadmin.entity.client;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import net.andwy.andwyadmin.entity.BizEntity;

@Entity
@Table(name = "t_client_push_log")
public class ClientPushLog extends BizEntity {
    private Client client;
    /**
     * format:{appId,pushCount|appId,pushCount}
     */
    private String detail;
    private String timeDetail;
    @ManyToOne
    @JoinColumn(name = "client_id")
    public Client getClient() {
        return client;
    }
    public void setClient(Client client) {
        this.client = client;
    }
    public String getDetail() {
        return detail;
    }
    public void setDetail(String detail) {
        this.detail = detail;
    }
    public String getTimeDetail() {
        return timeDetail;
    }
    public void setTimeDetail(String timeDetail) {
        this.timeDetail = timeDetail;
    }
}