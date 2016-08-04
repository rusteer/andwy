package net.andwy.andwyadmin.repository.stat;
import java.util.List;
import net.andwy.andwyadmin.entity.client.ClientStat;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ClientStatDao extends PagingAndSortingRepository<ClientStat, Long>, JpaSpecificationExecutor<ClientStat> {
    @Query("from ClientStat a where a.clientId=?1 and a.statDate=?2 ")
    ClientStat getStat(Long clientId, String statDate);
    @Query("from ClientStat a where a.clientId=?1 and a.statDate>=?2 and a.statDate<=?3")
    List<ClientStat> getStat(Long clientId, String fromDate, String endDate);
}
