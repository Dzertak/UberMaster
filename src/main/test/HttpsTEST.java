import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import ubermaster.entity.model.BaseEntity;
import ubermaster.entity.model.Master;
import ubermaster.entity.model.Order;
import ubermaster.entity.model.Poke;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class HttpsTEST
{
	static final String HEADER_CON_TYPE = "content-type";
	static final String APP_JSON = "application/json";
	static final String HEADER_AUTH = "Authorization";
	static String headerAuthVal = "UberToken ";

	private static final String GET_ENTITY_URL = "http://localhost:8090/entities/getEntity?id=▼&class=▼";

	@BeforeClass
	public static void beforeClassMethod()
	{
	//==:	AUTH
		HttpClient httpClient = new DefaultHttpClient();
		final String USER_PHONE = "610114056442";
		final String USER_PASS = "ankpy2lPDTY6pkcsJ";
		final String URL_AUTH = "http://localhost:8090/auth";
		try
		{
			HttpPost request = new HttpPost(URL_AUTH);
			StringEntity params = new StringEntity
					(
						"{\"phoneNumber\":\"" + USER_PHONE +"\",\n"
							+
						"\t\"password\":\"" + USER_PASS + "\"}");
			request.addHeader(HEADER_CON_TYPE, APP_JSON);
			request.setEntity(params);
			HttpResponse response = httpClient.execute(request);

			int pointer;
			StringBuilder stringBuilder = new StringBuilder();
			while ((pointer = response.getEntity().getContent().read()) != -1)
				stringBuilder.append((char)pointer);

			System.out.println(stringBuilder);

			JSONObject jsonObject = new JSONObject(stringBuilder.toString());
			headerAuthVal += jsonObject.getString("token");
		}

		catch (Exception exc)
		{
			exc.printStackTrace();
		}

		finally
		{
			httpClient.getConnectionManager().shutdown();
		}
	}

	@AfterClass
	public static void afterClassMethod()
	{

	}

	@Test
	public void getPokeTEST() throws  IOException
	{
		getEntity(1, Poke.class);
	}

	@Test
	public void getMasterTEST() throws  IOException
	{
		getEntity(16, Master.class);
	}

	@Test
	public void getPokeOrder() throws  IOException
	{
		getEntity(37, Order.class);
	}

	private String getEntity(long id, Class<? extends BaseEntity> classType) throws IOException
	{
		String url = new String(GET_ENTITY_URL);
		url = url.replaceFirst("▼", Long.toString(id));
		url = url.replaceFirst("▼", classType.getSimpleName());

		HttpClient client = new DefaultHttpClient();
		HttpGet request = new HttpGet(url);

		// add request header
		request.addHeader(HEADER_CON_TYPE, APP_JSON);
		request.addHeader(HEADER_AUTH, headerAuthVal);
		HttpResponse response = client.execute(request);

		System.out.println("Response Code : "
				+ response.getStatusLine().getStatusCode());

		BufferedReader rd = new BufferedReader(
				new InputStreamReader(response.getEntity().getContent()));

		StringBuilder result = new StringBuilder();
		String line;
		while ((line = rd.readLine()) != null) {
			result.append(line);
		}

		System.out.println(result);

		return null;
	}
}
