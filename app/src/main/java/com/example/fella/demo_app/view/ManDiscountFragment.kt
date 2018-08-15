package com.example.fella.demo_app.view

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.fella.demo_app.R
import com.example.fella.demo_app.adapters.DiscountAdapter
import com.example.fella.demo_app.utils.InfiniteScrollListener
import com.example.fella.demo_app.utils.inflate
import kotlinx.android.synthetic.main.fragment_man_discount.*
import android.content.Intent
import android.net.Uri
import android.support.v7.widget.GridLayoutManager
import com.example.fella.demo_app.App
import com.example.fella.demo_app.adapters.DiscountDelegateAdapter
import com.example.fella.demo_app.utils.AdapterConstants
import com.example.fella.demo_app.utils.EqualSpacingItemDecoration
import com.example.fella.demo_app.viewmodel.DiscountViewModel
import com.example.fella.demo_app.viewmodel.DiscountViewModelFactory
import com.example.fella.demo_app.viewmodel.ToolBarViewModel
import com.example.fella.demo_app.viewmodel.ToolbarViewModelFactory
import javax.inject.Inject


class ManDiscountFragment : Fragment(), DiscountDelegateAdapter.OnViewSelectedListener {
    override fun onItemClicked(itemURL: String) {
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(itemURL)))
    }

    @Inject
    lateinit var toolbarViewModelFactory: ToolbarViewModelFactory
    @Inject
    lateinit var discountViewModelFactory: DiscountViewModelFactory
    private lateinit var discountAdapter: DiscountAdapter
    private val gridLayout = GridLayoutManager(context, 2)
    lateinit var model: DiscountViewModel
    private lateinit var toolbarModel: ToolBarViewModel
    private fun showError(error: String) {
        Snackbar.make(man_discount_recycler_view, error, Snackbar.LENGTH_INDEFINITE)
                .setAction("Повторить попытку") { requestData(isHot) }.show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.discountsComponent.injectMan(this)
    }

    private fun requestData(isHot: Boolean) {
        if (isHot) {
            model.getManHotDiscounts()
        } else {
            model.getManDiscounts()
        }
    }


    private var isHot: Boolean = false

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        model = ViewModelProviders.of(activity!!, discountViewModelFactory).get(DiscountViewModel::class.java)
        toolbarModel = ViewModelProviders.of(activity!!, toolbarViewModelFactory).get(ToolBarViewModel::class.java)
        discountAdapter = DiscountAdapter(this)
        val itemDecoration = EqualSpacingItemDecoration(16, 2)
        gridLayout.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return when (discountAdapter.getItemViewType(position)) {
                    AdapterConstants.DISCOUNTS -> 1
                    AdapterConstants.LOADING -> 2
                    else -> -1
                }
            }
        }
        man_discount_recycler_view.apply {
            layoutManager = gridLayout
            adapter = discountAdapter
            addItemDecoration(itemDecoration)
            addOnScrollListener(InfiniteScrollListener({ requestData(isHot) }, gridLayout))
        }
        model.getManDiscounts().observe(this, Observer { item ->
            discountAdapter.removeAllItems()
            discountAdapter.addItems(item!!)
        })
        model.showManError.observe(this, Observer {
            it?.getContentIfNotHandled()?.let {
                showError(it)
            }
        })
        toolbarModel.getToolbarState().observe(this, Observer {
            isHot = it!!
            model.pageMan.value = 1
            model.manDiscount.value = arrayListOf()
            man_discount_recycler_view.clearOnScrollListeners()
                man_discount_recycler_view.addOnScrollListener(
                        InfiniteScrollListener({ requestData(it) }, gridLayout)
                )
            requestData(it)
        })
        model.getManRecyclerViewState().observe(this, Observer { state ->
            gridLayout.onRestoreInstanceState(state)
        })
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return container?.inflate(R.layout.fragment_man_discount)
    }

    override fun onResume() {
        super.onResume()
        gridLayout.onRestoreInstanceState(model.manRecyclerViewState?.value)
    }

    override fun onStop() {
        super.onStop()
        model.manRecyclerViewState!!.value = gridLayout.onSaveInstanceState()
    }
}
