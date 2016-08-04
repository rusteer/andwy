package net.andwy.andwyadmin.entity.admin;
import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import net.andwy.andwyadmin.entity.IdEntity;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.NotBlank;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.ImmutableList;

@Entity
@Table(name = "t_user")
public class User extends IdEntity {
    private String loginName;
    private String name;
    private String plainPassword;
    private String password;
    private String salt;
    private String roles;
    private Date registerDate;
    public User() {}
    public User(Long id) {
        this.id = id;
    }
    @NotBlank
    public String getLoginName() {
        return loginName;
    }
    @NotBlank
    public String getName() {
        return name;
    }
    public String getPassword() {
        return password;
    }
    @Transient
    @JsonIgnore
    public String getPlainPassword() {
        return plainPassword;
    }
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
    public Date getRegisterDate() {
        return registerDate;
    }
    @Transient
    @JsonIgnore
    public List<String> getRoleList() {
        return ImmutableList.copyOf(StringUtils.split(roles, ","));
    }
    public String getRoles() {
        return roles;
    }
    public String getSalt() {
        return salt;
    }
    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public void setPlainPassword(String plainPassword) {
        this.plainPassword = plainPassword;
    }
    public void setRegisterDate(Date registerDate) {
        this.registerDate = registerDate;
    }
    public void setRoles(String roles) {
        this.roles = roles;
    }
    public void setSalt(String salt) {
        this.salt = salt;
    }
}