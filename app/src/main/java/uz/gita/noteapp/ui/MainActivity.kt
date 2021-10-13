package uz.gita.noteapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.fragment.NavHostFragment
import uz.gita.noteapp.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        val navHost = supportFragmentManager.findFragmentById(R.id.container) as NavHostFragment
        val graph = navHost.navController.navInflater.inflate(R.navigation.app_nav)
        navHost.navController.graph = graph


    }
}