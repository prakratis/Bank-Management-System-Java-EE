package com;

public class Document {
	private int documentId;
    private int customerId;
    private String documentType;
    private String filePath;
    private String uploadedAT;

 public Document() {}
 public int getDocumentId() {
		return documentId;
	}

	public void setDocumentId(int documentId) {
		this.documentId = documentId;
	}

	public int getCustomerId() {
		return customerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

	public String getDocumentType() {
		return documentType;
	}

	public void setDocumentType(String documentType) {
		this.documentType = documentType;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getUploadedAT() {
		return uploadedAT;
	}

	public void setUploadedAT(String uploadedAT) {
		this.uploadedAT = uploadedAT;
	}
}
