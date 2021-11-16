package za.ac.nwu.ac.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {

    @RequestMapping(value={"","/","/index"})
    public String getIndex(){
        return "index";
    }
}
