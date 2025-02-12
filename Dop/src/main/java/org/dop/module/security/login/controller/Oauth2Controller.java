package org.dop.module.security.login.controller;

import lombok.RequiredArgsConstructor;
import org.dop.config.property.SecurityRememberMeProperties;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("login")
public class Oauth2Controller {

    private final SecurityRememberMeProperties securityRememberMeProperties;


    @GetMapping
    public String login(Model model) {
        model.addAttribute("rememberMeEnable", securityRememberMeProperties.isEnable());

        return "login";
    }
}
