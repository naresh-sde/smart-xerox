# Smart Xerox - User Stories & Acceptance Criteria (Updated)

## Personas
- Customer (walk-in) - submits documents via QR
- Shop Operator - reviews, edits settings, prints, updates status
- Shop Owner/Admin - registers shop, manages staff, QR, settings
- Super Admin (optional for MVP) - approve/activate shops

## User Stories

### Shop Onboarding
| ID | Story | Acceptance Criteria |
|---|---|---|
| US-00 | As a shop owner, I want to register my Xerox shop so I can start receiving orders | Form: Shop name, Owner name, Mobile, Email (optional), Address, Branch/Location. Generate unique shop_code. Status: PENDING or ACTIVE based on config. Once active, generate QR with non-guessable qr_token. |
| US-00a | As a shop owner, I want to view/download/print my QR after approval | QR resolves only to ACTIVE shops. Inactive/Paused shops must not accept new orders. |

### Customer
| ID | Story | Acceptance Criteria |
|---|---|---|
| US-01 | As a customer, I want to scan QR so my docs reach the correct shop | QR contains qrToken. Public page shows shop name + branch. Only ACTIVE shops allowed. No customer account needed. |
| US-02 | As a customer, I want a unique identity per visit/session | On first load of shop upload page, backend must create a CustomerSession with a short, unique, human-friendly session_uid (e.g. CX-AB92K). This ID is tied to shop_id. Reuse same session if not expired/completed across multiple orders from same device/session. Name is still asked but NOT the grouping key. |
| US-03 | As a customer, I want to upload multiple documents in one order | Support multiple files (PDF/JPG/JPEG/PNG first). Show names, sizes, progress, remove/retry. Validate type/size. Prevent double-submit with idempotency key. |
| US-04 | As a customer, I want to give optional print preferences | Color/B&W, Paper Size, Copies, Sides. Prefilled from shop defaults. Clearly labeled as preferences - operator will confirm final settings. |
| US-05 | As a customer, I want confirmation with Order Number & Session info | Show Order #, Shop Name, Session ID (CX-XXXXXX), file count, preferences, status RECEIVED. Don'\''t guess ETA. |

### Operator
| ID | Story | Acceptance Criteria |
|---|---|---|
| US-06 | As an operator, I want orders grouped by Customer Session, not just name | Dashboard must group/list by customer_session_id. Order card must show Customer: <Display Name> (Session: CX-AB92K), Order #: SX-000123, files, time, status. Opening order shows all files of THAT order under a session-aware folder. Same person with same name from different scans must appear as different entries. |
| US-07 | As an operator, I want to edit all 4 print settings before confirming | Can edit Print Type, Paper Size, Copies, Sides. Show original customer preferences vs final settings side-by-side. All changes logged in audit. |
| US-08 | As an operator, I want to confirm, print and update status | Explicit confirmation modal. Timestamps on every transition. Can move to IN_REVIEW/NEEDS_CLARIFICATION/CONFIRMED/PRINTING/COMPLETED/CANCELLED with reason if needed. |

### Owner/Admin
| ID | Story | Acceptance Criteria |
|---|---|---|
| US-09 | As an owner, I want to manage shop, staff, QR and pause intake | Manage profile, add/remove operators, download/print QR, toggle acceptOrders, set defaults/limits/retention. |
| US-10 | As owner/customer, I want documents private and auto-deleted as per policy | Shop-level isolation. Files under uploads/{shopId}/{customerSessionUid}/{orderId}/. Signed/short-lived access. Retention job to delete expired orders/files. Access & setting changes audited. No document content in logs. |

## Order Statuses
SUBMITTED → IN_REVIEW → CONFIRMED → PRINTING → COMPLETED  
Can transition to NEEDS_CLARIFICATION or CANCELLED with actor + timestamp + reason.

## Key Rules
- Never group by display name alone - grouping key = customer_session_id
- One session can have multiple orders over time - each new order gets new order_id but same session_uid
- Foldering: physical/storage path must include customerSessionUid to avoid mixing files
- QR identifies shop only - customer identity is per session
- Operator must see Session ID clearly on list and detail to disambiguate same names
