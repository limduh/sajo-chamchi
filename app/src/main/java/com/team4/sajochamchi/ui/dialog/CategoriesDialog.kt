package com.team4.sajochamchi.ui.dialog

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.R
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetBehavior.BottomSheetCallback
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.team4.sajochamchi.data.model.SaveCategory
import com.team4.sajochamchi.data.repository.TotalRepositoryImpl
import com.team4.sajochamchi.databinding.DialogCategoriesBinding
import com.team4.sajochamchi.ui.adapter.SelectedCategoriesAdpter
import com.team4.sajochamchi.ui.adapter.UnselectedCategoriesAdapter
import com.team4.sajochamchi.ui.viewmodel.CategoryViewModel
import com.team4.sajochamchi.ui.viewmodel.CategoryViewModelFactory
import com.team4.sajochamchi.ui.viewmodel.ViewDetailViewModel
import com.team4.sajochamchi.ui.viewmodel.ViewDetailViewModelFactory


class CategoriesDialog(private val eventListener: EventListener) :
    BottomSheetDialogFragment() {

    companion object {
        private const val TAG = "CategoriesDialog"
        fun newInstance(eventListener: EventListener) =
            CategoriesDialog(eventListener)
    }

    interface EventListener {
        fun onDismiss()
    }

    private var _binding: DialogCategoriesBinding? = null
    private val binding: DialogCategoriesBinding
        get() = _binding!!

    private val categoryViewModel: CategoryViewModel by viewModels() {
        CategoryViewModelFactory(
            TotalRepositoryImpl(requireContext())
        )
    }

    private val unselectedadapter: UnselectedCategoriesAdapter by lazy {
        UnselectedCategoriesAdapter(object : UnselectedCategoriesAdapter.ItemClick {
            override fun onClick(position: Int, saveCategory: SaveCategory) {
                categoryViewModel.addCategory(position, saveCategory)
            }
        })
    }

    private val selectedadapter: SelectedCategoriesAdpter by lazy {
        SelectedCategoriesAdpter(object : SelectedCategoriesAdpter.ItemClick {
            override fun onClick(position: Int, saveCategory: SaveCategory) {
                categoryViewModel.removeCategory(position, saveCategory)
            }
        })
    }

    private val itemTouchHelper: ItemTouchHelper =
        ItemTouchHelper(object : ItemTouchHelper.Callback() {
            override fun getMovementFlags(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
            ): Int {
                val dragFlags = ItemTouchHelper.UP or ItemTouchHelper.DOWN
                return makeMovementFlags(dragFlags, 0)
            }

            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder,
            ): Boolean {
                val from = viewHolder.adapterPosition
                val to = target.adapterPosition
                categoryViewModel.onSelectedItemMove(from, to)
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {}
        })

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
        initViewModels()
    }


    private fun initViews() = with(binding) {
        selectedVisibleImageView.apply {
            isSelected = true
            setOnClickListener {
                if (isSelected) {
                    rvSelectedDialog.visibility = View.GONE
                } else {
                    rvSelectedDialog.visibility = View.VISIBLE
                }
                isSelected = !isSelected
            }
        }

        unselectedVisibleImageView.apply {
            isSelected = true
            setOnClickListener {
                if (isSelected) {
                    rvUnselectedDialog.visibility = View.GONE
                } else {
                    rvUnselectedDialog.visibility = View.VISIBLE
                }
                isSelected = !isSelected
            }
        }

        rvSelectedDialog.apply {
            adapter = selectedadapter
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            setHasFixedSize(false)
            isNestedScrollingEnabled = false
            addItemDecoration(DividerItemDecoration(requireContext(), LinearLayout.VERTICAL).apply {
                setDrawable(requireContext().resources.getDrawable(com.team4.sajochamchi.R.drawable.recyclerview_divider))
            })
            itemTouchHelper.attachToRecyclerView(this)
        }



        rvUnselectedDialog.apply {
            adapter = unselectedadapter
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            setHasFixedSize(false)
            isNestedScrollingEnabled = false
            addItemDecoration(DividerItemDecoration(requireContext(), LinearLayout.VERTICAL).apply {
                setDrawable(requireContext().resources.getDrawable(com.team4.sajochamchi.R.drawable.recyclerview_divider))
            })
        }


        closeImageButton.setOnClickListener {
            dismiss()
        }

        //드래그 방지
        /*try {
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
        }*/
    }

    private fun initViewModels() {
        with(categoryViewModel) {
            selectedCategory.observe(viewLifecycleOwner) { list ->
                Log.d(TAG, "initViewModels: selected : ${list.size}")
                selectedadapter.submitList(list)
            }
            unselectedCategory.observe(viewLifecycleOwner) { list ->
                Log.d(TAG, "initViewModels: unselected : ${list.size}")
                unselectedadapter.submitList(list)
                Log.d(TAG, "initViewModels: ${binding.rvUnselectedDialog.adapter?.itemCount}")
            }
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