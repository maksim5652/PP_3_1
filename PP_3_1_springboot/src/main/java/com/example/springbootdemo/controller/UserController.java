package com.example.springbootdemo.controller;


import com.example.springbootdemo.model.User;
import com.example.springbootdemo.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
public class UserController {

    private final UserService userService;
private static final String REDIRECT="redirect:/";

    public UserController(UserService userService) {
        this.userService = userService;
    }

    //выводим всех на view "index"
    @GetMapping(value = "/")
    public String showUsers(ModelMap model) {
        model.addAttribute("users", userService.getAllUsers());
        return "index";
    }

    //выводим одного на view "user"
    @GetMapping("/{id}")
    public String showOneUser(ModelMap model, @PathVariable("id") Long id) {
        model.addAttribute("user", userService.getUserById(id));
        return "user";
    }

    //получаем форму для добавления нового пользователя
    @GetMapping("/new")
    public String fillNewForm(@ModelAttribute("user") User user) {
        return "new";
    }

//создаем нового
    @PostMapping
    public String create(@ModelAttribute("user") @Valid User user, BindingResult bindResult) {
        if (bindResult.hasErrors()) {
            return"new";
        } else {
            userService.saveUser(user);//redirect - переход по ссылке на /
        }
        return REDIRECT;
    }

    //получаем форму на изменения
    @GetMapping("/edit/{id}")
    public String showUserForm(ModelMap model, @PathVariable("id") Long id) {
        model.addAttribute("user", userService.getUserById(id));
        return "edit";
    }

    //меняем данные пользователя
    @PatchMapping("/edit/{id}")
    public String update(@ModelAttribute("user") @Valid User user, BindingResult bindResult) {
        if (bindResult.hasErrors()) {
            return"edit";
        } else {
            userService.updateUser(user);
        }
        return REDIRECT;
    }

    //удаляем пользователя
    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") Long id) {
        userService.removeUserById(id);
        return REDIRECT;
    }
}
