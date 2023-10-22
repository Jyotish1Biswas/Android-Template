package com.jyotish.template.ui_screens

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.jyotish.template.R
import com.jyotish.template.data_store.UserDataStore
import com.jyotish.template.databinding.ActivityMainBinding
import com.jyotish.template.ui_screens.main_screen.DemoViewModel
import com.jyotish.template.ui_screens.main_screen.details.DetailsFragment
import com.jyotish.template.ui_screens.main_screen.home.HomeFragment

class MainActivity : AppCompatActivity(R.layout.activity_main) {
    private lateinit var binding: ActivityMainBinding
    private val viewModel:DemoViewModel by viewModels()
    private val userDataStore by lazy { UserDataStore.getInstance() }
    private val homeFragment = HomeFragment()
    private val detailsFragment = DetailsFragment()
    private var activeFragment: Fragment = homeFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // Worker Example

        setUpBottomNavigation()
        viewModel.updateData()
    }

    private fun setUpBottomNavigation() {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()

        fragmentTransaction.add(binding.navHostFragment.id, homeFragment, "Home")
        fragmentTransaction.add(binding.navHostFragment.id, detailsFragment, "Details")
        activeFragment = detailsFragment
        supportFragmentManager.beginTransaction().hide(activeFragment).show(homeFragment).commit()
        activeFragment = homeFragment
        fragmentTransaction.commit()

        binding.bottomNavigationView.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.navigation_home -> {
                    supportFragmentManager.beginTransaction().hide(activeFragment).show(homeFragment).commit()
                    activeFragment = homeFragment
                    true
                }
                R.id.navigation_details -> {
                    supportFragmentManager.beginTransaction().hide(activeFragment).show(detailsFragment).commit()
                    activeFragment = detailsFragment

                    true
                }
                else -> false
            }
        }
        binding.bottomNavigationView.post {
            binding.bottomNavigationView.selectedItemId = R.id.navigation_home
        }
    }

}