package com.gylgroup.conelalma.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import com.gylgroup.conelalma.entities.Rol;
import com.gylgroup.conelalma.entities.Usuario;
import com.gylgroup.conelalma.services.RolService;
import com.gylgroup.conelalma.services.UsuarioService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private RolService rolService;

    @GetMapping("/todos")
    @PreAuthorize("hasAnyRole('ADMIN','CLIENTE')")
    public ModelAndView obtenerUsuarios() {

        ModelAndView mav = new ModelAndView("/admin/usuarios-formulario");
        mav.addObject("usuarios", usuarioService.findAll());
        mav.addObject("usuario", new Usuario());
        mav.addObject("roles", rolService.findAll());
        mav.addObject("estado", false);// por defecto debe ser false
        mav.addObject("action", "guardarEmpleados");

        return mav;
    }

    @PostMapping("/guardarEmpleados")
    public ModelAndView guardarUsuario(@RequestParam(value = "imagen", required = false) MultipartFile imagen,
            @Valid Usuario usuario, BindingResult bindingResult,
            @RequestParam(required = false) Rol rol,
            RedirectAttributes attributes) {

        ModelAndView mav = new ModelAndView();
        if (bindingResult.hasErrors()) {

            mav.addObject("usuarios", usuarioService.findAll());
            mav.addObject("usuario", usuario);
            mav.addObject("roles", rolService.findAll());
            mav.addObject("estado", true);
            mav.addObject("action", "guardarEmpleados");
            mav.setViewName("/admin/usuarios-formulario");
        } else {

            try {

                usuarioService.save(usuario, rol, imagen);
                attributes.addFlashAttribute("exito", "REGISTRO CON EXITO!");
                mav.setViewName("redirect:/usuario/todos");
            } catch (Exception e) {

                attributes.addFlashAttribute("error", e.getMessage());
                mav.setViewName("redirect:/usuario/todos");
            }

        }

        return mav;
    }

    @GetMapping("/editar/{id}")
    public ModelAndView formEditarUsuario(@PathVariable Integer id) {

        ModelAndView mav = new ModelAndView("/admin/usuarios-formulario");
        mav.addObject("usuarios", usuarioService.findAll());
        mav.addObject("usuario", usuarioService.finById(id));
        mav.addObject("roles", rolService.findAll());
        mav.addObject("estado", true);
        mav.addObject("action", "modificar");
        return mav;
    }

    @PostMapping("/modificar")
    @PreAuthorize("hasAnyRole('ADMIN','CLIENTE')")
    public ModelAndView modificarUsuario(@RequestParam(value = "imagen", required = false) MultipartFile imagen,
            @RequestParam Integer id, @Valid Usuario usuario, BindingResult bindingResult,
            @RequestParam Rol rol,
            RedirectAttributes attributes, HttpSession session) {

        ModelAndView mav = new ModelAndView();

        if (bindingResult.hasErrors()) {

            mav.addObject("usuarios", usuarioService.findAll());
            mav.addObject("usuario", usuario);
            mav.addObject("roles", rolService.findAll());
            mav.addObject("estado", true);
            mav.addObject("action", "modificar");
            mav.setViewName("/admin/usuarios-formulario");
        } else {

            try {
                Usuario user = (Usuario) session.getAttribute("user");
                if(user.getRol().getNombre().equals("CLIENTE")){

                    usuarioService.updatePerfil(id, usuario, rol, imagen);
                    attributes.addFlashAttribute("exito", "MODIFICACION EXITOSA!");
                    mav.setViewName("redirect:/");

                }else{
                    usuarioService.update(id, usuario, rol, imagen);
                    attributes.addFlashAttribute("exito", "MODIFICACION EXITOSA!");
                    mav.setViewName("redirect:/usuario/todos");
                }

            } catch (Exception e) {

                attributes.addFlashAttribute("error", e.getMessage());
                if(usuario.getRol().getNombre().equals("CLIENTE")){
                    mav.setViewName("redirect:/");
                }else{
                    mav.setViewName("redirect:/usuario/todos");
                }

            }

        }

        return mav;
    }

    @PostMapping("/eliminar/{id}")
    public RedirectView eliminarUsuario(@PathVariable Integer id, RedirectAttributes attributes) {

        RedirectView redirectView = new RedirectView("/usuario/todos");
        try {

            usuarioService.disable(id);
            attributes.addFlashAttribute("exito", "USUARIO ELIMINADO CON EXITO!");
        } catch (Exception e) {

            attributes.addFlashAttribute("error", e.getMessage());
            redirectView.setUrl("/usuario/todos");
        }

        return redirectView;
    }

    /* registro unico del cliente */
    @GetMapping("/registrarse")
    public ModelAndView formRegistroCliente(ModelAndView mav) {

        mav = new ModelAndView("signup");
        mav.addObject("usuario", new Usuario());
        return mav;
    }

    @PostMapping("/guardar")
    public ModelAndView guardarCliente(@RequestParam(value = "imagen", required = false) MultipartFile imagen,
            @Valid @ModelAttribute Usuario usuario,
            BindingResult bindingResult,
            RedirectAttributes attributes) {

        ModelAndView mav = new ModelAndView();

        if (bindingResult.hasErrors()) {

            mav.setViewName("signup");
        } else {

            try {

                usuarioService.save(usuario, null, imagen);
                attributes.addFlashAttribute("exito", "REGISTRO CON EXITO!");
                mav.setViewName("redirect:/");
            } catch (Exception e) {

                attributes.addFlashAttribute("error", e.getMessage());
                mav.setViewName("redirect:/usuario/registrarse");
            }

        }
        return mav;
    }

    @GetMapping("/misdatos")
    public ModelAndView editarMisDatos(HttpSession session, HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("public/user-formulario");

        if (session.getAttribute("user") != null) {
            Usuario user = (Usuario) session.getAttribute("user");
            mav.addObject("usuario", user);
            mav.addObject("action", "modificar");
            mav.addObject("logueado", "true");
        } else {
            mav.addObject("logueado", "false");
        }
        return mav;
    }

}
