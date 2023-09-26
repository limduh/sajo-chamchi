package com.team4.sajochamchi.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import com.team4.sajochamchi.data.repository.TotalRepositoryImpl
import com.team4.sajochamchi.databinding.FragmentHomeBinding
import com.team4.sajochamchi.ui.activity.WebViewActivity
import com.team4.sajochamchi.ui.dialog.CategoriesDialog
import com.team4.sajochamchi.ui.dialog.ViewDetailDialog
import com.team4.sajochamchi.ui.viewmodel.HomeViewModel
import com.team4.sajochamchi.ui.viewmodel.HomeViewModelFactory
import com.team4.sajochamchi.ui.viewmodel.MainSharedViewModel


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding: FragmentHomeBinding
        get() = _binding!!

    private val homeViewModel: HomeViewModel by viewModels() {
        HomeViewModelFactory(TotalRepositoryImpl(requireContext()))
    }
    private val mainSharedViewModel: MainSharedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initViewModels()
    }

    private fun initViews() = with(binding) {
        detailDialogButton.setOnClickListener {
            val dialog = ViewDetailDialog.newInstance(object : ViewDetailDialog.ClickEventListener {
                override fun shareButtonClicked() {

                }

                override fun favoriteButtonClicked() {

                }

                override fun thumbnailImageClicked() {

                }
            })
            dialog.show(this@HomeFragment.childFragmentManager, "Detail Dialog")
        }

        webViewActivityButton.setOnClickListener {
            startActivity(
                WebViewActivity.newIntent(
                    requireContext(),
                    "test",
                    "https://www.youtube.com/"
                )
            )
        }

        categoriesDialogButton.setOnClickListener {
            val dialog = CategoriesDialog.newInstance(object : CategoriesDialog.EventListener {

            })
            dialog.show(this@HomeFragment.childFragmentManager, "Categories Dialog")
        }
    }

    private fun initViewModels() {
        with(homeViewModel) {

        }

        with(mainSharedViewModel) {
            homeEvent.observe(viewLifecycleOwner) {

            }
        }
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }


    companion object {
        fun newInstance() = HomeFragment()
    }
}