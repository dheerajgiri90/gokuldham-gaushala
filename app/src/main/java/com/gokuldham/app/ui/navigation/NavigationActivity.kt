package com.gokuldham.app.ui.navigation

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.gokuldham.app.BR
import com.gokuldham.app.R
import com.gokuldham.app.databinding.ActivityNavigationBinding
import com.gokuldham.app.ui.account.AccountFragment
import com.gokuldham.app.ui.base.BaseActivity
import com.gokuldham.app.ui.entry.EntryFragment
import com.gokuldham.app.ui.home.HomeFragment
import com.gokuldham.app.util.CommonUtils

class NavigationActivity : BaseActivity<ActivityNavigationBinding, NavigationViewModel>(),
    NavigationNavigator {

    override val bindingVariable: Int
        get() = BR.navigationViewModel

    override val layoutId: Int
        get() = R.layout.activity_navigation

    override val viewModel = NavigationViewModel()

    private var doubleBackToExitPressedOnce = false
    private var runnable: Runnable? = null
    private lateinit var messageBadge: View


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.statusBarColor =
            ContextCompat.getColor(this@NavigationActivity, R.color.colorPrimary)
        viewModel.navigator = this

        init()
        setCurrentFragment(HomeFragment(), getString(R.string.home))
    }

    override fun init() {

        viewDataBinding!!.bottomNavigationView.setOnNavigationItemSelectedListener { menuItem ->
            viewDataBinding!!.bottomNavigationView.menu.setGroupCheckable(0, true, true)
            when (menuItem.itemId) {

                R.id.navigation_home -> run {
                    setCurrentFragment(HomeFragment(), "होम")

                }

                R.id.navigation_message -> run {
                    setCurrentFragment(EntryFragment(), "एंट्री")

                }

//                R.id.navigation_account -> run {
//                    setCurrentFragment(AccountFragment(), "सेटिंग")
//                }

            }
            true
        }

    }


    private fun setCurrentFragment(fragment: Fragment, tag: String) =
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.flFragment, fragment)
            commit()
        }

    override fun onBackPressed() {

        if (supportFragmentManager.backStackEntryCount == 0) {
            val currentFragment = supportFragmentManager.fragments.last()
            if (currentFragment is HomeFragment) {
                viewDataBinding!!.bottomNavigationView.menu.setGroupCheckable(0, false, true)
                setCurrentFragment(HomeFragment(), getString(R.string.home))
            } else {
                askBeforeExit()
            }
        } else {
            super.onBackPressed()
        }
    }

    private fun askBeforeExit() {
        val handler = Handler(Looper.getMainLooper())
        if (!doubleBackToExitPressedOnce) {
            doubleBackToExitPressedOnce = true
            CommonUtils.showMessage(getString(R.string.want_to_exit), this)
            handler.postDelayed(Runnable { doubleBackToExitPressedOnce = false }
                .also { runnable = it }, 2000)
        } else {
            runnable?.let { handler.removeCallbacks(it) }
            finishAffinity()

        }
    }

}