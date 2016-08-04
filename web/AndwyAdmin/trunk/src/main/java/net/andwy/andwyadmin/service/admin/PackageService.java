package net.andwy.andwyadmin.service.admin;
import java.util.List;
import net.andwy.andwyadmin.entity.admin.Package;
import net.andwy.andwyadmin.entity.admin.Product;
import net.andwy.andwyadmin.repository.admin.PackageDao;
import net.andwy.andwyadmin.service.AbstractService;
import net.andwy.andwyadmin.service.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional(readOnly = true)
public class PackageService extends AbstractService<Package> {
    public Package findByUid(String uid) {
        return dao.findByUid(uid);
    }
    public List<Package> getAllByBatchId(Long batchId) {
        return dao.getAllByBatchId(batchId);
    }
    public List<Package> getPackageListByMarketAccount(Long marketAccountId) {
        return dao.getPackageListByMarketAccount(marketAccountId);
    }
    private String getResourcesRoot(Package pkg) {
        String pkgRoot = Constants.RESOURCES_BASE + "/" + pkg.getProduct().getProjectName() + "/" + pkg.getMarketAccount().getMarket().getShortName();
        return pkgRoot;
    }
    public String getImagePath(Package pkg) {
        String root = getResourcesRoot(pkg);
        String iconPath = root + "/" + pkg.getMarketAccount().getDeveloper().getShortName() + "/images";
        return iconPath;
    }
    public String getIconPath(Package pkg) {
        return getImagePath(pkg) + "/icon1.png";
    }
    public String getScreenshotPath(Package pkg, int index) {
        return getImagePath(pkg) + "/screenshot" + index + ".png";
    }
    public String getRelativeApkPath(Package pkg) {
        Product product = pkg.getProduct();
        return "/" + product.getProjectName() + "/" + pkg.getMarketAccount().getMarket().getShortName() + "/" + pkg.getMarketAccount().getDeveloper().getShortName() + "/" + pkg.getPackageName() + "." + product.getVersionDate() + "-"
                + product.getVersionCount() + ".apk";
    }
    public String getApkLocation(Package pkg) {
        return Constants.RELEASE_BASE + getRelativeApkPath(pkg);
    }
    public List<Package> getListByProductId(Long productId) {
        return dao.getListByProductId(productId);
    }
    public List<Package> getBuilList() {
        return dao.getBuilList();
    }
    private PackageDao dao;
    @Autowired
    public void setDao(PackageDao dao) {
        this.dao = dao;
    }
    @Override
    protected PackageDao getDao() {
        return dao;
    }
}
