package de.dlsa.api.responses;

public class EvaluationResponse {
    private String firstName;
    private String lastName;
    private int requiredMonths;
    private double requiredDls;
    private double achievedDls;
    private double costPerDls;
    private double toPay;
    private String comment;

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
