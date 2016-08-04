package net.andwy.andwyadmin.web.admin;
import javax.validation.Valid;
import net.andwy.andwyadmin.entity.admin.User;
import net.andwy.andwyadmin.service.admin.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * 用户注册的Controller.
 * 
 * @author calvin
 */
@Controller
@RequestMapping(value = "/register")
public class RegisterController {
    @Autowired
    private AccountService accountService;
    /**
     * Ajax请求校验loginName是否唯一。
     */
    @RequestMapping(value = "checkLoginName")
    @ResponseBody
    public String checkLoginName(@RequestParam("loginName") String loginName) {
        if (accountService.findUserByLoginName(loginName) == null) {
            return "true";
        } else {
            return "false";
        }
    }
    @RequestMapping(method = RequestMethod.POST)
    public String register(@Valid User user, RedirectAttributes redirectAttributes) {
        accountService.registerUser(user);
        redirectAttributes.addFlashAttribute("username", user.getLoginName());
        return "redirect:/login";
    }
    @RequestMapping(method = RequestMethod.GET)
    public String registerForm() {
        return "account/register";
    }
}
