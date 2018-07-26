package com.example.fella.demo_app.view

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.view.Menu
import android.view.View
import android.widget.AdapterView
import com.example.fella.demo_app.App
import com.example.fella.demo_app.R
import kotlinx.android.synthetic.main.activity_discounts.*
import android.widget.ArrayAdapter
import android.widget.Spinner


class DiscountsActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    override fun onNothingSelected(parent: AdapterView<*>?) {
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
        setContentView(R.layout.activity_discounts)
        setSupportActionBar(this.findViewById(R.id.my_toolbar))
        supportActionBar!!.setDisplayShowTitleEnabled(false)
        val spinner = findViewById<View>(R.id.spinner) as Spinner
        val adapt = ArrayAdapter.createFromResource(this,
                R.array.types, android.R.layout.simple_spinner_item)
        adapt.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapt
        when (spinner.selectedItemPosition) {
            0 -> {
                if (App.state == null)
                    changeFragment(ManDiscountFragment())
            }
            1 -> {
                if (App.state == null)
                    changeFragment(WomanDiscountFragment())
            }
            2 -> {
                if (App.state == null)
                    changeFragment(ChildDiscountFragment())
            }
        }
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        spinner.onItemSelectedListener = this
        return super.onCreateOptionsMenu(menu)
    }

    private fun changeFragment(f: Fragment, cleanStack: Boolean = false) {
        App.page = 1
        App.discounts_woman.clear()
        App.discounts_man.clear()
        App.discounts_child.clear()
        App.state = null
        App.compositeDisposable.clear()
        val ft = supportFragmentManager.beginTransaction()
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
