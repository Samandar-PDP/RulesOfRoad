package com.example.rulesofroad

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.view.isVisible
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import androidx.navigation.ui.*
import com.example.rulesofroad.databinding.ActivityMainBinding
import com.example.rulesofroad.model.Symbol
import java.lang.NullPointerException

class MainActivity : AppCompatActivity(), NavController.OnDestinationChangedListener {
    private lateinit var navController: NavController
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private lateinit var appBarConfiguration: AppBarConfiguration
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        navController = findNavController(R.id.nav_host_fragment_home)
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.homeFragment,
                R.id.favoriteFragment,
                R.id.infoFragment
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        binding.bottomNav.setupWithNavController(navController)
        navController.addOnDestinationChangedListener(this)

        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ),
                0
            )
        }

    }

    // onBackPressed in fragment <-
    override fun onSupportNavigateUp(): Boolean {
        navController = findNavController(R.id.nav_host_fragment_home)
        return (NavigationUI.navigateUp(
            navController,
            appBarConfiguration
        ) || super.onSupportNavigateUp())
    }

    fun hideBottomNav() {
        binding.bottomNav.visibility = View.INVISIBLE
    }

    fun showBottomNav() {
        binding.bottomNav.visibility = View.VISIBLE
    }

    override fun onDestinationChanged(
        controller: NavController,
        destination: NavDestination,
        arguments: Bundle?
    ) {
        when (destination.id) {
            R.id.saveFragment -> {
                hideBottomNav()
            }
            R.id.homeFragment -> {
                showBottomNav()
            }
            R.id.detailFragment -> {
                hideBottomNav()
                val symbol = arguments?.getSerializable("symbol") as? Symbol
                destination.label = symbol?.name
            }
            R.id.editFragment -> {
                val symbol = arguments?.getSerializable("symbol") as? Symbol
                destination.label = symbol?.name
            }
        }
    }
}