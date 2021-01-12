package com.tech.recyclerviewdynamictest

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.io.File

class MainActivity : AppCompatActivity() {
    var recycler_view:RecyclerView?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        recycler_view=findViewById(R.id.recycler_view)


        var status=ContextCompat.checkSelfPermission(this,Manifest.permission.WRITE_EXTERNAL_STORAGE)
        if(status==PackageManager.PERMISSION_GRANTED) {
            fetchData()
        }else
        {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE),120)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,

        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(grantResults[0]==PackageManager.PERMISSION_GRANTED)
        {
            fetchData()
        }else
        {
            Toast.makeText(this,"user is not allowed to access data",Toast.LENGTH_SHORT).show()
        }
    }
    fun fetchData() {
        var path="/storage/emulated/0/WhatsApp/Media/WhatsApp Images/"
        var f= File(path)
        if(!f.exists())
        {
            var path="/storage/SdCard0/WhatsApp/Media/WhatsApp Images/"
            var f= File(path)
        }
       var listfiles= f.listFiles()

        recycler_view?.layoutManager=LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
        var myAdapter=MyAdapter(this,listfiles)
        recycler_view?.adapter=myAdapter

    }
}