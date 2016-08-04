package net.andwy.andwyadmin.web.admin;
import javax.validation.Valid;
import net.andwy.andwyadmin.entity.admin.User;
import net.andwy.andwyadmin.service.admin.AccountService;
import net.andwy.andwyadmin.service.admin.ShiroDbRealm.ShiroUser;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 用户修改自己资料的Controller.
 * 
 * @author calvin
 */
@Controller
@RequestMapping(value = "/profile")
public class ProfileController {
    @Autowired
    private AccountService accountService;
    /**
     * 取出Shiro中的当前用户Id.
     */
    private Long getCurrentUserId() {
        ShiroUser user = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
        return user.id;
    }
    @ModelAttribute("preloadUser")
    public User getUser(@RequestParam(value = "id", required = false) Long id) {
        if (id != null) { return accountService.getUser(id); }
        return null;
    }
    @RequestMapping(method = RequestMethod.POST)
    public String update(@Valid @ModelAttribute("preloadUser") User user) {
        accountService.updateUser(user);
        updateCurrentUserName(user.getName());
        return "redirect:/";
    }
    /**
     * 更新Shiro中当前用户的用户名.
     */
    private void updateCurrentUserName(String userName) {
        ShiroUser user = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
        user.name = userName;
    }
    @RequestMapping(method = RequestMethod.GET)
    public String updateForm(Model model) {
        Long id = getCurrentUserId();
        model.addAttribute("user", accountService.getUser(id));
        return "account/profile";
    }
}
