package me.figo.models;

import com.google.gson.annotations.Expose;

public class ErrorResponse {

  @Expose
  private ErrorObject error;

  public ErrorResponse() {
  }

  public ErrorObject getError() {
    return error;
  }

}
