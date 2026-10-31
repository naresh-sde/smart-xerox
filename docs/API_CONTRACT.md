# Smart Xerox - API Contract (Updated)

## Public
- GET /api/public/shops/{qrToken} - resolve ACTIVE shop (returns shop info + config). If shop inactive/paused -> 403.
- POST /api/public/shops/{qrToken}/sessions/init - create or resume CustomerSession. Returns {sessionId, sessionUid: "CX-AB92K", shopId, expiresAt}
- POST /api/public/shops/{qrToken}/orders - create order (multipart). Requires valid sessionUid in header/cookie. Body: customerName, preferences{printType,paperSize,copies,sides}, files[]. Include Idempotency-Key.
- GET /api/public/orders/{publicOrderNumber} - receipt/status (optionally verify with sessionUid)

## Auth
- POST /api/auth/login - JWT login (OWNER/OPERATOR)
- POST /api/auth/refresh

## Shop Registration (Onboarding)
- POST /api/public/shops/register - register new shop. Status PENDING/ACTIVE per config. Returns shopCode + next steps.
- GET /api/public/shops/registration/status?shopCode=... - check status

## Operator
- GET /api/operator/orders - list orders for shop. Must support grouping/filter by sessionUid, customerName, orderNumber, status, date.
- GET /api/operator/orders/{id} - detail with files, customer prefs, final settings, session info
- PATCH /api/operator/orders/{id}/print-settings - save final settings (audit logged)
- POST /api/operator/orders/{id}/confirm - confirm final settings
- PATCH /api/operator/orders/{id}/status - update status (with reason if NEEDS_CLARIFICATION/CANCELLED)
- GET /api/operator/orders/{id}/files/{fileId}/download - secure download/preview (short-lived)

## Owner/Admin
- POST /api/admin/shops - create/activate shop (SUPER_ADMIN)
- GET /api/admin/shops/{id}/qr - get/regenerate QR (non-guessable token)
- POST /api/admin/shops/{id}/users - add staff (OWNER only)
- PUT /api/admin/shops/{id}/settings - defaults, limits, retention, acceptOrders toggle
- GET /api/admin/shops/{id}/sessions - list active customer sessions (for audit/debug)

## Notes
- Never trust customerName for scoping - use sessionUid + shopId
- All operator/admin scoped by shopId
- Idempotency-Key required for order creation
- File access must validate shopId + order ownership
