package com.oona.testapp1

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Browser
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.system.exitProcess


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Package name of GMail app
        //val packageName = "com.google.android.gm"
        val packageName = "com.oona.testapp2"
        val urlWebPageName = "http://davidlaverda.yj.fr/OonaOpenApp1.html"

        // Click listener for button widget
        button.setOnClickListener{
            // Launch the app programmatically
            launchApp(packageName)
        }

        buttonClose.setOnClickListener {
            // Close
            finish();
            exitProcess(0);
        }

        buttonOpenWebApp.setOnClickListener {
            // Lunch the web app
            // val i = Intent(Intent.ACTION_VIEW)
            // i.data = Uri.parse(urlWebPageName)
            // startActivity(i)
            openUrlInChrome(urlWebPageName)
        }

    }


    // Custom method to launch an app
    private fun launchApp(packageName: String) {
        // Get an instance of PackageManager
        val pm = applicationContext.packageManager

        // Initialize a new Intent
        val intent:Intent? = pm.getLaunchIntentForPackage(packageName)

        // Add category to intent
        intent?.addCategory(Intent.CATEGORY_LAUNCHER)

        // If intent is not null then launch the app
        if(intent!=null){
            applicationContext.startActivity(intent)
        }else{
            toast("Intent null.")
        }
    }

    private fun openUrlInChrome(url: String) {
        try {
            try {
                val uri =
                    Uri.parse("googlechrome://navigate?url=$url")
                val i = Intent(Intent.ACTION_VIEW, uri)
                i.putExtra(Browser.EXTRA_APPLICATION_ID, "com.android.chrome")
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(i)
            } catch (e: ActivityNotFoundException) {
                val uri = Uri.parse(url)
                // Chrome is probably not installed
                // OR not selected as default browser OR if no Browser is selected as default browser
                val i = Intent(Intent.ACTION_VIEW, uri)
                startActivity(i)
            }
        } catch (ex: Exception) {
            //
        }
    }

}

// Extension function to show toast message
fun Context.toast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}