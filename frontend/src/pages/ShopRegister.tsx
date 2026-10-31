import { useState } from "react";
import axios from "axios";

export default function ShopRegister() {
  const [form, setForm] = useState({shopName:"", branchLabel:"", ownerName:"", phone:"", email:"", address:""});
  const [res, setRes] = useState<any>(null);

  const submit = async (e:any) => {
    e.preventDefault();
    const r = await axios.post("/api/public/shops/register", form);
    setRes(r.data);
  };

  return (
    <div className="max-w-xl mx-auto p-6">
      <h2 className="text-xl font-bold mb-4">Register Shop</h2>
      <form onSubmit={submit} className="space-y-3">
        <input className="w-full border p-2" placeholder="Shop Name" value={form.shopName} onChange={e=>setForm({...form,shopName:e.target.value})}/>
        <input className="w-full border p-2" placeholder="Branch/Location" value={form.branchLabel} onChange={e=>setForm({...form,branchLabel:e.target.value})}/>
        <input className="w-full border p-2" placeholder="Owner Name" value={form.ownerName} onChange={e=>setForm({...form,ownerName:e.target.value})}/>
        <input className="w-full border p-2" placeholder="Phone" value={form.phone} onChange={e=>setForm({...form,phone:e.target.value})}/>
        <input className="w-full border p-2" placeholder="Email" value={form.email} onChange={e=>setForm({...form,email:e.target.value})}/>
        <input className="w-full border p-2" placeholder="Address" value={form.address} onChange={e=>setForm({...form,address:e.target.value})}/>
        <button className="bg-blue-600 text-white px-4 py-2 rounded">Register</button>
      </form>
      {res && (
        <div className="mt-4 p-4 bg-green-50 border rounded">
          <div><b>Shop Code:</b> {res.shopCode}</div>
          <div><b>QR Token:</b> {res.qrToken}</div>
          <div><b>QR URL:</b> <a className="text-blue-600" href={res.qrUrl}>{res.qrUrl}</a></div>
        </div>
      )}
    </div>
  );
}
