package com.smartxerox.controller;

import com.smartxerox.model.PrintOrder;
import com.smartxerox.model.Shop;
import com.smartxerox.repository.PrintOrderRepository;
import com.smartxerox.repository.ShopRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/operator/orders")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class OperatorOrderController {
    private final PrintOrderRepository orderRepository;
    private final ShopRepository shopRepository;

    @GetMapping
    public List<Map<String, Object>> list(@RequestParam String shopId) {
        Shop shop = shopRepository.findById(java.util.UUID.fromString(shopId)).orElseThrow();
        return orderRepository.findByShopOrderBySubmittedAtDesc(shop).stream().map(o -> {
            Map<String, Object> m = new HashMap<>();
            m.put("id", o.getId());
            m.put("publicOrderNumber", o.getPublicOrderNumber());
            m.put("customerName", o.getCustomerName());
            m.put("sessionUid", o.getCustomerSession().getSessionUid());
            m.put("status", o.getStatus());
            m.put("submittedAt", o.getSubmittedAt());
            m.put("fileCount", o.getFiles().size());
            return m;
        }).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public Map<String, Object> detail(@PathVariable String id) {
        PrintOrder o = orderRepository.findById(java.util.UUID.fromString(id)).orElseThrow();
        Map<String, Object> m = new HashMap<>();
        m.put("id", o.getId());
        m.put("publicOrderNumber", o.getPublicOrderNumber());
        m.put("customerName", o.getCustomerName());
        m.put("sessionUid", o.getCustomerSession().getSessionUid());
        m.put("shopId", o.getShop().getId());
        m.put("status", o.getStatus());
        m.put("submittedAt", o.getSubmittedAt());
        m.put("files", o.getFiles().stream().map(f -> Map.of(
                "id", f.getId(),
                "originalFilename", f.getOriginalFilename(),
                "sizeBytes", f.getSizeBytes(),
                "mimeType", f.getMimeType()
        )).collect(Collectors.toList()));
        return m;
    }
}
