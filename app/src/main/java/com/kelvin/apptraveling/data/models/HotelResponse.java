package com.kelvin.apptraveling.data.models;

import java.util.List;

public class HotelResponse {
    private List<Hotel> results;
    private int totalCount;

    public List<Hotel> getResults() {
        return results;
    }

    public void setResults(List<Hotel> results) {
        this.results = results;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }
}
