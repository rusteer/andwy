package net.andwy.andwyadmin.service.admin;
import net.andwy.andwyadmin.entity.admin.Market;
import net.andwy.andwyadmin.repository.admin.MarketDao;
import net.andwy.andwyadmin.service.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional(readOnly = true)
public class MarketService extends AbstractService<Market> {
    private MarketDao dao;
    @Autowired
    public void setDao(MarketDao dao) {
        this.dao = dao;
    }
    @Override
    protected CrudRepository<Market, Long> getDao() {
        return dao;
    }
}
