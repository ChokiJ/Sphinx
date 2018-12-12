package xyz.choki.sphinx

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_hidden_place.*
import org.jetbrains.anko.find
import org.jetbrains.anko.toast
import java.nio.file.Files.find
import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
//import sun.text.normalizer.UTF16.append




class activity_hidden_place : AppCompatActivity(){
    var longitude:Double = 0.0
    var latitude:Double = 0.0
    var lastlongitude:Double = 0.0
    var lastlatitude:Double = 0.0
    private var locationClientSingle: AMapLocationClient? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hidden_place)
        buttonGetGPS.setOnClickListener()
        {
            startSingleLocation()
        }

    }
    //单次定位
    fun startSingleLocation(){
        if (null == locationClientSingle) {
            locationClientSingle = AMapLocationClient(this.applicationContext)
        }
        val locationClientOption = AMapLocationClientOption()
        //使用单次定位
        locationClientOption.isOnceLocation = true
        // 地址信息
        locationClientOption.isNeedAddress = true
        locationClientOption.isLocationCacheEnable = false
        locationClientSingle!!.setLocationOption(locationClientOption)
        locationClientSingle!!.setLocationListener(locationSingleListener)
        locationClientSingle!!.startLocation()
    }

    /**
     * 单次客户端的定位监听
     */
    var locationSingleListener: AMapLocationListener = AMapLocationListener { location ->
        val callBackTime = System.currentTimeMillis()
        val sb = StringBuffer()
        sb.append("单次定位完成\n")
        //sb.append("回调时间: " + Utils.formatUTC(callBackTime, null) + "\n")
        if (null == location) {
            sb.append("定位失败：location is null!!!!!!!")
        } else {
            sb.append(Utils.getLocationStr(location))
        }
        //textGPS.setText(sb.toString())
        lastlongitude = longitude
        lastlatitude = latitude
        longitude = location.longitude
        latitude = location.latitude
        textGPS.text = "当前记录\n经度$longitude 纬度$latitude\n上次记录\n经度$lastlongitude 纬度$lastlatitude"
    }
}

