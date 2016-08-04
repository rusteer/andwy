package net.andwy.andwyadmin.entity.client;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "t_category")
public final class Category extends UUIDEntity {
    private String name;
    private String iconUrl;
    public Category() {}
    public Category(Long id) {
        this.id = id;
    }
    public String getIconUrl() {
        return iconUrl;
    }
    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
}
