package net.andwy.andwyadmin.service.admin;
import net.andwy.andwyadmin.entity.admin.MarketAccount;
import net.andwy.andwyadmin.repository.admin.MarketAccountDao;
import net.andwy.andwyadmin.service.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional(readOnly = true)
public class MarketAccountService extends AbstractService<MarketAccount> {
    private MarketAccountDao dao;
    @Autowired
    public void setDao(MarketAccountDao dao) {
        this.dao = dao;
    }
    @Override
    protected MarketAccountDao getDao() {
        return dao;
    }
}
