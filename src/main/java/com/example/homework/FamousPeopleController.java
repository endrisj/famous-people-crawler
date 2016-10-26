package com.example.homework;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FamousPeopleController {

    @Autowired
    private FamousPeopleService famousPeopleService;
    
    @RequestMapping(value = "/url-to-be-scanned", method = RequestMethod.POST)
    public ResponseEntity<Void> urlToBeScanned(@RequestBody String urlToBeScanned) {
        HttpHeaders responseHeaders = new HttpHeaders();
        HttpStatus httpStatus = HttpStatus.CREATED;
        if (!famousPeopleService.saveIfNotExists(urlToBeScanned)) {
            responseHeaders.set("Warning", "299 famousPeopleService \"URL was already scanned.\"");
            httpStatus = HttpStatus.OK;
        }
        return new ResponseEntity<>(responseHeaders, httpStatus);
        
        /**
        * TODO: 
        * 1) remove System.out.println or use logger 
        * 2) before saving, check, if CrawlingSource exists 
        *      - IF NO: create in same transaction. Return HTTP STATUS CREATED 
        *      - IF YES: return Warning & HTTP STATUS OK
        * 3) improve unit tests: 
        *      - code coverage & mutation testing 
        *      - think, is all main cases are covered
        */
    }
}
