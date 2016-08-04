package net.andwy.andwyadmin.repository.stat;
import java.util.List;
import net.andwy.andwyadmin.entity.client.AppStat;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface AppStatDao extends PagingAndSortingRepository<AppStat, Long>, JpaSpecificationExecutor<AppStat> {
    //
    @Query("from AppStat a where a.appId=?1 and a.statDate=?2 ")
    AppStat getStat(Long appId, String statDate);
    //
    @Query("from AppStat a where a.appId=?1 and a.statDate>=?2 and a.statDate<=?3")
    List<AppStat> getStat(Long appId, String fromDate, String endDate);
    //
    @Query("from AppStat a where a.statDate=?1 ")
    List<AppStat> getStats(String statDate);
}
