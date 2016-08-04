package net.andwy.andwyadmin.service.client;
import java.util.List;
import net.andwy.andwyadmin.entity.client.App;
import net.andwy.andwyadmin.repository.client.AppDao;
import net.andwy.andwyadmin.service.AbstractCacheService;
import net.andwy.andwyadmin.service.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional(readOnly = true)
public class AppService extends AbstractCacheService<App> {
    @Autowired
    public void setDao(AppDao dao) {
        this.dao = dao;
    }
    @Override
    public AppDao getDao() {
        return dao;
    }
    private static final Long CACHE_KEY_ENABLED_LIST = 1111L;
    private static final Long CACHE_KEY_PUSH_LIST = 2222L;
    private static final Long CACHE_KEY_GAME_LIST = 3333L;
    private static final Long CACHE_KEY_APP_LIST = 4444L;
    private AppDao dao;
    @Override
    protected List<App> fetchListFromDB(Long key) {
        if (CACHE_KEY_ENABLED_LIST.equals(key)) return dao.getEnabledList();
        if (CACHE_KEY_PUSH_LIST.equals(key)) return dao.getPushList();
        if (CACHE_KEY_GAME_LIST.equals(key)) return dao.getGameRecommendList();
        if (CACHE_KEY_APP_LIST.equals(key)) return dao.getAppRecommendList();
        return null;
    }
    public App findByUid(String uid) {
        return dao.findByUid(uid);
    }
    public List<App> getPushList() {
        return dao.getPushList();
        // return getCacheList(CACHE_KEY_PUSH_LIST);
    }
    public List<App> getEnabledList() {
        return getCacheList(CACHE_KEY_ENABLED_LIST);
    }
    public String getAppFolder(App app) {
        return Constants.BASE + "/app/" + app.getUid();
    }
    public List<App> getAppRecommendList() {
        return getCacheList(CACHE_KEY_APP_LIST);
    }
    public List<App> getGameRecommendList() {
        return getCacheList(CACHE_KEY_GAME_LIST);
    }
}
