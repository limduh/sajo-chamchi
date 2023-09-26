package com.team4.sajochamchi.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.team4.sajochamchi.R
import com.team4.sajochamchi.databinding.FragmentHomeBinding
import com.team4.sajochamchi.ui.dialog.CategoriesDialog
import com.team4.sajochamchi.ui.dialog.ViewDetailDialog


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding: FragmentHomeBinding
        get() = _binding!!

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

        categoriesDialogButton.setOnClickListener {
            val dialog = CategoriesDialog.newInstance(object : CategoriesDialog.EventListener {

            })
            dialog.show(this@HomeFragment.childFragmentManager, "Categories Dialog")
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