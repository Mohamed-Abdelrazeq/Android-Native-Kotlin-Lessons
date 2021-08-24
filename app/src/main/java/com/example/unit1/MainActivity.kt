package com.example.unit1

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import com.example.unit1.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private  lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)

        val name = "Mohamed"
        val age = 20
        val country = "Egypt"

        val me = Person(name,age, country)

        val sharedPref = getPreferences(Context.MODE_PRIVATE)


        //SharedPref
        binding.btnStoreData.setOnClickListener {
           sharedPref.edit().apply() {
                putInt("Age", age)
                putString("Name", name)
                putString("Country", country)
                apply()
            }
        }
        binding.btnRetriveData.setOnClickListener {
            sharedPref.apply {
                println(getInt("Age",0))
                println(getString("Name", null))
                println(getString("Country",null))
            }
        }
        //Navigation
        binding.btnForward.setOnClickListener {
            Intent(this,SecondActivity::class.java).also {
                it.putExtra("Extra_Me",me)
                it.putExtra("Extra_Name",name)
                startActivity(it)
            }
        }
        //RequestPermission
        binding.btnRequests.setOnClickListener {
            requestPermissions()
        }
        //GetLocalData
        binding.btnGetImage.setOnClickListener {
            Intent(Intent.ACTION_GET_CONTENT).also {
                getContent.launch("image/*")

            }
        }

    }

    //Permissions
    private fun hasWriteExternalStoragePermission() =
        ActivityCompat.checkSelfPermission(this,Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED

    private fun hasReadExternalStoragePermission() =
        ActivityCompat.checkSelfPermission(this,Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED

    private fun hasForegroundLocationPermission() =
        ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED

    private fun hasBackgroundLocationPermission() =
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) {
            ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_BACKGROUND_LOCATION) == PackageManager.PERMISSION_GRANTED
        } else {
            false
        }

    private fun requestPermissions (){
        val permissionsToRequest = mutableListOf<String>()

        if(!hasWriteExternalStoragePermission()){
            permissionsToRequest.add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }
        if(!hasReadExternalStoragePermission()){
            permissionsToRequest.add(Manifest.permission.READ_EXTERNAL_STORAGE)
        }
        if(!hasForegroundLocationPermission()){
            permissionsToRequest.add(Manifest.permission.ACCESS_COARSE_LOCATION)
        }
        if(!hasBackgroundLocationPermission()){
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) {
                permissionsToRequest.add(Manifest.permission.ACCESS_BACKGROUND_LOCATION)
            }
        }

        if(permissionsToRequest.isNotEmpty()){
            ActivityCompat.requestPermissions(this,permissionsToRequest.toTypedArray(),0)
        }
    }
    //Data Picker
    private val getContent = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        println(uri)
        binding.imageView.setImageURI(uri)
    }

}