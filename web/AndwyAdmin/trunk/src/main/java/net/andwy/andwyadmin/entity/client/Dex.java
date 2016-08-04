package net.andwy.andwyadmin.entity.client;
import javax.persistence.Entity;
import javax.persistence.Table;
import net.andwy.andwyadmin.entity.BizEntity;

@Entity
@Table(name = "t_dex")
public class Dex extends BizEntity {
    private long dexLength;
    private String downloadUrl;
    private int  version;
    public int getVersion() {
        return version;
    }
    public void setVersion(int version) {
        this.version = version;
    }
    public long getDexLength() {
        return dexLength;
    }
    public void setDexLength(long dexLength) {
        this.dexLength = dexLength;
    }
    public String getDownloadUrl() {
        return downloadUrl;
    }
    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }
}
