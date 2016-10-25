package com.example.homework;

import java.util.concurrent.atomic.AtomicLong;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FamousPeopleController {

    private final AtomicLong counter = new AtomicLong();

    @RequestMapping(value = "/url-to-be-scanned", method = RequestMethod.POST)
    public ResponseEntity<Void> urlToBeScanned(@RequestBody String urlToBeScanned) {
        System.out.println("url to be scanned: " + urlToBeScanned);

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Warning", "299 famousPeopleService \"URL was already scanned.\"");
        return new ResponseEntity<>(responseHeaders, HttpStatus.CREATED);
    }
}
