package com.example.playground;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface CrawlingSourceDao extends CrudRepository<CrawlingSource, Long> {

    @Query("SELECT count(*) > 0 FROM CrawlingSource cs WHERE cs.url = ?1")
    public boolean existsByUrl(String url);
    public CrawlingSource findOneByUrl(String url);
    
}
