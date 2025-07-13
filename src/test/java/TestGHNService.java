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

            // ✅ Test lấy danh sách quận/huyện của TP. Hồ Chí Minh (ProvinceID = 201)
            System.out.println("\n===== Districts of Hồ Chí Minh (201) =====");
            List<Map<String, String>> districts = ghnService.getDistricts(201);
            for (Map<String, String> d : districts) {
                System.out.println(d.get("id") + " - " + d.get("name"));
            }

            // ✅ Test lấy danh sách phường/xã của Quận 1 (DistrictID = 1450)
            System.out.println("\n===== Wards of Quận 1 (1450) =====");
            List<Map<String, String>> wards = ghnService.getWards(1450);
            for (Map<String, String> w : wards) {
                System.out.println(w.get("code") + " - " + w.get("name"));
            }

        } catch (Exception e) {
            System.err.println("Lỗi khi gọi API GHN: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
