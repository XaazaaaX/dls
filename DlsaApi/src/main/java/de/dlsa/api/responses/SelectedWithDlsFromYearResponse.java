package de.dlsa.api.responses;

import java.util.List;

/**
 * Repräsentiert die aggregierte Antwortstruktur für DLS-Leistungen
 * eines ausgewählten Jahres nach bestimmten Kategorien (z. B. Gruppen, Bereiche, Sparten).
 *
 * @version 05/2025
 */
public class SelectedWithDlsFromYearResponse {

    /** Liste der Zeitachsen-Beschriftungen (z. B. Monate oder andere Einheiten). */
    private List<String> labels;

    /** Enthält DLS-Datenreihen für die einzelnen Gruppierungen. */
    private List<SelectedWithDlsFromYearBodyResponse> body;

    // --- Getter & Fluent Setter ---

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
