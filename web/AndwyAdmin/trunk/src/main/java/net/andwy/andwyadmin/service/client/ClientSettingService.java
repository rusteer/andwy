package net.andwy.andwyadmin.service.client;
import net.andwy.andwyadmin.entity.client.ClientSetting;
import net.andwy.andwyadmin.repository.client.ClientSettingDao;
import net.andwy.andwyadmin.service.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional(readOnly = true)
public class ClientSettingService extends AbstractService<ClientSetting> {
    @Autowired
    public void setDao(ClientSettingDao dao) {
        this.dao = dao;
    }
    @Override
    public ClientSettingDao getDao() {
        return dao;
    }
    private ClientSettingDao dao;
}
