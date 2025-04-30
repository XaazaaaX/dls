package de.dlsa.api.responses;

import java.util.List;

public class SelectedWithDlsFromYearBodyResponse {
    private String label;
    private List<Double> data;

    public String getLabel() {
        return label;
    }

    public SelectedWithDlsFromYearBodyResponse setLabel(String year) {
        this.label = year;
        return this;
    }

    public List<Double> getData() {
        return data;
    }

    public SelectedWithDlsFromYearBodyResponse setData(List<Double> data) {
        this.data = data;
        return this;
    }
}
