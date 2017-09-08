package me.figo;

import org.junit.Assert;
import org.junit.Test;

import com.google.gson.Gson;

import me.figo.models.AdditionalTransactionInfo;

public class GsonTest {

	@Test
	public void testGson() {
		FigoApi api = new FigoApi("", 0);
		Gson g = api.createGson();
		AdditionalTransactionInfo obj = g.fromJson(
				"{\"compensation_amount\": \"6,10\",\"original_amount\": \"575.40\"}", AdditionalTransactionInfo.class);
		Assert.assertNull(obj.getCompensation_amount());
		Assert.assertEquals(obj.getOriginal_amount().toPlainString(), "575.40");
	}
}
