package de.dlsa.api.responses;

import java.util.List;

public class AnnualServiceHoursResponse {
    private List<String> labels;
    private List<Double> data;

    public List<String> getLabels() {
        return labels;
    }

    public AnnualServiceHoursResponse setLabels(List<String> labels) {
        this.labels = labels;
        return this;
    }

    public List<Double> getData() {
        return data;
    }

    public AnnualServiceHoursResponse setData(List<Double> data) {
        this.data = data;
        return this;
    }

}
