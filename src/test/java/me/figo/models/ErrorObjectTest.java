package me.figo.models;

import com.google.gson.Gson;
import me.figo.internal.GsonAdapter;
import org.junit.Assert;
import org.junit.Test;

public class ErrorObjectTest {

  @Test
  public void testGson() {
    Gson g = GsonAdapter.createGson();

        String errorResponse = "{\n" +
      "  \"error\": {\n" +
      "    \"code\": 1000,\n" +
      "    \"data\": {\"bank_code\": [\"Not a valid string.\"],\n" +
      "             \"credentials\": [\"Credentials must contain at least 2 strings.\"]},\n" +
      "    \"description\": \"Request body doesn't match input schema.\",\n" +
      "    \"group\": \"client\"\n" +
      "  }\n" +
      "}";

    ErrorResponse resp = g.fromJson(errorResponse, ErrorResponse.class);

    ErrorObject obj = resp.getError();

    Assert.assertNotNull(obj.getData());
    Assert.assertTrue(obj.getData().containsKey("bank_code"));
    Assert.assertEquals(obj.getData().get("bank_code").get(0), "Not a valid string.");
  }

}
