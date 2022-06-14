package com.example.cuahangthietbionline.activity

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import com.example.cuahangthietbionline.R
import com.example.cuahangthietbionline.databinding.ActivityThongTinBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class ThongTinActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityThongTinBinding
    private lateinit var toolbarthongtin: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityThongTinBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        toolbarthongtin = findViewById(R.id.toolbarthongtin)
        funActionBar()
    }

    private fun funActionBar() {
        setSupportActionBar(toolbarthongtin)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbarthongtin.setNavigationOnClickListener{finish()}
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mMap.isMyLocationEnabled=true
        // Add a marker in Sydney and move the camera
        val uitschool = LatLng(10.87020640161575, 106.80335551850331)
        mMap.addMarker(MarkerOptions().position(uitschool).title("Trường Đại học Công nghệ Thông tin - ĐHQG TP.HCM").snippet("Đường Hàn Thuyên, khu phố 6 P, Thủ Đức, Thành phố Hồ Chí Minh, Việt Nam").icon(BitmapDescriptorFactory.defaultMarker()))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(uitschool))
        mMap.mapType = GoogleMap.MAP_TYPE_NORMAL
        val cameraPosition=CameraPosition.builder().target(uitschool).zoom(90F).build()
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))

    }
}