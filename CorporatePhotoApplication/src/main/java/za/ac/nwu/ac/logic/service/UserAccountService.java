package za.ac.nwu.ac.logic.service;

import org.springframework.stereotype.Service;
import za.ac.nwu.ac.domain.dto.UserAccountDto;
import za.ac.nwu.ac.domain.persistence.UserAccount;


public interface UserAccountService {

    UserAccount createUserAccount(UserAccountDto userAccountDto);

    UserAccount logInUser(UserAccountDto userAccountDto);
}
