package com.pazmandipeter.devoralime.wuptest

import android.os.Bundle
import android.view.Gravity
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.pazmandipeter.devoralime.wuptest.databinding.ActivityMainBinding
import com.pazmandipeter.devoralime.wuptest.utils.setTitle
import dagger.hilt.android.AndroidEntryPoint
import java.util.regex.Pattern

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    private val viewModel : MainActivityViewModel by viewModels()

    private lateinit var navController: NavController

    private val appBarConfiguration by lazy {
        AppBarConfiguration(
            setOf(
                R.id.accountsOverviewFragment
            )
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val badge = binding.bottomNav.getOrCreateBadge(R.id.nav_state)
        badge.backgroundColor = ContextCompat.getColor(this, R.color.orange)
        badge.badgeTextColor = ContextCompat.getColor(this, R.color.white)
        badge.number = 3

        supportActionBar?.title = null

        binding.toolbar.setTitle(
            getString(R.string.premium_card),
            binding.tvTitle,
            null
        )

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment_container) as NavHostFragment
        navController = navHostFragment.findNavController()
        binding.bottomNav.setupWithNavController(navController)

        //Setting up the action bar
        NavigationUI.setupWithNavController(binding.toolbar, navController, appBarConfiguration)


        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            when (destination.id) {
                R.id.accountsOverviewFragment -> {
                    binding.tvTitle.gravity = Gravity.CENTER_HORIZONTAL
                    binding.toolbar.setTitle(
                        getString(R.string.premium_card),
                        binding.tvTitle,
                        null
                    )
                    viewModel.fragmentIsDestroyed = true
                }
                R.id.accountDetailsFragment -> {
                    binding.tvTitle.gravity = Gravity.LEFT
                    binding.toolbar.setTitle(
                        getString(R.string.details),
                        binding.tvTitle,
                        null
                    )
                }
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}

