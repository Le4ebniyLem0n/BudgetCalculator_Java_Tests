package org.example;

public class BudgetRequest {
    private double dailyCost;
    private int days;
    private boolean includeInsurance;

    public double getDailyCost() { return dailyCost; }
    public void setDailyCost(double dailyCost) { this.dailyCost = dailyCost; }

    public int getDays() { return days; }
    public void setDays(int days) { this.days = days; }

    public boolean isIncludeInsurance() { return includeInsurance; }
    public void setIncludeInsurance(boolean includeInsurance) { this.includeInsurance = includeInsurance; }
}
