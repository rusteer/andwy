package net.andwy.andwyadmin.service.client;
import net.andwy.andwyadmin.entity.client.BlackClient;
import net.andwy.andwyadmin.entity.client.Client;
import net.andwy.andwyadmin.repository.client.BlackClientDao;
import net.andwy.andwyadmin.service.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional(readOnly = true)
public class BlackClientService extends AbstractService<BlackClient> {
    private BlackClientDao dao;
    public boolean isBlack(Client client) {
        return dao.getByClientId(client.getId()) != null;
    }
    @Autowired
    public void setDao(BlackClientDao dao) {
        this.dao = dao;
    }
    @Override
    protected BlackClientDao getDao() {
        return dao;
    }
}
