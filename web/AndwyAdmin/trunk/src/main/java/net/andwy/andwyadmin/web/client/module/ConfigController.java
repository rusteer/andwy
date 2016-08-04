package net.andwy.andwyadmin.web.client.module;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import module.ads.bean.CApp;
import module.ads.bean.CConfig;
import module.ads.bean.SpriteType;
import net.andwy.andwyadmin.entity.KeyValue;
import net.andwy.andwyadmin.entity.client.App;
import net.andwy.andwyadmin.entity.client.ClientPackage;
import net.andwy.andwyadmin.entity.client.Config;
import net.andwy.andwyadmin.service.client.IPRangeService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ConfigController extends ModuleController {
    @Autowired
    IPRangeService ipRangeService;
    public KeyValue<String, JSONObject> execute(ClientPackage clientPackage, HttpServletRequest request, HttpServletResponse response) throws Exception {
        String reason = null;
        JSONObject result = null;
        if (clientPackage != null) {
            Config config = configService.getConfig(clientPackage.getPkg());
            if (config != null) {
                result = this.toJson(request, clientPackage, config);
            } else {
                reason = "handleConfigRequest: config is null";
            }
        } else {
            reason = "handleConfigRequest: clientPackage is null";
        }
        return new KeyValue<String, JSONObject>(reason, result);
    }
    //private static final String SERVER_CONTEXT = "/6de7ba5c10d542faa771beba31428125";
    private static final String SERVER_CONTEXT = "/abcde";
    /*private String getTargetHost(ClientPackage clientPackage, HttpServletRequest request) {
        String host = ServerSetting.defaultHost;
        if (clientPackage != null) {
            Long batchId = clientPackage.getPkg().getProduct().getBatchId();
            if (StringUtils.isNotBlank(ServerSetting.defaultHost2) && batchId >= 10) {
                //host = ServerSetting.defaultHost2;
            }
        }
        return ipRangeService.isUnicomIp(request.getRemoteAddr()) ? ServerSetting.unicomHost : host;
    }*/
    protected JSONObject toJson(HttpServletRequest request, ClientPackage clientPackage, Config config) {
        CConfig cConfig = new CConfig();
        try {
           /* if (!ServerSetting.debug) {
                //Check the host name
                String host = getTargetHost(clientPackage, request);
                String requestHost = new URL(request.getRequestURL().toString()).getHost();
                if (!host.equalsIgnoreCase(requestHost)) cConfig.remoteUrl = "http://" + host + SERVER_CONTEXT;
            }*/
            cConfig.pushInterval = config.getPushRequestInterval();
            cConfig.pushStartHour = config.getPushStartHour();
            cConfig.pushEndHour = config.getPushEndHour();
            boolean debug = isClinetDebug(request);
            if (debug || ("Y".equals(config.getEnableRecommend()) && clientPackage.getClient().getAdsVersion() >= 4)) {
                cConfig.showSpirit = true;
                cConfig.showSpiritBackground = true;
                cConfig.spiritType = SpriteType.KUZAI.ordinal();
                cConfig.encodeResponse = false;
            }
            List<CApp> list = new ArrayList<CApp>();
            for (App app : appService.getPushList()) {
                CApp cApp = new CApp();
                cApp.id = app.getId();
                cApp.pkgName = app.getPkgName();
                list.add(cApp);
            }
            cConfig.availabeAppList = list;
            return cConfig.toJson();
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return null;
    }
}
