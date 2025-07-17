package de.dlsa.api.responses;

import java.util.List;

/**
 * Response-Klasse zur Darstellung der jährlich geleisteten Dienstleistungsstunden (DLS).
 * Wird z. B. für die Visualisierung in Diagrammen verwendet.
 * Enthält eine Liste von Jahres-Labels sowie zugehörige Summen.
 *
 * @author Benito Ernst
 * @version 05/2025
 */
public class AnnualServiceHoursResponse {

    /**
     * Liste der Jahres-Labels (z. B. ["2021", "2022", "2023"]).
     */
    private List<String> labels;

    /**
     * Liste der zugehörigen DLS-Summen je Jahr.
     */
    private List<Double> data;

    // --- Getter & Setter mit Fluent-API-Stil ---

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
