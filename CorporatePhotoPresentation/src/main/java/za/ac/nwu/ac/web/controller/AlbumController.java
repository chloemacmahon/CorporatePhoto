package za.ac.nwu.ac.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import za.ac.nwu.ac.domain.dto.AlbumDto;
import za.ac.nwu.ac.domain.persistence.UserAccount;
import za.ac.nwu.ac.logic.service.UserAccountService;

@Controller
public class AlbumController {

    private UserAccountService userAccountService;

    @Autowired
    public AlbumController(UserAccountService userAccountService) {
        this.userAccountService = userAccountService;
    }

    @RequestMapping(value = "create-album/{id}", method = RequestMethod.GET)
    public String showCreateAlbum(@PathVariable Long id, Model model){
        model.addAttribute("user", userAccountService.findUserById(id));
        model.addAttribute("album", new AlbumDto());
        return "create-album";
    }

    @RequestMapping(value = "create-album/{id}", method = RequestMethod.POST)
    public String createAlbum(@PathVariable Long id, AlbumDto albumDto, BindingResult bindingResult, Model model){
        if (bindingResult.hasErrors()) {
            model.addAttribute("user", (UserAccount) model.asMap().get("user"));
            return "view-albums";
        }
        userAccountService.createAlbum(userAccountService.findUserById(id), albumDto.getAlbumName());
        model.addAttribute("user", userAccountService.findUserById(id));
        return "view-albums";
    }

}
