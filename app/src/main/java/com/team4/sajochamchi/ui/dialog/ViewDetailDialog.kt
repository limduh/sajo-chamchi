package com.team4.sajochamchi.ui.dialog

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetBehavior.BottomSheetCallback
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.team4.sajochamchi.data.model.SaveItem
import com.team4.sajochamchi.data.repository.TotalRepositoryImpl
import com.team4.sajochamchi.databinding.DialogViewDetailBinding
import com.team4.sajochamchi.ui.activity.WebViewActivity
import com.team4.sajochamchi.ui.viewmodel.HomeViewModel
import com.team4.sajochamchi.ui.viewmodel.HomeViewModelFactory
import com.team4.sajochamchi.ui.viewmodel.ViewDetailViewModel
import com.team4.sajochamchi.ui.viewmodel.ViewDetailViewModelFactory


class ViewDetailDialog(
    private val video: SaveItem,
) : BottomSheetDialogFragment() {

    companion object {
        fun newInstance(
            saveItem: SaveItem,
        ) = ViewDetailDialog(saveItem)
    }


    private var _binding: DialogViewDetailBinding? = null
    private val binding: DialogViewDetailBinding
        get() = _binding!!

    private val viewDetailViewModel: ViewDetailViewModel by viewModels() {
        ViewDetailViewModelFactory(
            video,
            TotalRepositoryImpl(requireContext(), video.videoId.orEmpty())
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = DialogViewDetailBinding.inflate(inflater, container, false)
        //dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        //layout 크기 조절
        binding.layout.layoutParams = ViewGroup.LayoutParams(
            resources.displayMetrics.widthPixels,
            resources.displayMetrics.heightPixels
        )
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val offsetFromTop = 0
        (dialog as? BottomSheetDialog)?.behavior?.apply {
            isFitToContents = false
            setExpandedOffset(offsetFromTop)
            state = BottomSheetBehavior.STATE_EXPANDED
        }

        initViews()
        initViewModels()
    }

    private fun initViews() = with(binding) {
        Glide.with(requireContext())
            .load(video.thumbnailsUrl.orEmpty())
            .into(imgThumbnail)

        titleTextView.text = video.title
        descTextView.text = video.description

        favoriteImageView.setOnClickListener {
            if (favoriteImageView.isSelected){
                viewDetailViewModel.deleteItem(video)
            } else {
                viewDetailViewModel.favoriteItem(video)
            }
            //clickEventListener.favoriteButtonClicked(video, favoriteImageView.isSelected)
            favoriteImageView.isSelected = !favoriteImageView.isSelected
        }

        imgThumbnail.setOnClickListener {
            startActivity(
                WebViewActivity.newIntent(
                    requireContext(),
                    video.title.orEmpty(),
                    "https://www.youtube.com/watch?v=${video.videoId.orEmpty()}"
                )
            )
        }

        closeImageButton.setOnClickListener {
            dismiss()
        }

        shareImageButton.setOnClickListener {
            // clickEventListener.shareButtonClicked()
            val shareIntent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(
                    Intent.EXTRA_TEXT,
                    "https://www.youtube.com/watch?v=${video.videoId.orEmpty()}"// 전달하려는 Data(Value)
                )
                type = "text/plain"
            }
            startActivity(Intent.createChooser(shareIntent, null))
        }
        //드래그 방지
        try {
            val behavior = (dialog as BottomSheetDialog).behavior
            behavior.addBottomSheetCallback(object : BottomSheetCallback() {
                override fun onStateChanged(bottomSheet: View, newState: Int) {
                    if (newState == BottomSheetBehavior.STATE_DRAGGING) {
                        behavior.state = BottomSheetBehavior.STATE_EXPANDED
                    }
                }

                override fun onSlide(bottomSheet: View, slideOffset: Float) {}
            })
        } catch (e: NoSuchFieldException) {
            e.printStackTrace()
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        }
    }

    private fun initViewModels() {
        with(viewDetailViewModel) {
            isFavorite.observe(viewLifecycleOwner) { list ->
                binding.favoriteImageView.isSelected = list.isNotEmpty()
            }
        }
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}