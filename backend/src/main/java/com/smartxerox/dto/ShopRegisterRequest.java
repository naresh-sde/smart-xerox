package com.smartxerox.dto;

import lombok.Data;
import java.util.List;

@Data
public class ShopRegisterRequest {
    private String shopName;
    private String branchLabel;
    private String ownerName;
    private String phone;
    private String email;
    private String address;
}
