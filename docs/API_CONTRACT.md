# Smart Xerox - API Contract (MVP)

## Public
- GET /api/public/shops/{qrToken} - resolve shop
- POST /api/public/shops/{qrToken}/orders - create order (multipart: customerName, preferences[], files[])
- GET /api/public/orders/{publicOrderNumber} - get receipt/status

## Auth
- POST /api/auth/login - JWT login
- POST /api/auth/refresh

## Operator
- GET /api/operator/orders - list (filter by status/search)
- GET /api/operator/orders/{id} - detail
- PATCH /api/operator/orders/{id}/print-settings - save final settings
- POST /api/operator/orders/{id}/confirm - confirm
- PATCH /api/operator/orders/{id}/status - update status
- GET /api/operator/orders/{id}/files/{fileId}/download - signed download/preview

## Owner/Admin
- POST /api/admin/shops - create shop
- GET /api/admin/shops/{id}/qr - get/regenerate QR
- POST /api/admin/shops/{id}/users - add staff
- PUT /api/admin/shops/{id}/settings - defaults/retention

## Notes
- All operator/admin scoped by shopId
- Files never exposed directly
- Idempotency key for order creation
