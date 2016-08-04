package net.andwy.andwyadmin.repository.admin;
import net.andwy.andwyadmin.entity.admin.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface TaskDao extends PagingAndSortingRepository<Task, Long>, JpaSpecificationExecutor<Task> {
    @Modifying
    @Query("delete from Task task where task.user.id=?1")
    void deleteByUserId(Long id);
    Page<Task> findByUserId(Long id, Pageable pageRequest);
}
