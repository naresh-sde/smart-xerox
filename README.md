# Smart Xerox

Smart Xerox is a mobile-first web platform for local Xerox and print shops. Each shop receives a unique QR code. Customers scan it and submit documents through a browser - no app installation, no WhatsApp number sharing required. Shop operators review files, edit print settings if needed, confirm and print. Pay at counter.

## Tech Stack
- Backend: Java 17, Spring Boot 3.x, Spring Data JPA, Spring Security (JWT), Validation, Web
- Storage: Local filesystem / AWS S3 compatible (configurable)
- Frontend: React 18 + Vite, TypeScript, React Router, Axios, Tailwind CSS
- DB: PostgreSQL/MySQL (H2 for dev)
- QR: ZXing / qrcode.react
- File Upload: Multipart

## Modules
- public: QR resolve, customer upload page, order receipt/status
- operator: dashboard, order detail, print settings editor, status updates
- admin/owner: shop management, QR, staff, settings, reports
- auth: login, roles (OWNER, OPERATOR)

## Core Features (MVP)
- Shop-specific QR code
- Customer name + multi-file upload (PDF/JPG/JPEG/PNG)
- Order grouped by customer name with unique order number
- Optional customer preferences (Color/B&W, Paper Size, Copies, Sides)
- Operator can edit all 4 settings before confirming
- Order lifecycle: SUBMITTED → IN_REVIEW → CONFIRMED → PRINTING → COMPLETED (or NEEDS_CLARIFICATION/CANCELLED)
- Shop-level isolation
- Private file storage with signed URLs/secure access
- Audit trail of changes

## Quick Start
Backend: cd backend && mvn spring-boot:run
Frontend: cd frontend && npm install && npm run dev

## Project Structure
`
smart-xerox/
├── backend/
├── frontend/
├── docs/
└── README.md
`

## License
MIT
