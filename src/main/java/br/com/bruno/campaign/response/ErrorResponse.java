package br.com.bruno.campaign.response;

public class ErrorResponse {

	private int errorStatus;
	private String errorMessage;

	public ErrorResponse(int errorStatus, String errorMessage) {
		this.errorStatus = errorStatus;
		this.errorMessage = errorMessage;
	}

	public int getErrorStatus() {
		return errorStatus;
	}

	public void setErrorStatus(int errorStatus) {
		this.errorStatus = errorStatus;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	@Override
	public String toString() {
		return "ErrorResponse{" + "errorStatus=" + errorStatus + ", errorMessage='" + errorMessage + '\'' + '}';
	}
}
