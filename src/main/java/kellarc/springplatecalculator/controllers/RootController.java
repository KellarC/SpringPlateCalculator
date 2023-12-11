package kellarc.springplatecalculator.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class RootController {

    @RequestMapping(method= RequestMethod.GET, value="/")
    public String index() { return "index.html"; }
}
