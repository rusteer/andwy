package net.andwy.andwyadmin.web.client.module;
import java.util.ArrayList;
import java.util.List;
import module.ads.bean.CApp;
import module.ads.bean.CAppList;
import module.ads.bean.PushType;
import module.ads.bean.params.PreDownloadParams;
import net.andwy.andwyadmin.entity.KeyValue;
import net.andwy.andwyadmin.entity.client.App;
import net.andwy.andwyadmin.entity.client.ClientPackage;
import net.andwy.andwyadmin.entity.client.ClientPushLog;
import net.andwy.andwyadmin.entity.client.Config;
import net.andwy.andwyadmin.web.client.RequestMethod;
import org.apache.commons.collections.CollectionUtils;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

@Component
public class PreDownloadController extends ModuleController {
    public KeyValue<String, JSONObject> execute(ClientPackage clientPackage, PreDownloadParams preDownloadParams) throws Exception {
        String emptyReason = null;
        if (isFunctionClosed()) {
            emptyReason = "预先下载功能已经关闭";
        }
        Config config = null;
        if (emptyReason == null && isPublishing(clientPackage)) {
            emptyReason = "package is publishing,disable predownload";
        }
        if (emptyReason == null && "Xiaomi".equalsIgnoreCase(clientPackage.getClient().getManufacturer())) {
            emptyReason = "小米手机暂停推送";
        }
        if (emptyReason == null) {
            config = configService.getConfig(clientPackage.getPkg());
        }
        if (config == null) emptyReason = "config is null";
        if (emptyReason == null) {
            if (!"Y".equals(config.getEnablePush())) {
                emptyReason = "PreDownloadController:(config.getEnablePush()=" + config.getEnablePush() + ")";
            }
        }
        
        List<App> list = null;
        if (emptyReason == null) {
            List<App> pushList = appService.getPushList();
            list = filterInstalledApp(pushList, preDownloadParams);
            if (CollectionUtils.isEmpty(list)) {
                emptyReason = "No ads available for predownload";
            }
            if (emptyReason == null) {
                filterExceedLimitsApps(pushList, preDownloadParams);
                if (CollectionUtils.isEmpty(pushList)) {
                    emptyReason = "list is emtpy after invoke filterExceedLimitsApps(pushList, params)";
                }
            }
        }
        
       
        
        CAppList result = new CAppList();
        if (emptyReason == null) {
            ClientPushLog pushLog = clientPushLogService.findByClientId(clientPackage.getClient());
            List<CApp> cList = new ArrayList<CApp>();
            for (App app : pickAppByHotOrder(list, pushLog, 2)) {
                cList.add(toCApp(app, RequestMethod.preDownload, PushType.get(config.getPushType().intValue())));
            }
            result.list = cList;
        }
        return new KeyValue<String, JSONObject>(emptyReason, result.toJson());
    }
    private boolean isFunctionClosed() {
        return true;
    }
}
