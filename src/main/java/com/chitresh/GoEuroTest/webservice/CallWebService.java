package com.chitresh.GoEuroTest.webservice;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import com.chitresh.GoEuroTest.config.AppConfig;

public class CallWebService {

	public synchronized void callService(String cityName) {

		try {

			DefaultHttpClient httpClient = new DefaultHttpClient();
			HttpGet getRequest = new HttpGet(AppConfig.URL + cityName);
			getRequest.addHeader("accept", "application/json");

			HttpResponse response = httpClient.execute(getRequest);

			if (response.getStatusLine().getStatusCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : "
						+ response.getStatusLine().getStatusCode());
			}

			BufferedReader br = new BufferedReader(new InputStreamReader(
					(response.getEntity().getContent())));

			String output;
			while ((output = br.readLine()) != null) {
				File file = getFileObject();
				parseJSONAndWriteToFile(output, file);
			}

			httpClient.getConnectionManager().shutdown();

		} catch (ClientProtocolException e) {

			e.printStackTrace();

		} catch (IOException e) {

			e.printStackTrace();
		}

	}

	private File getFileObject() throws IOException {

		File file = new File("cityName.csv");

		// if file does not exists, then create it
		if (!file.exists()) {
			file.createNewFile();
		}

		System.out.print("File path: " + file.getAbsolutePath());

		return file;
	}

	private void parseJSONAndWriteToFile(String output, File file)
			throws IOException {

		JSONArray jsonArray = new JSONArray(output);

		FileWriter fw = new FileWriter(file.getAbsoluteFile());
		BufferedWriter bw = new BufferedWriter(fw);

		try {

			for (int i = 0; i < jsonArray.length(); i++) {

				JSONObject jsonObject = (JSONObject) jsonArray.get(i);

				int id = jsonObject.getInt("_id");
				String name = jsonObject.getString("name");
				String type = jsonObject.getString("type");

				JSONObject geo_position = jsonObject
						.getJSONObject("geo_position");
				double latitude = geo_position.getDouble("latitude");
				double longitude = geo_position.getDouble("longitude");

				bw.write(id + "," + name + "," + type + "," + latitude + ","
						+ longitude + "\n");

			}
		} finally {
			bw.close();
		}
	}

}
