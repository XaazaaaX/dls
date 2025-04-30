package de.dlsa.api.responses;

import java.util.List;

public class SectorsWithDlsFromYearResponse {
    private List<String> labels;
    private List<SectorsWithDlsFromYearBodyResponse> body;

    public List<String> getLabels() {
        return labels;
    }

    public SectorsWithDlsFromYearResponse setLabels(List<String> labels) {
        this.labels = labels;
        return this;
    }

    public List<SectorsWithDlsFromYearBodyResponse> getBody() {
        return body;
    }

    public SectorsWithDlsFromYearResponse setBody(List<SectorsWithDlsFromYearBodyResponse> body) {
        this.body = body;
        return this;
    }
}
