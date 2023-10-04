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
import com.team4.sajochamchi.databinding.FragmentMyPageBinding
import com.team4.sajochamchi.ui.adapter.SaveVideoAdapter
import com.team4.sajochamchi.ui.dialog.EditDialog
import com.team4.sajochamchi.ui.dialog.ViewDetailDialog
import com.team4.sajochamchi.ui.viewmodel.MainSharedViewModel
import com.team4.sajochamchi.ui.viewmodel.MyPageViewModelFactory
import com.team4.sajochamchi.ui.viewmodel.MypageViewModel
import kotlin.math.log

class MyPageFragment : Fragment() {
    private var _binding: FragmentMyPageBinding? = null
    private val binding: FragmentMyPageBinding
        get() = _binding!!
    private val mypageViewModel: MypageViewModel by viewModels {
        MyPageViewModelFactory(TotalRepositoryImpl(requireContext()))
    }

    private val mainSharedViewModel: MainSharedViewModel by activityViewModels()
    private val saveVideoAdapter : SaveVideoAdapter by lazy {
        SaveVideoAdapter(){ saveItem ->

        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentMyPageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initViewModels()
    }

    private fun initViews() = with(binding) {
        saveRecyclerView.apply {
            adapter = saveVideoAdapter
            layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)
        }


        editImageView.setOnClickListener {
            val dialog = EditDialog.newInstance(
                nameTextView.text.toString(),
                descriptionTextView.text.toString(),
                object : EditDialog.ClickEventListener{
                    override fun nameChanged(string: String) {
                        mypageViewModel.saveNamePrefs(string)
                    }

                    override fun descriptionChanged(string: String) {
                        mypageViewModel.saveDescriptionPrefs(string)
                    }

                    override fun favoriteClearClicked() {
                        mypageViewModel.deleteAllFavorite()
                    }

                }
            )
            dialog.show(this@MyPageFragment.childFragmentManager, "Detail Dialog")
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
            dialog.show(this@MyPageFragment.childFragmentManager, "Detail Dialog")
        }
    }

    private fun initViewModels() {
        with(mypageViewModel) {
            saveItems.observe(viewLifecycleOwner){ list->
                //Log.d(TAG, "initViewModels: ${list.size}")
                saveVideoAdapter.submitList(list)
            }

            saveName.observe(viewLifecycleOwner){
                binding.nameTextView.text = it
            }

            saveDescription.observe(viewLifecycleOwner){
                binding.descriptionTextView.text = it
            }
        }

        with(mainSharedViewModel) {
            myPageEvent.observe(viewLifecycleOwner) {

            }
        }
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }


    companion object {
        private const val TAG = "MyPageFragment"
        fun newInstance() = MyPageFragment()
    }
}