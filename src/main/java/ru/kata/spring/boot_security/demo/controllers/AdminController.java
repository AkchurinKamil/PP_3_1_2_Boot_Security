package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.kata.spring.boot_security.demo.entity.User;
import ru.kata.spring.boot_security.demo.services.RoleService;
import ru.kata.spring.boot_security.demo.services.UserService;



@Controller
@RequestMapping("/admin")
public class AdminController {

    private RoleService roleService;
    private UserService userService;

    @Autowired
    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping
    public String userList(Model model) {
        model.addAttribute("allUsers", userService.allUsers());
        return "admin";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") Long id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }


    @PostMapping
    public String create(@ModelAttribute("user") User user) {
        userService.addUsers(user);
        return "redirect:/admin";
    }


    @GetMapping("/new")
    public ModelAndView newUser() {
        User user = new User();
        ModelAndView mav = new ModelAndView("new");
        mav.addObject("user", user);
        mav.addObject("allRoles", roleService.getAllRoles() );
        return mav;
    }

    @GetMapping("/{id}/edit")
    public ModelAndView editUser(@PathVariable(name = "id") Long id) {
        User user = userService.findUserById(id);
        ModelAndView mav = new ModelAndView("edit");
        mav.addObject("user", user);
        mav.addObject("allRoles", roleService.getAllRoles() );
        return mav;
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("user") User user) {
        userService.update(user);
        return "redirect:/admin";
    }
}

