package model;

public class ShippingInfo {
    private int provinceId;
    private String provinceName;
    private int districtId;
    private String districtName;
    private String wardCode;
    private String wardName;
    private int fee;
    private String serviceType;
    
    public ShippingInfo() {}
    
    public ShippingInfo(int provinceId, int districtId, String wardCode, int fee) {
        this.provinceId = provinceId;
        this.districtId = districtId;
        this.wardCode = wardCode;
        this.fee = fee;
    }
    
    // Getters and Setters
    public int getProvinceId() {
        return provinceId;
    }
    
    public void setProvinceId(int provinceId) {
        this.provinceId = provinceId;
    }
    
    public String getProvinceName() {
        return provinceName;
    }
    
    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }
    
    public int getDistrictId() {
        return districtId;
    }
    
    public void setDistrictId(int districtId) {
        this.districtId = districtId;
    }
    
    public String getDistrictName() {
        return districtName;
    }
    
    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }
    
    public String getWardCode() {
        return wardCode;
    }
    
    public void setWardCode(String wardCode) {
        this.wardCode = wardCode;
    }
    
    public String getWardName() {
        return wardName;
    }
    
    public void setWardName(String wardName) {
        this.wardName = wardName;
    }
    
    public int getFee() {
        return fee;
    }
    
    public void setFee(int fee) {
        this.fee = fee;
    }
    
    public String getServiceType() {
        return serviceType;
    }
    
    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }
    
    @Override
    public String toString() {
        return "ShippingInfo{" +
                "provinceId=" + provinceId +
                ", provinceName='" + provinceName + '\'' +
                ", districtId=" + districtId +
                ", districtName='" + districtName + '\'' +
                ", wardCode='" + wardCode + '\'' +
                ", wardName='" + wardName + '\'' +
                ", fee=" + fee +
                ", serviceType='" + serviceType + '\'' +
                '}';
    }
}