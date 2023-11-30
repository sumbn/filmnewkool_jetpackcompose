package com.example.appnewkool

import android.app.PendingIntent
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import com.example.appnewkool.common.AppSharePreference
import com.example.appnewkool.ui.nav.Navigation
import com.example.appnewkool.ui.theme.AppNewkoolTheme
import com.example.appnewkool.ui.theme.BlueWhite
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var appSharePreference: AppSharePreference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            AppNewkoolTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = BlueWhite) {
                    Navigation()
                }
            }
        }
    }

    override fun onStop() {
        appSharePreference.getEditor()?.putString("token","")?.apply()
        Log.e("activity", "onStop: " + " đã vào onstop" )
        super.onStop()
    }
}
