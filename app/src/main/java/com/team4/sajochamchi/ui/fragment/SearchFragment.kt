package com.team4.sajochamchi.ui.fragment

import android.content.Context.INPUT_METHOD_SERVICE
import android.os.Bundle
import android.text.Editable
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.team4.sajochamchi.R
import com.team4.sajochamchi.data.repository.TotalRepositoryImpl
import com.team4.sajochamchi.databinding.FragmentSearchBinding
import com.team4.sajochamchi.ui.adapter.SearchHistoryAdapter
import com.team4.sajochamchi.ui.adapter.SearchResultAdapter
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

    private val searchResultAdapter: SearchResultAdapter by lazy {
        SearchResultAdapter { video ->
            /*val dialog = ViewDetailDialog.newInstance(object : ViewDetailDialog.ClickEventListener {
                override fun shareButtonClicked() {

                }

                override fun favoriteButtonClicked() {

                }

                override fun thumbnailImageClicked() {

                }
            })
            dialog.show(this@SearchFragment.childFragmentManager, "Detail Dialog")*/
        }
    }

    private val searchHistoryAdapter: SearchHistoryAdapter by lazy {
        SearchHistoryAdapter(
            onClickEventListener = { str ->
                binding.searchEditText.setText(str)
                searchViewModel.searchVideos(str)
                searchViewModel.addSearchHistory(str)
            },
            onDeleteImageClickEventListener = { pos ->
                searchViewModel.deleteSearchHistory(pos)
            })
    }

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
        // 최신 검색어 visibility 관련
        var selected = true
        searchHistoryStatusImageView.isSelected = selected
        searchHistoryStatusImageView.setOnClickListener {
            if (selected) {
                totalRecentryView.visibility = View.GONE
            } else {
                totalRecentryView.visibility = View.VISIBLE
            }
            selected = !selected
            searchHistoryStatusImageView.isSelected = selected
        }

        // 입력 관련
        searchEditText.apply {
            val searchDrawble = searchEditText.compoundDrawablesRelative[2]
            val clearDrawable = searchEditText.compoundDrawablesRelative[0]
            var clearDrawableShowed: Boolean

            if (text.isNullOrEmpty()) {
                clearDrawableShowed = false
                setCompoundDrawablesRelative(null, null, searchDrawble, null)
            } else {
                clearDrawableShowed = true
                setCompoundDrawablesRelative(null, null, clearDrawable, null)
            }

            addTextChangedListener { s: Editable? ->
                if (s != null) {
                    when {
                        s.isEmpty() -> {
                            setCompoundDrawablesRelative(null, null, searchDrawble, null)
                            clearDrawableShowed = false
                        }

                        else -> if (!clearDrawableShowed) {
                            setCompoundDrawablesRelative(null, null, clearDrawable, null)
                            clearDrawableShowed = true
                        }
                    }
                }
            }

            // clear버튼 동작
            setOnTouchListener { v, event ->
                var hasConsumed = false
                if (v is EditText) {
                    if (event.x >= v.width - v.totalPaddingRight) {
                        if (event.action == MotionEvent.ACTION_UP && clearDrawableShowed) {
                            text?.clear()
                            setCompoundDrawablesRelative(null, null, searchDrawble, null)
                            clearDrawableShowed = false
                        }
                        hasConsumed = true
                    }
                }
                hasConsumed
            }

            // 키보드  엔터시에 생기는 동작
            setOnKeyListener { v, keyCode, event ->
                if (event.action == KeyEvent.ACTION_DOWN && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    val imm: InputMethodManager =
                        activity?.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(windowToken, 0)
                    //todo search process
                    searchViewModel.searchVideos(text.toString())
                    searchViewModel.addSearchHistory(text.toString())
                    return@setOnKeyListener true
                }
                return@setOnKeyListener false
            }
        }

        recentlySearchRecyclerView.apply {
            adapter = searchHistoryAdapter
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        }

        resultRecyclerview.apply {
            adapter = searchResultAdapter
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        }

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
            searchResult.observe(viewLifecycleOwner) { list ->
                if (list.isEmpty()){
                    binding.noSearch.visibility = View.VISIBLE
                    binding.resultRecyclerview.visibility = View.GONE
                }else{
                    binding.noSearch.visibility = View.GONE
                    binding.resultRecyclerview.visibility = View.VISIBLE
                }
                searchResultAdapter.submitList(list)
            }
            searchHistory.observe(viewLifecycleOwner) { list ->
                if (list.isEmpty()){
                    searchHistoryAdapter.submitList(list)
                    binding.historyEmptyView.visibility = View.VISIBLE
                }else{
                    binding.historyEmptyView.visibility = View.GONE
                    searchHistoryAdapter.submitList(list)
                    binding.recentlySearchRecyclerView.scrollToPosition(0)
                }
            }
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