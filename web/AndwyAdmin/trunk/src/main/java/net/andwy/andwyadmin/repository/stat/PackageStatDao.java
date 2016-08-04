package net.andwy.andwyadmin.repository.stat;
import java.util.List;
import net.andwy.andwyadmin.entity.client.PackageStat;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface PackageStatDao extends PagingAndSortingRepository<PackageStat, Long>, JpaSpecificationExecutor<PackageStat> {
    @Query("from PackageStat a where a.packageId=?1 and a.statDate=?2 ")
    PackageStat getStat(Long packageId, String statDate);
    @Query("from PackageStat a where a.packageId=?1 and a.statDate>=?2 and a.statDate<=?3")
    List<PackageStat> getStat(Long packageId, String fromDate, String endDate);
}
