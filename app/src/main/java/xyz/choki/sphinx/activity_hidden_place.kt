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

class activity_hidden_place : AppCompatActivity() {
    private lateinit var textView: TextView
    private lateinit var locationManager: LocationManager
    private var latitude: Double = 0.0
    private var longitude: Double = 0.0


    //定义一个权限COde，用来识别Location权限
    private val LOCATION_PERMISSION = 1

    //使用匿名内部类创建了LocationListener的实例qq
    val locationListener = object : LocationListener {
        override fun onProviderDisabled(provider: String?) {
            toast("关闭了GPS")
            textGPS.text = "关闭了GPS"
        }

        override fun onProviderEnabled(provider: String?) {
            toast("打开了GPS")
            showLocation(textGPS, locationManager)
        }

        override fun onLocationChanged(location: Location?) {
            //toast("变化了")
            //showLocation(textGPS, locationManager)
        }


        override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }
    }

        override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hidden_place)

        //对lateinit的变量进行初始化
        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        //textView = textGPS as TextView
            //按钮刷新
            buttonGetGPS.setOnClickListener {
                showLocation(textGPS, locationManager)
                textGPS.text = "\n当前经度$longitude 纬度$latitude"
            }


            //如果手机的SDK版本使用新的权限模型，检查是否获得了位置权限，如果没有就申请位置权限，如果有权限就刷新位置
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val hasLocationPermission = checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)
            if (hasLocationPermission != PackageManager.PERMISSION_GRANTED) {
                //requestPermissions是异步执行的
                requestPermissions(arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION),
                        LOCATION_PERMISSION)
            }
            else {
                showLocation(textGPS, locationManager)
            }
        }
        else {
            showLocation(textGPS, locationManager)
        }
    }
    override fun onPause() {
        super.onPause()
        val hasLocationPermission = checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)
        if ((locationManager != null) && ((hasLocationPermission == PackageManager.PERMISSION_GRANTED))) {
            locationManager.removeUpdates(locationListener)
        }
    }
    override fun onResume() {
        //挂上LocationListener, 在状态变化时刷新位置显示，因为requestPermissionss是异步执行的，所以要先确认是否有权限
        super.onResume()
        val hasLocationPermission = checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)
        if ((locationManager != null) && ((hasLocationPermission == PackageManager.PERMISSION_GRANTED))) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0F, locationListener)
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000,0F, locationListener)
            showLocation(textGPS, locationManager)
        }
    }


    //申请下位置权限后，要刷新位置信息
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == LOCATION_PERMISSION) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                toast("获取了位置权限")
                showLocation(textGPS, locationManager)
            }
        }
    }

    fun showLocation(textView: TextView, locationManager: LocationManager) {
        textView.text = getLocation(locationManager).toString()
    }

    //获取位置信息
    fun getLocation(locationManager: LocationManager): Location? {
        var location: Location? = null
        if (this.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_DENIED) {
            toast("没有位置权限")
        }
        else if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            toast("没有打开GPS")
        }
        else {
            location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
            if (location == null) {
                toast("位置信息为空")
                location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
                if (location == null) {
                    toast("网络位置信息也为空")
                }
                else {
                    toast("当前使用网络位置")
                }
            }
        }
        latitude = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER).latitude
        longitude = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER).longitude
        return location
    }
}

