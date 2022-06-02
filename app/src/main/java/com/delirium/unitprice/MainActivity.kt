package com.delirium.unitprice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.delirium.unitprice.calculate.CalculateFragment
import com.delirium.unitprice.databinding.ActivityMainBinding
import com.delirium.unitprice.display.PreviousFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val bindingMain = ActivityMainBinding.inflate(layoutInflater)
        setContentView(bindingMain.root)

        val fragmentCalculate = CalculateFragment()
        supportFragmentManager.beginTransaction()
            .add(R.id.fragmentCalculate, fragmentCalculate)
            .commit()

        val fragmentDisplay = PreviousFragment()
        supportFragmentManager.beginTransaction()
            .add(R.id.fragmentPrevious, fragmentDisplay)
            .commit()
    }
}