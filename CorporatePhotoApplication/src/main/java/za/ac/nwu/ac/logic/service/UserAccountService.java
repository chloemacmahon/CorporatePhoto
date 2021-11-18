package za.ac.nwu.ac.logic.service;

import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import za.ac.nwu.ac.domain.dto.UserAccountDto;
import za.ac.nwu.ac.domain.persistence.UserAccount;
import za.ac.nwu.ac.domain.persistence.album.Album;
import za.ac.nwu.ac.domain.persistence.album.SharedAlbum;
import za.ac.nwu.ac.domain.persistence.photo.Photo;
import za.ac.nwu.ac.domain.persistence.photo.PhotoMetaData;

import java.util.List;


public interface UserAccountService {

    UserAccount findUserById(Long id);

    UserAccount findUserByEmail(String email);

    UserAccount createUserAccount(UserAccountDto userAccountDto);

    UserAccount logInUser(UserAccountDto userAccountDto);

    UserAccount createAlbum(UserAccount userAccount, String albumName);

    void addPhotoToOwnedAlbum(UserAccount userAccount, MultipartFile photo);

    void addPhotoToOwnedAlbum(UserAccount userAccount, PhotoMetaData photoMetaData, MultipartFile photo);

    void sharePhotoToUser(UserAccount ownerAccount, Long photoId, UserAccount sharedAccount);

    void sharePhotoWithLink(Long id, String sharableLink);

    void addPhotoToAlbum(UserAccount owner, Photo photo, String albumName);

    void addPhotoToAlbum(UserAccount owner, MultipartFile photo, String albumName);

    void shareAlbumWithUser(UserAccount sharedAccount, SharedAlbum album);

    List<UserAccount> findUsersWithPhotoAccess(Long photoId);

    void removeAccessToPhoto(UserAccount user, Photo photo);

    void deleteUserAccount(UserAccount userAccount);



}
