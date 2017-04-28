package bouns;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

class Restaurant {
	private String name;
	private double rating;
	private String lat;
	private String lng;
	private String isOpen;

	public Restaurant(String name, double rating, String lat, String lng, String isOpen) {
		super();
		this.name = name;
		this.rating = rating;
		this.lat = lat;
		this.lng = lng;
		this.isOpen = isOpen;
	}

	public void showStatus() {
		System.out.println("地點名稱: " + name);
		System.out.println("評分: " + rating);
		System.out.println("緯度,經度 : " + lat + ", " + lng);
		System.out.println("是否營業中 :" + isOpen);
		System.out.println("============================");
	}

}

public class InfoParser {
	public static void main(String[] args) throws IOException, JSONException {

		String apiUrl = "https://maps.googleapis.com/maps/api/place/nearbysearch/";
		String dataFormat = "json";
		String[] location = { "24.95375", "121.22575" };
		int radius = 500;
		String types = "food";
		String name = "咖啡";
		String language = "zh-TW";
		String apiKey = "AIzaSyAYmC8oUYc9DGAZn8hqZKakFeclhAbTRSI";
		

		String url_str = apiUrl + dataFormat + "?"
						+"location=" + location[0] + "," + location[1] + "&"
						+"radius=" + radius + "&"
						+"types=" + types + "&"
						+"name=" + name + "&"
						+"languange=" + language + "&"
						+"key=" + apiKey;

		URL url = new URL(url_str);
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setRequestMethod("GET");
		con.setDoInput(true);

		StringBuilder sb = new StringBuilder();
		try (BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()))) {
			String str;
			while ((str = br.readLine()) != null) {
				sb.append(str);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		JSONArray results = new JSONObject(sb.toString()).getJSONArray("results");

		for (int i = 0; i < results.length(); i++) {
			JSONObject data = results.getJSONObject(i);
			String storeName = data.getString("name");
			String lat = String.valueOf(data.getJSONObject("geometry").getJSONObject("location").getDouble("lat"));
			String lng = String.valueOf(data.getJSONObject("geometry").getJSONObject("location").getDouble("lng"));
			double rating = data.getDouble("rating");
			
			// 處理無營業時間的資料
			String isOpen;
			try {
				if (data.getJSONObject("opening_hours").getBoolean("open_now") == true) {
					isOpen = "營業中";
				} else {
					isOpen = "休業中";
				}
			} catch (JSONException e) {
				isOpen = "無資料";
			}

			Restaurant rt = new Restaurant(storeName, rating, lat, lng, isOpen);
			rt.showStatus();
		}

	}


}
