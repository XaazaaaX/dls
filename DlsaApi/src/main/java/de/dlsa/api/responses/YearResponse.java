package de.dlsa.api.responses;

/**
 * Repräsentiert ein einzelnes Jahr in einer Antwortstruktur.
 * Wird z. B. für Filteroptionen, Diagramme oder Berichtszeiträume verwendet.
 *
 * @version 05/2025
 */
public class YearResponse {

    /** Das Jahr, z. B. 2024. */
    private int year;

    // --- Getter & Fluent Setter ---

    public int getYear() {
        return year;
    }

    public YearResponse setYear(int year) {
        this.year = year;
        return this;
    }
}
