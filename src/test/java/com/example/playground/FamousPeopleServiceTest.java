package com.example.playground;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

public class FamousPeopleServiceTest {

    private CrawlingSourceDao crawlingSourceDao;
    private FamousPeopleService service;
    
    @Before
    public void setup() {
        crawlingSourceDao = Mockito.mock(CrawlingSourceDao.class);
        service = new FamousPeopleService(crawlingSourceDao);
    }
    
    @Test
    public void saveIfNotExists_withNonExistingUrl_setsUrlAndMarksAsNotScanned() {
        String url = "good-url";
        when(crawlingSourceDao.existsByUrl(url)).thenReturn(false);
        CrawlingSource result = service.saveIfNotExists(url);
        assertEquals(0, result.getFamousPeople().size());
        assertFalse(result.getIsScanned());
        assertEquals(url, result.getUrl());
    }
    
    @Test
    public void saveFamousPeopleIfUrlKnown_withRegisteredUrlAndFamousPeople_correctlyMapsAndMarksAsScanned() {
        String url = "http://";
        List<String> famousPeople = Arrays.asList("Jonas", "Antanas Basas");
        CrawlingSource crawlingSource = new CrawlingSource();
        crawlingSource.setUrl(url);
        when(crawlingSourceDao.findOneByUrl(url)).thenReturn(crawlingSource);
        CrawlingSource result = service.saveFamousPeopleIfUrlKnown(url, famousPeople);
        assertTrue(crawlingSource.getIsScanned());
        assertEquals(2, crawlingSource.getFamousPeople().size());
        assertEquals(famousPeople.get(0), result.getFamousPeople().get(0).getName());
        assertEquals(famousPeople.get(1), result.getFamousPeople().get(1).getName());
    }
    
    @Test
    public void saveFamousPeopleIfUrlKnown_withRegisteredUrlAndWithoutFamousPeople_overwritesFamousPeople() {
        String url = "http://";
        List<String> famousPeople = new ArrayList<>();
        CrawlingSource crawlingSource = new CrawlingSource();
        crawlingSource.setUrl(url);
        FamousPerson famousPerson = new FamousPerson();
        famousPerson.setName("Petras");
        crawlingSource.addFamousPerson(famousPerson);
        when(crawlingSourceDao.findOneByUrl(url)).thenReturn(crawlingSource);
        CrawlingSource result = service.saveFamousPeopleIfUrlKnown(url, famousPeople);
        assertEquals(0, result.getFamousPeople().size());
    }
    
    @Test
    public void saveRepositoryKeyIfUrlKnown_withRegisteredUrl_setRepositoryKeyAndMarksAsScanned() {
        String url = "https://";
        String repositoryKey = "secret-key";
        CrawlingSource crawlingSource = new CrawlingSource();
        crawlingSource.setUrl(url);
        when(crawlingSourceDao.findOneByUrl(url)).thenReturn(crawlingSource);
        CrawlingSource result = service.saveRepositoryKeyIfUrlKnown(url, repositoryKey);
        assertTrue(crawlingSource.getIsScanned());
        assertEquals(repositoryKey, result.getRepositoryKey());
    }
}
