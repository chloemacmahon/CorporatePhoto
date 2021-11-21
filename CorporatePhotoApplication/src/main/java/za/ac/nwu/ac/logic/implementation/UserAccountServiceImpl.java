package za.ac.nwu.ac.logic.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import za.ac.nwu.ac.domain.dto.PhotoDto;
import za.ac.nwu.ac.domain.dto.UserAccountDto;
import za.ac.nwu.ac.domain.exception.*;
import za.ac.nwu.ac.domain.persistence.UserAccount;
import za.ac.nwu.ac.domain.persistence.album.Album;
import za.ac.nwu.ac.domain.persistence.album.SharedAlbum;
import za.ac.nwu.ac.domain.persistence.photo.Photo;
import za.ac.nwu.ac.domain.persistence.photo.PhotoMetaData;
import za.ac.nwu.ac.logic.service.AlbumService;
import za.ac.nwu.ac.logic.service.PhotoService;
import za.ac.nwu.ac.logic.service.UserAccountService;
import za.ac.nwu.ac.repository.AlbumRepository;
import za.ac.nwu.ac.repository.PhotoRepository;
import za.ac.nwu.ac.repository.UserAccountRepository;

import java.time.LocalDate;
import java.util.List;

@Service
public class UserAccountServiceImpl implements UserAccountService {

    private UserAccountRepository userAccountRepository;

    private PhotoRepository photoRepository;

    private AlbumRepository albumRepository;

    private AlbumService albumService;

    private PhotoService photoService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserAccountServiceImpl(UserAccountRepository userAccountRepository, PhotoRepository photoRepository, AlbumRepository albumRepository, AlbumService albumService, PhotoService photoService) {
        this.userAccountRepository = userAccountRepository;
        this.photoRepository = photoRepository;
        this.albumRepository = albumRepository;
        this.albumService = albumService;
        this.photoService = photoService;
    }

    public UserAccount findUserById(Long id){
        if (userAccountRepository.findById(id).isPresent()) {
            UserAccount user = userAccountRepository.findById(id).get();
            if(user.getOwnedPhotosAlbum().getPhotos().size() != 0 && user.getAlbums().size() != 0){
                return user;
            } else {
                if (user.getOwnedPhotosAlbum().getPhotos().size() == 0)
                    user.generateAllPhotoSharableLinks();
                if (user.getAlbums().size() == 0)
                    user.generateAllAlbumSharableLinks();
                return userAccountRepository.save(user);
            }
        }else
            throw new UserDoesNotExistException("User does not exist");
    }

    public UserAccount findUserByEmail(String email){
        if (userAccountRepository.findByEmail(email) != null) {
            UserAccount user = userAccountRepository.findByEmail(email);
            if(user.getOwnedPhotosAlbum().getPhotos().size() == 0)
                user.generateAllPhotoSharableLinks();
            if(user.getAlbums().size() == 0)
                user.generateAllAlbumSharableLinks();
            return userAccountRepository.save(user);
        }else
            throw new UserDoesNotExistException("User does not exist");
    }

    public UserAccount createUserAccount(UserAccountDto userAccountDto){
        if(userAccountRepository.findByEmail(userAccountDto.getEmail()) != null){
            throw new InvalidEmailException("Email is already in the system");
        }
        if(!ValidationServiceImpl.isValidName(userAccountDto.getName())){
            throw new InvalidNameException("Name does not start with a capital or does not just contain letters");
        }
        if(!ValidationServiceImpl.isValidName(userAccountDto.getSurname())){
            throw new InvalidSurnameException("Surname does not start with a capital or does not just contain letters");
        }
        if(!ValidationServiceImpl.isValidPassword(userAccountDto.getPassword())){
            throw new InvalidPasswordException("Password is not valid according to password standards");
        }
        if(!ValidationServiceImpl.isValidEmail(userAccountDto.getEmail())){
            throw new InvalidEmailException("Email is not valid ");
        }
        String encodedPassword = passwordEncoder.encode(userAccountDto.getPassword());
        UserAccount userAccount = new UserAccount(userAccountDto.getEmail(), userAccountDto.getName(), userAccountDto.getSurname(), encodedPassword);
        return userAccountRepository.save(userAccount);
    }

    public UserAccount logInUser(UserAccountDto userAccountDto){
        UserAccount userAccount = userAccountRepository.findByEmail(userAccountDto.getEmail());
        if(userAccount == null){
            throw new UserDoesNotExistException("User with this email address does not exist");
        }
        if(!passwordEncoder.matches(userAccountDto.getPassword(), userAccount.getEncodedPassword())){
            throw new InvalidPasswordException("Password is incorrect"+ userAccount.getEncodedPassword());
        }
        return userAccount;
    }

    public UserAccount createAlbum(UserAccount userAccount, String albumName) {
        userAccount.createAlbum(albumName);
        UserAccount userAccount1 = userAccountRepository.save(userAccount);
        userAccount1.generateAllAlbumSharableLinks();
        return userAccountRepository.save(userAccount1);
    }

