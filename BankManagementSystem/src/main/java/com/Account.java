package com;
import java.sql.Timestamp;
public class Account {
//--- 1. Fields ---
	    private String accountNo;          
		private int customerId;          
	    private String accountType;      
	    private double balance;      
	    private String accountStatus;   
	    private Timestamp openedAt;      
	    
public Account() {}
public String getAccountNo() {
	return accountNo;
}

public void setAccountNo(String accountNo) {
    this.accountNo = accountNo; 
}
public int getCustomerId() {
	return customerId;
}

public void setCustomerId(int customerId) {
	this.customerId = customerId;
}

public String getAccountType() {
	return accountType;
}

public void setAccountType(String accountType) {
	this.accountType = accountType;
}

public double getBalance() {
	return balance;
}

public void setBalance(double d) {
	this.balance = d;
}

public String getAccountStatus() {
	return accountStatus;
}

public void setAccountStatus(String accountStatus) {
	this.accountStatus = accountStatus;
}

public Timestamp getOpenedAt() {
    return openedAt;
}

public void setOpenedAt(Timestamp openedAt) {
    this.openedAt = openedAt;
}
}
