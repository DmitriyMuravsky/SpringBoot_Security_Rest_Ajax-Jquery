package dmuravsky.controller;

import dmuravsky.dao.RoleDAO;
import dmuravsky.model.Role;
import dmuravsky.model.User;
import dmuravsky.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Set;

@Controller
public class AuthController {

    @Autowired
    UserService userService;

    @Autowired
    RoleDAO roleDAO;

    @RequestMapping(value = "/user/accountInfo", method = RequestMethod.GET)
    public String accountInfo(Model model) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("user", user);
        return "accountInfo";
    }

    @RequestMapping(value = "auth/signUp", method = RequestMethod.GET)
    public String signUp(Model model) {
        model.addAttribute("user", new User());
        return "signUp";
    }

    @PostMapping(value = "/auth/signUp")
    public String signUp(@ModelAttribute User user) {
        Set<Role> roles = new HashSet<Role>();
        roles.add(roleDAO.getRoleByName("user"));
        user.setRoles(roles);
        userService.addUser(user);
        return "redirect:/auth/signIn";
    }
}
