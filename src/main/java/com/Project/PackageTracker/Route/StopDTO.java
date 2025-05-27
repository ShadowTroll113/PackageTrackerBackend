package com.Project.PackageTracker.Route;

public class StopDTO {
    private Long branchId;
    private Double latitude;
    private Double longitude;

    public StopDTO(Long branchId, Double latitude, Double longitude) {
        this.branchId = branchId;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Long getBranchId() {
        return branchId;
    }

    public void setBranchId(Long branchId) {
        this.branchId = branchId;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }
}
