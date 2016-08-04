package net.andwy.andwyadmin.entity.client;
import javax.persistence.MappedSuperclass;
import net.andwy.andwyadmin.entity.BizEntity;

@MappedSuperclass
public class UUIDEntity extends BizEntity {
    private String uid;
    public String getUid() {
        return uid;
    }
    public void setUid(String uid) {
        this.uid = uid;
    }
}
