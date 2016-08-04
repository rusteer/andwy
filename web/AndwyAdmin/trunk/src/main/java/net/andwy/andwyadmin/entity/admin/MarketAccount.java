package net.andwy.andwyadmin.entity.admin;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import net.andwy.andwyadmin.entity.BizEntity;
import org.hibernate.validator.constraints.NotBlank;

@Entity
@Table(name = "t_market_account")
public class MarketAccount extends BizEntity {
    private Market market;
    private Developer developer;
    private String loginName;
    private String loginPassword;
    public MarketAccount() {}
    public MarketAccount(Long id) {
        this.id = id;
    }
    @ManyToOne
    @JoinColumn(name = "developer_id")
    public Developer getDeveloper() {
        return developer;
    }
    @NotBlank
    public String getLoginName() {
        return loginName;
    }
    public String getLoginPassword() {
        return loginPassword;
    }
    @ManyToOne
    @JoinColumn(name = "market_id")
    public Market getMarket() {
        return market;
    }
    public void setDeveloper(Developer account) {
        developer = account;
    }
    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }
    public void setLoginPassword(String loginPassword) {
        this.loginPassword = loginPassword;
    }
    public void setMarket(Market market) {
        this.market = market;
    }
}