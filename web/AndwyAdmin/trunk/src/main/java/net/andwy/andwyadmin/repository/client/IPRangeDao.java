package net.andwy.andwyadmin.repository.client;
import java.util.List;
import net.andwy.andwyadmin.entity.client.IPRange;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface IPRangeDao extends PagingAndSortingRepository<IPRange, Long>, JpaSpecificationExecutor<IPRange> {
    @Query("from IPRange r where r.owner=?1 and r.first<= ?2 and r.last >= ?2")
    List<IPRange> get(String owner, String ip);
}
