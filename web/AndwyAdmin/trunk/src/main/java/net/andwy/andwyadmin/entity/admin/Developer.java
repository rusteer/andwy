package net.andwy.andwyadmin.entity.admin;
import javax.persistence.Entity;
import javax.persistence.Table;
import net.andwy.andwyadmin.entity.BizEntity;
import org.hibernate.validator.constraints.NotBlank;

@Entity
@Table(name = "t_developer")
public class Developer extends BizEntity {
    private String name;
    private String nickName;
    private String email;
    private String emailPassword;
    private String developerName;
    private String qq;
    private String mobile;
    private String shortName;
    public Developer() {}
    public Developer(Long id) {
        this.id = id;
    }
    @NotBlank
    public String getDeveloperName() {
        return developerName;
    }
    public String getEmail() {
        return email;
    }
    public String getEmailPassword() {
        return emailPassword;
    }
    public String getMobile() {
        return mobile;
    }
    @NotBlank
    public String getName() {
        return name;
    }
    public String getQq() {
        return qq;
    }
    @NotBlank
    public String getShortName() {
        return shortName;
    }
    public void setDeveloperName(String developerName) {
        this.developerName = developerName;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setEmailPassword(String emailPassword) {
        this.emailPassword = emailPassword;
    }
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setQq(String qq) {
        this.qq = qq;
    }
    public void setShortName(String shortName) {
        this.shortName = shortName;
    }
    public String getNickName() {
        return nickName;
    }
    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
}