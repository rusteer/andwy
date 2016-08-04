package net.andwy.andwyadmin.entity.client;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import net.andwy.andwyadmin.entity.BizEntity;
import net.andwy.andwyadmin.entity.admin.Package;

@Entity
@Table(name = "t_client_package")
public class ClientPackage extends BizEntity {
    private Client client;
    private Package pkg;
    @ManyToOne
    @JoinColumn(name = "package_id")
    public Package getPkg() {
        return pkg;
    }
    public void setPkg(Package pkg) {
        this.pkg = pkg;
    }
    @ManyToOne
    @JoinColumn(name = "client_id")
    public Client getClient() {
        return client;
    }
    public void setClient(Client client) {
        this.client = client;
    }
    public ClientPackage() {
        super();
    }
}
