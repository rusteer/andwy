package net.andwy.andwyadmin.entity.admin;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import net.andwy.andwyadmin.entity.IdEntity;
import org.hibernate.validator.constraints.NotBlank;

//JPA标识
@Entity
@Table(name = "t_task")
public class Task extends IdEntity {
    private String title;
    private String description;
    private User user;
    public String getDescription() {
        return description;
    }
    // JSR303 BeanValidator的校验规则
    @NotBlank
    public String getTitle() {
        return title;
    }
    // JPA 基于USER_ID列的多对一关系定义
    @ManyToOne
    @JoinColumn(name = "user_id")
    public User getUser() {
        return user;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public void setUser(User user) {
        this.user = user;
    }
}
