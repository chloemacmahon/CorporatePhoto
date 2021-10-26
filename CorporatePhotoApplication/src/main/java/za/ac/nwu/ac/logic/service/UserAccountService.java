package za.ac.nwu.ac.logic.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import za.ac.nwu.ac.domain.dto.UserAccountDto;
import za.ac.nwu.ac.domain.persistence.UserAccount;
import za.ac.nwu.ac.domain.persistence.photo.Photo;


public interface UserAccountService {

    UserAccount findUserById(Long id);

    UserAccount createUserAccount(UserAccountDto userAccountDto);

    UserAccount logInUser(UserAccountDto userAccountDto);

    UserAccount createAlbum(UserAccount userAccount, String albumName);

    void addPhotoToOwnedAlbum(UserAccount userAccount, MultipartFile photo);

    void sharePhotoToUser(UserAccount ownerAccount, Long photoId, UserAccount sharedAccount);

    void addPhotoToAlbum(UserAccount owner, Photo photo, String albumName);

    void addPhotoToAlbum(UserAccount owner, MultipartFile photo, String albumName);
}
