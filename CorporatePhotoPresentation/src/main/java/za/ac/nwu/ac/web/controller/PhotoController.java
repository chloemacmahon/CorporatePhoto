package za.ac.nwu.ac.web.controller;

import com.azure.core.annotation.Get;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import za.ac.nwu.ac.logic.service.PhotoServices;

@Controller
public class PhotoController {
    private final PhotoServices photoServices;

    @Autowired
    public PhotoController(PhotoServices photoServices) {
        this.photoServices = photoServices;
    }



}
