package com.example.cuahangthietbionline.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.animation.AnimationUtils
import android.widget.AdapterView
import android.widget.ImageView
import android.widget.ListView
import android.widget.ViewFlipper
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.example.cuahangthietbionline.R
import com.example.cuahangthietbionline.adapter.LoaispAdapter
import com.example.cuahangthietbionline.adapter.SanphamAdapter
import com.example.cuahangthietbionline.model.Giohang
import com.example.cuahangthietbionline.model.Loaisp
import com.example.cuahangthietbionline.model.Sanpham
import com.example.cuahangthietbionline.ultil.CheckConnection
import com.example.cuahangthietbionline.ultil.Images
import com.example.cuahangthietbionline.ultil.Server
import com.example.cuahangthietbionline.ultil.SpacingItemDecorator
import com.google.android.material.navigation.NavigationView
import com.squareup.picasso.Picasso
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject


class MainActivity : AppCompatActivity() {

    private lateinit var toolbar: Toolbar
    private lateinit var viewFlipper: ViewFlipper
    private lateinit var recyclerViewmanhinhchinh: RecyclerView
    private lateinit var navigationView: NavigationView
    private lateinit var listViewmanhinhchinh: ListView
    private lateinit var drawerLayout: DrawerLayout

    private lateinit var mangloaisp: ArrayList<Loaisp>
    private lateinit var loaispAdapter: LoaispAdapter

    private lateinit var mangsanpham: ArrayList<Sanpham>
    private lateinit var sanphamAdapter: SanphamAdapter

