package com.enesdokuz.goodsounds.ui.category.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.enesdokuz.goodsounds.R
import com.enesdokuz.goodsounds.ui.base.BaseFragment
import com.enesdokuz.goodsounds.ui.category.adapter.CategoryAdapter
import com.enesdokuz.goodsounds.ui.category.viewmodel.CategoryViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_category.*

class CategoryFragment : BaseFragment() {

    companion object {
        fun newInstance() = CategoryFragment()
    }

    private val viewModel: CategoryViewModel by activityViewModels()
    private val categoryAdapter = CategoryAdapter(arrayListOf())

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_category, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        initUI()
        observeLiveData()
        initController()
        viewModel.getCategories()
    }

    override fun initUI() {
        viewModel.isLoading.value = false
        recyclerCategory.setHasFixedSize(true)
        recyclerCategory.isNestedScrollingEnabled = false
        recyclerCategory.layoutManager = LinearLayoutManager(context)
        recyclerCategory.adapter = categoryAdapter
    }

    override fun observeLiveData() {
        viewModel.categories.observe(viewLifecycleOwner, Observer {
            it?.let {
                categoryAdapter.update(it)
            }
        })

        viewModel.isLoading.observe(viewLifecycleOwner, Observer {
            it?.let {
                if (it) {
                    progressCategory.visibility = View.VISIBLE
                } else {
                    progressCategory.visibility = View.GONE
                }
            }
        })

        viewModel.errorMessage.observe(viewLifecycleOwner, Observer {
            it?.let {
                Snackbar.make(rlMainCategory, "$it", Snackbar.LENGTH_LONG)
                    .setAction(getString(R.string.tryAgain), View.OnClickListener {
                        viewModel.getCategories()
                    }).show()
            }
        })
    }

    override fun initController() {}
}