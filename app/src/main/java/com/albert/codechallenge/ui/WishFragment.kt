package com.albert.codechallenge.ui

import android.app.Activity
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.albert.codechallenge.R
import com.albert.codechallenge.model.Wish
import com.albert.codechallenge.model.WishListAdapter
import com.albert.codechallenge.model.WishViewModel
import kotlinx.android.synthetic.main.wish_fragment.*

class WishFragment(activity: Activity) : Fragment() {

    private lateinit var mActivity: Activity
    lateinit var adapter: WishListAdapter
    private lateinit var viewModel: WishViewModel
    lateinit var wishIn: Wish

    init{
        mActivity = activity
    }


    companion object {

        fun newInstance(activity: Activity): WishFragment{
            val fragment = WishFragment(activity)
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.wish_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = WishListAdapter(mActivity /*this.requireContext()*/)
        recyclerview.adapter = adapter
        recyclerview.layoutManager = LinearLayoutManager(this.requireContext())
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        // Get a new or existing ViewModel from the ViewModelProvider.
        viewModel = ViewModelProviders.of(this).get(WishViewModel::class.java)
         // Add an observer on the LiveData returned by getAlphabetizedWords.
        // The onChanged() method fires when the observed data changes and the activity is
        // in the foreground.

        viewModel.allWishes.observe(this, Observer { wishes ->
            // Update the cached copy of the words in the adapter.
            wishes?.let { adapter.setWishes(it) }
        })
    }

}
