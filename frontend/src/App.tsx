import { Routes, Route, Link } from "react-router-dom";
import Home from "./pages/Home";
import ShopRegister from "./pages/ShopRegister";
import ShopUpload from "./pages/ShopUpload";
import OrderReceipt from "./pages/OrderReceipt";
import OperatorDashboard from "./pages/OperatorDashboard";
import OperatorOrderDetail from "./pages/OperatorOrderDetail";

export default function App() {
  return (
    <div className="min-h-screen bg-gray-50">
      <nav className="bg-white shadow px-4 py-3 flex gap-4">
        <Link to="/" className="font-semibold">Smart Xerox</Link>
        <Link to="/register-shop">Register Shop</Link>
        <Link to="/operator">Operator</Link>
      </nav>
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/register-shop" element={<ShopRegister />} />
        <Route path="/shop/:qrToken" element={<ShopUpload />} />
        <Route path="/receipt/:orderNo" element={<OrderReceipt />} />
        <Route path="/operator" element={<OperatorDashboard />} />
        <Route path="/operator/orders/:id" element={<OperatorOrderDetail />} />
      </Routes>
    </div>
  );
}
