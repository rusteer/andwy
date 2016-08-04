package net.andwy.andwyadmin.service.stat;
import java.text.SimpleDateFormat;
import java.util.Date;
import net.andwy.andwyadmin.entity.client.App;
import net.andwy.andwyadmin.entity.client.PackageStat;
import net.andwy.andwyadmin.repository.stat.PackageStatDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional(readOnly = true)
public class PackageStatService {
    //private static final Logger logger = LoggerFactory.getLogger("ClientLogger");
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    @Autowired
    PackageStatDao dao;
    @Transactional(readOnly = false)
    public void increasePushStat(Long packageId, App app) {
        String today = dateFormat.format(new Date());
        PackageStat stat = this.dao.getStat(packageId, today);
        if (stat == null) {
            stat = new PackageStat(packageId, today);
        }
        if (!app.isPrivateApp()) {
            stat.setPushCount(stat.getPushCount() + 1);
        }
        stat.setAllPushCount(stat.getAllPushCount() + 1);
        this.dao.save(stat);
        //PackageStat stat = new PackageStat(packageId, );
        //countDao.increasePushStat(stat);
    }
    @Transactional(readOnly = false)
    public void increaseInstallStat(Long packageId, App app) {
        String today = dateFormat.format(new Date());
        float price = app.getPrice();
        PackageStat stat = this.dao.getStat(packageId, today);
        if (stat == null) {
            stat = new PackageStat(packageId, today);
        }
        if (!app.isPrivateApp()) {
            stat.setInstallCount(stat.getInstallCount() + 1);
            stat.setInstallEarning(stat.getInstallEarning() + price);
        }
        stat.setAllInstallCount(stat.getAllInstallCount() + 1);
        stat.setAllInstallEarning(stat.getAllInstallEarning() + price);
        this.dao.save(stat);
        //PackageStat stat = new PackageStat(packageId, dateFormat.format(new Date()));
        //stat.setInstallEarning(price);
        // countDao.increaseInstallStat(stat);
    }
}
