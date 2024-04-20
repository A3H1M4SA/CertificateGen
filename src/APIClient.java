import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;

public class APIClient {
    private String baseUrl;

    public APIClient(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    //Handles the API Request
    public String makeRequest(Map<String, String> parameters) throws IOException {
        String urlParameters = constructQueryParameters(parameters);
        URL url = new URL(baseUrl + "?" + urlParameters);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET"); // Or "POST" depending on your API

        int responseCode = conn.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            try (BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
                String inputLine;
                StringBuilder response = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }

                return response.toString(); // Return the response for further processing
            }
        } else {
            throw new IOException("API request failed with response code: " + responseCode);
        }
    }

    private String constructQueryParameters(Map<String, String> parameters) throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();
        for (Map.Entry<String, String> entry : parameters.entrySet()) {
            if (result.length() > 0) result.append('&');
            result.append(URLEncoder.encode(entry.getKey(), "UTF-8"))
                    .append('=')
                    .append(URLEncoder.encode(entry.getValue(), "UTF-8"));
        }
        return result.toString();
    }
}
