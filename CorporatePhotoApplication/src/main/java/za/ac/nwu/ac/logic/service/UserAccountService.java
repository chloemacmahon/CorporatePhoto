package za.ac.nwu.ac.logic.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import za.ac.nwu.ac.domain.dto.UserAccountDto;
import za.ac.nwu.ac.domain.persistence.UserAccount;


public interface UserAccountService {

    UserAccount findUserById(Long id);

    UserAccount createUserAccount(UserAccountDto userAccountDto);

    UserAccount logInUser(UserAccountDto userAccountDto);

    UserAccount createAlbum(UserAccount userAccount, String albumName);

    void addPhotoToOwnedAlbum(UserAccount userAccount, MultipartFile photo);
}
