package com.team4.sajochamchi.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.team4.sajochamchi.databinding.FragmentSearchBinding
import com.team4.sajochamchi.ui.dialog.ViewDetailDialog


class SearchFragment : Fragment() {

    private var _binding : FragmentSearchBinding? = null
    private val binding : FragmentSearchBinding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentSearchBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun initViews() = with(binding){
        detailDialogButton.setOnClickListener {
            val dialog = ViewDetailDialog.newInstance(object : ViewDetailDialog.ClickEventListener{
                override fun shareButtonClicked() {

                }

                override fun favoriteButtonClicked() {

                }

                override fun thumbnailImageClicked() {

                }
            })
            dialog.show(this@SearchFragment.childFragmentManager,"Detail Dialog")
        }
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }


    companion object {
        fun newInstance() = SearchFragment()
    }
}