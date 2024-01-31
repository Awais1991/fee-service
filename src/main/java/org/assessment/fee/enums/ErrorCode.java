package org.assessment.fee.enums;

/**
 * @author Awais
 * @created 1/24/2024 - 11:30 PM
 * @project demo-assessment
 */
public enum ErrorCode {
	INVALID_VALUE("200101", "Invalid value"),
	INVALID_FIELD("200102", "% invalid value"),
	VALUE_REQ("200103", "%s required"),
	ADD_FEE_FAILED("200104", "Failed to add fee"),
	FEE_EXITS("200105", "Fee already exit"),
	FEE_NOT_FOUND("200106", "Fee not found"),
	INVALID_DATES("200107", "Date range is invalid"),
	NOT_FOUND("200108", "Record not found"),
	;
	private String code;
	private String message;

	ErrorCode(String code, String message) {
		this.code = code;
		this.message = message;
	}

	public String getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}
}
