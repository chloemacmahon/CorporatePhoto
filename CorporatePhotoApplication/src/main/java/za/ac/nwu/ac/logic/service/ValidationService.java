package za.ac.nwu.ac.logic.service;

import org.springframework.stereotype.Service;

public interface ValidationService {

    boolean isValidPassword(String password);

    boolean isValidEmail(String email);
}
