package com.example.fella.demo_app.view

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProviders
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import com.example.fella.demo_app.App
import com.example.fella.demo_app.R
import kotlinx.android.synthetic.main.activity_discounts.*
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.example.fella.demo_app.viewmodel.ToolBarViewModel
import com.example.fella.demo_app.viewmodel.ToolbarViewModelFactory
import javax.inject.Inject


class DiscountsActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {
    private var isFav = false
    private lateinit var viewModel: ToolBarViewModel
    @Inject
    lateinit var toolbarViewModelFactory: ToolbarViewModelFactory

    override fun onNothingSelected(parent: AdapterView<*>?) {
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.action_hot -> {
                viewModel._toolbarState.value = !isFav
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        when (position) {
            0 -> {
                changeFragment(ManDiscountFragment())
            }
            1 -> {
                changeFragment(WomanDiscountFragment())
            }
            2 -> {
                changeFragment(ChildDiscountFragment())
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.discountsComponent.injectActivity(this)
        setContentView(R.layout.activity_discounts)
        viewModel = ViewModelProviders.of(this, toolbarViewModelFactory).get(ToolBarViewModel::class.java)
        setSupportActionBar(this.findViewById(R.id.my_toolbar))
        supportActionBar!!.setDisplayShowTitleEnabled(false)
        val spinner = findViewById<View>(R.id.spinner) as Spinner
        val adapt = ArrayAdapter.createFromResource(this,
                R.array.types, android.R.layout.simple_spinner_item)
        adapt.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapt
        if (savedInstanceState == null) {
            when (spinner.selectedItemPosition) {
                0 -> {
                    changeFragment(ManDiscountFragment())
                }
                1 -> {
                    changeFragment(WomanDiscountFragment())
                }
                2 -> {
                    changeFragment(ChildDiscountFragment())
                }
            }
        }
        viewModel.getToolbarState().observe(this, Observer {
            isFav = it!!
            invalidateOptionsMenu()
        })
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        if (isFav) menu?.getItem(0)?.setIcon(R.drawable.ic_fire)
        else menu?.getItem(0)?.setIcon(R.drawable.ic_fire_off)
        return super.onPrepareOptionsMenu(menu)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        spinner.onItemSelectedListener = this
        menuInflater.inflate(R.menu.toolbar_items, menu)
        return super.onCreateOptionsMenu(menu)
    }

    private fun changeFragment(f: Fragment, cleanStack: Boolean = false) {
        val ft = supportFragmentManager.beginTransaction()
        viewModel._toolbarState.value=false
        if (cleanStack) {
            clearBackStack()
        }
        ft.setCustomAnimations(
                R.anim.abc_fade_in, R.anim.abc_fade_out, R.anim.abc_popup_enter, R.anim.abc_popup_exit)
        ft.replace(R.id.discount_activity_content, f)
        ft.commit()
    }

    private fun clearBackStack() {
        val manager = supportFragmentManager
        if (manager.backStackEntryCount > 0) {
            val first = manager.getBackStackEntryAt(0)
            manager.popBackStack(first.id, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        }
    }
}
