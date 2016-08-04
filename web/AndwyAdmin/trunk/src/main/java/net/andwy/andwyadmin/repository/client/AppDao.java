package net.andwy.andwyadmin.repository.client;
import java.util.List;
import net.andwy.andwyadmin.entity.client.App;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface AppDao extends PagingAndSortingRepository<App, Long>, JpaSpecificationExecutor<App> {
    @Query("from App a1 where a1.push='Y' and a1.status='E'")
    List<App> getPushList();
    @Query("from App a2 where a2.status='E' order by a2.hot desc")
    List<App> getEnabledList();
    
    /**
     * 应用列表
     * @return
     */
    @Query("from App a3 where a3.type=1 and a3.status='E' order by a3.hot desc")
    List<App> getAppRecommendList();
    
    /**
     * 游戏列表
     * @return
     */
    @Query("from App a4 where a4.type=2 and a4.status='E' order by a4.hot desc")
    List<App> getGameRecommendList();
    
    App findByUid(String uid);
}
