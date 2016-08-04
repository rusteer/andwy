package net.andwy.andwyadmin.repository.stat;
import net.andwy.andwyadmin.entity.client.PackageStat;
import net.andwy.andwyadmin.repository.MyBatisRepository;

@MyBatisRepository
public interface PackageStatDaoMybatis {
    void increasePushStat(PackageStat stat);
    void increaseInstallStat(PackageStat stat);
}
