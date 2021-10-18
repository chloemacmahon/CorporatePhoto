package za.ac.nwu.ac.web.controller;

import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import za.ac.nwu.ac.domain.dto.UserAccountDto;
import za.ac.nwu.ac.logic.service.UserAccountService;

import javax.validation.Valid;


@Controller
public class UserAccountController {

    private UserAccountService userAccountService;

    @Autowired
    public UserAccountController(UserAccountService userAccountService) {
        this.userAccountService = userAccountService;
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
            userAccountService.logInUser(userAccountDto);
            model.addAttribute("user", new UserAccountDto());
            return "log-in";
        } catch (RuntimeException e){
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
        if(bindingResult.hasErrors())
            return "log-in";
        try {
            userAccountService.createUserAccount(userAccountDto);
            model.addAttribute("user",new UserAccountDto());
            return "log-in";
        } catch (RuntimeException e){
            return "log-in";
        }
    }
}
