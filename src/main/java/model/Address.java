package model;

public class Address {
    private int districtId;
    private int wardCode;
    private String addressDetail; // Dùng chung cho tên tỉnh, quận, phường
    private String districtName; // Tên quận/huyện
    private String wardName;     // Tên phường/xã

    // Constructor
    public Address(int districtId, int wardCode, String addressDetail, String districtName, String wardName) {
        this.districtId = districtId;
        this.wardCode = wardCode;
        this.addressDetail = addressDetail;
        this.districtName = districtName;
        this.wardName = wardName;
    }

    // Getters
    public int getDistrictId() {
        return districtId;
    }
    
    public int getWardCode() {
        return wardCode;
    }

    public String getAddressDetail() {
        return addressDetail;
    }

    public String getDistrictName() {
        return districtName;
    }

    public String getWardName() {
        return wardName;
    }

    @Override
    public String toString() {
        // Đây là cách hiển thị trong ComboBox (nếu dùng Swing) hoặc Debug
        // Với JSP, bạn sẽ truy cập từng getter để hiển thị
        if (addressDetail != null && !addressDetail.isEmpty()) {
             return addressDetail;
        } else if (districtName != null && !districtName.isEmpty()) {
            return districtName;
        } else if (wardName != null && !wardName.isEmpty()){
            return wardName;
        }
        return "Unknown Address";
    }
}