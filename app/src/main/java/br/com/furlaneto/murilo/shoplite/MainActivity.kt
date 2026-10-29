package br.com.furlaneto.murilo.shoplite

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import br.com.furlaneto.murilo.shoplite.core.preferences.AppPreferences
import br.com.furlaneto.murilo.shoplite.core.theme.ShopLiteTheme
import br.com.furlaneto.murilo.shoplite.enum.ThemeModeApp
import br.com.furlaneto.murilo.shoplite.navigation.Routes
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { _ -> }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        enableEdgeToEdge()
        checkNotificationPermission()
        
        setContent {
            val context = LocalContext.current
            val appPreferences = remember { AppPreferences(context) }
            val themeModeValue by appPreferences.themeFlow().collectAsState(initial = ThemeModeApp.SYSTEM.value)

            val isDarkTheme = when (themeModeValue) {
                ThemeModeApp.DARK.value -> true
                ThemeModeApp.LIGHT.value -> false
                else -> isSystemInDarkTheme()
            }

            ShopLiteTheme(
                darkTheme = isDarkTheme,
                dynamicColor = false
            ) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Routes()
                }
            }
        }
    }

    private fun checkNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }
    }
}
