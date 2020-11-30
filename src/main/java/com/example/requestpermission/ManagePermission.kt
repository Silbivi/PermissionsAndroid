package com.example.requestpermission

import android.app.AlertDialog
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class ManagePermission(val activity: MainActivity, val list: List<String>, val code: Int){
    fun checkPermission(){
        if(isPermissionGranted() != PackageManager.PERMISSION_GRANTED){
            showAlter()
        }else{
            activity.toast("Permission is Already Granted")
        }
    }
    private fun isPermissionGranted(): Int{
        var counter=0;
        for(permission in list){
            counter += ContextCompat.checkSelfPermission(activity, permission)
        }
        return counter
    }
    private fun deniedPermission():String{
        for(permission in list){
            if(ContextCompat.checkSelfPermission(activity, permission)== PackageManager.PERMISSION_DENIED) return permission
        }
        return ""
    }
    private fun showAlter(){
        val builder = AlertDialog.Builder(activity)
        builder.setTitle("Need Permission(s)")
        builder.setMessage("Some Permissions are required to do the task")
        builder.setPositiveButton("OK", { dialog, which -> requestPermission() })
        builder.setNeutralButton("Cancel", null)
        val dialog = builder.create()
        dialog.show()
    }
    private fun requestPermission(){
        val permission = deniedPermission()
        if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)){
            activity.toast("Should show an Explanation")
        }else{
            ActivityCompat.requestPermissions(activity, list.toTypedArray(), code)
        }
    }
    fun processPermisionResult(requestCode: Int, permission: Array<String>, grantResult: IntArray) : Boolean{
        var result = 0
        if(grantResult.isNotEmpty()){
            for(item in grantResult){
                result += item
            }
        }
        if(result == PackageManager.PERMISSION_GRANTED) return true
        return false
    }
}