    public void addPhotoToOwnedAlbum(UserAccount userAccount, MultipartFile photo){
        PhotoMetaData photoMetaData = new PhotoMetaData(LocalDate.now(), userAccount);
        addPhotoToOwnedAlbum(userAccount,photoMetaData,photo);
    }

    public void addPhotoToOwnedAlbum(UserAccount userAccount, PhotoMetaData photoMetaData, MultipartFile photo){
        PhotoDto photoDto = new PhotoDto(photoMetaData);
        try {
            userAccount.addOwnedPhoto(photoService.createPhoto(photoDto, photo, userAccount.generatePhotoName()));
            UserAccount userAccount1 = userAccountRepository.save(userAccount);
            userAccount1.getOwnedPhotosAlbum().generateAllPhotoSharableLinks();
            userAccountRepository.save(userAccount1);
        } catch (Exception e){
            throw new FailedToCreatePhotoException("Could not create photo nested exception"+e.getMessage());
        }
    }

    public void sharePhotoToUser(UserAccount ownerAccount, Long photoId, UserAccount sharedAccount){
        try {
            if(photoRepository.findById(photoId).isPresent()){
                Photo photoToShare = photoRepository.findById(photoId).get();
                if(photoToShare.getPhotoMetaData().getOwner().getUserAccountId().equals(ownerAccount.getUserAccountId())){
                    sharedAccount.addSharedPhoto(photoToShare);
                    userAccountRepository.save(sharedAccount);
                } else{
                    throw new AccessNotSufficientException("Only the owner can share a photo");
                }
            } else{
                throw new FailedToSharePhotoException("Photo could not be found");
            }
        } catch(Exception e){
            throw new FailedToSharePhotoException(e.getMessage());
        }
    }

    public void sharePhotoWithLink(Long id, String sharableLink){
        try{
            Photo photo = photoService.findPhotoBySharableLink(sharableLink);
            UserAccount user = findUserById(id);
            user.addSharedPhoto(photo);
            userAccountRepository.save(user);
        } catch(Exception e){
            throw new FailedToSharePhotoException("could not share photo nested exception is: "+e.getLocalizedMessage());
        }
    }

    public void addPhotoToAlbum(UserAccount owner, Photo photo, String albumName){
        try{
                Album albumToAdd = owner.findAlbumByName(albumName);
                albumToAdd.addPhotoToAlbum(photo);
                userAccountRepository.save(owner);

        } catch (Exception e){
            throw new FailedToCreatePhotoException();
        }
    }

    public void addPhotoToAlbum(UserAccount owner, MultipartFile photo, String albumName){
        try{
            addPhotoToOwnedAlbum(owner,photo);
            addPhotoToAlbum(owner, owner.findMostRecentlyAddedOwnedPhoto(), albumName);
        } catch (Exception e){
            throw new FailedToCreatePhotoException();
        }
    }

    public void shareAlbumWithUser(UserAccount sharedAccount, SharedAlbum album){
        try{
            album.addAccessAccount(sharedAccount);
            sharedAccount.addAlbumToAlbums(album);
            albumRepository.save(album);
            userAccountRepository.save(sharedAccount);
        } catch (Exception e){
            throw new FailedToShareAlbum("Failed to share album nested exception is: "+ e.getLocalizedMessage());
        }
    }

    public List<UserAccount> findUsersWithPhotoAccess(Long photoId){
        try{
            return userAccountRepository.findBySharedPhotosAlbumPhotosPhotoId(photoId);
        } catch (Exception e){
            throw new UserDoesNotExistException("Users could not be found nested exception "+ e.getLocalizedMessage());
        }
    }

    public void removeAccessToPhoto(UserAccount user, Photo photo){
        try{
            user.removeSharedPhoto(photo);
            userAccountRepository.save(user);
        } catch (Exception e){
            throw new FailedToRemoveAccess("Failed to remove access nested exception is "+ e.getLocalizedMessage());
        }
    }

    public void deleteUserAccount(UserAccount userAccount){
        try {
            for (SharedAlbum album : userAccount.getAlbums()) {
                if (!album.getOwner().equals(userAccount)) {
                    album.removeAccess(userAccount);
                    albumRepository.save(album);
                    userAccount.getAlbums().remove(album);
                } else {
                    userAccount.getAlbums().remove(album);
                    albumService.deleteAlbum(album.getAlbumId(),userAccount.getUserAccountId());
                }
                userAccountRepository.save(userAccount);
            }
            for (Photo photo: userAccount.getOwnedPhotosAlbum().getPhotos()) {
                userAccount.getOwnedPhotosAlbum().getPhotos().remove(photo);
                photoService.deletePhotoFromDatabase(photo.getPhotoId());
            }
            userAccountRepository.save(userAccount);
            userAccountRepository.delete(userAccount);
        } catch (Exception e){
            throw new FailedToDeleteUser("Failed to delete user nested exception is "+ e.getLocalizedMessage());
        }
    }
}
