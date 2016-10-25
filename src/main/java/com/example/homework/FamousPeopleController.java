package com.example.homework;

import java.util.concurrent.atomic.AtomicLong;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FamousPeopleController {
    
    private final AtomicLong counter = new AtomicLong();

    @RequestMapping(value = "/url-to-be-scanned", method = RequestMethod.POST)
    public void urlToBeScanned(@RequestBody String urlToBeScanned) {
        System.out.println("url to be scanned: "+urlToBeScanned);
    }
}
