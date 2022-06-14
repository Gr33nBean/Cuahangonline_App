package com.example.cuahangthietbionline.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.cuahangthietbionline.R
import com.example.cuahangthietbionline.model.Giohang
import com.example.cuahangthietbionline.model.Sanpham
import com.squareup.picasso.Picasso
import java.text.DecimalFormat

class ChiTietSanPham : AppCompatActivity() {
    private lateinit var toolbarchitiet: Toolbar
    private lateinit var imgchitiet: ImageView
    private lateinit var txtten: TextView
    private lateinit var txtgia: TextView
    private lateinit var txtmota: TextView
    private lateinit var btndatmua: Button
    private lateinit var spinner: Spinner

    private var id:Int = 0
    private var Tenchitiet:String=""
    private var Giachitiet:Int = 0
    private var Hinhanhchitiet:String=""
    private var Motachitiet:String=""
    private var Idsanpham:Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chi_tiet_san_pham)
        AnhXa()
        ActionToolbar()
        GetInformation()
        CatchEventSpinner()
        EvenButton()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId)
        {
            R.id.menugiohang->{
                val intent=Intent(applicationContext,com.example.cuahangthietbionline.activity.Giohang::class.java)
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun EvenButton() {
        btndatmua.setOnClickListener (object : View.OnClickListener {
            override fun onClick(v: View?) {
                if (MainActivity.manggiohang.size > 0) {
                    val sl: Int = (spinner.selectedItem.toString()).toInt()
                    var exists = false
                    for (i in 0 .. (MainActivity.manggiohang.size-1)) {
                        if (MainActivity.manggiohang[i].getIdsp() == id) {
                            MainActivity.manggiohang[i].setSoluongsp(MainActivity.manggiohang[i].getSoluongsp() + sl)
                            if (MainActivity.manggiohang[i].getSoluongsp() >= 10) {
                                MainActivity.manggiohang[i].setSoluongsp(10)
                            }
                            MainActivity.manggiohang[i].setGiasp((Giachitiet * MainActivity.manggiohang[i].getSoluongsp()).toLong())
                            exists = true
                        }
                    }
                    if (exists == false) {
                        val soluong: Int = (spinner.selectedItem.toString()).toInt()
                        val giamoi: Long = (soluong * Giachitiet).toLong()
                        MainActivity.manggiohang.add(Giohang(id,Tenchitiet,giamoi,Hinhanhchitiet,soluong))
                    }
                } else {
                    val soluong: Int = (spinner.selectedItem.toString()).toInt()
                    val giamoi: Long = (soluong * Giachitiet).toLong()
                    MainActivity.manggiohang.add(Giohang(id, Tenchitiet, giamoi, Hinhanhchitiet, soluong))
                }
                val intent = Intent(applicationContext, com.example.cuahangthietbionline.activity.Giohang::class.java)
                startActivity(intent)
            }
        })
    }
    private fun CatchEventSpinner() {
        val soluong = arrayOf(1,2,3,4,5,6,7,8,9,10)
        val arrayAdapter = ArrayAdapter(this,R.layout.spinner_text,soluong)
        spinner.adapter=arrayAdapter
    }

    @SuppressLint("SetTextI18n")
    private fun GetInformation() {
        val sanpham:Sanpham = intent.getSerializableExtra("thongtinsanpham") as Sanpham
        id=sanpham.Id
        Tenchitiet=sanpham.Tensanpham
        Giachitiet=sanpham.Giasanpham
        Hinhanhchitiet=sanpham.Hinhanhsanpham
        Motachitiet=sanpham.Motasanpham
        Idsanpham=sanpham.IdSanpham

        txtten.setText(Tenchitiet)
        val decimalFormat = DecimalFormat("###,###,###")
        txtgia.setText("Giá : "+ decimalFormat.format(Giachitiet)+" Đ")
        txtmota.setText(Motachitiet)
        Picasso.get().load(Hinhanhchitiet).placeholder(R.drawable.noimage)
            .error(R.drawable.errorimage).into(imgchitiet)

    }

    private fun ActionToolbar() {
        setSupportActionBar(toolbarchitiet)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbarchitiet.setNavigationOnClickListener{finish()}

    }

    private fun AnhXa() {
        toolbarchitiet = findViewById(R.id.toolbarchitietsanpham)
        imgchitiet = findViewById(R.id.imageviewchitietsanpham)
        txtten = findViewById(R.id.textviewtenchitietsanpham)
        txtgia = findViewById(R.id.textviewgiachitietsanpham)
        txtmota = findViewById(R.id.textviewmotachitietsanpham)
        btndatmua = findViewById(R.id.buttondatmua)
        spinner = findViewById(R.id.spinner)

    }
}