package net.andwy.andwyadmin.web.client.module;
import module.ads.bean.params.InstallParams;
import net.andwy.andwyadmin.entity.KeyValue;
import net.andwy.andwyadmin.entity.client.App;
import net.andwy.andwyadmin.entity.client.ClientPackage;
import net.andwy.andwyadmin.service.stat.ClientStatService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class InstallReportController extends ModuleController {
    @Autowired
    protected ClientStatService clientStatService;
    public KeyValue<String, JSONObject> execute(ClientPackage clientPackage, InstallParams params) {
        String uuid = params.appUUid;
        //long channel = params.optLong("channel");
        App app = appService.findByUid(uuid);
        if (app != null) {
            appStatService.increaseInstallStat(app.getId());
            packageStatService.increaseInstallStat(clientPackage.getPkg().getId(), app );
            clientStatService.increaseInstallStat(clientPackage.getClient().getId(), app );
        }
        return new KeyValue<String, JSONObject>(null, new JSONObject());
    }
}
