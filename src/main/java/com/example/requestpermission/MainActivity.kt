package com.example.requestpermission

import android.Manifest
import android.content.Context
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    private val permissionrequestcode=123
    private lateinit var managepermission : ManagePermission
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val list = listOf<String>(
            Manifest.permission.READ_CALENDAR,
            Manifest.permission.CAMERA,
            Manifest.permission.READ_CONTACTS,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.SEND_SMS,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.CALL_PHONE
        )
        managepermission = ManagePermission(this,list,permissionrequestcode)

        button.setOnClickListener{
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                managepermission.checkPermission()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray) {
            when (requestCode){
                permissionrequestcode ->{
                    val isPermissionGranted = managepermission.processPermisionResult(requestCode,permissions,grantResults)
                    if(isPermissionGranted){
                        toast("Permission Granted")
                    }else{
                        toast("Permission Denied")
                    }
                    return
                }
            }
        }
    }
fun Context.toast(message: String){
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}