package net.andwy.andwyadmin.service.client;
import net.andwy.andwyadmin.entity.client.Client;
import net.andwy.andwyadmin.repository.client.ClientDao;
import net.andwy.andwyadmin.service.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional(readOnly = true)
public class ClientService extends AbstractService<Client> {
    private ClientDao dao;
    @Autowired
    public void setDao(ClientDao dao) {
        this.dao = dao;
    }
    @Override
    public ClientDao getDao() {
        return dao;
    }
    public Client findByDeviceId(String deviceId) {
        return dao.findByDeviceId(deviceId);
    }
}
