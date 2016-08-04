package net.andwy.andwyadmin.repository.client;
import java.util.List;
import net.andwy.andwyadmin.entity.client.Dex;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface DexDao extends PagingAndSortingRepository<Dex, Long>, JpaSpecificationExecutor<Dex> {
    @Query("from  Dex a where a.status='E' order by a.version desc")
    List<Dex> getLastVersion();
}