    companion object {
       var manggiohang: ArrayList<Giohang> = ArrayList()
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        AnhXa()
        if (CheckConnection.haveNetworkConnection(applicationContext))
        {

            ActionBar()
            ActionViewFlipper()
            GetDuLieuLoaisp()
            GetDuLieuSPMoiNhat()
            CatchOnItemListView()
        }
        else{
            CheckConnection.ShowToast_Short(applicationContext,"Bạn hãy kiểm lại kết nối")
            finish()
        }

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

    private fun CatchOnItemListView() {
        listViewmanhinhchinh.onItemClickListener =
            AdapterView.OnItemClickListener { _, _, position, _ ->
                when (position) {
                    0 -> {
                        if (CheckConnection.haveNetworkConnection(applicationContext))
                        {
                            val intent = Intent(this@MainActivity, MainActivity::class.java)
                            startActivity(intent)
                        }
                        else{
                            CheckConnection.ShowToast_Short(applicationContext,"Bạn hãy kiểm tra kết nối")
                        }
                        drawerLayout.closeDrawer(GravityCompat.START)
                    }
                    1 -> {
                        if (CheckConnection.haveNetworkConnection(applicationContext))
                        {
                            val intent = Intent(this@MainActivity, DienThoaiActivity::class.java)
                            intent.putExtra("idloaisanpham", mangloaisp[position].Id)
                            startActivity(intent)
                        }
                        else{
                            CheckConnection.ShowToast_Short(applicationContext,"Bạn hãy kiểm tra kết nối")
                        }
                        drawerLayout.closeDrawer(GravityCompat.START)
                    }
                    2 -> {
                        if (CheckConnection.haveNetworkConnection(applicationContext))
                        {
                            val intent = Intent(this@MainActivity, LapTopActivity::class.java)
                            intent.putExtra("idloaisanpham", mangloaisp[position].Id)
                            startActivity(intent)
                        }
                        else{
                            CheckConnection.ShowToast_Short(applicationContext,"Bạn hãy kiểm tra kết nối")
                        }
                        drawerLayout.closeDrawer(GravityCompat.START)
                    }
                    3 -> {
                        if (CheckConnection.haveNetworkConnection(applicationContext))
                        {
                            val intent = Intent(this@MainActivity, LienHeActivity::class.java)
                            startActivity(intent)
                        }
                        else{
                            CheckConnection.ShowToast_Short(applicationContext,"Bạn hãy kiểm tra kết nối")
                        }
                        drawerLayout.closeDrawer(GravityCompat.START)
                    }
                    4 -> {
                        if (CheckConnection.haveNetworkConnection(applicationContext))
                        {
                            val intent = Intent(this@MainActivity, ThongTinActivity::class.java)
                            startActivity(intent)
                        }
                        else{
                            CheckConnection.ShowToast_Short(applicationContext,"Bạn hãy kiểm tra kết nối")
                        }
                        drawerLayout.closeDrawer(GravityCompat.START)
                    }
                    else -> { // Note the block
                        CheckConnection.ShowToast_Short(applicationContext,"Lỗi!")
                    }
                }
            }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun GetDuLieuSPMoiNhat() {
        val requestQueue = Volley.newRequestQueue(applicationContext)

        val jsonArrayRequest = JsonArrayRequest(Server.DuongDansanphammoinhat,
            { response:JSONArray ->
                var ID:Int
                var Tensanpham:String
                var Giasanpham: Int
                var Hinhanhsanpham: String
                var Motasanpham: String
                var Idsanpham: Int
                for (i in 1..response.length())
                {
                    try {
                        val jsonObject:JSONObject = response.getJSONObject(i-1)
                        ID = jsonObject.getInt("id")
                        Tensanpham = jsonObject.getString("tensp")
                        Giasanpham = jsonObject.getInt("giasp")
                        Hinhanhsanpham= jsonObject.getString("hinhanhsp")
                        Motasanpham=jsonObject.getString("motasp")
                        Idsanpham=jsonObject.getInt("idsanpham")
                        mangsanpham.add(Sanpham(ID,Tensanpham,Giasanpham,Hinhanhsanpham,Motasanpham,Idsanpham))
                        sanphamAdapter.notifyDataSetChanged()
                    }
                    catch (e:JSONException)
                    {
                        e.printStackTrace()
                    }
                }
            }, { error ->
                CheckConnection.ShowToast_Short(applicationContext, error.toString())
            })
        requestQueue.add(jsonArrayRequest)

    }

    private fun GetDuLieuLoaisp() {
        val requestQueue = Volley.newRequestQueue(applicationContext)

        val jsonArrayRequest = JsonArrayRequest(Server.DuongDanLoaisp,
            { response:JSONArray ->
                var id: Int
                var tenloaisp: String
                var hinhanhloaisp: String
                for (i in 1..response.length())
                {
                    try {
                        val jsonObject:JSONObject = response.getJSONObject(i-1)
                        id =jsonObject.getInt("id")
                        tenloaisp=jsonObject.getString("tenloaisp")
                        hinhanhloaisp=jsonObject.getString("hinhanhloaisp")
                        mangloaisp.add(Loaisp(id, tenloaisp, hinhanhloaisp))
                        loaispAdapter.notifyDataSetChanged()
                    }
                    catch (e:JSONException)
                    {
                        e.printStackTrace()
                    }
                }
                mangloaisp.add(mangloaisp.count(), Loaisp(0,"Liên hệ",Images.Contact))
                mangloaisp.add(mangloaisp.count(), Loaisp(0,"Thông tin",Images.Infor))
            }, { error ->
                CheckConnection.ShowToast_Short(applicationContext, error.toString())
            })
        requestQueue.add(jsonArrayRequest)

    }

    private fun ActionViewFlipper() {
        val mangquangcao: ArrayList<String> = ArrayList()
        mangquangcao.add("https://cdn.cellphones.com.vn/media/ltsoft/promotion/690x300_TUF-2022.png")
        mangquangcao.add("https://cdn2.cellphones.com.vn/690x300/https://dashboard.cellphones.com.vn/storage/matebook-d15.jpg")
        mangquangcao.add("https://cdn2.cellphones.com.vn/690x300/https://dashboard.cellphones.com.vn/storage/Tab%20s7.png")
        mangquangcao.add("https://cdn.didongviet.vn/pub/media/mageplaza/bannerslider/banner/image/s/a/sale-giua-thang-6_780x520.ai.jpg")
        for (item in mangquangcao)
        {
            val imageView = ImageView(applicationContext)
            Picasso.get().load(item).into(imageView)
            imageView.scaleType = ImageView.ScaleType.FIT_XY
            viewFlipper.addView(imageView)
        }

        viewFlipper.flipInterval = 5000
        viewFlipper.isAutoStart = true
        val animation_slide_in = AnimationUtils.loadAnimation(applicationContext,R.anim.slide_in_right)
        val animation_slide_out = AnimationUtils.loadAnimation(applicationContext,R.anim.slide_out_right)
        viewFlipper.inAnimation = animation_slide_in
        viewFlipper.outAnimation = animation_slide_out
    }

    private fun ActionBar() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationIcon(android.R.drawable.ic_menu_sort_by_size)
        toolbar.setNavigationOnClickListener { drawerLayout.openDrawer(GravityCompat.START) }
    }

    private fun AnhXa() {
        toolbar =  findViewById(R.id.toolbarmanhinhchinh)
        viewFlipper = findViewById(R.id.viewlipper)
        recyclerViewmanhinhchinh=findViewById(R.id.recyclerview)
        navigationView=findViewById(R.id.navigationview)
        listViewmanhinhchinh=findViewById(R.id.listviewmanhinhchinh)
        drawerLayout = findViewById(R.id.drawerlayout)

        mangloaisp = ArrayList()
        mangloaisp.add(0, Loaisp(0,"Trang chính",Images.Home))
        loaispAdapter = LoaispAdapter(mangloaisp,applicationContext)
        listViewmanhinhchinh.adapter=loaispAdapter

        mangsanpham= ArrayList()
        sanphamAdapter= SanphamAdapter(mangsanpham,applicationContext)
        recyclerViewmanhinhchinh.setHasFixedSize(true)
        recyclerViewmanhinhchinh.layoutManager=GridLayoutManager(applicationContext,2)
        val spacing = SpacingItemDecorator(SpacingItemDecorator.SpaceNumber)
        recyclerViewmanhinhchinh.addItemDecoration(spacing)
        recyclerViewmanhinhchinh.adapter=sanphamAdapter

        if (manggiohang != null) {

        } else {
            manggiohang = ArrayList()
        }

    }

}