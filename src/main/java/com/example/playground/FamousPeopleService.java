package com.example.playground;

import java.util.List;
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
    
    /**
     * @param url
     * @param famousPeople
     * @return true if saved
     */
    @Transactional
    public boolean saveIfUrlKnown(String url, List<String> famousPeople) {
        CrawlingSource crawlingSource = crawlingSourceDao.findOneByUrl(url);
        if (null == crawlingSource) {
            return false;
        }
        mapFamousPeople2entities(crawlingSource, famousPeople);
        crawlingSourceDao.save(crawlingSource);
        return true;
    }
    
    private void mapFamousPeople2entities(CrawlingSource crawlingSource, List<String> famousPeople) {
        crawlingSource.getFamousPeople().clear();
        for (String name : famousPeople) {
            FamousPerson famousPerson = new FamousPerson();
            famousPerson.setName(name);
            crawlingSource.addFamousPerson(famousPerson);
        }
    }
}
