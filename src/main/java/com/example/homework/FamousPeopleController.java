package com.example.homework;

import java.util.concurrent.atomic.AtomicLong;
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

    private final AtomicLong counter = new AtomicLong();
    
    @Autowired
    private CrawlingSourceDao crawlingSourceDao;

    @RequestMapping(value = "/url-to-be-scanned", method = RequestMethod.POST)
    public ResponseEntity<Void> urlToBeScanned(@RequestBody String urlToBeScanned) {
        System.out.println("url to be scanned: " + urlToBeScanned);
        
        /**
         * TODO:
         * 1) remove System.out.println or use logger
         * 2) before saving, check, if CrawlingSource exists
         *      - IF NO:    create in same transaction. Return HTTP STATUS CREATED
         *      - IF YES:   return Warning & HTTP STATUS OK
         * 3) improve unit tests:
         *      - code coverage & mutation testing
         *      - think, is all main cases are covered
         */
        
        CrawlingSource crawlingSource = new CrawlingSource();
        crawlingSource.setUrl(urlToBeScanned);
        crawlingSourceDao.save(crawlingSource);
        
        System.out.println(crawlingSourceDao.findAll());
        
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Warning", "299 famousPeopleService \"URL was already scanned.\"");
        return new ResponseEntity<>(responseHeaders, HttpStatus.CREATED);
    }
}
