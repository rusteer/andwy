package net.andwy.andwyadmin.web.client;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.andwy.andwyadmin.entity.client.Dex;
import net.andwy.andwyadmin.service.Base64Util;
import net.andwy.andwyadmin.service.client.DexService;
import net.andwy.andwyadmin.web.client.module.ModuleController;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
//@Scope("request")
@RequestMapping(value = "/dex")
public class DexController extends ModuleController {
    @Autowired
    DexService service;
    protected static final Logger clientLogger = LoggerFactory.getLogger("ClientLogger");
    @RequestMapping(value = "/update")
    public void handleClientRequest(HttpServletRequest request, HttpServletResponse response) {
        String result = null;
        try {
            JSONObject requestobj = null;
            String encodedData = request.getParameter("p");
            String decodedData = null;
            if (StringUtils.isNotBlank(encodedData)) {
                decodedData = Base64Util.decodeString(encodedData);
                clientLogger.info("dex-update-request:" + decodedData);
                requestobj = new JSONObject(decodedData);
            }
            JSONObject obj = new JSONObject();
            if (requestobj != null) {
                int version = requestobj.optInt("v");
                if (version > -1) {
                    Dex dex = service.getLastVersion();
                    if (dex != null && dex.getVersion() > version) {
                        obj.put("L", dex.getDexLength());
                        obj.put("U", dex.getDownloadUrl());
                        result = Base64Util.encodeString(obj.toString());
                    }
                }
            }
            clientLogger.info("dex-update-response:" + obj.toString());
        } catch (Throwable e) {
            errorLogger.error(e.getMessage(), e);
        }
        if (result == null) result = "";
        WebUtil.write(request, response, result);
    }
}
