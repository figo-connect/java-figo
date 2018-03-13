package me.figo;

import com.google.gson.Gson;
import me.figo.internal.GsonAdapter;
import me.figo.models.ErrorResponse;
import org.junit.Assert;
import org.junit.Test;

public class FigoApiExceptionTest {

  @Test
  public void ensureNonNullMessage() {
    Gson g = GsonAdapter.createGson();

    String errorResponse = "{\n" +
      "  \"error\": {\n" +
      "    \"code\": 1000,\n" +
      "    \"description\": \"Request body doesn't match input schema.\"\n" +
      "  }\n" +
      "}";

    ErrorResponse resp = g.fromJson(errorResponse, ErrorResponse.class);
    FigoApiException ex = new FigoApiException(resp);

    Assert.assertNotNull(ex.getMessage());
    Assert.assertEquals(ex.getMessage(), ex.getError().toString());
  }

}
