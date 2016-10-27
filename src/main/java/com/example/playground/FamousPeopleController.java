package com.example.playground;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FamousPeopleController {

    @Autowired
    private FamousPeopleService famousPeopleService;
    
    @Autowired
    private CrawlingSourceDao crawlingSourceDao;
    
    @RequestMapping(value = "/url-to-be-scanned", method = RequestMethod.POST)
    public ResponseEntity<Void> urlToBeScanned(@RequestBody String urlToBeScanned) {
        HttpHeaders responseHeaders = new HttpHeaders();
        HttpStatus httpStatus = HttpStatus.CREATED;
        if (!famousPeopleService.saveIfNotExists(urlToBeScanned)) {
            responseHeaders.set("Warning", "299 famousPeopleService \"URL was already scanned.\"");
            httpStatus = HttpStatus.OK;
        }
        return new ResponseEntity<>(responseHeaders, httpStatus);
    }
    
    @RequestMapping(value = "/famous-people-for-url", method = RequestMethod.POST)
    public ResponseEntity<String> famousPeopleForUrl(@RequestBody FamousPeopleForUrlDto famousPeopleForUrlDto) {
        HttpStatus httpStatus = HttpStatus.OK;
        String message = "Save succeeded.";
        if (!famousPeopleService.saveIfUrlKnown(famousPeopleForUrlDto.getUrl(), famousPeopleForUrlDto.getFamousPeople())) {
            httpStatus = HttpStatus.BAD_REQUEST;
            message = "URL `"+famousPeopleForUrlDto.getUrl()+"` is unknown.";
        }
        return new ResponseEntity<>(message, httpStatus);
    }
    
    @RequestMapping(value = "/url", method = RequestMethod.GET)
    public ResponseEntity<Iterable<CrawlingSource>> showAll() {
        return new ResponseEntity<>(crawlingSourceDao.findAll(), HttpStatus.OK);
    }
    
    @RequestMapping(value = "/url/{id}", method = RequestMethod.GET)
    public ResponseEntity<CrawlingSource> showOne(@PathVariable("id") long id) {
        return new ResponseEntity<>(crawlingSourceDao.findOne(id), HttpStatus.OK);
    }
}
