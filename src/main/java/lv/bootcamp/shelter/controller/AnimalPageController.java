package lv.bootcamp.shelter.controller;


import lv.bootcamp.shelter.form.AnimalForm;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import jakarta.validation.Valid;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import lombok.RequiredArgsConstructor;
import lv.bootcamp.shelter.service.AnimalService;

@Controller
@RequiredArgsConstructor
public class AnimalPageController {

    private final AnimalService animalService;

    @GetMapping("/")
    public String index() {
        return "index";

    }

    @GetMapping("/animals")
    public String listAnimals(Model model) {
        model.addAttribute("form",
                new AnimalForm(null, null, null, null, null, null));

        model.addAttribute("animals", animalService.findAll());
        return "animals";
    }

    @PostMapping("/animals")
    public String createAnimal(
            @Valid @ModelAttribute("form") AnimalForm form,
            BindingResult bindingResult,
            Model model
    ) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("animals", animalService.findAll());
            return "animals";
        }

        animalService.createFromForm(form);
        return "redirect:/animals";
    }
}
