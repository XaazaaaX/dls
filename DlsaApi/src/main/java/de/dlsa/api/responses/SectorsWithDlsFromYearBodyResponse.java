package de.dlsa.api.responses;

import java.util.List;

public class SectorsWithDlsFromYearBodyResponse {
    private String label;
    private List<Double> data;

    public String getLabel() {
        return label;
    }

    public SectorsWithDlsFromYearBodyResponse setLabel(String year) {
        this.label = year;
        return this;
    }

    public List<Double> getData() {
        return data;
    }

    public SectorsWithDlsFromYearBodyResponse setData(List<Double> data) {
        this.data = data;
        return this;
    }
}
