package com.example.playground;

import java.util.ArrayList;
import java.util.List;
import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Mockito.*;
import static org.junit.Assert.assertEquals;
import org.springframework.http.MediaType;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class FamousPeopleControllerTest {

    @Autowired
    private MockMvc mockMvc;
    
    @MockBean
    private CrawlingSourceDao crawlingSourceDao;
    
    @After
    public void tearDown() {
        reset(crawlingSourceDao);
    }
    
    @Test
    public void urlToBeScanned_withNotScannedUrl_returnsHttpStatusCreatedWithoutWarning() throws Exception {
        when(crawlingSourceDao.existsByUrl("http://docs.spring.io/spring/docs/current/javadoc-api/")).thenReturn(false);
        mockMvc.perform(
                post("/url-to-be-scanned")
                .content("http://docs.spring.io/spring/docs/current/javadoc-api/")
            )
            .andExpect(status().isCreated())
            .andExpect(header().doesNotExist("Warning"));
    }
    
    @Test
    public void urlToBeScanned_correctlySavesUrl() throws Exception {
        when(crawlingSourceDao.existsByUrl("http://docs.spring.io/spring/docs/current/javadoc-api/")).thenReturn(false);
        mockMvc.perform(
                post("/url-to-be-scanned")
                .content("http://docs.spring.io/spring/docs/current/javadoc-api/")
            );
        ArgumentCaptor<CrawlingSource> argumentCaptor = ArgumentCaptor.forClass(CrawlingSource.class);
        verify(crawlingSourceDao).save(argumentCaptor.capture());
        assertEquals("http://docs.spring.io/spring/docs/current/javadoc-api/", argumentCaptor.getValue().getUrl());
    }
    
    @Test
    public void urlToBeScanned_withScannedUrl_returnsHttpStatusOkWithWarning() throws Exception {
        when(crawlingSourceDao.existsByUrl("http://docs.spring.io/spring/docs/current/javadoc-api/")).thenReturn(true);
        mockMvc.perform(
                post("/url-to-be-scanned")
                .content("http://docs.spring.io/spring/docs/current/javadoc-api/")
            )
            .andExpect(status().isOk())
            .andExpect(header().string("Warning", "299 famousPeopleService \"URL was already scanned.\""));
    }
    
    @Test
    public void famousPeopleForUrl_withUnregisteredUrl_returnsBadRequest() throws Exception {
        when(crawlingSourceDao.findOneByUrl("wikipedia")).thenReturn(null);
        mockMvc.perform(
                post("/famous-people-for-url")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"famousPeople\": [\"Petras Cvirka\"],\"url\": \"wikipedia\"}")
            )
            .andExpect(status().isBadRequest());
        verify(crawlingSourceDao, never()).save(any(CrawlingSource.class));
    }
    
    @Test
    public void famousPeopleForUrl_withRegisteredUrlAndFamousPeople_savesAndReturnsHttpStatusOk() throws Exception {
        CrawlingSource crawlingSource = new CrawlingSource();
        crawlingSource.setUrl("wikipedia");
        when(crawlingSourceDao.findOneByUrl("wikipedia")).thenReturn(crawlingSource);
        mockMvc.perform(
                post("/famous-people-for-url")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"famousPeople\": [\"Petras Cvirka\"],\"url\": \"wikipedia\"}")
            )
            .andExpect(status().isOk());
        ArgumentCaptor<CrawlingSource> argumentCaptor = ArgumentCaptor.forClass(CrawlingSource.class);
        verify(crawlingSourceDao).save(argumentCaptor.capture());
        assertEquals(1, crawlingSource.getFamousPeople().size());
        assertEquals("Petras Cvirka", crawlingSource.getFamousPeople().get(0).getName());
    }
    
    @Test
    public void famousPeopleForUrl_withRegisteredUrlAndWithoutFamousPeople_savesAndReturnsHttpStatusOk() throws Exception {
        CrawlingSource crawlingSource = new CrawlingSource();
        crawlingSource.setUrl("wikipedia");
        when(crawlingSourceDao.findOneByUrl("wikipedia")).thenReturn(crawlingSource);
        mockMvc.perform(
                post("/famous-people-for-url")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"famousPeople\": [],\"url\": \"wikipedia\"}")
            )
            .andExpect(status().isOk());
        ArgumentCaptor<CrawlingSource> argumentCaptor = ArgumentCaptor.forClass(CrawlingSource.class);
        verify(crawlingSourceDao).save(argumentCaptor.capture());
        assertEquals(0, crawlingSource.getFamousPeople().size());
    }
    
    @Test
    public void famousPeopleForUrl_withRegisteredUrlAndFamousPeople_overwritesAndReturnsHttpStatusOk() throws Exception {
        CrawlingSource crawlingSource = new CrawlingSource();
        crawlingSource.setUrl("wikipedia");
        FamousPerson famousPerson = new FamousPerson();
        famousPerson.setName("Antanas");
        crawlingSource.addFamousPerson(famousPerson);
        when(crawlingSourceDao.findOneByUrl("wikipedia")).thenReturn(crawlingSource);
        mockMvc.perform(
                post("/famous-people-for-url")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"famousPeople\": [\"Petras Cvirka\"],\"url\": \"wikipedia\"}")
            )
            .andExpect(status().isOk());
        ArgumentCaptor<CrawlingSource> argumentCaptor = ArgumentCaptor.forClass(CrawlingSource.class);
        verify(crawlingSourceDao).save(argumentCaptor.capture());
        assertEquals(1, crawlingSource.getFamousPeople().size());
        assertEquals("Petras Cvirka", crawlingSource.getFamousPeople().get(0).getName());
    }
    
    @Test
    public void showAll_returnsAll() throws Exception {
        CrawlingSource crawlingSource = new CrawlingSource();
        crawlingSource.setUrl("wikipedia");
        FamousPerson famousPerson = new FamousPerson();
        famousPerson.setName("Antanas");
        crawlingSource.addFamousPerson(famousPerson);
        List<CrawlingSource> crawlingSources = new ArrayList<>();
        crawlingSources.add(crawlingSource);
        when(crawlingSourceDao.findAll()).thenReturn(crawlingSources);
        mockMvc.perform(get("/url"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].famousPeople", Matchers.hasSize(1)))
            .andExpect(jsonPath("$[0].url", Matchers.is("wikipedia")))
            .andExpect(jsonPath("$[0].famousPeople[0].name", Matchers.is("Antanas")));
    }
}
