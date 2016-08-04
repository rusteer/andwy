package net.andwy.andwyadmin.repository.admin;
import net.andwy.andwyadmin.entity.admin.User;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface UserDao extends PagingAndSortingRepository<User, Long> {
    User findByLoginName(String loginName);
}
