package za.ac.nwu.ac.logic.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import za.ac.nwu.ac.domain.dto.UserAccountDto;
import za.ac.nwu.ac.domain.exception.*;
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

    public UserAccount findUserById(Long id){
        if (userAccountRepository.findById(id).isPresent())
            return userAccountRepository.findById(id).get();
        else
            throw new UserDoesNotExistException();
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
        userAccountRepository.save(userAccount);
        return userAccount;
    }
}
