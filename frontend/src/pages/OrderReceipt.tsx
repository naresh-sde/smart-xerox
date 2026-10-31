import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import axios from "axios";

export default function OrderReceipt() {
  const { orderNo } = useParams();
  const [data, setData] = useState<any>(null);
  useEffect(() => {
    // try to fetch - backend needs qr context; simplified
    axios.get(`/api/public/orders/${orderNo}`, { params: { qrToken: "" } }).catch(()=>{});
    // for now just show
    setData({publicOrderNumber: orderNo});
  }, [orderNo]);
  return (
    <div className="max-w-xl mx-auto p-6">
      <h2 className="text-xl font-bold">Order Submitted</h2>
      <div className="mt-2">Order #: <b>{data?.publicOrderNumber}</b></div>
      <div className="text-sm text-gray-600 mt-2">Show this to operator if needed. Status will update on dashboard.</div>
    </div>
  );
}
