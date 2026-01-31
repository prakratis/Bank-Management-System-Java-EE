package com;

	public class Transaction {

	    private long transactionId;
	    private long accountNo;
	    private String transactionType;      
	    private double amount;
	    private double balanceAfter;
	    private Long referenceAccount;       
	    private String description;
	    private String transactionStatus;     
	    private String createdAt;             
	
	    public Transaction() {} 

	    public Transaction(long id, String type, double amount, java.sql.Timestamp timestamp, Long refAccount) {
	        this.transactionId = id;
	        this.transactionType = type;
	        this.amount = amount;
	        this.createdAt = (timestamp != null) ? timestamp.toString() : null;
	        this.referenceAccount = refAccount;
	    }
	   

	    public long getTransactionId() {
	        return transactionId;
	    }

	    public void setTransactionId(long transactionId) {
	        this.transactionId = transactionId;
	    }

	    public long getAccountNo() {
	        return accountNo;
	    }

	    public void setAccountNo(long accountNo) {
	        this.accountNo = accountNo;
	    }

	    public String getTransactionType() {
	        return transactionType;
	    }

	    public void setTransactionType(String transactionType) {
	        this.transactionType = transactionType;
	    }

	    public double getAmount() {
	        return amount;
	    }

	    public void setAmount(double d) {
	        this.amount = d;
	    }

	    public double getBalanceAfter() {
	        return balanceAfter;
	    }

	    public void setBalanceAfter(double d) {
	        this.balanceAfter = d;
	    }

	    public Long getReferenceAccount() {
	        return referenceAccount;
	    }

	    public void setReferenceAccount(Long referenceAccount) {
	        this.referenceAccount = referenceAccount;
	    }

	    public String getDescription() {
	        return description;
	    }

	    public void setDescription(String description) {
	        this.description = description;
	    }

	    public String getTransactionStatus() {
	        return transactionStatus;
	    }

	    public void setTransactionStatus(String transactionStatus) {
	        this.transactionStatus = transactionStatus;
	    }

	    public String getCreatedAt() {
	        return createdAt;
	    }

	    public void setCreatedAt(String createdAt) {
	        this.createdAt = createdAt;
	    }

		public void setStatus(String string) {
			// TODO Auto-generated method stub
			
		}
	}


