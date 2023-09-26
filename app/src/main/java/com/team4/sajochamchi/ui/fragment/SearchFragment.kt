package com.team4.sajochamchi.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.team4.sajochamchi.data.repository.TotalRepositoryImpl
import com.team4.sajochamchi.databinding.FragmentSearchBinding
import com.team4.sajochamchi.ui.dialog.ViewDetailDialog
import com.team4.sajochamchi.ui.viewmodel.MainSharedViewModel
import com.team4.sajochamchi.ui.viewmodel.SearchViewModel
import com.team4.sajochamchi.ui.viewmodel.SearchViewModelFactory


class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding: FragmentSearchBinding
        get() = _binding!!

    private val searchViewModel: SearchViewModel by viewModels {
        SearchViewModelFactory(TotalRepositoryImpl(requireContext()))
    }
    private val mainSharedViewModel: MainSharedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
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
            dialog.show(this@SearchFragment.childFragmentManager, "Detail Dialog")
        }
    }

    private fun initViewModels() {
        with(searchViewModel) {
            
        }

        with(mainSharedViewModel) {
            searchEvent.observe(viewLifecycleOwner) {

            }
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