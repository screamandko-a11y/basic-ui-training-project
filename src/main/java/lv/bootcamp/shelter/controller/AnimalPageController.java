package lv.bootcamp.shelter.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AnimalPageController {

    @GetMapping("/")
    public String index() {
        return "index";

    }
}
