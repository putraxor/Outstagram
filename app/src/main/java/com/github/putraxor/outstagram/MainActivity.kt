package com.github.putraxor.outstagram

import android.Manifest
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.TedPermission
import kotlinx.android.synthetic.main.activity_main.*
import gun0912.tedbottompicker.TedBottomPicker
import android.R.attr.key
import android.support.v4.app.NotificationCompat.getExtras
import android.content.Intent




class MainActivity : AppCompatActivity() {

    private val TAG: String = this::class.java.name

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        demo.setOnClickListener { checkPermission() }
    }

    /**
     * Permission listener
     */
    private var permListener = object : PermissionListener {
        override fun onPermissionGranted() {
            val picker = TedBottomPicker.Builder(this@MainActivity)
                    .setOnImageSelectedListener {
                        Log.d(TAG, "Gambar yang dipilih $it")// here is selected uri
                        val intent = Intent(applicationContext, FilterActivity::class.java)
                        //val bundle = Bundle()
                        //bundle.putString("uri", it.toString())
                        intent.putExtra("uri", it.toString())
                        startActivity(intent)
                    }
                    .create()

            picker.show(supportFragmentManager)
        }

        override fun onPermissionDenied(denieds: ArrayList<String>) {
            Toast.makeText(this@MainActivity, "Oops sayang sekali ditolak", Toast.LENGTH_SHORT).show()
        }
    }

    /**
     * Permission check
     */
    private fun checkPermission() {
        TedPermission.with(this)
                .setPermissionListener(permListener)
                .setDeniedMessage("Izin akses diperlukan untuk menggunakan aplikasi ini. Izinkan permisi melalui menu Setting > Permission")
                .setPermissions(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .check()
    }

}
