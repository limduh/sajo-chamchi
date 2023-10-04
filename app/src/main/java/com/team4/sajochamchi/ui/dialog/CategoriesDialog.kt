package com.team4.sajochamchi.ui.dialog

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.R
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetBehavior.BottomSheetCallback
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.team4.sajochamchi.data.model.SaveCategory
import com.team4.sajochamchi.databinding.DialogCategoriesBinding
import com.team4.sajochamchi.ui.adapter.SelectedCategoriesAdpter
import com.team4.sajochamchi.ui.adapter.UnselectedCategoriesAdapter


class CategoriesDialog(private val eventListener: EventListener) :
    BottomSheetDialogFragment() {

    companion object {
        fun newInstance(eventListener: EventListener) =
            CategoriesDialog(eventListener)
    }

    interface EventListener {
        fun onDismiss()
    }

    private var _binding: DialogCategoriesBinding? = null
    private val binding: DialogCategoriesBinding
        get() = _binding!!

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        return super.onCreateDialog(savedInstanceState).apply {
            setOnShowListener {
                window?.findViewById<View>(R.id.touch_outside)
                    ?.setOnClickListener(null)
                (window?.findViewById<View>(R.id.design_bottom_sheet)
                    ?.layoutParams as CoordinatorLayout.LayoutParams).behavior = null
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = DialogCategoriesBinding.inflate(inflater, container, false)
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
    }

    private fun initViews() = with(binding) {

        val unselectedItems = ArrayList<SaveCategory>()
        val selecedItems = ArrayList<SaveCategory>()
        unselectedItems.add(SaveCategory("dd","dd"))
        unselectedItems.add(SaveCategory("dd","dd"))
        unselectedItems.add(SaveCategory("dd","dd"))
        selecedItems.add(SaveCategory("dd","dd"))
        selecedItems.add(SaveCategory("dd","dd"))
        selecedItems.add(SaveCategory("dd","dd"))

        val unselectedadapter = UnselectedCategoriesAdapter( object :UnselectedCategoriesAdapter.ItemClick{
            override fun onClick(saveCategory: SaveCategory) {

            }

        })
        val selectedadapter = SelectedCategoriesAdpter(object : SelectedCategoriesAdpter.ItemClick{
            override fun onClick(saveCategory: SaveCategory) {

            }
        })
        rvUnselectedDialog.apply {
            adapter = unselectedadapter
            layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)
            setHasFixedSize(true)
            addItemDecoration(DividerItemDecoration(requireContext(), LinearLayout.VERTICAL))
        }

        rvSelectedDialog.apply {
            adapter = unselectedadapter
            layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)
            setHasFixedSize(true)
            addItemDecoration(DividerItemDecoration(requireContext(), LinearLayout.VERTICAL))
        }

        closeImageButton.setOnClickListener {
            dismiss()
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

    override fun onDismiss(dialog: DialogInterface) {
        eventListener.onDismiss()
        super.onDismiss(dialog)
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}