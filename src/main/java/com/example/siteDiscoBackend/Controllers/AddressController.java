package com.example.siteDiscoBackend.Controllers;

import com.example.siteDiscoBackend.Address.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/address")
public class AddressController {
    @Autowired
    private AddressRepository repository;

    //TODO
}
