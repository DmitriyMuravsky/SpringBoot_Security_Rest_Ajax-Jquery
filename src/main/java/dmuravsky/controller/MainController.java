package dmuravsky.controller;

import dmuravsky.model.Role;
import dmuravsky.model.User;
import dmuravsky.service.RoleService;
import dmuravsky.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
public class MainController {

    @Autowired
    UserService userService;

    @Autowired
    RoleService roleService;

    @RequestMapping("/")
    public String greeting() {
        return "signIn";
    }

    @RequestMapping(value = "/admin/users", method = RequestMethod.GET)
    public String users(Model model) {
        List<User> users = userService.getAllUsers();
        List<Role> roles = roleService.getAllRoles();
        model.addAttribute("users", users);
        User userActive = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("userActive", userActive);
        model.addAttribute("newUser", new User());
        model.addAttribute("roles", roles);
        return "users";
    }

    @PostMapping(value = "/admin/add")
    public String add(@ModelAttribute User user) {
        userService.addUser(user);
        return "redirect:/admin/users";
    }

    @PostMapping(value = "/admin/edit/{id}")
    public String edit(@PathVariable("id") int id, @ModelAttribute User user) {
        userService.updateUser(user);
        return "redirect:/admin/users";
    }

    @PostMapping(value = "/admin/delete/{id}")
    public String delete(@PathVariable("id") int id) {
        userService.deleteUser(userService.getOneUserById(id));
        return "redirect:/admin/users";
    }
}
