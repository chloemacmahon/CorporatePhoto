package za.ac.nwu.ac.logic.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import za.ac.nwu.ac.domain.dto.UserAccountDto;
import za.ac.nwu.ac.domain.exception.InvalidEmailException;
import za.ac.nwu.ac.domain.exception.InvalidPasswordException;
import za.ac.nwu.ac.domain.exception.UserDoesNotExistException;
import za.ac.nwu.ac.domain.persistence.UserAccount;
import za.ac.nwu.ac.logic.service.UserAccountService;
import za.ac.nwu.ac.logic.service.ValidationService;
import za.ac.nwu.ac.repository.UserAccountRepository;

@Service
public class UserAccountServiceImpl implements UserAccountService {

    private UserAccountRepository userAccountRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserAccountServiceImpl(UserAccountRepository userAccountRepository) {
        this.userAccountRepository = userAccountRepository;
    }

    public UserAccount createUserAccount(UserAccountDto userAccountDto){
        if(userAccountRepository.findByEmail(userAccountDto.getEmail()) != null){
            throw new InvalidEmailException("Email is already in the system");
        }
        if(!ValidationServiceImpl.isValidPassword(userAccountDto.getPassword())){
            throw new InvalidPasswordException("Password is not valid according to password standards");
        }
        if(!ValidationServiceImpl.isValidEmail(userAccountDto.getEmail())){
            throw new InvalidEmailException("Email is not valid ");
        }
        String encodedPassword = passwordEncoder.encode(userAccountDto.getPassword());
        UserAccount userAccount = new UserAccount(userAccountDto.getEmail(), encodedPassword);
        return userAccountRepository.save(userAccount);
    }

    public UserAccount logInUser(UserAccountDto userAccountDto){
        UserAccount userAccount = userAccountRepository.findByEmail(userAccountDto.getEmail());
        if(userAccount == null){
            throw new UserDoesNotExistException("User with this email address does not exist");
        }
        if(passwordEncoder.matches(userAccountDto.getPassword(), userAccount.getEncodedPassword())){
            throw new InvalidPasswordException("Password is incorrect");
        }
        return userAccount;
    }
}
