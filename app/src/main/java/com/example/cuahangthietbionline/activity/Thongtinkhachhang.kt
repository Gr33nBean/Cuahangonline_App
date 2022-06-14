package com.example.cuahangthietbionline.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.cuahangthietbionline.R
import com.example.cuahangthietbionline.model.Sanpham
import com.example.cuahangthietbionline.ultil.CheckConnection
import com.example.cuahangthietbionline.ultil.Server
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

class Thongtinkhachhang : AppCompatActivity() {

    lateinit var edittenkhachhang: EditText
    lateinit var editemail: EditText
    lateinit var editsdt: EditText
    lateinit var btnxacnhan: Button
    lateinit var btntrove: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_thongtinkhachhang)

        AnhXa()

        btntrove.setOnClickListener {
            finish()
        }

        if (CheckConnection.haveNetworkConnection(applicationContext))
        {
            EventButton();
        }
        else
        {
            CheckConnection.ShowToast_Short(applicationContext,"Bạn hãy kiểm tra lại kết nối.")
        }
    }

    private fun EventButton() {
        btnxacnhan.setOnClickListener {
            val ten:String=edittenkhachhang.text.toString().trim()
            val sdt=editsdt.text.toString().trim()
            val email=editemail.text.toString().trim()
            if (ten.length>0 && sdt.length>0 && email.length>0)
            {
                val requestQueue:RequestQueue = Volley.newRequestQueue(applicationContext)
                val stringRequest: StringRequest =object:StringRequest(Request.Method.POST, Server.Duongdandonhang,
                    Response.Listener { madonhang ->
                    if (madonhang.toString().toInt()>0)
                    {
                        val queue:RequestQueue = Volley.newRequestQueue(applicationContext)
                        val request: StringRequest =object:StringRequest(Method.POST, Server.Duongdanchitietdonhang,Response.Listener
                        { response ->
                            if (response.equals("1"))
                            {
                                MainActivity.manggiohang.clear()
                                CheckConnection.ShowToast_Short(applicationContext,"Bạn đã thêm dữ liệu giỏ hàng thành công")
                                val intent=Intent(applicationContext,MainActivity::class.java)
                                startActivity(intent)
                                CheckConnection.ShowToast_Short(applicationContext,"Mời bạn tiếp tục mua hàng")
                            }
                            else
                            {
                                CheckConnection.ShowToast_Short(applicationContext,"Dữ liệu giỏ hàng bạn đã bị lỗi")
                            }
                        }, Response.ErrorListener {  })
                        {
                            override fun getParams(): MutableMap<String, String> {
                                val jsonArray:JSONArray = JSONArray()
                                for (i in 0 until MainActivity.manggiohang.size)
                                {
                                    val  jsonObject:JSONObject = JSONObject()
                                    try {
                                        jsonObject.put("madonhang",madonhang)
                                        jsonObject.put("masanpham",MainActivity.manggiohang[i].getIdsp())
                                        jsonObject.put("tensanpham",MainActivity.manggiohang[i].getTensp())
                                        jsonObject.put("giasanpham",MainActivity.manggiohang[i].getGiasp())
                                        jsonObject.put("soluongsanpham",MainActivity.manggiohang[i].getSoluongsp())
                                    }catch (e: JSONException)
                                    {
                                        e.printStackTrace()
                                    }
                                    jsonArray.put(jsonObject)
                                }
                                val param: HashMap<String,String> = HashMap()
                                param.put("json",jsonArray.toString())
                                return param
                            }
                        }
                        queue.add(request)
                    }
                },
                    Response.ErrorListener { })
                {
                    override fun getParams(): MutableMap<String, String> {
                        val param: HashMap<String,String> = HashMap()
                        param.put("tenkhachhang",ten)
                        param.put("sodienthoai",sdt)
                        param.put("email",email)

                        return param
                    }
                }
                requestQueue.add(stringRequest)
            }
            else
            {
                CheckConnection.ShowToast_Short(applicationContext,"Hãy kiểm tra lại dữ liệu")
            }
        }
    }

    private fun AnhXa() {
        edittenkhachhang=findViewById(R.id.editexttenkhachhang)
        editemail=findViewById(R.id.editextemail)
        editsdt=findViewById(R.id.editextsodienthoai)
        btnxacnhan=findViewById(R.id.buttonxacnhan)
        btntrove=findViewById(R.id.buttontrove)
    }
}