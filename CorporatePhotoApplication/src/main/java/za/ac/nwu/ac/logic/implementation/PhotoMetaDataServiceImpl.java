package za.ac.nwu.ac.logic.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import za.ac.nwu.ac.domain.dto.PhotoMetaDataDto;
import za.ac.nwu.ac.domain.exception.FailedToCreateTagException;
import za.ac.nwu.ac.domain.exception.PhotoMetaDataDoesNotExistException;
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

    public PhotoMetaData createPhotoMetaData(LocalDate dateCaptured, UserAccount owner) {
        PhotoMetaData photoMetaData = new PhotoMetaData(dateCaptured, owner);
        return photoMetaDataRepository.save(photoMetaData);
    }

    public PhotoMetaData createPhotoMetaData(LocalDate dateCaptured, UserAccount owner, List<Tag> tags) {
        PhotoMetaData photoMetaData = new PhotoMetaData(dateCaptured, owner, tags);
        return photoMetaDataRepository.save(photoMetaData);
    }

    public PhotoMetaData createPhotoMetaData(LocalDate dateCaptured, UserAccount owner, List<Tag> tags, String geolocation){
        PhotoMetaData photoMetaData = new PhotoMetaData(dateCaptured, owner, tags, geolocation);
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

    public PhotoMetaData viewPhotoMetaData(Long metaDataId){
        if(photoMetaDataRepository.findById(metaDataId).isPresent())
            return photoMetaDataRepository.findById(metaDataId).get();
        else
            throw new PhotoMetaDataDoesNotExistException();
    }

    public void deletePhotoMetaDataFromDatabase(Long photoId, Long metaDataId){
        if(photoMetaDataRepository.findById(metaDataId).isPresent()) {
            photoMetaDataRepository.deleteById(metaDataId);
            photoRepository.deleteById(photoId);
        }
        else
            throw new PhotoMetaDataDoesNotExistException();
    }

//    public void updatePhotoMetaDataGeolocation(PhotoMetaDataDto photoMetaDataDto, Long metaDataID, String tag, String geolocation){
//        if (photoMetaDataRepository.findById(metaDataID).isPresent())
//
//        else
//            throw new PhotoMetaDataDoesNotExistException();
//    }
}
