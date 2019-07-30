package com.albert.codechallenge.ui

import android.app.Activity
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.albert.codechallenge.model.Doc
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.search_item.view.*
import android.widget.ProgressBar
import com.albert.codechallenge.R
import com.albert.codechallenge.ui.SearchApplication.Companion.COVER_URL_LARGE
import com.albert.codechallenge.ui.SearchApplication.Companion.COVER_URL_MIDDLE
import com.squareup.picasso.Callback


class SearchListAdapter


(private val mActivity: Activity) : RecyclerView.Adapter<SearchListAdapter.ViewHolder>() {
    private val mDocs: MutableList<Doc> = mutableListOf<Doc>()

    private var viewDialog: ViewDialog? = null

    fun deleteDocs() {
        mDocs.clear()
        notifyDataSetChanged()
    }

    fun addDocs(docs: MutableList<Doc>) {
        mDocs.clear()
        val rangeStart = mDocs.size
        val rangeEnd = rangeStart + docs.size
        mDocs.addAll(docs)

        Log.e(TAG, "mDocs size: " + mDocs.size)
        notifyDataSetChanged()
    }

    fun addDoc(doc: Doc) {

        if (!mDocs.contains(doc)) {
            mDocs.add(doc)
            notifyItemInserted(mDocs.size)
        } else {
            notifyItemChanged(mDocs.indexOf(doc))
        }

    }

    fun updateDoc(doc: Doc) {
        if (mDocs.contains(doc)) {
            notifyItemChanged(mDocs.indexOf(doc))
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        viewDialog = ViewDialog(mActivity)

        val inflater = LayoutInflater.from(mActivity)
        val contactView = inflater.inflate(R.layout.search_item, parent, false)
        return ViewHolder(contactView)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val doc = mDocs[position]
        holder.docAuthor.setText(doc.authorName?.first())
        holder.docTitle.setText(doc.title)
        loadImagePicasso(mActivity, doc, holder.docCover, holder.progressBar)

        holder.docCover.setOnClickListener{ viewDialog?.showDialog(doc) }
        holder.itemView.setOnClickListener { viewDialog?.showDialog(doc) }

        holder.docCover.requestLayout()

    }

    override fun getItemCount(): Int {
        return mDocs.size
    }

    class ViewHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val docAuthor: TextView
        val docTitle: TextView
        val docCover: ImageView
        val progressBar: ProgressBar

        init {
            docAuthor = itemView.doc_author
            docTitle = itemView.doc_title
            docCover = itemView.doc_cover
            progressBar = itemView.progressBar
        }
    }



    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)
        if (viewDialog != null) {
            viewDialog?.hideDialog()
        }
    }

    override fun onViewDetachedFromWindow(holder: ViewHolder) {
        super.onViewDetachedFromWindow(holder)
        if (viewDialog != null) {
            viewDialog?.hideDialog()
        }

    }

    private fun loadImagePicasso(activity: Activity, doc: Doc, view: ImageView, progressBar: ProgressBar) {
        progressBar.visibility = View.VISIBLE

        if(doc.coverI != null) {
            Picasso.get()
                    .load(COVER_URL_MIDDLE(doc.coverI.toString()))
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

    companion object {
        private val TAG = SearchListAdapter::class.java.simpleName
    }
}
