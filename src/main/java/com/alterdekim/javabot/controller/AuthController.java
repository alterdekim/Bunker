package com.alterdekim.javabot.controller;

import com.alterdekim.javabot.dto.UserDTO;
import com.alterdekim.javabot.entities.Invite;
import com.alterdekim.javabot.entities.User;
import com.alterdekim.javabot.service.InviteService;
import com.alterdekim.javabot.service.UserService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
public class AuthController {

    private final String base_title = " | Bunker";

    private final UserService userService;
    private final InviteService inviteService;

    public AuthController(UserService userService, InviteService inviteService) {
        this.inviteService = inviteService;
        this.userService = userService;
    }

    @GetMapping("/login")
    public String loginPage(Model model) {
        model.addAttribute("title", "Login" + base_title);
        return "login";
    }

    @GetMapping("/access-denied")
    public String accessDenied(Model model) {
        model.addAttribute("title", "Access denied");
        return "access-denied";
    }

    @GetMapping("/signup")
    public String showRegistrationForm(Model model) {
        UserDTO userDto = new UserDTO();
        model.addAttribute("user", userDto);
        return "signup";
    }

    @PostMapping("/signup")
    public String registration(
            @ModelAttribute("user") @Valid UserDTO userDto,
            BindingResult result,
            Model model) {
        User existingUser = userService.findByUsername(userDto.getUsername());
        Invite existingInvite = inviteService.findById(1);

        if(existingUser != null && existingUser.getUsername() != null && !existingUser.getUsername().isEmpty() ){
            result.rejectValue("username", null,
                    "There is already an account registered with the same username");
            return "redirect:/signup?error=1";
        }

        if(!existingInvite.getInvite_code().equals(userDto.getInvite_code())) {
            result.rejectValue("invite_code", null, "Incorrect invite code.");
            return "redirect:/signup?error=1";
        }

        if(result.hasErrors()) {
            model.addAttribute("user", new UserDTO());
            return "redirect:/signup?error=1";
        }

        userService.saveUser(userDto);
        return "redirect:/";
    }
}
