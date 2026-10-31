import { useEffect, useState } from "react";
import { useParams, useNavigate } from "react-router-dom";
import axios from "axios";

export default function ShopUpload() {
  const { qrToken } = useParams();
  const nav = useNavigate();
  const [shop, setShop] = useState<any>(null);
  const [session, setSession] = useState<any>(null);
  const [customerName, setCustomerName] = useState("");
  const [files, setFiles] = useState<FileList|null>(null);
  const [prefs, setPrefs] = useState({printType:"BLACK_WHITE", paperSize:"A4", copies:1, sides:"ONE_SIDED"});
  const [loading, setLoading] = useState(false);

  useEffect(() => {
    axios.get(`/api/public/shops/${qrToken}`).then(r=>setShop(r.data));
    axios.post(`/api/public/shops/${qrToken}/sessions/init`).then(r=>setSession(r.data));
  }, [qrToken]);

  const submit = async (e:any) => {
    e.preventDefault();
    setLoading(true);
    const fd = new FormData();
    fd.append("customerName", customerName);
    fd.append("printType", prefs.printType);
    fd.append("paperSize", prefs.paperSize);
    fd.append("copies", String(prefs.copies));
    fd.append("sides", prefs.sides);
    if (files) {
      for (let i=0; i<files.length; i++) fd.append("files", files[i]);
    }
    try {
      const r = await axios.post(`/api/public/shops/${qrToken}/orders`, fd, {
        headers: { "X-Session-Uid": session?.sessionUid, "Idempotency-Key": crypto.randomUUID() }
      });
      nav(`/receipt/${r.data.publicOrderNumber}`);
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="max-w-xl mx-auto p-6">
      <h2 className="text-xl font-bold">{shop?.shopName} {shop?.branchLabel && `- ${shop.branchLabel}`}</h2>
      {session && <div className="text-sm text-gray-600 mb-4">Session ID: <b>{session.sessionUid}</b> (unique per scan)</div>}
      <form onSubmit={submit} className="space-y-3">
        <input className="w-full border p-2" placeholder="Your Name" value={customerName} onChange={e=>setCustomerName(e.target.value)} required/>
        <div>
          <label className="block text-sm mb-1">Upload Files (PDF/JPG/JPEG/PNG)</label>
          <input type="file" multiple onChange={e=>setFiles(e.target.files)}/>
        </div>
        <div className="border p-3 rounded bg-white">
          <div className="font-medium mb-2">Print Preferences (operator will confirm final settings)</div>
          <div className="grid grid-cols-2 gap-2">
            <select className="border p-2" value={prefs.printType} onChange={e=>setPrefs({...prefs,printType:e.target.value})}>
              <option value="BLACK_WHITE">Black & White</option>
              <option value="COLOR">Color</option>
            </select>
            <select className="border p-2" value={prefs.paperSize} onChange={e=>setPrefs({...prefs, paperSize:e.target.value})}>
              <option value="A4">A4</option>
              <option value="A3">A3</option>
              <option value="LEGAL">Legal</option>
            </select>
            <input type="number" min={1} className="border p-2" value={prefs.copies} onChange={e=>setPrefs({...prefs,copies:Number(e.target.value)})}/>
            <select className="border p-2" value={prefs.sides} onChange={e=>setPrefs({...prefs,sides:e.target.value})}>
              <option value="ONE_SIDED">Single Sided</option>
              <option value="TWO_SIDED">Double Sided</option>
            </select>
          </div>
        </div>
        <button disabled={loading} className="bg-blue-600 text-white px-4 py-2 rounded">{loading?"Submitting...":"Submit Order"}</button>
      </form>
    </div>
  );
}
