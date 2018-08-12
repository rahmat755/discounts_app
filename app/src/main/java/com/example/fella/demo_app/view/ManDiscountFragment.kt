package com.example.fella.demo_app.view


import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
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
import android.util.Log
import com.example.fella.demo_app.App
import com.example.fella.demo_app.viewmodel.DiscountViewModel
import com.example.fella.demo_app.viewmodel.DiscountViewModelFactory
import javax.inject.Inject


class ManDiscountFragment : Fragment(), DiscountAdapter.OnViewSelectedListener {
    override fun onItemClicked(itemURL: String) {
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(itemURL)))
    }

    @Inject
    lateinit var discountViewModelFactory: DiscountViewModelFactory
    private lateinit var discountAdapter: DiscountAdapter
    private val linearLayout = LinearLayoutManager(context)
    lateinit var model :DiscountViewModel

    private fun showError(error: String) {
        Snackbar.make(man_discount_recycler_view, error, Snackbar.LENGTH_INDEFINITE)
                .setAction("Повторить попытку") { requestData() }.show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.discountsComponent.injectMan(this)
    }
    private fun requestData() {
        progressBar_man.visibility=View.VISIBLE
        model.getManDiscounts()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        model = ViewModelProviders.of(this, discountViewModelFactory).get(DiscountViewModel::class.java)
        discountAdapter = DiscountAdapter(this)

        man_discount_recycler_view.apply {
            layoutManager = linearLayout
            adapter = discountAdapter
            addOnScrollListener(InfiniteScrollListener({ requestData() }, linearLayout))
        }
        model.getManRecyclerViewState().observe(this, Observer{ state ->
            linearLayout.onRestoreInstanceState(state)
        })
        model.getManDiscounts().observe(this, Observer{ item ->
            discountAdapter.removeAllItems()
            discountAdapter.addItems(item!!)
        })
        model.hideManProgressBar.observe(this, Observer {
            it?.getContentIfNotHandled()?.let{
                progressBar_man.visibility=View.GONE
            }
        })
        model.showManError.observe(this, Observer {
            it?.getContentIfNotHandled()?.let {
                showError(it)
            }
        })

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return container?.inflate(R.layout.fragment_man_discount)
    }

    override fun onResume() {
        super.onResume()
        linearLayout.onRestoreInstanceState(model.manRecyclerViewState?.value)
    }
    override fun onStop() {
        super.onStop()
        model.manRecyclerViewState!!.value = linearLayout.onSaveInstanceState()
    }

}
