package za.ac.nwu.ac.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import za.ac.nwu.ac.domain.dto.AlbumDto;
import za.ac.nwu.ac.domain.persistence.UserAccount;
import za.ac.nwu.ac.logic.service.AlbumService;
import za.ac.nwu.ac.logic.service.UserAccountService;

@Controller
public class AlbumController {

    private final UserAccountService userAccountService;

    private final AlbumService albumService;

    @Autowired
    public AlbumController(UserAccountService userAccountService, AlbumService albumService) {
        this.userAccountService = userAccountService;
        this.albumService = albumService;
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

    @RequestMapping(value = "/edit-albums/{id}", method = RequestMethod.GET)
    public String showEditAlbum(@PathVariable Long id, Model model){
        model.addAttribute("user", userAccountService.findUserById(id));
        return "edit-albums";
    }

    @RequestMapping(value = "/delete-album/{id}/{albumId}",method = RequestMethod.GET)
    public String deleteAlbum(@PathVariable Long id, @PathVariable Long albumId, Model model){
        try{
            model.addAttribute("user",userAccountService.findUserById(id));
            albumService.deleteAlbum(albumId, id);
            return "view-albums";
        } catch (Exception e){
            LoggingController.logError(e.getMessage());
            model.addAttribute("user",userAccountService.findUserById(id));
            model.addAttribute("deleteAlbumError",true);
            return "view-albums";
        }
    }

    @RequestMapping(value = "/update-album-name/{id}/{albumId}", method = RequestMethod.POST)
    public String updateAlbum(@PathVariable Long id, @PathVariable Long albumId, @RequestParam("albumName") String albumName, Model model) {
        try {
            albumService.updateAlbumName(albumId,albumName);
            UserAccount user = userAccountService.findUserById(id);
            model.addAttribute("user", user);
            return "view-albums";
        } catch (Exception e) {
            model.addAttribute("couldNotCreateTag", true);
            LoggingController.logError(e.getMessage());
            UserAccount user = userAccountService.findUserById(id);
            model.addAttribute("user", user);
            model.addAttribute("updateAlbumError", true);
            return "view-albums";
        }
    }
}
