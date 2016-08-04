package net.andwy.andwyadmin.service.client;
import java.util.List;
import net.andwy.andwyadmin.entity.client.Dex;
import net.andwy.andwyadmin.repository.client.DexDao;
import net.andwy.andwyadmin.service.AbstractCacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional(readOnly = true)
public class DexService extends AbstractCacheService<Dex> {
    private static final Long CACHE_KEY_ENABLED_LIST = 1234567L;
    private DexDao dao;
    @Override
    protected List<Dex> fetchListFromDB(Long key) {
        return dao.getLastVersion();
    }
    public Dex getLastVersion() {
        //List<Dex> list = this.getCacheList(CACHE_KEY_ENABLED_LIST);
        List<Dex> list = dao.getLastVersion();
        if (list != null && list.size() > 0) { return list.get(0); }
        return null;
    }
    @Autowired
    public void setDao(DexDao dao) {
        this.dao = dao;
    }
    @Override
    public DexDao getDao() {
        return dao;
    }
}
