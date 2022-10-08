package com.pmstudios.stronger.controller;

import com.pmstudios.stronger.entity.LoggedSet;
import com.pmstudios.stronger.service.LoggedSetService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/logged-set")
public class LoggedSetController {

    LoggedSetService loggedSetService;


}
