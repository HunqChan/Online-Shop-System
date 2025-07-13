import service.GHNService;

import java.util.List;
import java.util.Map;

public class TestGHNService {
    public static void main(String[] args) {
        GHNService ghnService = new GHNService();

        try {
            // ✅ Test lấy danh sách tỉnh/thành phố
            System.out.println("===== Provinces =====");
            List<Map<String, String>> provinces = ghnService.getProvinces();
            for (Map<String, String> p : provinces) {
                System.out.println(p.get("id") + " - " + p.get("name"));
            }

            // ✅ Test lấy danh sách quận/huyện
            System.out.println("\n===== Districts of Ha Noi (201) =====");
            List<Map<String, String>> districts = ghnService.getDistricts(201);
            for (Map<String, String> d : districts) {
                System.out.println(d.get("id") + " - " + d.get("name"));
            }

            // ✅ Test lấy danh sách phường/xã
            System.out.println("\n===== Wards of Soc Son =====");
            List<Map<String, String>> wards = ghnService.getWards(1583);
            for (Map<String, String> w : wards) {
                System.out.println(w.get("code") + " - " + w.get("name"));
            }

        } catch (Exception e) {
            System.err.println("Lỗi khi gọi API GHN: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
