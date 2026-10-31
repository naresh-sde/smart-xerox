package com.smartxerox.controller;

import com.smartxerox.model.*;
import com.smartxerox.repository.*;
import com.smartxerox.util.IdGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("/api/public/shops/{qrToken}/orders")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class OrderPublicController {
    private final ShopRepository shopRepository;
    private final CustomerSessionRepository sessionRepository;
    private final PrintOrderRepository orderRepository;
    private final OrderFileRepository fileRepository;
    private final CustomerPreferenceRepository prefRepository;

    @Value("${app.upload-dir:./uploads}")
    private String uploadDir;

    @PostMapping(consumes = {"multipart/form-data"})
    public Map<String, Object> create(@PathVariable String qrToken,
                                      @RequestHeader(value = "X-Session-Uid", required = false) String sessionUid,
                                      @RequestParam("customerName") String customerName,
                                      @RequestParam(value = "printType", required = false) String printType,
                                      @RequestParam(value = "paperSize", required = false) String paperSize,
                                      @RequestParam(value = "copies", required = false, Integer copies,
                                                    @RequestParam(value = "sides", required = false) String sides,
                                                    @RequestHeader(value = "Idempotency-Key", required = false) String idempotencyKey,
                                                    @RequestPart(value = "files", required = false) List<MultipartFile> files) {
        Shop shop = shopRepository.findByQrToken(qrToken).orElseThrow();
        if (shop.getStatus() != ShopStatus.ACTIVE || !shop.isAcceptOrders()) {
            throw new RuntimeException("Shop not accepting orders");
        }
        if (sessionUid == null) throw new RuntimeException("Session required");
        CustomerSession session = sessionRepository.findByShopAndSessionUid(shop, sessionUid).orElseThrow();

        if (idempotencyKey != null) {
            Optional<PrintOrder> existing = orderRepository.findByIdempotencyKey(idempotencyKey);
            if (existing.isPresent()) {
                Map<String, Object> res = new HashMap<>();
                res.put("orderId", existing.get().getId());
                res.put("publicOrderNumber", existing.get().getPublicOrderNumber());
                res.put("sessionUid", session.getSessionUid());
                res.put("status", existing.get().getStatus());
                res.put("message", "Order already submitted");
                return res;
            }
        }

        PrintOrder order = PrintOrder.builder()
                .shop(shop)
                .customerSession(session)
                .customerName(customerName)
                .publicOrderNumber(IdGenerator.orderNumber())
                .status(OrderStatus.SUBMITTED)
                .idempotencyKey(idempotencyKey)
                .submittedAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
        orderRepository.save(order);

        CustomerPreference pref = CustomerPreference.builder()
                .order(order)
                .printType(printType != null ? printType : shop.getDefaultPrintType())
                .paperSize(paperSize != null ? paperSize : shop.getDefaultPaperSize())
                .copies(copies != null ? copies : shop.getDefaultCopies())
                .sides(sides != null ? sides : shop.getDefaultSides())
                .createdAt(LocalDateTime.now())
                .build();
        prefRepository.save(pref);

        if (files != null) {
            for (MultipartFile mf : files) {
                if (mf.isEmpty()) continue;
                try {
                    File dir = new File(uploadDir + File.separator + shop.getId() + File.separator + session.getSessionUid() + File.separator + order.getId());
                    dir.mkdirs();
                    String stored = UUID.randomUUID().toString() + "_" + mf.getOriginalFilename().replaceAll("[^a-zA-Z0-9._-]", "_");
                    File dest = new File(dir, stored);
                    mf.transferTo(dest);
                    OrderFile of = OrderFile.builder()
                            .order(order)
                            .originalFilename(mf.getOriginalFilename())
                            .storedFilename(stored)
                            .storageKey(dest.getAbsolutePath())
                            .mimeType(mf.getContentType())
                            .sizeBytes(mf.getSize())
                            .createdAt(LocalDateTime.now())
                            .build();
                    fileRepository.save(of);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }

        Map<String, Object> res = new HashMap<>();
        res.put("orderId", order.getId());
        res.put("publicOrderNumber", order.getPublicOrderNumber());
        res.put("sessionUid", session.getSessionUid());
        res.put("customerName", order.getCustomerName());
        res.put("status", order.getStatus());
        res.put("message", "Order submitted successfully");
        return res;
    }

    @GetMapping("/{publicOrderNumber}")
    public Map<String, Object> get(@PathVariable String qrToken, @PathVariable String publicOrderNumber) {
        PrintOrder order = orderRepository.findByPublicOrderNumber(publicOrderNumber).orElseThrow();
        Map<String, Object> res = new HashMap<>();
        res.put("publicOrderNumber", order.getPublicOrderNumber());
        res.put("sessionUid", order.getCustomerSession().getSessionUid());
        res.put("customerName", order.getCustomerName());
        res.put("shopName", order.getShop().getName());
        res.put("status", order.getStatus());
        res.put("submittedAt", order.getSubmittedAt());
        res.put("fileCount", order.getFiles().size());
        return res;
    }
}
