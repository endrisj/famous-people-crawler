package com.example.homework;

import java.util.List;

public class FamousPeopleForUrlDto {

    private String url;
    private List<String> famousPeople;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<String> getFamousPeople() {
        return famousPeople;
    }

    public void setFamousPeople(List<String> famousPeople) {
        this.famousPeople = famousPeople;
    }
}
