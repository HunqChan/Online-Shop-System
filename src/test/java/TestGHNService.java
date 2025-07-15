import proxy.GHNProxy;

import java.util.List;
import java.util.Map;

public class TestGHNService {
    public static void main(String[] args) {
        GHNProxy ghnProxy = new GHNProxy();

        try {
            // ✅ Test lấy danh sách tỉnh/thành phố
            System.out.println("===== Provinces =====");
            List<Map<String, String>> provinces = ghnProxy.getProvinces();
            for (Map<String, String> p : provinces) {
                System.out.println(p.get("ProvinceID") + " - " + p.get("ProvinceName"));
            }

            // ✅ Test lấy danh sách quận/huyện
            System.out.println("\n===== Districts of Ha Noi (201) =====");
            List<Map<String, String>> districts = ghnProxy.getDistricts(201);
            for (Map<String, String> d : districts) {
                System.out.println(d.get("DistrictID") + " - " + d.get("DistrictName"));
            }

            // ✅ Test lấy danh sách phường/xã
            System.out.println("\n===== Wards of Soc Son =====");
            List<Map<String, String>> wards = ghnProxy.getWards(1583);
            for (Map<String, String> w : wards) {
                System.out.println(w.get("WardCode") + " - " + w.get("WardName"));
            }

        } catch (Exception e) {
            System.err.println("Lỗi khi gọi API GHN: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
