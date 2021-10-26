package za.ac.nwu.ac.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import za.ac.nwu.ac.domain.dto.PhotoDto;
import za.ac.nwu.ac.domain.persistence.UserAccount;
import za.ac.nwu.ac.domain.persistence.photo.Photo;
import za.ac.nwu.ac.domain.persistence.photo.PhotoMetaData;
import za.ac.nwu.ac.logic.service.PhotoService;
import za.ac.nwu.ac.logic.service.UserAccountService;

import java.time.LocalDate;
import java.util.Date;

@Controller
@Component
public class PhotoController {
    private final PhotoService photoService;

    private final UserAccountService userAccountService;

    @Autowired
    public PhotoController(PhotoService photoService, UserAccountService userAccountService) {
        this.photoService = photoService;
        this.userAccountService = userAccountService;
    }

    @RequestMapping(value = "/photo-upload/{id}", method = RequestMethod.GET)
    public String showUploadPhoto(@PathVariable Long id, Model model){
        model.addAttribute("user",userAccountService.findUserById(id));
        return "photo-upload";
    }

    @RequestMapping(value = "/photo-upload/{id}",method = RequestMethod.POST)
    public String uploadPhoto(@PathVariable Long id, @RequestParam("file")MultipartFile file, Model model){
        try {
            userAccountService.addPhotoToOwnedAlbum(userAccountService.findUserById(id),file);
            model.addAttribute("user",userAccountService.findUserById(id));
            return "view-albums";
        } catch (Exception e){
            model.addAttribute("user",userAccountService.findUserById(id));
            LoggingController.logError(e.getMessage());
            return "view-albums";
        }
    }

    @RequestMapping(value = "/view-photo/{id}/{photoId}", method = RequestMethod.GET)
    public String showViewPhoto(@PathVariable Long id, @PathVariable Long photoId, Model model){
        try{
            UserAccount user = userAccountService.findUserById(id);
            model.addAttribute("user", user);
            Photo photo = photoService.findPhotoById(photoId);
            model.addAttribute("photo", photo);
            if (photo.getPhotoMetaData().getOwner().getUserAccountId().equals(id)){
                model.addAttribute("ownedPhoto", true);
            }
            else {
                model.addAttribute("sharedPhoto", true);
            }
            return "view-photo";
        } catch (Exception e){
            model.addAttribute("couldNotLoad", true);
            return "view-photo";
        }
    }

    @RequestMapping(value = "/share-photo/{id}/{photoId}", method = RequestMethod.POST)
    public String sharePhoto(@PathVariable Long id, @PathVariable Long photoId, @RequestParam("email") String email, Model model){
        userAccountService.sharePhotoToUser(userAccountService.findUserById(id),photoId, userAccountService.findUserByEmail(email));
        model.addAttribute("user",userAccountService.findUserById(id));
        model.addAttribute("photo",photoService.findPhotoById(photoId));
        return "view-photo";
    }

    @RequestMapping(value = "/move-to-album/{id}/{photoId}", method = RequestMethod.POST)
    public String movePhotoToAlbum(@PathVariable Long id, @PathVariable Long photoId, @RequestParam("albumName") String albumName, Model model){
        userAccountService.addPhotoToAlbum(userAccountService.findUserById(id), photoService.findPhotoById(photoId), albumName);
        model.addAttribute("user",userAccountService.findUserById(id));
        model.addAttribute("photo",photoService.findPhotoById(photoId));
        return "view-photo";
    }


}
