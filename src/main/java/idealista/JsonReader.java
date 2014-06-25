package idealista;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;

import org.json.JSONException;
import org.json.JSONArray;
import org.json.JSONObject;

public class JsonReader {

	private static String readAll(Reader rd) throws IOException {
		StringBuilder sb = new StringBuilder();
		int cp;
		while ((cp = rd.read()) != -1) {
			sb.append((char) cp);
		}
		return sb.toString();
	}

	public static JSONArray readJsonFromUrl(String url) throws IOException,
			JSONException {
		InputStream is = new URL(url).openStream();
		try {
			BufferedReader rd = new BufferedReader(new InputStreamReader(is,
					Charset.forName("UTF-8")));
			String jsonText = readAll(rd);
			JSONArray json = new JSONArray(jsonText);
			return json;
		} finally {
			is.close();
		}
	}

	public static void main(String[] args) throws IOException, JSONException {
		// this key can be obtained from the API
		String k = "32a4f7f866fc111dfbca129fe3b604f8";
		String url = "http://www.idealista.com/labs/propertyMap.htm?action=json&k=" + k + "&operation=sale&center=42.848031,-2.672968&distance=3000&numPage=";

		Integer numPage = 1;
		int totalPages;
		WriteFile wFile = new WriteFile();
		do {
			JSONArray json = readJsonFromUrl(url + numPage.toString());

			JSONObject obj = json.getJSONObject(1);

			totalPages = obj.getInt("totalPages");
			Integer actualPage = obj.getInt("actualPage");
			System.out.println(actualPage + " / " + totalPages);

			JSONArray elementList = obj.getJSONArray("elementList");

			for (int i = 0; i < elementList.length(); i++) {
				JSONObject element = elementList.getJSONObject(i);
				Integer distance = element.getInt("distance");
				Integer rooms = element.getInt("rooms");
				Integer size = element.getInt("size");
				Integer price = element.getInt("price");

				wFile.write(distance.toString(), size.toString(),
						rooms.toString(), price.toString());

			}
			try {
				Thread.sleep(1300);
			} catch (InterruptedException ex) {
				Thread.currentThread().interrupt();
			}
			numPage++;
		} while(numPage <= totalPages);
		wFile.closeFile();

	}
}