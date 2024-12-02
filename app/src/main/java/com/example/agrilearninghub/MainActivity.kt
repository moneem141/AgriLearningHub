package com.example.agrilearninghub

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Scaffold
import cafe.adriel.voyager.navigator.Navigator
import com.example.agrilearninghub.ui.root.RootScreen
import com.example.agrilearninghub.ui.theme.AgriLearningHubTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AgriLearningHubTheme {
                Scaffold { _ ->
                    Navigator(RootScreen)
                }
            }
        }
    }
}