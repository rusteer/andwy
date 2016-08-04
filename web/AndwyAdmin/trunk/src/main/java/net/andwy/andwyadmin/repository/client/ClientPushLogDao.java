package net.andwy.andwyadmin.repository.client;
import net.andwy.andwyadmin.entity.client.ClientPushLog;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ClientPushLogDao extends PagingAndSortingRepository<ClientPushLog, Long>, JpaSpecificationExecutor<ClientPushLog> {
    @Query("from ClientPushLog a where a.client.id=?1")
    ClientPushLog findByClientId(Long clientId);
}
