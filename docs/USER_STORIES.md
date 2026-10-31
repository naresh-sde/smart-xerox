# Smart Xerox - User Stories & Acceptance Criteria

## Personas
- Customer (walk-in) - submits documents via QR
- Shop Operator - reviews, edits settings, prints, updates status
- Shop Owner/Admin - manages shop, staff, QR, settings

## User Stories

### Customer
| ID | Story | Acceptance Criteria |
|---|---|---|
| US-01 | As a customer, I want to scan QR so docs reach correct shop | QR has non-guessable token; shows shop name/branch; inactive shops blocked; no account needed |
| US-02 | As a customer, I want to enter name to identify order | Name required; basic validation; privacy note shown; order ID independent |
| US-03 | As a customer, I want to upload multiple docs | Multiple files, show names/sizes/progress, remove/retry, type/size validation, handle failures |
| US-04 | As a customer, I want optional print preferences | Color/B&W, paper size, copies, sides; defaults from shop; marked as preference subject to operator confirmation |
| US-05 | As a customer, I want confirmation with order number | Prevent double submit, show order number/summary/status, no fake ETA unless set |

### Operator
| ID | Story | Acceptance Criteria |
|---|---|---|
| US-06 | As operator, I want orders grouped by customer | Dashboard newest first, shows name/order ID/files/time/status; order detail is folder per order |
| US-07 | As operator, I want to edit all 4 print settings | Can edit print type/paper size/copies/sides before confirm; original prefs visible for audit; final settings distinct |
| US-08 | As operator, I want to confirm and process | Confirmation summarizes files+final settings; explicit confirm; timestamps; can mark completed or flag problem |

### Owner/Admin
| ID | Story | Acceptance Criteria |
|---|---|---|
| US-09 | As owner, I want to manage shop & QR | View/download/print QR, pause orders, staff scoped to shop |
| US-10 | As owner/customer, I want docs private & deleted on schedule | Only shop staff access, HTTPS, private storage, signed URLs, retention config, audit logs |

## Order Statuses
SUBMITTED → IN_REVIEW → CONFIRMED → PRINTING → COMPLETED  
Transitions can go to NEEDS_CLARIFICATION or CANCELLED with audit.

## Non-functional (MVP)
- Mobile-first
- Shop-level isolation
- Validation & rate limiting
- Secure file access
- Idempotent submission
