package net.andwy.andwyadmin.service.admin;
import java.util.List;
import net.andwy.andwyadmin.entity.admin.Developer;
import net.andwy.andwyadmin.repository.admin.DeveloperDao;
import net.andwy.andwyadmin.service.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional(readOnly = true)
public class DeveloperService extends AbstractService<Developer> {
    private DeveloperDao dao;
    @Autowired
    public void setDao(DeveloperDao dao) {
        this.dao = dao;
    }
    @Override
    protected DeveloperDao getDao() {
        return dao;
    }
    public List<Developer> getEnabledList() {
        return dao.getEnabledList();
    }
}
