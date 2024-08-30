package com.example.siteDiscoBackend.Controllers;

import com.example.siteDiscoBackend.Showcase.ShowcaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/showcase")
public class ShowcaseController {
    @Autowired
    private ShowcaseRepository repository;

    
}
