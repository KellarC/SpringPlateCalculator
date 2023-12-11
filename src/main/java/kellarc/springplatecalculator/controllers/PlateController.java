package kellarc.springplatecalculator.controllers;

import kellarc.springplatecalculator.model.entities.Plate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PlateController {
    @GetMapping("/calculate/{weight}")
    public String getResponse(@PathVariable int weight) {
        return Plate.convertToResponse(Plate.getNumberOfPlates(weight));
    }
}
