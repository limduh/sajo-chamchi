package com.team4.sajochamchi.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.team4.sajochamchi.data.repository.TotalRepositoryImpl
import com.team4.sajochamchi.databinding.FragmentHomeBinding
import com.team4.sajochamchi.ui.activity.WebViewActivity
import com.team4.sajochamchi.ui.adapter.ChannelAdapter
import com.team4.sajochamchi.ui.adapter.HorizontalVideoAdapter
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

    private val horizontalPopularVideoAdapter: HorizontalVideoAdapter by lazy {
        HorizontalVideoAdapter { video ->
            Log.d(TAG, " $video")
            val dialog =
                ViewDetailDialog.newInstance(video)
            dialog.show(this@HomeFragment.childFragmentManager, "Detail Dialog")
        }
    }

    private val horizontalCategoryVideoAdapter: HorizontalVideoAdapter by lazy {
        HorizontalVideoAdapter { video ->
            Log.d(TAG, " $video")
            val dialog =
                ViewDetailDialog.newInstance(video)
            dialog.show(this@HomeFragment.childFragmentManager, "Detail Dialog")
        }
    }

    private val channelAdapter: ChannelAdapter by lazy {
        ChannelAdapter { channel ->
            Log.d(TAG, " $channel")
            startActivity(
                WebViewActivity.newIntent(
                    requireContext(),
                    channel.title.orEmpty(),
                    "https://www.youtube.com/channel/${channel.id.orEmpty()}"
                )
            )
        }
    }


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
        recentlyRecyclerView.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = horizontalPopularVideoAdapter
        }

        categoryVideoRecyclerView.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = horizontalCategoryVideoAdapter
        }

        channelsRecyclerView.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = channelAdapter
        }

        categoriesDialogButton.setOnClickListener {
            val dialog = CategoriesDialog.newInstance(object : CategoriesDialog.EventListener {

            })
            dialog.show(this@HomeFragment.childFragmentManager, "Categories Dialog")
        }
    }

    private fun initViewModels() {
        with(homeViewModel) {
            getAllMostPopular()
            popularItemList.observe(viewLifecycleOwner) { list ->
                horizontalPopularVideoAdapter.submitList(list)
            }
            getAllMostPopularWithCategoryId("1")
            categoryItemList.observe(viewLifecycleOwner) { list ->
                horizontalCategoryVideoAdapter.submitList(list)
            }
            channelItemList.observe(viewLifecycleOwner) { list ->
                channelAdapter.submitList(list)
            }
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
        private const val TAG = "HomeFragment"
        fun newInstance() = HomeFragment()
    }
}