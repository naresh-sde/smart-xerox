import { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import axios from "axios";

export default function OperatorDashboard() {
  const [orders, setOrders] = useState<any[]>([]);
  const shopId = localStorage.getItem("shopId") || prompt("Enter Shop ID") || "";
  useEffect(() => {
    if (shopId) {
      localStorage.setItem("shopId", shopId);
      axios.get(`/api/operator/orders?shopId=${shopId}`).then(r=>setOrders(r.data));
    }
  }, [shopId]);
  return (
    <div className="max-w-4xl mx-auto p-6">
      <h2 className="text-xl font-bold mb-4">Operator Dashboard (grouped by Session)</h2>
      <div className="space-y-2">
        {orders.map(o => (
          <Link to={`/operator/orders/${o.id}`} key={o.id} className="block border rounded p-3 bg-white hover:bg-gray-50">
            <div className="flex justify-between">
              <div>
                <div><b>{o.customerName}</b> <span className="text-xs bg-blue-100 px-2 py-0.5 rounded">Session: {o.sessionUid}</span></div>
                <div className="text-sm text-gray-600">Order #: {o.publicOrderNumber} | Files: {o.fileCount}</div>
              </div>
              <div className="text-sm">{o.status}</div>
            </div>
          </Link>
        ))}
      </div>
    </div>
  );
}
