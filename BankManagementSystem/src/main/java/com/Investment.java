package com;

import java.sql.Timestamp;

public class Investment {
    private int id;
    private int customerId;
    private String type; // FD or RD
    private double principalAmount;
    private double interestRate;
    private int durationMonths;
    private Timestamp startDate;
    private String status;


    public Investment() {}

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public int getCustomerId() { return customerId; }
    public void setCustomerId(int customerId) { this.customerId = customerId; }
    
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public double getPrincipalAmount() { return principalAmount; }
    public void setPrincipalAmount(double principalAmount) { this.principalAmount = principalAmount; }

    public double getInterestRate() { return interestRate; }
    public void setInterestRate(double interestRate) { this.interestRate = interestRate; }

    public int getDurationMonths() { return durationMonths; }
    public void setDurationMonths(int durationMonths) { this.durationMonths = durationMonths; }

    public Timestamp getStartDate() { return startDate; }
    public void setStartDate(Timestamp startDate) { this.startDate = startDate; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    
    public double getMaturityAmount() {
        return this.principalAmount + (this.principalAmount * (this.interestRate / 100.0) * (this.durationMonths / 12.0));
    }
}