package net.andwy.andwyadmin.service.client;
import net.andwy.andwyadmin.entity.client.ClientPackage;
import net.andwy.andwyadmin.repository.client.ClientPackageDao;
import net.andwy.andwyadmin.service.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional(readOnly = true)
public class ClientPackageService extends AbstractService<ClientPackage> {
    private ClientPackageDao dao;
    @Autowired
    public void setDao(ClientPackageDao dao) {
        this.dao = dao;
    }
    @Override
    public ClientPackageDao getDao() {
        return dao;
    }
    public ClientPackage findByUniqKey(Long packageId, Long clientId) {
        return dao.findByUniqKey(packageId, clientId);
    }
}
