# Smart Xerox - Data Model (Updated)

## Entities

### Shop
- id (UUID/Long)
- shop_code (unique, human friendly e.g. SX-00123)
- qr_token (unique, non-guessable, URL safe)
- name, branch_label, address, phone, email, owner_name
- status (PENDING | ACTIVE | INACTIVE | PAUSED)
- accept_orders (boolean)
- defaults (print_type/paper_size/copies/sides)
- limits (max_files, max_size_mb, allowed_types)
- retention_days (int)
- created_at, updated_at, approved_at, approved_by

### ShopUser
- id
- shop_id (FK)
- name, username/email, password_hash
- role (OWNER | OPERATOR | SUPER_ADMIN)
- status (ACTIVE | INACTIVE)
- created_at, last_login_at

### CustomerSession
- id (UUID)
- shop_id (FK)
- session_uid (unique per shop or globally, short e.g. CX-AB92K) - human friendly, used for disambiguation
- display_name (optional from first order, or collected)
- device_fingerprint_hash (optional)
- ip_hash (optional for rate limiting)
- created_at, last_seen_at, expires_at
- status (ACTIVE | COMPLETED | EXPIRED)

### PrintOrder
- id (UUID)
- shop_id (FK)
- customer_session_id (FK) **NOT NULL**
- public_order_number (unique, e.g. SX-00012345)
- customer_name (display only)
- status (SUBMITTED | IN_REVIEW | NEEDS_CLARIFICATION | CONFIRMED | PRINTING | COMPLETED | CANCELLED)
- submitted_at, updated_at, completed_at, cancelled_at
- idempotency_key (unique per shop or globally)
- notes (operator notes)

### OrderFile
- id (UUID)
- order_id (FK)
- original_filename
- stored_filename
- storage_key/path
- mime_type, size_bytes
- checksum
- upload_status
- created_at

### CustomerPreference (snapshot)
- id
- order_id (FK)
- print_type, paper_size, copies, sides
- created_at

### FinalPrintSettings
- id
- order_id (FK)
- print_type, paper_size, copies, sides
- updated_by (shop_user_id)
- updated_at

### OrderEvent (Audit)
- id
- order_id (FK)
- shop_id (FK)
- actor_id (shop_user_id or null for system/customer)
- actor_type (SYSTEM | CUSTOMER | OPERATOR | OWNER)
- event_type (ORDER_CREATED | SETTINGS_EDITED | STATUS_CHANGED | FILE_UPLOADED | CONFIRMED)
- old_value, new_value (JSON)
- reason (text)
- ip_hash, created_at

## Rules
- **Grouping key**: PrintOrder.customer_session_id
- **Folder structure**: uploads/{shopId}/{customerSession.session_uid}/{orderId}/
- **Uniqueness**: session_uid should be unique per shop (or globally) and short
- **Disambiguation**: Operator UI must show (Display Name + Session ID)
- **Isolation**: Every query must filter by shop_id
