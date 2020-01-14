package edu.uestc.web.admin;

import edu.uestc.po.User;
import edu.uestc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

/**
 * Created by zw on 2019/12/14.
 */
@Controller
@RequestMapping("admin")
public class LoginController {


    @Autowired
    private UserService userService;

    @GetMapping
    public String loginPage() {
        return "admin/login";
    }


    @PostMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        HttpSession session,
                        RedirectAttributes attributes,
                        Model model) {
        User user = userService.checkUser(username, password);
        if (user != null) {
            user.setPassword(null);
            session.setAttribute("user",user);
            model.addAttribute("username",user.getUsername());
            return "admin/index";
        } else {
            attributes.addFlashAttribute("message", "用户名和密码错误");
            return "redirect:/admin";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("user");
        session.removeAttribute("conditions");
        session.removeAttribute("conclusions");
        session.removeAttribute("cond");
        session.removeAttribute("cons");
        session.removeAttribute("methods");
        session.removeAttribute("ruleData");
        return "redirect:/admin";
    }

    @GetMapping("/index")
    public String index(HttpSession session,Model model) {
        User user = (User) session.getAttribute("user");
        if(user!=null){
            model.addAttribute("username",user.getUsername());
            return "admin/index";
        }
        return "index";
    }

}
