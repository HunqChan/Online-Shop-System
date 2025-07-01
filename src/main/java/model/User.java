package model;

import lombok.*;

import java.sql.Timestamp;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private int userId;
    private int roleId;
    private String fullName;
    private String password;
    private String avatarUrl;
    private Boolean gender;
    private String email;
    private String phoneNumber;

    private Integer provinceId;
    private String provinceName;
    private String wardCode;
    private String wardName;
    private String detailAddress;

    private Timestamp createdAt;
    private Timestamp updatedAt;
    private boolean isDeleted;
    private String resetPasswordToken;
    private Timestamp resetPasswordExpiry;
}
