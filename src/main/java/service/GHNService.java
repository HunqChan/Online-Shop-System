package service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

public class GHNService {
    private static final String GHN_TOKEN = "ebf83fc3-600d-11f0-b272-6641004027c3"; // 🔁 thay token thật
    private static final String BASE_URL = "https://online-gateway.ghn.vn/shiip/public-api";
    private final ObjectMapper mapper = new ObjectMapper();

    // ✅ Get list of provinces (id + name)
    public List<Map<String, String>> getProvinces() throws IOException {
        URL url = new URL(BASE_URL + "/master-data/province");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Token", GHN_TOKEN);
        conn.setRequestProperty("Content-Type", "application/json");

        BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        JsonNode root = mapper.readTree(reader);
        reader.close();

        List<Map<String, String>> provinces = new ArrayList<>();
        JsonNode data = root.get("data");
        if (data != null && data.isArray()) {
            for (JsonNode node : data) {
                Map<String, String> province = new HashMap<>();
                province.put("id", String.valueOf(node.get("ProvinceID").asInt()));
                province.put("name", node.get("ProvinceName").asText());
                provinces.add(province);
            }
        }
        return provinces;
    }

    // ✅ Get list of districts (id + name) by province_id
    public List<Map<String, String>> getDistricts(int provinceId) throws IOException {
        URL url = new URL(BASE_URL + "/master-data/district");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Token", GHN_TOKEN);
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setDoOutput(true);

        String body = "{\"province_id\":" + provinceId + "}";
        try (OutputStream os = conn.getOutputStream()) {
            os.write(body.getBytes());
            os.flush();
        }

        BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        JsonNode root = mapper.readTree(reader);
        reader.close();

        List<Map<String, String>> districts = new ArrayList<>();
        JsonNode data = root.get("data");
        if (data != null && data.isArray()) {
            for (JsonNode node : data) {
                Map<String, String> district = new HashMap<>();
                district.put("id", String.valueOf(node.get("DistrictID").asInt()));
                district.put("name", node.get("DistrictName").asText());
                districts.add(district);
            }
        }
        return districts;
    }

    // ✅ Get list of wards (code + name) by district_id
    public List<Map<String, String>> getWards(int districtId) throws IOException {
        URL url = new URL(BASE_URL + "/master-data/ward");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Token", GHN_TOKEN);
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setDoOutput(true);

        String body = "{\"district_id\":" + districtId + "}";
        try (OutputStream os = conn.getOutputStream()) {
            os.write(body.getBytes());
            os.flush();
        }

        BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        JsonNode root = mapper.readTree(reader);
        reader.close();

        List<Map<String, String>> wards = new ArrayList<>();
        JsonNode data = root.get("data");
        if (data != null && data.isArray()) {
            for (JsonNode node : data) {
                Map<String, String> ward = new HashMap<>();
                ward.put("code", node.get("WardCode").asText());
                ward.put("name", node.get("WardName").asText());
                wards.add(ward);
            }
        }
        return wards;
    }
}
