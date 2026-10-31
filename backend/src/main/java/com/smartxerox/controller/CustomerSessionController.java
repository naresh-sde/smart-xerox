package com.smartxerox.controller;

import com.smartxerox.model.*;
import com.smartxerox.repository.*;
import com.smartxerox.util.IdGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/public/shops/{qrToken}/sessions")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class CustomerSessionController {
    private final ShopRepository shopRepository;
    private final CustomerSessionRepository sessionRepository;

    @PostMapping("/init")
    public Map<String, Object> init(@PathVariable String qrToken) {
        Shop shop = shopRepository.findByQrToken(qrToken).orElseThrow();
        if (shop.getStatus() != ShopStatus.ACTIVE || !shop.isAcceptOrders()) {
            throw new RuntimeException("Shop not accepting orders");
        }
        String sessionUid = IdGenerator.sessionUid();
        CustomerSession session = CustomerSession.builder()
                .shop(shop)
                .sessionUid(sessionUid)
                .status(SessionStatus.ACTIVE)
                .createdAt(LocalDateTime.now())
                .lastSeenAt(LocalDateTime.now())
                .expiresAt(LocalDateTime.now().plusHours(24))
                .build();
        sessionRepository.save(session);
        Map<String, Object> res = new HashMap<>();
        res.put("sessionId", session.getId());
        res.put("sessionUid", session.getSessionUid());
        res.put("shopId", shop.getId());
        res.put("expiresAt", session.getExpiresAt());
        return res;
    }
}
