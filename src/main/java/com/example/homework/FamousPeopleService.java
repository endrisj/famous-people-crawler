package com.example.homework;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FamousPeopleService {

    @Autowired
    private CrawlingSourceDao crawlingSourceDao;
    
    /**
     * @param urlToBeScanned
     * @return true if saved
     */
    @Transactional
    public boolean saveIfNotExists(String urlToBeScanned) {
        if (crawlingSourceDao.existsByUrl(urlToBeScanned)) {
            return false;
        }
        CrawlingSource crawlingSource = new CrawlingSource();
        crawlingSource.setUrl(urlToBeScanned);
        crawlingSourceDao.save(crawlingSource);
        return true;
    }
}
