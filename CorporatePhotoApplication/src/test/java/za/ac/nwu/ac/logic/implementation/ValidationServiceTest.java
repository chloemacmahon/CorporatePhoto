package za.ac.nwu.ac.logic.implementation;

import junit.framework.TestCase;
import za.ac.nwu.ac.logic.service.ValidationService;

public class ValidationServiceTest extends TestCase {

    public void testIsValidNameWithValidName() {
        assertTrue(ValidationServiceImpl.isValidName("Name"));
    }

    public void testIsValidNameWithNameWithoutCapital() {
        assertFalse(ValidationServiceImpl.isValidName("name"));
    }

    public void testIsValidNameWithNameWithNumbers() {
        assertFalse(ValidationServiceImpl.isValidName("Nam3"));
    }

    public void testIsValidNameWithNameWithSpecialCharacter() {
        assertFalse(ValidationServiceImpl.isValidName("Name!"));
    }

    public void testIsValidPasswordWithValidPassword() {
        assertTrue(ValidationServiceImpl.isValidPassword("AA00aaa!"));
    }

    public void testIsValidPasswordWithInvalidPassword() {
        assertFalse(ValidationServiceImpl.isValidPassword("AA00a"));
    }

    public void testIsValidEmailWithValidEmail() {
        assertTrue(ValidationServiceImpl.isValidEmail("aaa@bbb.ccc"));
    }

    public void testIsValidEmailWithInvalidEmail() {
        assertFalse(ValidationServiceImpl.isValidEmail("aaabbb.ccc"));
    }
}