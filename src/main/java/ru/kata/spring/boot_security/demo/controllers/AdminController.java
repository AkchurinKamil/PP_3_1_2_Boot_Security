package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.kata.spring.boot_security.demo.entity.Role;
import ru.kata.spring.boot_security.demo.entity.User;
import ru.kata.spring.boot_security.demo.repositories.RoleRepository;
import ru.kata.spring.boot_security.demo.services.UserDetailsServiceImpl;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final RoleRepository roleRepository;

    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    public AdminController(UserDetailsServiceImpl userDetailsService, RoleRepository roleRepository) {
        this.userDetailsService = userDetailsService;
        this.roleRepository = roleRepository;
    }

    @GetMapping
    public String userList(Model model) {
        model.addAttribute("allUsers", userDetailsService.allUsers());
        return "admin";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") Long id) {
        userDetailsService.deleteUser(id);
        return "redirect:/admin";
    }



    @PostMapping
    public String create(@ModelAttribute("user") User user) {
        userDetailsService.addUsers(user);
        return "redirect:/admin";
    }


    @GetMapping("/new")
    public ModelAndView newUser() {
        User user = new User();
        ModelAndView mav = new ModelAndView("new");
        mav.addObject("user", user);
        List<Role> roles = (List<Role>) roleRepository.findAll();
        mav.addObject("allRoles", roles);

        return mav;
    }

    @GetMapping("/{id}/edit")
    public ModelAndView editUser(@PathVariable(name = "id") Long id) {
        User user = userDetailsService.findUserById(id);
        ModelAndView mav = new ModelAndView("edit");
        mav.addObject("user", user);

        List<Role> roles = (List<Role>) roleRepository.findAll();

        mav.addObject("allRoles", roles);

        return mav;
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("user") User user) {
        userDetailsService.update(user);
        return "redirect:/admin";
    }
}
