package com.pmstudios.stronger.loggedSet;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/logged-set")
public class LoggedSetController {

    LoggedSetService loggedSetService;


}
