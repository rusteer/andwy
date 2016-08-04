package net.andwy.andwyadmin.bean;
public class Bean {
    private Long id;
    private String name;
    private boolean hasData;
    public Bean(Long id, String name) {
        this.id = id;
        this.name = name;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public boolean isHasData() {
        return hasData;
    }
    public void setHasData(boolean hasData) {
        this.hasData = hasData;
    }
}
