package de.dlsa.api.responses;

public class TopDlsMemberResponse {
    private String label;
    private Double data;

    public TopDlsMemberResponse setLabel(String label) {
        this.label = label;
        return this;
    }

    public TopDlsMemberResponse setData(Double data) {
        this.data = data;
        return this;
    }

    public String getLabel() {
        return label;
    }

    public Double getData() {
        return data;
    }
}
