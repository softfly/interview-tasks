package sainsbury.serversidetest.errors;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ErrorMessagesFactory {
  UNKNOWN("ERROR-0000", "Unknown Error. Check logs."),//
  UNKNOWN_HOST("ERROR-0001", "Unknown Host."),//
  WRONG_HTTP_STATUS("ERROR-0002", "HTTP request resulted in a not OK HTTP response."),
  PARSING_ERROR("ERROR-0003", "");

  private String error;
  private String message;

  ErrorMessagesFactory(String error, String message) {
    this.error = error;
    this.message = message;
  }

  public ErrorMessage createErrorMessage() {
    ErrorMessage errorMessage = new ErrorMessage();
    errorMessage.setError(error);
    errorMessage.setMessage(message);
    return errorMessage;
  }

  public String getError() {
    return error;
  }

  public String getMessage() {
    return message;
  }
}
