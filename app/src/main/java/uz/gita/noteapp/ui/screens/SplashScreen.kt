package uz.gita.noteapp.ui.screens

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import uz.gita.noteapp.R

@SuppressLint("CustomSplashScreen")
class SplashScreen : Fragment(R.layout.screen_splash) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Handler().postDelayed({ findNavController().navigate(R.id.action_splashScreen_to_mainScreen) }, 2000)

    }
}
