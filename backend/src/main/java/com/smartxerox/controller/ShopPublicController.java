package com.smartxerox.controller;

import com.smartxerox.dto.ShopRegisterRequest;
import com.smartxerox.model.Shop;
import com.smartxerox.model.ShopStatus;
import com.smartxerox.repository.ShopRepository;
import com.smartxerox.util.IdGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/public/shops")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ShopPublicController {
    private final ShopRepository shopRepository;

    @PostMapping("/register")
    public Map<String, Object> register(@RequestBody ShopRegisterRequest req) {
        Shop shop = Shop.builder()
                .shopCode(IdGenerator.shopCode())
                .qrToken(IdGenerator.qrToken())
                .name(req.getShopName())
                .branchLabel(req.getBranchLabel())
                .ownerName(req.getOwnerName())
                .phone(req.getPhone())
                .email(req.getEmail())
                .address(req.getAddress())
                .status(ShopStatus.ACTIVE)
                .acceptOrders(true)
                .retentionDays(7)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
        shopRepository.save(shop);
        Map<String, Object> res = new HashMap<>();
        res.put("shopId", shop.getId());
        res.put("shopCode", shop.getShopCode());
        res.put("qrToken", shop.getQrToken());
        res.put("qrUrl", "/shop/" + shop.getQrToken());
        res.put("status", shop.getStatus());
        res.put("message", "Shop registered successfully");
        return res;
    }

    @GetMapping("/{qrToken}")
    public Map<String, Object> resolve(@PathVariable String qrToken) {
        Shop shop = shopRepository.findByQrToken(qrToken).orElseThrow();
        if (shop.getStatus() != ShopStatus.ACTIVE || !shop.isAcceptOrders()) {
            throw new RuntimeException("Shop not accepting orders");
        }
        Map<String, Object> res = new HashMap<>();
        res.put("shopId", shop.getId());
        res.put("shopName", shop.getName());
        res.put("branchLabel", shop.getBranchLabel());
        res.put("defaults", Map.of(
                "printType", shop.getDefaultPrintType(),
                "paperSize", shop.getDefaultPaperSize(),
                "copies", shop.getDefaultCopies(),
                "sides", shop.getDefaultSides()
        ));
        return res;
    }
}
