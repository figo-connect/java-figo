package me.figo;

import me.figo.models.ErrorObject;
import me.figo.models.ErrorResponse;

public class FigoApiException extends FigoException {

  private static final long serialVersionUID = -1855328588622789903L;

  private ErrorObject error;

  public FigoApiException(ErrorResponse response) {
    super(response.getError().getMessage() != null ? response.getError().getMessage() : response.getError().getDescription(),
            response.getError().getDescription());
    this.error = response.getError();
  }

  public ErrorObject getError() {
    return error;
  }

}
