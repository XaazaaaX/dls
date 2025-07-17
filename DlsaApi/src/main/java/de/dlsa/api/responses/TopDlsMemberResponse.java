package de.dlsa.api.responses;

/**
 * Repräsentiert eine einzelne DLS-Auswertungseinheit für ein Mitglied,
 * typischerweise verwendet in Ranglisten oder Diagrammen.
 *
 * @version 05/2025
 */
public class TopDlsMemberResponse {

    /** Der Name des Mitglieds oder eine Darstellung als Label. */
    private String label;

    /** Die erbrachte Gesamtmenge an DLS-Leistungen. */
    private Double data;

    // --- Getter & Fluent Setter ---

    public String getLabel() {
        return label;
    }

    public TopDlsMemberResponse setLabel(String label) {
        this.label = label;
        return this;
    }

    public Double getData() {
        return data;
    }

    public TopDlsMemberResponse setData(Double data) {
        this.data = data;
        return this;
    }
}
