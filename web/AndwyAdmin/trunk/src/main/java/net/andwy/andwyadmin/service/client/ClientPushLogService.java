package net.andwy.andwyadmin.service.client;
import net.andwy.andwyadmin.entity.client.Client;
import net.andwy.andwyadmin.entity.client.ClientPushLog;
import net.andwy.andwyadmin.repository.client.ClientPushLogDao;
import net.andwy.andwyadmin.service.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional(readOnly = true)
public class ClientPushLogService extends AbstractService<ClientPushLog> {
    private ClientPushLogDao dao;
    @Autowired
    public void setDao(ClientPushLogDao dao) {
        this.dao = dao;
    }
    public ClientPushLog findByClientId(Client client) {
        return dao.findByClientId(client.getId());
    }
    @Override
    protected ClientPushLogDao getDao() {
        return dao;
    }
}
