package com.team4.sajochamchi.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.team4.sajochamchi.R
import com.team4.sajochamchi.databinding.ActivityMainBinding
import com.team4.sajochamchi.ui.fragment.HomeFragment
import com.team4.sajochamchi.ui.fragment.MyPageFragment
import com.team4.sajochamchi.ui.fragment.SearchFragment
import com.team4.sajochamchi.ui.viewmodel.MainSharedBottomMenu
import com.team4.sajochamchi.ui.viewmodel.MainSharedViewModel

class MainActivity : AppCompatActivity() {
    companion object{
        const val MENU_HOME = 0
        const val MENU_SEARCH = 1
        const val MENU_MYPAGE = 2
    }

    private lateinit var binding: ActivityMainBinding

    private val mainSharedViewModel: MainSharedViewModel by viewModels()

    private val homeFragment: HomeFragment = HomeFragment.newInstance()
    private val searchFragment: SearchFragment = SearchFragment.newInstance()
    private val myPageFragment: MyPageFragment = MyPageFragment.newInstance()
    private val fragmentArray = arrayOf(
        homeFragment, searchFragment, myPageFragment
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViews()
        initViewModels()
    }


    private fun initViews() = with(binding) {
        //nav view
        bottomNavigationView.itemIconTintList = null

        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.bottom_home -> {
                    mainSharedViewModel.updateBottomMenuPos(MENU_HOME)
                }

                R.id.bottom_search -> {
                    mainSharedViewModel.updateBottomMenuPos(MENU_SEARCH)
                }

                R.id.bottom_mypage -> {
                    mainSharedViewModel.updateBottomMenuPos(MENU_MYPAGE)
                }
            }
            return@setOnItemSelectedListener true
        }
    }

    private fun initViewModels() {
        val fragmentManager = supportFragmentManager

        with(mainSharedViewModel) {
            bottomMenu.observe(this@MainActivity) { menu ->
                when (menu) {
                    is MainSharedBottomMenu.UpdateBottomMenuPosition -> {
                        fragmentManager.beginTransaction()
                            .replace(R.id.frame_layout, fragmentArray[menu.postion]).commit()
                    }
                }
            }
        }
    }
}