package com.example.cuahangthietbionline.activity

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import com.example.cuahangthietbionline.R
import com.example.cuahangthietbionline.adapter.GiohangAdapter
import com.example.cuahangthietbionline.ultil.CheckConnection
import java.text.DecimalFormat

class Giohang : AppCompatActivity() {

    private lateinit var lvgiohang: ListView
    private lateinit var txtthongbao: TextView

    private lateinit var btnthanhtoan: Button
    private lateinit var btntieptucmua: Button
    private lateinit var toolbargiohang: Toolbar
    private lateinit var giohangAdapter: GiohangAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_giohang)
        AnhXa()
        ActionToolbar()
        CheckData()
        EvenUltil()
        CatchOnItemListView()
        EventButton()
    }

    private fun EventButton() {
        btntieptucmua.setOnClickListener {
            val intent = Intent(applicationContext, MainActivity::class.java)
            startActivity(intent)
        }
        btnthanhtoan.setOnClickListener {
            if (MainActivity.manggiohang.size > 0) {
                val intent = Intent(applicationContext, Thongtinkhachhang::class.java)
                startActivity(intent)
            } else {
                CheckConnection.ShowToast_Short(
                    applicationContext,
                    "Giỏ hàng của bạn chưa có sản phẩm để thanh toán."
                )

            }
        }
    }

    private fun CatchOnItemListView() {
        lvgiohang.onItemLongClickListener = object : AdapterView.OnItemLongClickListener{
            override fun onItemLongClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long): Boolean {
                var builder:AlertDialog.Builder = AlertDialog.Builder(this@Giohang)
                builder.setTitle("Xác nhận xóa sản phẩm");
                builder.setMessage("Bạn có chắc muốn xóa sản phẩm này")
                builder.setPositiveButton("Có"
                ) { dialog, which ->
                    if (MainActivity.manggiohang.size <= 0) {
                        txtthongbao.visibility = View.VISIBLE

                    } else {
                        MainActivity.manggiohang.removeAt(position)
                        giohangAdapter.notifyDataSetChanged()
                        EvenUltil()
                        if (MainActivity.manggiohang.size <= 0) {
                            txtthongbao.visibility = View.VISIBLE
                        } else {
                            txtthongbao.visibility = View.INVISIBLE
                        }
                    }
                }
                builder.setNegativeButton("Không",object : DialogInterface.OnClickListener{
                    override fun onClick(dialog: DialogInterface?, which: Int) {
                        giohangAdapter.notifyDataSetChanged()
                        EvenUltil()
                    }

                })
                builder.show()
                return true
            }
        }
    }

    companion object{

        lateinit var txttongtien: TextView

        @SuppressLint("SetTextI18n")
        fun EvenUltil() {
            var tongtien:Long = 0
            for (i in 0 until MainActivity.manggiohang.size)
            {
                tongtien += MainActivity.manggiohang[i].getGiasp()
            }
            val decimalFormat=DecimalFormat("###,###,###")
            txttongtien.text = decimalFormat.format(tongtien)+" Đ"

        }
    }

    private fun CheckData() {
        if (MainActivity.manggiohang.size<=0)
        {
            giohangAdapter.notifyDataSetChanged()
            txtthongbao.visibility= View.VISIBLE
            lvgiohang.visibility=View.INVISIBLE
        }
        else
        {
            giohangAdapter.notifyDataSetChanged()
            txtthongbao.visibility = View.INVISIBLE
            lvgiohang.visibility=View.VISIBLE
        }
    }

    private fun ActionToolbar() {
        setSupportActionBar(toolbargiohang)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbargiohang.setNavigationOnClickListener{finish()}
    }

    private fun AnhXa() {
        lvgiohang= findViewById(R.id.listviewgiohang)
        txtthongbao=findViewById(R.id.textviewthongbao)
        txttongtien=findViewById(R.id.textviewtongtien)
        btnthanhtoan=findViewById(R.id.buttonthanhtoangiohang)
        btntieptucmua=findViewById(R.id.buttontieptucmuahang)
        toolbargiohang=findViewById(R.id.toolbargiohang)
        val array=MainActivity.manggiohang
        giohangAdapter= GiohangAdapter(this@Giohang,array)
        lvgiohang.adapter=giohangAdapter
    }
}