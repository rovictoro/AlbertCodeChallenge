package com.albert.codechallenge.model

/*
 * Copyright (C) 2017 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import android.app.Activity
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import com.albert.codechallenge.R
import com.albert.codechallenge.ui.ViewDialog
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso


class WishListAdapter constructor( activity: Activity) : RecyclerView.Adapter<WishListAdapter.WishViewHolder>() {

    private val mActivity = activity
    private val inflater: LayoutInflater = LayoutInflater.from(mActivity)
    private var wishes = emptyList<Wish>() // Cached copy of words
    private var viewDialog: ViewDialog? = null

    inner class WishViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val docCover = itemView.findViewById<ImageView>(R.id.doc_cover)
        val docAuthorName = itemView.findViewById<TextView>(R.id.doc_author)
        val docTitle = itemView.findViewById<TextView>(R.id.doc_title)
        val docPublisher = itemView.findViewById<TextView>(R.id.doc_publisher)
        val docFirstPublishYear = itemView.findViewById<TextView>(R.id.doc_first_publish_year)
        val progressBar = itemView.findViewById<ProgressBar>(R.id.progressBar)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WishViewHolder {
        viewDialog = ViewDialog(mActivity)
        val itemView = inflater.inflate(R.layout.wish_item, parent, false)
        return WishViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: WishViewHolder, position: Int) {
        val current = wishes[position]
        holder.docAuthorName.text = current.docAuthor
        holder.docTitle.text = current.docTitle
        holder.docPublisher.text = current.docPublisher
        holder.docFirstPublishYear.text = current.docFirstPublishYear

        holder.docCover.setOnClickListener{ viewDialog?.showDialog(current) }
        holder.itemView.setOnClickListener { viewDialog?.showDialog(current) }

        loadImagePicasso(current, holder.docCover, holder.progressBar)
    }

    internal fun setWishes(wishes: List<Wish>) {
        this.wishes = wishes
        notifyDataSetChanged()
    }

    override fun getItemCount() = wishes.size

    private fun loadImagePicasso(wish: Wish, view: ImageView, progressBar: ProgressBar) {

        progressBar.visibility = View.VISIBLE

        val displayMetrics = mActivity.resources.displayMetrics
        val height = displayMetrics.heightPixels
        val width = displayMetrics.widthPixels


        if(wish.docCoverUrl.isNotEmpty()) {
            Picasso.get()
                    .load(wish.docCoverUrl)
                    .placeholder(R.drawable.ic_broken_image_blue_48dp)
                    //.error(R.drawable.ic_search_green_24dp)
                    .tag(TAG)
                    .resize(width*4/10, height*4/10 )
                    .centerInside()
                    .into(view, object : Callback {
                        override fun onSuccess() {
                            progressBar.visibility = View.INVISIBLE
                        }

                        override fun onError(e: Exception) {
                            progressBar.visibility = View.INVISIBLE
                        }
                    })
        } else {

            Picasso.get()
                    .load(R.drawable.ic_broken_image_blue_48dp)
                    .placeholder(R.drawable.ic_broken_image_blue_48dp)
                    //.error(R.drawable.ic_search_green_24dp)
                    .tag(TAG)
                    .into(view, object : Callback {
                        override fun onSuccess() {
                            progressBar.visibility = View.INVISIBLE
                        }

                        override fun onError(e: Exception) {
                            progressBar.visibility = View.INVISIBLE
                        }
                    })

        }

    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)
        if (viewDialog != null) {
            viewDialog?.hideDialog()
        }

    }

    override fun onViewDetachedFromWindow(holder: WishViewHolder) {
        super.onViewDetachedFromWindow(holder)
        if (viewDialog != null) {
            viewDialog?.hideDialog()
        }

    }

    companion object {
        private val TAG = WishListAdapter::class.java.simpleName
    }
}


