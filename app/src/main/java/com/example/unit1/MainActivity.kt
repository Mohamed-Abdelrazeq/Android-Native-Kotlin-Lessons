package com.example.unit1

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.databinding.DataBindingUtil
import com.example.unit1.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var notificationManager: NotificationManager

    private val channelID = "TEST"
    private val channelName = "MAIN_CHANNEL"
    private val notificationID = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //////SET VARIABLE//////
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)
        //Navigation
        val name = "Mohamed"
        val age = 20
        val country = "Egypt"
        val me = Person(name, age, country)
        //sharedPref
        val sharedPref = getPreferences(Context.MODE_PRIVATE)
        //Notifications
        val textContent = "This is my TextNotification"
        val textTitle = "Hello World"
        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        createNotificationChannel()
        //////END//////

        //////FUNCTIONS//////
        //Notifications
        binding.btnNotifications.setOnClickListener {
            // Create an explicit intent for an Activity in your app
            val intent = Intent(this, MainActivity::class.java)
            val pendingIntent: PendingIntent = PendingIntent.getActivity(this, 0, intent, 0)

            val notification = NotificationCompat.Builder(this, channelID)
                    .setSmallIcon(R.drawable.ic_launcher_background)
                    .setContentTitle(textTitle)
                    .setContentText(textContent)
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true)
            notificationManager.notify(notificationID,notification.build())
        }
        //SharedPref
        binding.btnStoreData.setOnClickListener {
           sharedPref.edit().apply {
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
        //sharedPref
        binding.btnGetImage.setOnClickListener {
            Intent(Intent.ACTION_GET_CONTENT).also {
                getContent.launch("image/*")

            }
        }
        //////END//////
    }

    //Permissions
    private fun hasWriteExternalStoragePermission() =
        ActivityCompat.checkSelfPermission(this,Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED

    private fun hasReadExternalStoragePermission() =
        ActivityCompat.checkSelfPermission(this,Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED

    private fun hasForegroundLocationPermission() =
        ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED

    private fun hasBackgroundLocationPermission() =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
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
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
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
    //Notifications
    private fun createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = channelName
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(channelID, name, importance)
            // Register the channel with the system
            notificationManager.createNotificationChannel(channel)
        }
    }
}