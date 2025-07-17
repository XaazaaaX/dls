package de.dlsa.api.responses;

/**
 * Antwortstruktur zur Auswertung der Dienstleistungsstunden (DLS) eines Mitglieds.
 * Enthält alle Kennzahlen für die Abrechnungs- oder Berichtsanzeige.
 *
 * @version 05/2025
 * @author Benito Ernst
 */
public class EvaluationResponse {

    /** Vorname des Mitglieds. */
    private String firstName;

    /** Nachname des Mitglieds. */
    private String lastName;

    /** Anzahl der relevanten Monate im Bezugszeitraum. */
    private int requiredMonths;

    /** Anzahl der zu leistenden DLS im Zeitraum. */
    private double requiredDls;

    /** Anzahl der tatsächlich erbrachten DLS. */
    private double achievedDls;

    /** Kosten pro nicht geleisteter DLS. */
    private double costPerDls;

    /** Gesamtsumme, die zu zahlen ist. */
    private double toPay;

    /** Anmerkung oder Begründung zur Bewertung. */
    private String comment;

    // --- Getter & Setter mit Fluent-API-Stil ---

    public String getFirstName() {
        return firstName;
    }

    public EvaluationResponse setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public EvaluationResponse setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public int getRequiredMonths() {
        return requiredMonths;
    }

    public EvaluationResponse setRequiredMonths(int requiredMonths) {
        this.requiredMonths = requiredMonths;
        return this;
    }

    public double getRequiredDls() {
        return requiredDls;
    }

    public EvaluationResponse setRequiredDls(double requiredDls) {
        this.requiredDls = requiredDls;
        return this;
    }

    public double getAchievedDls() {
        return achievedDls;
    }

    public EvaluationResponse setAchievedDls(double achievedDls) {
        this.achievedDls = achievedDls;
        return this;
    }

    public double getCostPerDls() {
        return costPerDls;
    }

    public EvaluationResponse setCostPerDls(double costPerDls) {
        this.costPerDls = costPerDls;
        return this;
    }

    public double getToPay() {
        return toPay;
    }

    public EvaluationResponse setToPay(double toPay) {
        this.toPay = toPay;
        return this;
    }

    public String getComment() {
        return comment;
    }

    public EvaluationResponse setComment(String comment) {
        this.comment = comment;
        return this;
    }
}
