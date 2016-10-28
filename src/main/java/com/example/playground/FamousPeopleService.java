package com.example.playground;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FamousPeopleService {

    @Autowired
    private CrawlingSourceDao crawlingSourceDao;
    
    FamousPeopleService(CrawlingSourceDao crawlingSourceDao) {
        this.crawlingSourceDao = crawlingSourceDao;
    }
    
    /**
     * @param urlToBeScanned
     * @return CrawlingSource if saved, else null
     */
    public CrawlingSource saveIfNotExists(String urlToBeScanned) {
        if (crawlingSourceDao.existsByUrl(urlToBeScanned)) {
            return null;
        }
        CrawlingSource crawlingSource = new CrawlingSource();
        crawlingSource.setUrl(urlToBeScanned);
        crawlingSourceDao.save(crawlingSource);
        return crawlingSource;
    }
    
    /**
     * @param url
     * @param famousPeople
     * @return CrawlingSource if saved, else null
     */
    @Transactional
    public CrawlingSource saveFamousPeopleIfUrlKnown(String url, List<String> famousPeople) {
        CrawlingSource crawlingSource = crawlingSourceDao.findOneByUrl(url);
        if (null == crawlingSource) {
            return null;
        }
        crawlingSource.setIsScanned(true);
        mapFamousPeople2entities(crawlingSource, famousPeople);
        return crawlingSource;
    }
    
    private void mapFamousPeople2entities(CrawlingSource crawlingSource, List<String> famousPeople) {
        crawlingSource.getFamousPeople().clear();
        for (String name : famousPeople) {
            FamousPerson famousPerson = new FamousPerson();
            famousPerson.setName(name);
            crawlingSource.addFamousPerson(famousPerson);
        }
    }
    
    /**
     * @param url
     * @param repositoryKey
     * @return CrawlingSource if saved, else null
     */
    @Transactional
    public CrawlingSource saveRepositoryKeyIfUrlKnown(String url, String repositoryKey) {
        CrawlingSource crawlingSource = crawlingSourceDao.findOneByUrl(url);
        if (null == crawlingSource) {
            return null;
        }
        crawlingSource.setIsScanned(true);
        crawlingSource.setRepositoryKey(repositoryKey);
        return crawlingSource;
    }
}
