package net.andwy.andwyadmin.repository.admin;
import net.andwy.andwyadmin.entity.admin.Rank;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface RankDao extends PagingAndSortingRepository<Rank, Long>, JpaSpecificationExecutor<Rank> {}
