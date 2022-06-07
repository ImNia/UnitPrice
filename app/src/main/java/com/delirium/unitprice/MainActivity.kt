package com.delirium.unitprice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupWithNavController
import com.delirium.unitprice.calculate.CalculateFragment
import com.delirium.unitprice.databinding.ActivityMainBinding
import com.delirium.unitprice.previous.PreviousFragment
import io.realm.Realm

class MainActivity : AppCompatActivity() {
    lateinit var appBarNavController: AppBarConfiguration
    lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initDatabase()
        supportActionBar?.hide()

        val bindingMain = ActivityMainBinding.inflate(layoutInflater)
        setContentView(bindingMain.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.myNavHostFragment) as NavHostFragment

        navController = navHostFragment.navController
        appBarNavController = AppBarConfiguration(navController.graph)

        bindingMain.toolBar.setupWithNavController(navController, appBarNavController)
        bindingMain.toolBar.setOnMenuItemClickListener { menuItem ->
            when(menuItem.itemId) {
                else -> {
                    Log.i("MAIN", "PROBLEM in App Bar")
                    false
                }
            }
        }

        bindingMain.buttonAppBar.setOnClickListener {
            navController.navigate(R.id.newCalculation)
        }
    }

    private fun initDatabase() {
        Realm.init(this)
        Realm.setDefaultConfiguration(RealmConfiguration().getConfigDB())
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarNavController)
                || super.onSupportNavigateUp()
    }
}