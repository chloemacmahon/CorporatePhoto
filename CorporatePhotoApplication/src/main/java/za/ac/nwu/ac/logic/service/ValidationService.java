package za.ac.nwu.ac.logic.service;

import org.springframework.stereotype.Service;

public interface ValidationService {

    boolean isValidName(String name);

    boolean isValidPassword(String password);

    boolean isValidEmail(String email);
}
