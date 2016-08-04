package net.andwy.andwyadmin.web.admin;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.andwy.andwyadmin.entity.admin.MarketAccount;
import net.andwy.andwyadmin.entity.admin.Package;
import net.andwy.andwyadmin.entity.admin.Product;
import net.andwy.andwyadmin.entity.client.Config;
import net.andwy.andwyadmin.service.Util;
import net.andwy.andwyadmin.service.admin.BuildService;
import net.andwy.andwyadmin.service.admin.MarketAccountService;
import net.andwy.andwyadmin.service.admin.PackageService;
import net.andwy.andwyadmin.service.admin.ProductService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

@Controller
@RequestMapping(value = "/package")
public class PackageController {
    @Autowired
    PackageService pkgService;
    @Autowired
    BuildService buildService;
    @Autowired
    ProductService productService;
    @Autowired
    MarketAccountService marketAccountService;
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String upload(HttpServletRequest request, HttpServletResponse response) {
        boolean result = true;
        Long id = Long.valueOf(request.getParameter("id"));
        Long productId = Long.valueOf(request.getParameter("product"));
        Long marketAccountId = Long.valueOf(request.getParameter("marketAccount"));
        Package pkg = id == 0 ? new Package() : pkgService.get(id);
        pkg.setMarketAccount(new MarketAccount(marketAccountId));
        pkg.setInjectAds("Y".equals(request.getParameter("injectAds"))?"Y":"N");
        pkg.setAdsDetected("Y".equals(request.getParameter("adsDetected"))?"Y":"N");
        pkg.setNeedBuild(request.getParameter("needBuild"));
        pkg.setNeedPublish(request.getParameter("needPublish"));
        pkg.setPackageName(request.getParameter("packageName"));
        pkg.setProduct(new Product(productId));
        pkg.setPackageProductName(request.getParameter("packageProductName"));
        pkg.setPublishStatus(request.getParameter("publishStatus"));
        pkg.setPublishUrl(request.getParameter("publishUrl"));
        pkg.setDownloadUrl(request.getParameter("downloadUrl"));
        pkg.setFailureDescription(request.getParameter("failureDescription"));
        pkg.setMarketVersion(request.getParameter("marketVersion"));
        pkg.setSubmitDate(request.getParameter("submitDate"));
        pkg.setPublishDate(request.getParameter("publishDate"));
        pkg.setPublishSuccessCount(Long.valueOf(request.getParameter("publishSuccessCount")));
        String publishingVersionCode = request.getParameter("publishingVersionCode");
        if (StringUtils.isNotBlank(publishingVersionCode)) {
            pkg.setPublishingVersionCode(Integer.valueOf(publishingVersionCode));
        } else {
            pkg.setPublishingVersionCode(null);
        }
        String config = request.getParameter("config");
        if (config != null && config.length() > 0) {
            Long configId = Long.valueOf(config);
            pkg.setConfig(new Config(configId));
        }
        Date date = new Date();
        pkg.setUpdateTime(date);
        if (id == 0) {
            pkg.setCreateTime(date);
            pkg.setStatus("E");
        }
        pkg.setStatus("E".equals(request.getParameter("status")) ? "E" : "D");
        pkg.setVersionDescription(request.getParameter("versionDescription"));
        if ("Y".equals(pkg.getNeedBuild())) {
            pkg.setBuildStatus("Waiting for Build");
        }
        if (pkg.getUid() == null || pkg.getUid().length() == 0) {
            pkg.setUid(Util.generateUUID());
        }
        pkg = pkgService.save(pkg);
        pkg.setMarketAccount(marketAccountService.get(marketAccountId));
        pkg.setProduct(productService.get(productId));
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
        //创建文件夹  
        File imageFolder = new File(pkgService.getImagePath(pkg));
        if (!imageFolder.exists()) imageFolder.mkdirs();
        for (Entry<String, MultipartFile> entity : fileMap.entrySet()) {
            MultipartFile mf = entity.getValue();
            if (mf.getSize() > 0) {
                String name = entity.getKey();
                File uploadFile = new File(imageFolder.getAbsolutePath() + "/" + name + ".png");
                try {
                    FileCopyUtils.copy(mf.getBytes(), uploadFile);
                } catch (IOException e) {
                    request.setAttribute("reason", Util.getStack(e));
                    result = false;
                    e.printStackTrace();
                }
            }
        }
        request.setAttribute("result", result);
        return "package/result";
    }
    @RequestMapping(value = "loadImage/{id}")
    public String loadImage(@PathVariable("id") Long id) {
        return null;
    }
    @RequestMapping(value = "mclist")
    public String getPackageListByMarketAccount(HttpServletRequest request, HttpServletResponse response) {
        Long marketAccountId = null;
        try {
            marketAccountId = Long.valueOf(request.getParameter("mcid"));
        } catch (Throwable e) {}
        if (marketAccountId != null && marketAccountId > 0) {
            List<Package> list = pkgService.getPackageListByMarketAccount(marketAccountId);
            request.setAttribute("list", list);
        }
        return "package/list";
    }
}
