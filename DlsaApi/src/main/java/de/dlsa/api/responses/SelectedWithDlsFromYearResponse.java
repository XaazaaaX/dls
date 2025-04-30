package de.dlsa.api.responses;

import java.util.List;

public class SelectedWithDlsFromYearResponse {
    private List<String> labels;
    private List<SelectedWithDlsFromYearBodyResponse> body;

    public List<String> getLabels() {
        return labels;
    }

    public SelectedWithDlsFromYearResponse setLabels(List<String> labels) {
        this.labels = labels;
        return this;
    }

    public List<SelectedWithDlsFromYearBodyResponse> getBody() {
        return body;
    }

    public SelectedWithDlsFromYearResponse setBody(List<SelectedWithDlsFromYearBodyResponse> body) {
        this.body = body;
        return this;
    }
}
