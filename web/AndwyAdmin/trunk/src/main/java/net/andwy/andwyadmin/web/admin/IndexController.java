package net.andwy.andwyadmin.web.admin;
import javax.servlet.ServletRequest;
import net.andwy.andwyadmin.service.ServerSetting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(value = "/admin")
public class IndexController {
    @Autowired
    ServerSetting ServerSetting;
    @RequestMapping(value = "")
    public String index(@RequestParam(value = "isSuper", required = false, defaultValue = "N") String isSuper, ServletRequest request) {
        request.setAttribute("isSuper", "Y".equalsIgnoreCase(isSuper));
        request.setAttribute("versionType", ServerSetting.versionType);
        return "index/index";
    }
}
