# Smart Xerox - Customer Session Design

## Problem
When customers share same name (e.g. "Rahul"), grouping only by name causes confusion. Need a way to distinguish different people/sessions.

## Solution
Create a **CustomerSession** per unique visit to a shop via QR.

### Flow
1. Customer scans QR -> GET /api/public/shops/{qrToken}
2. If shop ACTIVE, call POST /api/public/shops/{qrToken}/sessions/init
   - Generate session_uid: `CX-` + 6 alphanumeric (uppercase) e.g. `CX-AB92K`
   - Set cookie `sx_session` (HttpOnly, SameSite=Lax, Secure in prod) or return in response
   - Link to shop_id, set expires_at (e.g. 24-48 hours)
3. Customer enters display name + uploads files + submits order
   - Order created with customer_session_id and customer_name (display only)
4. Operator sees: `Rahul Kumar (CX-AB92K) | Order: SX-000123`

### Rules
- session_uid is the **primary grouping key** for operator view
- One session can create multiple orders (if they submit again in same session)
- Different scans/devices get different sessions
- Foldering uses session_uid to prevent file mixing
- Name collisions are irrelevant
- Session expires after inactivity or configured TTL

### UI Changes
- Customer page: show "Session ID: CX-AB92K" subtly (helps if they return)
- Operator dashboard: group by session or show session badge prominently
- Order detail: header shows "Customer Session: CX-AB92K | Display Name: ..."
