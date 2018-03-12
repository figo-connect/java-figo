package me.figo.models;

import com.google.gson.annotations.Expose;

import java.util.List;
import java.util.Map;

public class ErrorObject {

  @Expose
  private String code;

  @Expose
  private String name;

  @Expose
  private String message;

  @Expose
  private String description;

  @Expose
  private String group;

  @Expose
  private Map<String, List<String>> data;

  public ErrorObject() {
  }

  public String getMessage() {
    return message;
  }

  public String getDescription() {
    return description;
  }

  public String getCode() {
    return code;
  }

  public String getName() {
    return name;
  }

  public String getGroup() {
    return group;
  }

  public Map<String, List<String>> getData() {
    return data;
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("ErrorObject [");
    if (code != null)
      builder.append("code=").append(code).append(", ");
    if (name != null)
      builder.append("name=").append(name).append(", ");
    if (message != null)
      builder.append("message=").append(message).append(", ");
    if (description != null)
      builder.append("description=").append(description).append(", ");
    if (data != null)
      builder.append("data=").append(data.toString());
    if (group != null)
      builder.append("group=").append(group);
    builder.append("]");
    return builder.toString();
  }

}
