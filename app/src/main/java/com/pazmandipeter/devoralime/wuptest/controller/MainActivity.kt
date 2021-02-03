package com.pazmandipeter.devoralime.wuptest.controller

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.LinearLayout
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat.animate
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.badge.BadgeDrawable
import com.pazmandipeter.devoralime.wuptest.R
import com.pazmandipeter.devoralime.wuptest.databinding.ActivityMainBinding
import com.pazmandipeter.devoralime.wuptest.utils.NetworkUtils
import com.pazmandipeter.devoralime.wuptest.utils.setTextAnimation
import com.pazmandipeter.devoralime.wuptest.utils.setTitle
import com.pazmandipeter.devoralime.wuptest.viewmodel.MainActivityViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val ANIMATION_DURATION = 1000.toLong()

    lateinit var binding: ActivityMainBinding
    private val viewModel: MainActivityViewModel by viewModels()
    private lateinit var navController: NavController
    private lateinit var badge: BadgeDrawable
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

        setupBadge()

        showBadge(3)

        setupNavigationFragment()

        setupToolbar()

        handleNetworkChanges()
    }

    private fun setupNavigationFragment() {
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment_container) as NavHostFragment
        navController = navHostFragment.findNavController()
        binding.bottomNav.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            when (destination.id) {
                R.id.accountsOverviewFragment -> {
                    viewModel.fragmentIsDestroyed = true
                }
                R.id.accountDetailsFragment -> {
                }
            }
        }
    }


    private fun setupToolbar() {
        supportActionBar?.title = null
        NavigationUI.setupWithNavController(binding.toolbar, navController, appBarConfiguration)
        binding.toolbar.setContentInsetsRelative(0, 0)
    }

    private fun setupBadge() {
        badge = binding.bottomNav.getOrCreateBadge(R.id.nav_state)
        badge.backgroundColor = ContextCompat.getColor(this, R.color.orange)
        badge.badgeTextColor = ContextCompat.getColor(this, R.color.white)
    }


    fun showBadge(count: Int?) {
        if (count == null) {
            badge.clearNumber()
            return
        }
        badge.number = count
    }

    fun setTitle(titleId: Int, duration: Long, outDuration: Long?, textGravity: Int) {
        binding.tvTitle.setTextAnimation(
            getString(titleId),
            duration,
            outDuration = outDuration,
            textGravity = textGravity
        )
    }


    private fun handleNetworkChanges() {
        NetworkUtils.getNetworkLiveData(applicationContext).observe(this, { isConnected ->
            if (!isConnected) {
                binding.textViewNetworkStatus.text = getString(R.string.text_no_connectivity)
                binding.networkStatusLayout.apply {
                    isVisible = true
                    setBackgroundColor(ContextCompat.getColor(this@MainActivity, R.color.colorStatusNotConnected))
                }
            } else {
                binding.textViewNetworkStatus.text = getString(R.string.text_connectivity)
                binding.networkStatusLayout.setBackgroundColor(ContextCompat.getColor(this@MainActivity, R.color.colorStatusConnected))
                doAnimation(binding.networkStatusLayout)

            }
        })
    }

    private fun doAnimation(linearLayout: LinearLayout) {
        linearLayout.apply {
            animate()
                .alpha(1f)
                .setStartDelay(ANIMATION_DURATION)
                .setDuration(ANIMATION_DURATION)
                .setListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator?) {
                        isGone = true
                    }
                })
        }
    }


    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }


}

