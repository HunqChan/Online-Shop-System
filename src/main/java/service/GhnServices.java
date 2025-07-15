package service;

import proxy.GhnAPI;

public class GhnServices {
    public String getProvinces() throws Exception {
        return GhnAPI.sendGET("https://dev-online-gateway.ghn.vn/shiip/public-api/master-data/province");
    }

    public String getDistricts(String json) throws Exception {
        return GhnAPI.sendPOST("https://dev-online-gateway.ghn.vn/shiip/public-api/master-data/district", json);
    }

    public String getWards(String json) throws Exception {
        return GhnAPI.sendPOST("https://dev-online-gateway.ghn.vn/shiip/public-api/master-data/ward", json);
    }

    public String calculateShippingFee(String json) throws Exception {
        return GhnAPI.sendPOST("https://dev-online-gateway.ghn.vn/shiip/public-api/v2/shipping-order/fee", json);
    }
    public String getAvailableServices(int fromDistrictId, int toDistrictId) throws Exception {
    String json = String.format("""
        {
            "shop_id": 197111,
            "from_district": %d,
            "to_district": %d
        }
    """, fromDistrictId, toDistrictId);
    
    return GhnAPI.sendPOST("https://dev-online-gateway.ghn.vn/shiip/public-api/v2/shipping-order/available-services", json);
}

    public String calculateShippingFee(int fromDistrictId, String fromWardCode, int toDistrictId, String toWardCode, int serviceId, int weight, int length, int width, int height) throws Exception {
        String json = String.format("""
            {
                \"from_district_id\": %d,
                \"from_ward_code\": \"%s\",
                \"to_district_id\": %d,
                \"to_ward_code\": \"%s\",
                \"service_id\": %d,
                \"weight\": %d,
                \"length\": %d,
                \"width\": %d,
                \"height\": %d
            }
        """, fromDistrictId, fromWardCode, toDistrictId, toWardCode, serviceId, weight, length, width, height);

        return calculateShippingFee(json);
    }
}