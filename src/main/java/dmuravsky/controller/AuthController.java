package dmuravsky.controller;

import dmuravsky.model.Role;
import dmuravsky.model.User;
import dmuravsky.service.RoleService;
import dmuravsky.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
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
    RoleService roleService;

    @RequestMapping("/")
    public String greeting() {
        return "signIn";
    }

    @RequestMapping(value = "/admin/getUsers", method = RequestMethod.GET)
    public String users() {
        return "users";
    }

    @RequestMapping(value = "/user/accountInfo", method = RequestMethod.GET)
    public String accountInfo() {
        return "accountInfo";
    }

    @RequestMapping(value = "/user/getInfo", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    @ResponseBody
    public User getInfo() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return user;
    }

    @RequestMapping(value = "auth/signUp", method = RequestMethod.GET)
    public String signUp(Model model) {
        model.addAttribute("user", new User());
        return "signUp";
    }

    @PostMapping(value = "/auth/signUp")
    public String signUp(@ModelAttribute User user) {
        Set<Role> roles = new HashSet<Role>();
        roles.add(roleService.getRoleByName("user"));
        user.setRoles(roles);
        userService.addUser(user);
        return "redirect:/auth/signIn";
    }
}
