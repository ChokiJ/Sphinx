package xyz.choki.sphinx

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.*
import com.amap.api.location.AMapLocationClient
import com.amap.api.location.AMapLocationClientOption
import com.amap.api.location.AMapLocationListener

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.indeterminateProgressDialog
import org.jetbrains.anko.toast
import org.jetbrains.anko.uiThread
import xyz.choki.sphinx.R.menu.menu_main
import java.net.URL
import java.util.jar.Manifest

class MainActivity : AppCompatActivity() {
    private val LOCATION_PERMISSION = 1
    private var locationClientSingle: AMapLocationClient? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        load()
        buttonStart.setOnClickListener()
        {
            //val dialog = indeterminateProgressDialog("请稍后...")
            //dialog.show()
            solve();
            //Thread.sleep(1000)
            //dialog.dismiss()
        }
        textDescription.setOnClickListener()
        {
            load()
        }
        //获取GPS权限
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            //无权限时执行
            //toast("GPS权限获取失败")
            requestPermissions(arrayOf(android.Manifest.permission.ACCESS_COARSE_LOCATION, android.Manifest.permission.ACCESS_FINE_LOCATION), LOCATION_PERMISSION)
        }
    }

    //加载谜题信息
    fun load(id:Int=0)
    {
        doAsync {
            var description = URL("https://spx.choki.xyz/puzzles?id=${id}&get=description").readText()
            Thread.sleep(200)
            var titile = URL("https://spx.choki.xyz/puzzles?id=${id}&get=titile").readText() + "-$id"
            Thread.sleep(200)
            uiThread {
                textTitle.text = titile
                textDescription.text = description
            }
        }

    }
    //解谜
    fun solve(){
        if (null == locationClientSingle) {
            locationClientSingle = AMapLocationClient(this.applicationContext)
        }
        val locationClientOption = AMapLocationClientOption()
        //使用单次定位
        locationClientOption.isOnceLocation = true
        locationClientOption.isLocationCacheEnable = false
        //高精准度
        locationClientOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy)
        locationClientOption.setOnceLocationLatest(true);
        locationClientSingle!!.setLocationOption(locationClientOption)
        locationClientSingle!!.setLocationListener(locationSingleListener)
        locationClientSingle!!.startLocation()
    }
    fun finishSolve(id:Int,lo:Double,la:Double)
    {
        toast("$lo $la")
        doAsync {
            var respond = URL("https://spx.choki.xyz/solve?id=${id}&longitude=${lo}&latitude=${la}").readText().toBoolean()
            Thread.sleep(200)
            var text = URL("https://spx.choki.xyz/puzzles?id=${id}&get=story").readText()
            Thread.sleep(200)
            if (respond) {
                uiThread {
                    toast("成功！")
                    //成功的情况下推进谜题
                    textDescription.text = text
                    buttonHint.text = "下一个"
                }
            } else {
                uiThread {
                    toast("谜题解决解决失败，未达到指定地点。")
                }
            }

        }
    }

    /**
     * 单次客户端的定位监听
     */
    var locationSingleListener: AMapLocationListener = AMapLocationListener { location ->
        val callBackTime = System.currentTimeMillis()
        val sb = StringBuffer()
        sb.append("单次定位完成\n")
        if (null == location) {
            sb.append("定位失败：location is null!!!!!!!")
        } else {
            sb.append(Utils.getLocationStr(location))
        }
        finishSolve(lo=location.longitude,la=location.latitude,id=999)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        when (item.itemId) {
            R.id.button_settings -> startActivity(Intent(this, activity_hidden_place::class.java))
            else -> return false
        }
        return true
    }
}
