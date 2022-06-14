package com.example.cuahangthietbionline.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AbsListView
import android.widget.AdapterView
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.cuahangthietbionline.R
import com.example.cuahangthietbionline.adapter.DienThoaiAdapter
import com.example.cuahangthietbionline.model.Sanpham
import com.example.cuahangthietbionline.ultil.CheckConnection
import com.example.cuahangthietbionline.ultil.Server
import org.json.JSONArray
import org.json.JSONException

class DienThoaiActivity : AppCompatActivity() {
    private lateinit var toolbardt: Toolbar
    private lateinit var lvdt: ListView
    private lateinit var dienThoaiAdapter: DienThoaiAdapter
    private lateinit var mangdt: ArrayList<Sanpham>
    private var iddt:Int = 0
    private var page:Int = 1
    private lateinit var footerview: View
    private var isLoading:Boolean =false
    private var limitdata:Boolean = false
    private lateinit var mhandler:mHandler

   @SuppressLint("HandlerLeak")
   inner class mHandler() : Handler() {
        override fun handleMessage(msg: Message) {
            when (msg.what) {
                0 -> lvdt.addFooterView(footerview)
                1 ->
                {
                    GetData(++page)
                    isLoading=false
                }
            }
            super.handleMessage(msg)
        }
    }

    inner class ThreadData():Thread(){
        override fun run() {
            mhandler.sendEmptyMessage(0)
            try {
                sleep(3000)
            }catch ( e:InterruptedException)
            {
                e.printStackTrace()
            }
            val message = mhandler.obtainMessage(1)
            mhandler.sendMessage(message)
            super.run()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dien_thoai)

        if (CheckConnection.haveNetworkConnection(applicationContext))
        {
            Anhxa()
            GetIdloaisp()
            ActionToolbar()
            GetData(page)
            LoadMoreData()
        }
        else{
            CheckConnection.ShowToast_Short(applicationContext,"Bạn hãy kiểm tra lại kết nối")
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

    private fun LoadMoreData() {
        lvdt.setOnItemClickListener(object : AdapterView.OnItemClickListener{
            override fun onItemClick(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val intent = Intent(applicationContext,ChiTietSanPham::class.java)
                intent.putExtra("thongtinsanpham",mangdt.get(position))
                startActivity(intent)
            }

        })
        lvdt.setOnScrollListener(object : AbsListView.OnScrollListener{
            override fun onScrollStateChanged(view: AbsListView?, scrollState: Int) {

            }

            override fun onScroll(view: AbsListView?, firstVisibleItem: Int, visibleItemCount: Int, totalItemCount: Int) {
                if (firstVisibleItem + visibleItemCount == totalItemCount && totalItemCount!=0 && !isLoading && !limitdata) {
                    isLoading=true
                    val threadData = ThreadData()
                    threadData.start()
                }
            }
        })
    }

    private fun GetData(pg: Int) {
        val requestQueue = Volley.newRequestQueue(applicationContext)
        val duongdan: String = Server.Duongdansanpham + pg.toString()
        val stringRequest = object : StringRequest(
            Method.POST, duongdan,
            Response.Listener { response ->
                var id: Int
                var Tendt: String
                var Giadt: Int
                var Hinhanhdt:String
                var Mota:String
                var Idspdt: Int
                if (response!=null && response.length!=2)
                {
                    lvdt.removeFooterView(footerview)
                    try {
                        val jsonArray = JSONArray(response)
                        for (i in 1..jsonArray.length())
                        {
                            val jsonObject = jsonArray.getJSONObject(i-1)
                            id=jsonObject.getInt("id")
                            Tendt=jsonObject.getString("tensp")
                            Giadt=jsonObject.getInt("giasp")
                            Hinhanhdt=jsonObject.getString("hinhanhsp")
                            Mota=jsonObject.getString("motasp")
                            Idspdt=jsonObject.getInt("idsanpham")
                            mangdt.add(Sanpham(id,Tendt,Giadt,Hinhanhdt,Mota,Idspdt))
                            dienThoaiAdapter.notifyDataSetChanged()
                        }
                    }
                    catch (e:JSONException)
                    {
                        e.printStackTrace()
                    }
                }else{
                    limitdata = true
                    lvdt.removeFooterView(footerview)
                    CheckConnection.ShowToast_Short(applicationContext,"Đã hết dữ liệu.")
                }
            },
            Response.ErrorListener { })
        {
            override fun getParams(): MutableMap<String, String> {
               val param: HashMap<String,String> = HashMap()
                param.put("idsanpham", iddt.toString())
                return param
            }
        }
        requestQueue.add(stringRequest)
    }


    private fun ActionToolbar() {
        setSupportActionBar(toolbardt)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbardt.setNavigationOnClickListener { finish() }
        
    }

    private fun GetIdloaisp() {
        iddt = intent.getIntExtra("idloaisanpham",-1)
        Log.d("giatriloaisanpham",iddt.toString())
    }

    @SuppressLint("InflateParams")
    private fun Anhxa() {
        toolbardt = findViewById(R.id.toolbardienthoai)
        lvdt= findViewById(R.id.listviewdienthoai)
        mangdt = ArrayList()
        dienThoaiAdapter= DienThoaiAdapter(applicationContext,mangdt)
        lvdt.adapter = dienThoaiAdapter
        val inflater: LayoutInflater = getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater
        footerview = inflater.inflate(R.layout.progressbar,null)
        mhandler= mHandler()
    }


}