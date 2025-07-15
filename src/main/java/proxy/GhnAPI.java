package proxy;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class GhnAPI {
    private static final String TOKEN = "35616a46-60c1-11f0-9b81-222185cb68c8";
    private static final String SHOP_ID = "197111";

    public static String sendGET(String endpoint) throws IOException {
        URL url = new URL(endpoint);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("Token", TOKEN);
        con.setRequestProperty("ShopId", SHOP_ID);

        return readResponse(con);
    }

    public static String sendPOST(String endpoint, String json) throws IOException {
        URL url = new URL(endpoint);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/json");
        con.setRequestProperty("Token", TOKEN);
        con.setRequestProperty("ShopId", SHOP_ID);
        con.setDoOutput(true);

        try (OutputStream os = con.getOutputStream()) {
            byte[] input = json.getBytes("utf-8");
            os.write(input, 0, input.length);
        }

        return readResponse(con);
    }
    
    

    private static String readResponse(HttpURLConnection con) throws IOException {
    int status = con.getResponseCode();
    InputStream stream = (status >= 400) ? con.getErrorStream() : con.getInputStream();

    BufferedReader in = new BufferedReader(new InputStreamReader(stream));
    String inputLine;
    StringBuilder content = new StringBuilder();
    while ((inputLine = in.readLine()) != null) {
        content.append(inputLine);
    }
    in.close();

    System.out.println("GHN response (" + status + "): " + content.toString()); // 👈 THÊM DÒNG NÀY

    return content.toString();
}
}
