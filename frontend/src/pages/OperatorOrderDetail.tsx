import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import axios from "axios";

export default function OperatorOrderDetail() {
  const { id } = useParams();
  const [order, setOrder] = useState<any>(null);
  useEffect(() => {
    axios.get(`/api/operator/orders/${id}`).then(r=>setOrder(r.data));
  }, [id]);
  return (
    <div className="max-w-3xl mx-auto p-6">
      <h2 className="text-xl font-bold mb-2">Order Detail</h2>
      {order && (
        <div className="space-y-3">
          <div className="bg-white border rounded p-3">
            <div><b>Customer:</b> {order.customerName}</div>
            <div><b>Session ID:</b> <span className="bg-blue-100 px-2 py-0.5 rounded">{order.sessionUid}</span> <span className="text-xs text-gray-500">(unique identity)</span></div>
            <div><b>Order #:</b> {order.publicOrderNumber}</div>
            <div><b>Status:</b> {order.status}</div>
          </div>
          <div className="bg-white border rounded p-3">
            <div className="font-medium mb-2">Files ({order.files?.length})</div>
            {order.files?.map((f:any) => (
              <div key={f.id} className="border-b py-1 text-sm">{f.originalFilename} ({Math.round(f.sizeBytes/1024)} KB)</div>
            ))}
          </div>
        </div>
      )}
    </div>
  );
}
