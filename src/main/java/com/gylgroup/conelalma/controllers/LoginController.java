package com.gylgroup.conelalma.controllers;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

@Controller
public class LoginController {

    @GetMapping("/login")
    public ModelAndView login(@RequestParam(required = false) String error, @RequestParam(required = false) String logout, Principal principal) {

        ModelAndView mav = new ModelAndView("login");
       /* if (error != null) {
            mav.addObject("error", "Correo o clave incorrecta");
        }
        if (logout != null) {
            mav.addObject("logout", "Sesión Finalizada");
        }
        if (principal != null) {
            LOGGER.info("Principal -> {}", principal.getName());
            mav.setViewName("redirect:/home");
        }*/
        return mav;
    }

    @GetMapping("/signup")
    public ModelAndView signup(@RequestParam(required = false) String error, HttpServletRequest request, Principal principal) {
        ModelAndView mav = new ModelAndView("./profile/signup.html");

        mav.addObject("title","Registrar usuario");
        /*Map<String , ?> flashMap = RequestContextUtils.getInputFlashMap(request);

        if (flashMap != null) {
            mav.addObject("exito", flashMap.get("exito"));
            mav.addObject("error", flashMap.get("error-name"));
            mav.addObject("usuario", flashMap.get("usuario"));
        }else{
            mav.addObject("usuario",new Usuario());
        }
        if (principal != null) {
            LOGGER.info("Principal -> {}", principal.getName());
            mav.setViewName("redirect:/home");
        }*/

        return mav;
    }
}