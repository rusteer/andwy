package net.andwy.andwyadmin.service.admin;
import net.andwy.andwyadmin.entity.admin.Rank;
import net.andwy.andwyadmin.repository.admin.RankDao;
import net.andwy.andwyadmin.service.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional(readOnly = true)
public class RankService extends AbstractService<Rank> {
    private RankDao dao;
    @Autowired
    public void setDao(RankDao dao) {
        this.dao = dao;
    }
    @Override
    public RankDao getDao() {
        return dao;
    }
}
