package com.example.moowin

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.moowin.Home.HomeSectionBuilder
import com.example.moowin.ui.theme.MoowinTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MoowinTheme {
                val homeSection = HomeSectionBuilder.build()

                val sections = listOf(
                    homeSection
                )

                BottomNavigator(
                    sections = sections
                )
                //AGREGAR NUEVOS APARTADOS
            }
        }
    }
}
