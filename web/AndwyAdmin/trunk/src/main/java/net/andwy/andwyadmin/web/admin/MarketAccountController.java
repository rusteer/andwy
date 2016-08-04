package net.andwy.andwyadmin.web.admin;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.andwy.andwyadmin.entity.admin.MarketAccount;
import net.andwy.andwyadmin.service.admin.MarketAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/mc")
public class MarketAccountController {
    @Autowired
    MarketAccountService service;
    @RequestMapping(value = "list")
    public String getPackageListByMarketAccount(HttpServletRequest request, HttpServletResponse response) {
        List<MarketAccount> list = service.getAll();
        Collections.sort(list, new Comparator<MarketAccount>() {
            @Override
            public int compare(MarketAccount o1, MarketAccount o2) {
                int result = o1.getMarket().getId().compareTo(o2.getMarket().getId());
                if (result == 0) result = o2.getDeveloper().getId().compareTo(o1.getDeveloper().getId());
                return result;
            }
        });
        request.setAttribute("list", list);
        return "marketAccount/list";
    }
}
