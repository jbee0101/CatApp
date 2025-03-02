package com.example.catapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.catapp.presentation.ui.navigation.CatNavGraph
import dagger.hilt.android.AndroidEntryPoint

/**
 * MainActivity is the entry point of the app and serves as the main UI container.
 * Its annotated with @AndroidEntryPoint to enable Hilt dependency injection within this activity.
 */
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    /**
     * onCreate is called when the activity is first created.
     * It initializes the activity's UI and sets up the navigation graph.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CatNavGraph()
        }
    }
}