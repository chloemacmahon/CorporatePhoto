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
import za.ac.nwu.ac.domain.dto.TagsUsedDto;
import za.ac.nwu.ac.domain.exception.CouldNotDeletePhotoException;
import za.ac.nwu.ac.domain.persistence.UserAccount;
import za.ac.nwu.ac.domain.persistence.photo.Photo;
import za.ac.nwu.ac.domain.persistence.photo.PhotoMetaData;
import za.ac.nwu.ac.logic.service.AlbumService;
import za.ac.nwu.ac.logic.service.PhotoMetaDataService;
import za.ac.nwu.ac.logic.service.PhotoService;
import za.ac.nwu.ac.logic.service.UserAccountService;

import java.time.LocalDate;

@Controller
@Component
public class PhotoController {
    private final PhotoService photoService;

    private final PhotoMetaDataService photoMetaDataService;

    private final UserAccountService userAccountService;

    private final AlbumService albumService;

    @Autowired
    public PhotoController(PhotoService photoService, PhotoMetaDataService photoMetaDataService, UserAccountService userAccountService, AlbumService albumService) {
        this.photoService = photoService;
        this.photoMetaDataService = photoMetaDataService;
        this.userAccountService = userAccountService;
        this.albumService = albumService;
    }

    @RequestMapping(value = "/photo-upload/{id}", method = RequestMethod.GET)
    public String showUploadPhoto(@PathVariable Long id, Model model) {
        model.addAttribute("user", userAccountService.findUserById(id));
        model.addAttribute("tags", photoMetaDataService.viewAllTags());
        model.addAttribute("tagsUsed", new TagsUsedDto());
        return "photo-upload";
    }

    @RequestMapping(value = "/photo-upload", method = RequestMethod.GET)
    public String showUploadPhoto(Model model) {
        model.addAttribute("user", (UserAccount) model.asMap().get("user"));
        model.addAttribute("tags", photoMetaDataService.viewAllTags());
        model.addAttribute("tagsUsed", new TagsUsedDto());
        return "photo-upload";
    }

    @RequestMapping(value = "/photo-upload/{id}", method = RequestMethod.POST)
    public String uploadPhoto(@PathVariable Long id, @RequestParam("file") MultipartFile file, TagsUsedDto tagsUsedDto, Model model) {
        try {
            PhotoMetaData photoMetaData = photoMetaDataService.createPhotoMetaData(LocalDate.now(), userAccountService.findUserById(id), tagsUsedDto.getTags());
            LoggingController.logInfo(tagsUsedDto.getTags().toString());
            userAccountService.addPhotoToOwnedAlbum(userAccountService.findUserById(id), photoMetaData, file);
            model.addAttribute("user", userAccountService.findUserById(id));
            return "view-albums";
        } catch (Exception e) {
            model.addAttribute("user", userAccountService.findUserById(id));
            model.addAttribute("photoUploadError", true);
            LoggingController.logError(e.getMessage());
            return "view-albums";
        }
    }

    @RequestMapping(value = "/view-photo/{id}/{photoId}", method = RequestMethod.GET)
    public String showViewPhoto(@PathVariable Long id, @PathVariable Long photoId, Model model) {
        try {
            UserAccount user = userAccountService.findUserById(id);
            model.addAttribute("user", user);
            Photo photo = photoService.findPhotoById(photoId);
            model.addAttribute("photo", photo);
            if (photo.getPhotoMetaData().getOwner().getUserAccountId().equals(id)) {
                model.addAttribute("ownedPhoto", true);
            } else {
                model.addAttribute("sharedPhoto", true);
            }
            return "view-photo";
        } catch (Exception e) {
            model.addAttribute("couldNotCreateTag", true);
            return "view-photo";
        }
    }

    @RequestMapping(value = "/share-photo/{id}/{photoId}", method = RequestMethod.POST)
    public String sharePhoto(@PathVariable Long id, @PathVariable Long photoId, @RequestParam("email") String email, Model model) {
        try {
            userAccountService.sharePhotoToUser(userAccountService.findUserById(id), photoId, userAccountService.findUserByEmail(email));
            model.addAttribute("user", userAccountService.findUserById(id));
            model.addAttribute("photo", photoService.findPhotoById(photoId));
            return "view-albums";
        } catch (Exception e){
            LoggingController.logError(e.getMessage());
            return showViewPhoto(id, photoId, model);
        }
    }

