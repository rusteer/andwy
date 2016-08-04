package net.andwy.andwyadmin.service.admin;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import net.andwy.andwyadmin.entity.admin.Package;
import net.andwy.andwyadmin.service.ServerSetting;
import net.andwy.andwyadmin.service.ServiceError;
import net.andwy.andwyadmin.service.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BuildService {
    private static final Logger logger = LoggerFactory.getLogger("AdminLogger");
    private static boolean building = false;
    private static final Object locker = new Object();
    @Autowired
    PackageService pkgService;
    @Autowired
    SourceBuilder sourceBuilder;
    @Autowired
    BinaryBuilder binaryBuilder;
    @Autowired
    ServerSetting config;
    // CornJob
    public void selectOne() {
        if (!config.enableBuild) return;
        synchronized (locker) {
            if (building) return;
            building = true;
        }
        try {
            execute();
        } catch (Throwable e) {
            logger.error(e.getMessage(), e);
        }
        synchronized (locker) {
            building = false;
        }
    }
    public void execute() {
        List<Package> buildList = pkgService.getBuilList();
        int batchCount = 3;
        for (final Package element : buildList) {
            ExecutorService executor = Executors.newSingleThreadExecutor();
            Future<ServiceError> future = executor.submit(new Callable<ServiceError>() {
                @Override
                public ServiceError call() throws Exception {
                    ServiceError error = null;
                    Package pkg = pkgService.get(element.getId());//Reget the data from db to avoid overriding the data.
                    pkg.setBuildStatus("Building");
                    if (Util.isEmpty(pkg.getUid())) pkg.setUid(Util.generateUUID());
                    pkgService.save(pkg);
                    try {
                        String projectType = pkg.getProduct().getProjectType();
                        logger.info("start building pkg " + pkg.getPackageProductName());
                        if (ProductService.PROJECT_TYPE_SOURCE.equals(projectType)) {
                            error = sourceBuilder.execute(pkg);
                        } else if (ProductService.PROJECT_TYPE_BINARY.equals(projectType)) {
                            error = binaryBuilder.execute(pkg);
                        } else {
                            error = new ServiceError("PorjectNotSupport", "Project Type<" + projectType + "> is not supported");
                        }
                        pkg = pkgService.get(pkg.getId());//Reget the data from db to avoid overriding the data.
                    } catch (Throwable e) {
                        error = AbstractBuilder.getBuildError("apktool-b-timeout", e);
                    }
                    if (error != null) {
                        logger.info("error while building pkg " + pkg.getPackageProductName());
                    } else {
                        logger.info("successfuly building pkg " + pkg.getPackageProductName());
                    }
                    pkg.setBuildStatus(error == null ? "Success" : error.message);
                    pkg.setBuildCount(pkg.getBuildCount() + 1);
                    pkg.setNeedBuild("N");
                    pkgService.save(pkg);
                    return error;
                }
            });
            try {
                future.get(2, TimeUnit.MINUTES);
            } catch (Throwable e) {
                logger.error(e.getMessage(), e);
            }
            executor.shutdownNow();
            if (batchCount-- < 0) break;
        }
    }
    public static int exeCmd(String cmd, StringBuilder out, StringBuilder error) {
        logger.info("start cmd:" + cmd);
        out.setLength(0);
        error.setLength(0);
        int result = 0;
        try {
            Runtime run = Runtime.getRuntime();
            Process p = run.exec(cmd);
            BufferedReader inBr = new BufferedReader(new InputStreamReader(p.getInputStream()));
            BufferedReader stdError = new BufferedReader(new InputStreamReader(p.getErrorStream()));
            String lineStr;
            while ((lineStr = inBr.readLine()) != null) {
                logger.info(lineStr);
                out.append(lineStr).append('\n');
            }
            while ((lineStr = stdError.readLine()) != null) {
                logger.error(lineStr);
                error.append(lineStr).append('\n');
            }
            if (p.waitFor() != 0) {
                result = p.exitValue();
                if (result != 0) {
                    logger.error("Error occurs while executing cmd {" + cmd + "} and the return value is " + result);
                }
            }
            inBr.close();
            stdError.close();
        } catch (Throwable e) {
            logger.error("Error while exucting cmd " + cmd, e);
        }
        logger.info("end cmd:" + cmd);
        return result;
    }
}
