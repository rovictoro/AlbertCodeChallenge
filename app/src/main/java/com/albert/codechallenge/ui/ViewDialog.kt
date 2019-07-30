package com.albert.codechallenge.ui

import android.app.Activity
import android.app.Dialog
import android.util.DisplayMetrics
import android.view.View
import android.view.Window
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import com.albert.codechallenge.model.Doc
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import android.view.WindowManager
import com.albert.codechallenge.R
import com.albert.codechallenge.model.Wish
import com.albert.codechallenge.model.WishViewModel

class ViewDialog(private val activity: Activity) {
    private var dialog: Dialog
    private var viewModel: WishViewModel

    init{
        dialog = Dialog(activity)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.custom_loading_layout)
        viewModel = WishViewModel(activity.application)
        val window = dialog.getWindow()
        window?.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT)
    }

    fun showDialog(doc: Doc?) {

        if (doc != null) {

            val close = dialog.findViewById<ImageView>(R.id.close)
            val save = dialog.findViewById<ImageView>(R.id.save)

            save.setOnClickListener {

                val wish = Wish(
                        id = 0L,
                        docAuthor = doc.authorName?.first().toString(),
                        docTitle = doc.title?:"",
                        docPublisher = doc.publisher?.first().toString(),
                        docFirstPublishYear = doc.firstPublishYear.toString() ,
                        docCoverUrl = SearchApplication.COVER_URL_LARGE(doc.coverI.toString())
                )

                viewModel.insert(wish)
                dialog.dismiss()
            }

            close.setOnClickListener {
                dialog.dismiss()
            }

            val docCover = dialog.findViewById<ImageView>(R.id.doc_cover)
            val docAuthorName = dialog.findViewById<TextView>(R.id.doc_author)
            val docTitle = dialog.findViewById<TextView>(R.id.doc_title)
            val docPublisher = dialog.findViewById<TextView>(R.id.doc_publisher)
            val docFirstPublishYear = dialog.findViewById<TextView>(R.id.doc_first_publish_year)
            val progressBar = dialog.findViewById<ProgressBar>(R.id.progressBar)

            if (doc.authorName?.first() != null) {
                val author = "Author: " + doc.authorName?.first()
                docAuthorName?.text = author
            }
            if (doc.title != null) {
                val title = "Title: " + doc.title
                docTitle?.text = title
            }
            if (doc.publisher?.first() != null) {
                val publisher = "Publisher: " + doc.publisher
                docPublisher?.text = publisher
            }
            if (doc.firstPublishYear != null) {
                val firstPublishYear = "First Publish Year: " + doc.firstPublishYear
                docFirstPublishYear?.text = firstPublishYear
            }

            loadImagePicasso(doc, docCover, progressBar)

        }

        dialog.show()

    }

    fun showDialog(wish: Wish?) {

        if (wish != null) {

            val delete = dialog.findViewById<ImageView>(R.id.save)
            delete.setBackgroundResource(R.drawable.ic_delete_forever_red_24dp)
            val close = dialog.findViewById<ImageView>(R.id.close)

            delete.setOnClickListener {
                viewModel.delete(wish.id)
                dialog.dismiss()
            }

            close.setOnClickListener {
                dialog.dismiss()
            }

            val docCover = dialog.findViewById<ImageView>(R.id.doc_cover)
            val docAuthorName = dialog.findViewById<TextView>(R.id.doc_author)
            val docTitle = dialog.findViewById<TextView>(R.id.doc_title)
            val docPublisher = dialog.findViewById<TextView>(R.id.doc_publisher)
            val productDepartment = dialog.findViewById<TextView>(R.id.doc_first_publish_year)
            val progressBar = dialog.findViewById<ProgressBar>(R.id.progressBar)

                val author = "Author: " + wish.docAuthor.first()
                docAuthorName?.text = author

                val title = "Title: " + wish.docTitle
                docTitle?.text = title

                val publisher = "Publisher: " + wish.docPublisher
                docPublisher?.text = publisher

                val firstPublishYear = "First Publish Year: " + wish.docFirstPublishYear
                productDepartment?.text = firstPublishYear

            loadImagePicasso(wish, docCover, progressBar)

        }

        dialog.show()

    }

    private fun dpToPx(dp: Int): Int {
        val displayMetrics = activity.resources.displayMetrics
        return Math.round(dp * (displayMetrics.xdpi/ DisplayMetrics.DENSITY_DEFAULT))
    }

    private fun loadImagePicasso(doc: Doc?, view: ImageView, progressBar: ProgressBar) {

        progressBar.visibility = View.VISIBLE

        val displayMetrics = activity.resources.displayMetrics
        val height = displayMetrics.heightPixels
        val width = displayMetrics.widthPixels


        if(doc?.coverI != null) {
            Picasso.get()
                    .load(SearchApplication.COVER_URL_LARGE(doc.coverI.toString()))
                    .placeholder(R.drawable.ic_broken_image_blue_48dp)
                    //.error(R.drawable.ic_search_green_24dp)
                    .tag(TAG)
                    .resize(width*8/10, height*8/10 )
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

    private fun loadImagePicasso(wish: Wish?, view: ImageView, progressBar: ProgressBar) {

        progressBar.visibility = View.VISIBLE

        val displayMetrics = activity.resources.displayMetrics
        val height = displayMetrics.heightPixels
        val width = displayMetrics.widthPixels


        if(!wish?.docCoverUrl.isNullOrEmpty()) {
            Picasso.get()
                    .load(wish?.docCoverUrl)
                    .placeholder(R.drawable.ic_broken_image_blue_48dp)
                    //.error(R.drawable.ic_search_green_24dp)
                    .tag(TAG)
                    .resize(width*8/10, height*8/10 )
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

    fun hideDialog() {
            dialog.dismiss()
    }

    companion object {
        private val TAG = ViewDialog::class.java.simpleName
    }
}
