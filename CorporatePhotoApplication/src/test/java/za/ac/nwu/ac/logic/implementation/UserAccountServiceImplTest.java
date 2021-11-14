package za.ac.nwu.ac.logic.implementation;

import junit.framework.TestCase;
import org.junit.Before;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import za.ac.nwu.ac.logic.service.PhotoService;
import za.ac.nwu.ac.repository.PhotoRepository;
import za.ac.nwu.ac.repository.UserAccountRepository;

public class UserAccountServiceImplTest extends TestCase {

    UserAccountServiceImpl userAccountService;

    @Mock
    private UserAccountRepository userAccountRepository;

    @Mock
    private PhotoRepository photoRepository;

    @Mock
    private PhotoService photoService;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        //userAccountService = new UserAccountServiceImpl(userAccountRepository, photoRepository, albumRepository, photoService);
    }

    public void testFindUserById() {
    }

    public void testFindUserByEmail() {
    }

    public void testCreateUserAccount() {
    }

    public void testLogInUser() {
    }

    public void testCreateAlbum() {
    }

    public void testAddPhotoToOwnedAlbum() {
    }

    public void testTestAddPhotoToOwnedAlbum() {
    }

    public void testSharePhotoToUser() {
    }

    public void testAddPhotoToAlbum() {
    }

    public void testTestAddPhotoToAlbum() {
    }
}