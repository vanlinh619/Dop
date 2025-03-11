package org.dop.module.security.login.controller;

import lombok.RequiredArgsConstructor;
import org.dop.config.property.Oauth2LoginProperties;
import org.dop.config.property.SecurityRememberMeProperties;
import org.dop.entity.state.Provider;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("{issuer}/login")
public class Oauth2Controller {

    private final SecurityRememberMeProperties securityRememberMeProperties;
    private final Oauth2LoginProperties oauth2LoginProperties;


    @GetMapping
    public String login(Model model, @PathVariable String issuer) {
        model.addAttribute("rememberMeEnable", securityRememberMeProperties.isEnable());
        model.addAttribute("anySocialEnable", oauth2LoginProperties.anySocialEnable());
        model.addAttribute("googleEnable", oauth2LoginProperties.isSocialEnable(Provider.GOOGLE));
        model.addAttribute("issuer", issuer);

        return "login";
    }
}
