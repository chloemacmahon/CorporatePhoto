package za.ac.nwu.ac.logic.implementation;

import com.azure.storage.internal.avro.implementation.schema.primitive.AvroNullSchema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import za.ac.nwu.ac.domain.exception.*;
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

    public void readPhotoMetaData(String photoPath) {

    }

    public String readPhotoMetaDataGeolocation(Long photoId, Long metaDataId){
        try{
            if(photoRepository.findById(photoId).isPresent()){
                PhotoMetaData photoMetaData = photoMetaDataRepository.findById(metaDataId).get();
                return photoMetaData.getGeolocation();
            }else {
                throw new PhotoDoesNotExistException();
            }
        }catch (Exception e) {
            throw new CouldNotFindPhotoMetaDataException("Photo geolocation could not be found");
        }
    }

    public PhotoMetaData createPhotoMetaData(LocalDate dateCaptured, UserAccount owner, List<Tag> tags) {
        PhotoMetaData photoMetaData = new PhotoMetaData(dateCaptured, owner, tags);
        return photoMetaDataRepository.save(photoMetaData);
    }

    public Photo addTagToPhoto(Photo photo, Tag tag) {
        photo.getPhotoMetaData().addTagToPhotoMetaData(tag);
        return photoRepository.save(photo);
    }

    public Tag createTag(String tagName, String tagDescription) {
        try {
            Tag tag = new Tag(tagName, tagDescription);
            return tagRepository.save(tag);
        } catch (Exception e) {
            throw new FailedToCreateTagException();
        }
    }


    public List<Tag> viewAllTags() {
        return tagRepository.findAll();
    }

    public void deletePhotoMetaDataFromDatabase(Long photoId, Long metaDataId) {
        try {
            if (photoMetaDataRepository.findById(metaDataId).isPresent()) {
                photoMetaDataRepository.deleteById(metaDataId);
            } else
                throw new PhotoMetaDataDoesNotExistException();
        } catch (Exception e) {
            throw new CouldNotDeletePhotoMetaDataException();
        }
    }

    public void updatePhotoMetaDataGeolocation(Long photoMetaDataId, String geolocation){
        try{
            if(photoMetaDataRepository.findById(photoMetaDataId).isPresent()){
                PhotoMetaData photoMetaData = photoMetaDataRepository.findById(photoMetaDataId).get();
                photoMetaData.setGeolocation(geolocation);
                photoMetaDataRepository.save(photoMetaData);
            }
            else {
                throw new CouldNotUpdatePhotoMetaData("Could not update photo geolocation");
            }

        }catch (Exception e) {

            throw new CouldNotUpdatePhotoMetaData("Geolocation could not be updated, nested exception is (" + e.getLocalizedMessage() +")");

        }
    }

    public void removePhotoMetaDataGeolocation(Long photoMetaDataId){
        try{
            if(photoMetaDataRepository.findById(photoMetaDataId).isPresent()){
                PhotoMetaData photoMetaData = photoMetaDataRepository.findById(photoMetaDataId).get();
                photoMetaData.setGeolocation(null);
                photoMetaDataRepository.save(photoMetaData);

            }
            else {
                throw new CouldNotRemovePhotoMetaDataException("Could not remove photo geolocation");
            }

        }catch (Exception e) {

            throw new CouldNotRemovePhotoMetaDataException("Geolocation could not be remove from photo, nested exception is (" + e.getLocalizedMessage() +")");

        }
    }

    public void updatePhotoTag (Long photoMetaDataId, Long tagId, String newTagName){
        try{
            if(photoMetaDataRepository.findById(photoMetaDataId).isPresent()){
                if(tagRepository.findById(tagId).isPresent())
                {
                    Tag tag = tagRepository.findById(tagId).get();
                    tag.setTagName(newTagName);
                    tagRepository.save(tag);
                }
                else{
                    throw new CouldNotFindTagException();
                }
            }
            else{
                throw new CouldNotFindPhotoMetaDataException();
            }
        }catch (Exception e)
        {
            throw new CouldNotUpdatePhotoTagException();
        }
    }

    public Long findPhotoMetaDataTag(String tagName){
        try{
            return tagRepository.findTagIdByTagName(tagName);
        } catch (Exception e) {
            throw new CouldNotFindTagException("Could not find tag: " +tagName+ " in Database");
        }
    }

    public void removePhotoTagFromPhotoMetaData (Long photoMetaDataId, Long tagId){
        try{
            if(photoMetaDataRepository.findById(photoMetaDataId).isPresent()){
                if(tagRepository.findById(tagId).isPresent())
                {
                   //Tag tag = tagRepository.findById(tagId).get();
                   PhotoMetaData photoMetaData = photoMetaDataRepository.findById(photoMetaDataId).get();
                   photoMetaData.getTags().remove(tagId);
                   photoMetaDataRepository.save(photoMetaData);

                }
                else{
                    throw new CouldNotFindTagException();
                }
            }
            else{
                throw new CouldNotFindPhotoMetaDataException();
            }
        }catch (Exception e) {
            throw new CouldNotDeletePhotoTagException("Could not remove tag from photo");
        }
    }

    public void deletePhotoTagFromDatabase (Long photoMetaDataId, Long tagId){
        try{
            if(photoMetaDataRepository.findById(photoMetaDataId).isPresent()){
                if(tagRepository.findById(tagId).isPresent())
                {
                    //Tag tag = tagRepository.findById(tagId).get();
                    PhotoMetaData photoMetaData = photoMetaDataRepository.findById(photoMetaDataId).get();
                    photoMetaData.getTags().remove(tagId);
                    photoMetaDataRepository.save(photoMetaData);

                    tagRepository.deleteById(tagId);
                }
                else{
                    throw new CouldNotFindTagException();
                }
            }
            else{
                throw new CouldNotFindPhotoMetaDataException();
            }
        }catch (Exception e) {
            throw new CouldNotDeletePhotoTagException();
        }
    }

    public List<Photo> searchPhotoByDateCaptured(LocalDate dateCaptured, Long owner){
        List<Photo> photoList = photoMetaDataRepository.findPhotoMetaDataIdByDateCaptured(dateCaptured, owner);
        return photoMetaDataRepository.findPhotoIdByPhotoMetaDataId(photoList);
    }

    public List<Photo> searchPhotoByGeolocation(String geolocation, Long owner){
        List<Photo> photoList = photoMetaDataRepository.findPhotoMetaDataIdByGeolocation(geolocation, owner);
        return photoMetaDataRepository.findPhotoIdByPhotoMetaDataId(photoList);
    }

    public PhotoMetaData findPhotoMetaDataIdByPhotoId(Long photoId)
    {
        try{
            if(photoRepository.findById(photoId).isPresent()){
                Photo photo = photoRepository.findById(photoId).get();
                return photo.getPhotoMetaData();
            }
            else{
                throw new CouldNotFindPhotoMetaDataException();
            }

        } catch (Exception e){
            throw new CouldNotFindPhotoMetaDataException();
        }
    }

//    public List<Photo> searchPhotoByTag(Long tagId, Long owner){
//
//        return photoMetaDataRepository.findPhotoIdByPhotoMetaDataId(photoList);
//    }
}

