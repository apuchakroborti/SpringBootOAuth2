package com.apu.springmvc.springsecuritymvc.webcontroller;


import com.apu.springmvc.springsecuritymvc.business.GetPostRequestHandler;
import com.apu.springmvc.springsecuritymvc.dto.User;
import com.apu.springmvc.springsecuritymvc.services.SecurityService;
import com.apu.springmvc.springsecuritymvc.services.UserService;
import com.apu.springmvc.springsecuritymvc.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private UserValidator userValidator;

    @GetMapping("/registration")
    public String registration(Model model) {
        model.addAttribute("userForm", new User());

        return "registration";
    }

    @PostMapping("/registration")
    public String registration(@ModelAttribute("userForm") User userForm, BindingResult bindingResult, ModelMap model) {
        userValidator.validate(userForm, bindingResult);

        if (bindingResult.hasErrors()) {
            return "registration";
        }

        userService.save(userForm);

        securityService.autoLogin(userForm.getUsername(), userForm.getPasswordConfirm());

       /* //for jwt start
        try{
            GetPostRequestHandler gph=new GetPostRequestHandler();
            String res=gph.createPost();
            if(res!=null){
                model.addAttribute("message",res);
                return "redirect:/welcome";
            }
        }catch (Exception e){
            System.out.println("Exception occur while sending posting request");
        }*/

        //for jwt end
        return "redirect:/welcome";
    }

   @GetMapping("/login")
    public String login(Model model, String error, String logout) {
        if (error != null)
            model.addAttribute("error", "Your username and password is invalid.");

        if (logout != null)
            model.addAttribute("message", "You have been logged out successfully.");



        return "login";
    }

    @GetMapping({"/", "/welcome"})
    public String welcome(ModelMap model) {

        //for jwt
        try{
            GetPostRequestHandler gph=new GetPostRequestHandler();
            String res=gph.createPost();
            if(res!=null){
                model.addAttribute("message",res);
                return "/welcome";
            }
        }catch (Exception e){
            System.out.println("Exception occur while sending posting request");
        }
        return "welcome";
    }

}