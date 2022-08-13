package com.example.intercomapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainPage : AppCompatActivity() {

    lateinit var bottomNavigationView : BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_page)
        bottomNavigationView = findViewById(R.id.bottomNavigationView)

        val firstFragment=HomeFragment()
        val secondFragment=PhoneFragment()
        val thirdFragment=KeyFragment()
        val forthFragment=AccountFragment()

        setCurrentFragment(firstFragment)

        bottomNavigationView.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.home->setCurrentFragment(firstFragment)
                R.id.phone->setCurrentFragment(secondFragment)
                R.id.qr->setCurrentFragment(thirdFragment)
                R.id.account->setCurrentFragment(forthFragment)
            }
            true
        }

    }

    private fun setCurrentFragment(fragment:Fragment)=
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.flFragment,fragment)
            commit()
        }
}