package com.example.playground;

import org.junit.After;
import org.junit.Before;
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

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class FamousPeopleControllerTest {

    @Autowired
    private MockMvc mockMvc;
    
    @MockBean
    private CrawlingSourceDao crawlingSourceDao;
    
    @Before
    public void setup() {
        when(crawlingSourceDao.existsByUrl("http://docs.spring.io/spring/docs/current/javadoc-api/")).thenReturn(false);
    }
    
    @After
    public void tearDown() {
        reset(crawlingSourceDao);
    }
    
    @Test
    public void urlToBeScanned_withNotScannedUrl_returnsHttpStatusCreatedWithoutWarning() throws Exception {
        mockMvc.perform(
                post("/url-to-be-scanned")
                .content("http://docs.spring.io/spring/docs/current/javadoc-api/")
            )
            .andExpect(status().isCreated())
            .andExpect(header().doesNotExist("Warning"));
    }
    
    @Test
    public void urlToBeScanned_correctlySavesUrl() throws Exception {
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
    
    /**
     * TODO: write tests:
     *      1) URL is unregistered. Error returned, and not saved
     *      2) URL registered, success returned, saved (with famousPeople)
     *      3) URL registered, success returned, saved (without famousPeople - missing)
     *      4) URL not provided
     *      5) famousPeople are overwritten
     */
}
