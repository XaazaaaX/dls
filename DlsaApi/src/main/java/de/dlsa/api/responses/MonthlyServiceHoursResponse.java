package de.dlsa.api.responses;

import java.util.List;

public class MonthlyServiceHoursResponse {
    private String label;
    private List<Double> data;

    // Getter & Setter

    public MonthlyServiceHoursResponse setLabel(String label) {
        this.label = label;
        return this;
    }

    public MonthlyServiceHoursResponse setData(List<Double> data) {
        this.data = data;
        return this;
    }

    public String getLabel() {
        return label;
    }

    public List<Double> getData() {
        return data;
    }
}