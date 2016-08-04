package net.andwy.andwyadmin.repository.admin;
import net.andwy.andwyadmin.entity.admin.Market;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface MarketDao extends PagingAndSortingRepository<Market, Long>, JpaSpecificationExecutor<Market> {}
