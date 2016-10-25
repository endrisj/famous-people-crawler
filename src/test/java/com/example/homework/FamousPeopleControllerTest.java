package com.example.homework;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class FamousPeopleControllerTest {

    @Autowired
    private MockMvc mockMvc;
    
    @Test
    public void urlToBeScanned_withNotScannedUrl_returnsHttpStatusOkWithoutWarning() throws Exception {
        mockMvc.perform(
                post("/url-to-be-scanned")
                .content("http://docs.spring.io/spring/docs/current/javadoc-api/")
            )
            .andExpect(status().isOk());
        
        // TODO: check, that were is no warning
    }
    
//    @Test
//    public void urlToBeScanned_correctlySavesUrl() {
//        throw new UnsupportedOperationException("TODO implement me!");
//    }
//    
//    @Test
//    public void urlToBeScanned_withNotScannedUrl_returnsHttpStatusOkWithWarning() {
//        throw new UnsupportedOperationException("TODO implement me!");
//    }
}
