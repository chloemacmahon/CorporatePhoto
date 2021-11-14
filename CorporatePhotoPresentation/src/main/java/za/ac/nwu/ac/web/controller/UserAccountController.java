package za.ac.nwu.ac.web.controller;


//import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import za.ac.nwu.ac.domain.dto.UserAccountDto;
import za.ac.nwu.ac.domain.exception.InvalidEmailException;
import za.ac.nwu.ac.domain.exception.InvalidNameException;
import za.ac.nwu.ac.domain.exception.InvalidPasswordException;
import za.ac.nwu.ac.domain.exception.InvalidSurnameException;
import za.ac.nwu.ac.logic.implementation.ValidationServiceImpl;
import za.ac.nwu.ac.logic.service.UserAccountService;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;


@Controller
public class UserAccountController {

    private UserAccountService userAccountService;
    private HttpSession session;

    @Autowired
    public UserAccountController(UserAccountService userAccountService, HttpSession session) {
        this.userAccountService = userAccountService;
        this.session = session;
    }

    @GetMapping(value = "log-in")
    public String showLogInPage(Model model){
        model.addAttribute("user", new UserAccountDto());
        return "log-in";
    }

    @RequestMapping(value = "log-in", method = RequestMethod.POST)
    public String logInUser(@Valid UserAccountDto userAccountDto, BindingResult bindingResult, Model model){
        if(bindingResult.hasErrors()) {
            model.addAttribute("user", new UserAccountDto());
            return "log-in";
        }
        try {
            model.addAttribute("user", userAccountService.logInUser(userAccountDto));
            LoggingController.logInfo(userAccountService.logInUser(userAccountDto).getEmail());
            return viewAlbums(userAccountService.logInUser(userAccountDto).getUserAccountId(),model);
        } catch (RuntimeException e){
            LoggingController.logError(e.getMessage());
            model.addAttribute("logInError", true);
            model.addAttribute("user", new UserAccountDto());
            return "log-in";
        }
    }

    @GetMapping(value = "create-account")
    public String showCreateAccount(Model model){
        model.addAttribute("user", new UserAccountDto());
        return "create-account";
    }

    @RequestMapping(value = "create-account", method = RequestMethod.POST)
    public String createUserAccount(@Valid UserAccountDto userAccountDto, BindingResult bindingResult, Model model){
        if(bindingResult.hasErrors()) {
            model.addAttribute("emptyFieldError", true);
            model.addAttribute("user", userAccountDto);
            return "create-account";
        }
        try {
            return viewAlbums(userAccountService.logInUser(userAccountDto).getUserAccountId(),model);
        } catch (RuntimeException e){
            if(e instanceof InvalidPasswordException) {
                model.addAttribute("passwordError", true);
            }
            if(e instanceof InvalidEmailException) {
                model.addAttribute("emailError", true);
            }
            if(e instanceof InvalidNameException) {
                model.addAttribute("nameError", true);
            }
            if(e instanceof InvalidSurnameException) {
                model.addAttribute("surnameError", true);
            }
            model.addAttribute("user", userAccountDto);
            return "create-account";
        }
    }


    @GetMapping(value = "view-albums/{id}")
    public String viewAlbums(@PathVariable Long id, Model model){
        try{
            model.addAttribute("user",userAccountService.findUserById(id));
            return "view-albums";
        } catch(Exception e){
            return showLogInPage(model);
        }
    }
}
