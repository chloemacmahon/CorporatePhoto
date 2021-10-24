package za.ac.nwu.ac.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import za.ac.nwu.ac.domain.persistence.UserAccount;
import za.ac.nwu.ac.logic.service.PhotoService;

@Controller
@Component
public class PhotoController {
    private final PhotoService photoService;

    @Autowired
    public PhotoController(PhotoService photoService) {
        this.photoService = photoService;
    }

    @RequestMapping(value = "/upload-photo", method = RequestMethod.GET)
    public String showUploadPhoto(Model model){
        model.addAttribute("user",(UserAccount)model.asMap().get("user"));
        return "photo-upload";
    }

    /*@RequestMapping(value = "upload-photo/{id}",method = RequestMethod.POST)
    public String uploadPhoto(@PathVariable Long id, @RequestParam("file")MultipartFile file, Model model){

    }*/
}
