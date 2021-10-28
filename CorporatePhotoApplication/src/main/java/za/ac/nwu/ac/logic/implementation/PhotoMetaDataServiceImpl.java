package za.ac.nwu.ac.logic.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import za.ac.nwu.ac.domain.exception.FailedToCreateTagException;
import za.ac.nwu.ac.domain.persistence.UserAccount;
import za.ac.nwu.ac.domain.persistence.photo.Photo;
import za.ac.nwu.ac.domain.persistence.photo.PhotoMetaData;
import za.ac.nwu.ac.domain.persistence.photo.Tag;
import za.ac.nwu.ac.logic.service.PhotoMetaDataService;
import za.ac.nwu.ac.repository.PhotoMetaDataRepository;
import za.ac.nwu.ac.repository.PhotoRepository;
import za.ac.nwu.ac.repository.TagRepository;

import java.time.LocalDate;
import java.util.List;

@Service
public class PhotoMetaDataServiceImpl implements PhotoMetaDataService {

    private PhotoMetaDataRepository photoMetaDataRepository;

    private PhotoRepository photoRepository;

    private TagRepository tagRepository;

    @Autowired
    public PhotoMetaDataServiceImpl(PhotoMetaDataRepository photoMetaDataRepository, PhotoRepository photoRepository, TagRepository tagRepository) {
        this.photoMetaDataRepository = photoMetaDataRepository;
        this.photoRepository = photoRepository;
        this.tagRepository = tagRepository;
    }

    public void readPhotoMetaData(String photoPath){

    }

    public PhotoMetaData createPhotoMetaData(LocalDate dateCaptured, UserAccount owner, List<Tag> tags) {
        PhotoMetaData photoMetaData = new PhotoMetaData(dateCaptured, owner, tags);
        return photoMetaDataRepository.save(photoMetaData);
    }

    public Photo addTagToPhoto(Photo photo, Tag tag){
        photo.getPhotoMetaData().addTagToPhotoMetaData(tag);
        return photoRepository.save(photo);
    }

    public Tag createTag(String tagName, String tagDescription){
        try {
            Tag tag = new Tag(tagName, tagDescription);
            return tagRepository.save(tag);
        } catch (Exception e){
            throw new FailedToCreateTagException();
        }
    }

    public List<Tag> viewAllTags(){
        return tagRepository.findAll();
    }
}
