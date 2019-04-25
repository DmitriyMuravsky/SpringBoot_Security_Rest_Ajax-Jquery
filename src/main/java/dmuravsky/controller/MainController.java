package dmuravsky.controller;

import dmuravsky.dao.UserDAO;
import dmuravsky.model.Role;
import dmuravsky.model.User;
import dmuravsky.service.RoleService;
import dmuravsky.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
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
        return "greeting";
    }

    @RequestMapping(value = "/admin/users", method = RequestMethod.GET)
    public String users(Model model) {
        List<User> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "users";
    }

    @RequestMapping(value = "/admin/add", method = RequestMethod.GET)
    public String add(Model model) {
        model.addAttribute("user", new User());
        return "add";
    }

    @PostMapping(value = "/admin/add")
    public String add(@ModelAttribute User user, @RequestParam("role") String role) {
        Set<Role> roles = new HashSet<Role>();
        roles.add(roleService.getRoleByName(role));
        user.setRoles(roles);
        userService.addUser(user);
        return "redirect:/admin/users";
    }

    @RequestMapping(value = "/admin/edit/{id}", method = RequestMethod.GET)
    public String edit(@PathVariable int id, Model model) {
        User user = userService.getOneUserById(id);
        model.addAttribute("user", user);
        return "edit";
    }

    @PostMapping(value = "/admin/edit")
    public String edit(@ModelAttribute User user, @RequestParam("id") int id, @RequestParam("role") String role) {
        Set<Role> roles = new HashSet<Role>();
        roles.add(roleService.getRoleByName(role));
        user.setRoles(roles);
        user.setId(id);
        userService.updateUser(user);
        return "redirect:/admin/users";
    }

    @RequestMapping(value = "/admin/delete/{id}", method = RequestMethod.GET)
    public String delete(@PathVariable int id, Model model) {
        User user = userService.getOneUserById(id);
        model.addAttribute("user", user);
        return "delete";
    }

    @PostMapping(value = "/admin/delete")
    public String delete(@ModelAttribute User user) {
        userService.deleteUser(user);
        return "redirect:/admin/users";
    }
}