    @RequestMapping(value = "/move-to-album/{id}/{photoId}", method = RequestMethod.POST)
    public String movePhotoToAlbum(@PathVariable Long id, @PathVariable Long photoId, @RequestParam("albumName") String albumName, Model model) {
        try {
            userAccountService.addPhotoToAlbum(userAccountService.findUserById(id), photoService.findPhotoById(photoId), albumName);
            model.addAttribute("user", userAccountService.findUserById(id));
            model.addAttribute("photo", photoService.findPhotoById(photoId));
            return "view-albums";
        } catch (Exception e){
            model.addAttribute("user", userAccountService.findUserById(id));
            model.addAttribute("photo", photoService.findPhotoById(photoId));
            model.addAttribute("couldNotMoveError", true);
            return showViewPhoto(id, photoId,model);
        }
    }

    @RequestMapping(value = "/create-tag/{id}", method = RequestMethod.POST)
    public String createTag(@PathVariable Long id, @RequestParam("tagName") String tagName, @RequestParam("tagDescription") String tagDescription, Model model) {
        try {
            photoMetaDataService.createTag(tagName, tagDescription);
            UserAccount user = userAccountService.findUserById(id);
            model.addAttribute("user", user);
            LoggingController.logInfo("");
            return showUploadPhoto(model) /*"/photo-upload"*/;
        } catch (Exception e) {
            model.addAttribute("couldNotCreateTag", true);
            LoggingController.logError(e.getMessage());
            return "view-photo";
        }
    }

    @RequestMapping(value = "/delete-photo/{id}/{photoId}", method = RequestMethod.GET)
    public String deletePhoto(@PathVariable Long id, @PathVariable Long photoId, Model model) {
        try {
            photoService.deletePhotoFromDatabase(photoId);
            model.addAttribute("user", userAccountService.findUserById(id));
            LoggingController.logInfo(id.toString());
            return "view-albums";
        } catch (Exception e) {
            model.addAttribute("user", userAccountService.findUserById(id));
            LoggingController.logError(e.getMessage());
            model.addAttribute("deletePhotoError", true);
            return "view-albums";
        }
    }

    @RequestMapping(value = "/remove-photo-from-album/{id}/{photoId}/{albumId}", method = RequestMethod.GET)
    public String removePhotoFromAlbum(@PathVariable Long id, @PathVariable Long photoId, @PathVariable Long albumId, Model model) {
        try {
            if (userAccountService.findUserById(id).getAlbums().contains(albumService.findAlbumById(albumId))) {
                albumService.deletePhotoFromAlbum(photoId, albumId);
                model.addAttribute("user", userAccountService.findUserById(id));
                return "view-albums";
            } else{
                throw new CouldNotDeletePhotoException("Album not owned");
            }
        } catch (Exception e) {
            model.addAttribute("user", userAccountService.findUserById(id));
            LoggingController.logInfo(e.getMessage());
            model.addAttribute("deletePhotoError", true);
            return "view-albums";
        }
    }

    @RequestMapping(value = "/edit-photo-data/{id}/{photoId}", method = RequestMethod.GET)
    public String editShowPhotoMetaDataGeolocation(@PathVariable Long id, @PathVariable Long photoId,Model model){
        model.addAttribute("user", userAccountService.findUserById(id));
        model.addAttribute("photo", photoService.findPhotoById(photoId));
        model.addAttribute("geolocation", "Enter geolocation");
        model.addAttribute("tags", photoMetaDataService.viewAllTags());
        model.addAttribute("tagsUsed", new TagsUsedDto());
        return "edit-photo-data";
    }


    @RequestMapping(value = "/edit-photo-data/{id}/{photoId}", method = RequestMethod.POST)
    public String editPhotoMetaDataGeolocation(@PathVariable Long id, @PathVariable Long photoId, @RequestParam("geolocation") String geolocation, Model model){

        PhotoMetaData photoMetaData = photoMetaDataService.findPhotoMetaDataIdByPhotoId(photoId);
        photoMetaDataService.updatePhotoMetaDataGeolocation(photoMetaData.getMetaDataId(), geolocation);

        model.addAttribute("user", userAccountService.findUserById(id));
        model.addAttribute("photo", photoService.findPhotoById(photoId));
        model.addAttribute("geolocation", "Enter geolocation");

        return "edit-photo-data";
    }

    @RequestMapping(value="/edit-photo-tag/{id}/{photoId}", method = RequestMethod.POST)
    public String editPhotoMetaDataTags(@PathVariable Long id, @PathVariable Long photoId, @RequestParam("oldTagName") String oldTagName ,@RequestParam("newTagName") String newTagName, Model model){
        PhotoMetaData photoMetaData = photoMetaDataService.findPhotoMetaDataIdByPhotoId(photoId);
        photoMetaDataService.updatePhotoTag(photoMetaData.getMetaDataId(), photoMetaDataService.findPhotoMetaDataTag(oldTagName), newTagName);

        model.addAttribute("tags", photoMetaDataService.viewAllTags());
        model.addAttribute("tagsUsed", new TagsUsedDto());

        return "edit-photo-data";
    }

}
