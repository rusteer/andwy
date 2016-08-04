package net.andwy.andwyadmin.service.client;
import java.util.List;
import net.andwy.andwyadmin.entity.admin.Package;
import net.andwy.andwyadmin.entity.client.Config;
import net.andwy.andwyadmin.repository.client.ConfigDao;
import net.andwy.andwyadmin.service.AbstractCacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional(readOnly = true)
public class ConfigService extends AbstractCacheService<Config> {
    private ConfigDao dao;
    @Autowired
    public void setDao(ConfigDao dao) {
        this.dao = dao;
    }
    @Override
    public ConfigDao getDao() {
        return dao;
    }
    private List<Config> getListByLevel(Long level) {
        return getCacheList(level);
    }
    public Config getGlobalConfig() {
        List<Config> list = getListByLevel(1L);
        if (list != null && list.size() > 0) return list.get(0);
        return null;
    }
    @Override
    protected List<Config> fetchListFromDB(Long key) {
        return dao.getListByLevel(key);
    }
    public Config getConfig(Package pkg) {
        Config config = null;
        if (pkg != null) {
            config = pkg.getConfig();
            if (config != null && "E".equals(config.getStatus())) return config;
            config = pkg.getProduct().getConfig();
            if (config != null && "E".equals(config.getStatus())) return config;
            config = pkg.getMarketAccount().getMarket().getConfig();
            if (config != null && "E".equals(config.getStatus())) return config;
            config = getGlobalConfig();
            if (config != null && "E".equals(config.getStatus())) return config;
        }
        return null;
    }
}
