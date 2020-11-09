package edu.hkbu17225736.comp4097.infoday

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.firebase.auth.FirebaseAuth
import edu.hkbu17225736.comp4097.infoday.data.Code
import edu.hkbu17225736.comp4097.infoday.ui.login.LoginActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.

        //val appBarConfiguration = AppBarConfiguration(setOf(
        //        R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications))
        val appBarConfiguration = AppBarConfiguration(setOf(
            R.id.newslistFragment, R.id.eventsFragment,
            R.id.infoFragmentNew, R.id.mapsFragment
        ))
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        findNavController(R.id.nav_host_fragment).popBackStack()
        return true
    }

//    fun buttonClick(view : View) {
//        findNavController(R.id.nav_host_fragment).navigate(R.id.action_navigation_home_to_infoFragmentNew)
//    }

    //MainActivity.kt
//It is called when the app is created or resumed from another app
override fun onResume() {
        super.onResume()
        //The SwitchPreferenceCompat will automatically store the preference value in a key-value dictionary.
        if (getSharedPreferences("${packageName}_preferences", 0).getBoolean("dark_mode", false)) {
        //switch to night mode
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES) } else {
        //switch to day mode
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO) }

        if (FirebaseAuth.getInstance().currentUser == null){
            startActivityForResult(Intent(this, LoginActivity::class.java), Code.LOGIN_RESULT)
            //Finally, we modify MainActivity to start LoginActivity in onResume by checking currentUser in FirebaseAuth.
        }
    }
}