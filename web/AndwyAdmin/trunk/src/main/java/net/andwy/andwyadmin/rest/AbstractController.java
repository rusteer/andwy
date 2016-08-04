package net.andwy.andwyadmin.rest;
import java.util.Date;
import net.andwy.andwyadmin.entity.BizEntity;

public class AbstractController {
    protected void handleCreate(BizEntity entity) {
        Date date = new Date();
        entity.setCreateTime(date);
        entity.setUpdateTime(date);
        if (!"E".equals(entity.getStatus())) entity.setStatus("D");
    }
    protected void handleUpdate(BizEntity entity) {
        entity.setUpdateTime(new Date());
        if (!"E".equals(entity.getStatus())) entity.setStatus("D");
    }
}
