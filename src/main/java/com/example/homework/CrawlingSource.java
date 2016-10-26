package com.example.homework;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class CrawlingSource {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private long id;
    
    @Column(unique = true)
    private String url;
    
    private String repositoryKey;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getRepositoryKey() {
        return repositoryKey;
    }

    public void setRepositoryKey(String repositoryKey) {
        this.repositoryKey = repositoryKey;
    }

    @Override
    public String toString() {
        return "CrawlingSource{" + "id=" + id + ", url=" + url + ", repositoryKey=" + repositoryKey + '}';
    }
}
