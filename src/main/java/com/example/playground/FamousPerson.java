package com.example.playground;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class FamousPerson {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    private CrawlingSource crawlingSource;
    
    private String name;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public CrawlingSource getCrawlingSource() {
        return crawlingSource;
    }

    public void setCrawlingSource(CrawlingSource crawlingSource) {
        this.crawlingSource = crawlingSource;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
