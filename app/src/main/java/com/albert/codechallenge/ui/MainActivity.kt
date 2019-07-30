package com.albert.codechallenge.ui

import android.app.Application
import android.content.SharedPreferences
import android.content.res.Configuration
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.albert.codechallenge.api.SearchService
import com.albert.codechallenge.dagger.NetworkMethodsImpl
import com.albert.codechallenge.model.Doc
import com.albert.codechallenge.model.Search
import com.google.gson.Gson
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.HttpException
import javax.inject.Inject
import android.text.Editable
import android.text.TextWatcher
import com.albert.codechallenge.R
import com.albert.codechallenge.Utils
import io.reactivex.subjects.BehaviorSubject
import java.util.concurrent.TimeUnit


class MainActivity : AppCompatActivity() {

    private lateinit var mySearchListAdapter: SearchListAdapter


    var searchQuery: String = ""
    var searchResult: String = ""

    var searchStart: Int  = 0
    var searchNumberFound = 0

    @Inject
    lateinit var _basicInterfaces: SearchService
    @Inject
    lateinit var _gson: Gson
    @Inject
    lateinit var _mApplication: Application
    @Inject
    lateinit var _mSharedPreferences: SharedPreferences

    @Inject
    lateinit var networkMethods: NetworkMethodsImpl

    @Inject
    lateinit var _mHttpLoggingInterceptor: HttpLoggingInterceptor

    // Subject holding the most recent user input
    var userInputSubject: BehaviorSubject<String> = BehaviorSubject.create()


    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mBundleSaveInstanceState = outState

    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        // Read values from the "savedInstanceState"-object and put them in your textview

    }


    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)

    }

    override fun onResume() {
        super.onResume()

    }

    public override fun onPause() {
        super.onPause()


    }


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        (application as SearchApplication).getActivityComponent().inject(this)

        val actionBar = supportActionBar
        actionBar?.setHomeButtonEnabled(false)
        actionBar?.setDisplayHomeAsUpEnabled(false)

        input_search_book.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                Log.e(TAG, "addTextChangedListener s: " + s)
                userInputSubject.onNext(s.toString())
            }
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            }
        })

        var disp:Disposable? = null
        userInputSubject
                .debounce(DEBOUNCE_DELAY, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(object : Observer<String> {
                    override fun onComplete() {
                        disp?.dispose()
                    }

                    override fun onNext(t: String) {
                        //getSearch(t, "1")
                        Log.e(TAG, "onNext t: " + t)
                        val searchQuery = t
                        if(t.isNotEmpty()) {
                            this@MainActivity.searchQuery = searchQuery
                            getSearch(t, "1")
                        }
                    }
                    override fun onError(e: Throwable) {
                        Log.e(TAG, "userInputSubject onError e: " + e.toString())
                    }
                    override fun onSubscribe(d: Disposable) {
                        disp = d
                    }
                }

                )

        input_search_book.setOnEditorActionListener() { v, actionId, event ->
            when (actionId){
            EditorInfo.IME_ACTION_DONE -> {
                val searchQuery = input_search_book.text.toString()
                if(searchQuery.isNotEmpty()) {
                    this.searchQuery = searchQuery
                    getSearch(searchQuery, "1")
                }
                Log.e(TAG, "IME_ACTION_DONE")
                false }
            else -> false
            }
        }

        chevron_left.setOnClickListener {

            if(searchStart != 0) {
                val left: Int = searchStart / 100
                Log.e(TAG, "chevron_left.setOnClickListener"  + " page: " + left.toString())
                getSearch(searchQuery, left.toString())
            }

        }

        chevron_right.setOnClickListener {

            if(searchStart + 100 < searchNumberFound ){
                val right: Int = searchStart / 100 + 2
                Log.e(TAG, "chevron_right.setOnClickListener"  + " page: " + right.toString())
                getSearch(searchQuery, right.toString())
            } else {
            }
        }

        recycle.setNestedScrollingEnabled(false)
        recycle.setHasFixedSize(true)
        recycle.setItemViewCacheSize(100)
        val layoutManager = LinearLayoutManager(this)
        recycle.setLayoutManager(layoutManager)
        mySearchListAdapter = SearchListAdapter(this)
        recycle.setAdapter(mySearchListAdapter)
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id: Int = item.itemId
        if (id == R.id.action_name) {

            val actionBar = supportActionBar
            actionBar?.setHomeButtonEnabled(true)
            actionBar?.setDisplayHomeAsUpEnabled(true)

            Log.e(TAG, "onOptionsItemSelected" )
            include_fragment.visibility = View.VISIBLE
            include_content_main.visibility = View.INVISIBLE
            val textFragment = WishFragment.newInstance(this@MainActivity)
            // Get the support fragment manager instance
            val manager = supportFragmentManager
            // Begin the fragment transition using support fragment manager
            val transaction = manager.beginTransaction()
            // Replace the fragment on container
            transaction.replace(R.id.include_fragment,textFragment)
            transaction.addToBackStack(null)
            // Finishing the transition
            transaction.commit()
            return true
        }
        if(id == android.R.id.home){

            include_fragment.visibility = View.INVISIBLE
            include_content_main.visibility = View.VISIBLE
            val actionBar = supportActionBar
            actionBar?.setHomeButtonEnabled(false)
            actionBar?.setDisplayHomeAsUpEnabled(false)

        }
        return super.onOptionsItemSelected(item)
    }

    private fun getSearch(searchQuery: String, page: String) {

        Utils.hideSoftKeyboard(this@MainActivity)

        Log.e(TAG, "getSearch start")

        if (networkMethods.isNetworkAvailable && searchQuery.isNotEmpty()) {

            Log.e(TAG, "getProducts start")
            //val callAuthor = _basicInterfaces.getAuthorSearch(searchQuery)
            //val callTitle = _basicInterfaces.getTitleSearch(searchQuery)
            val call = _basicInterfaces.getSearch(searchQuery, page)
            var dis: Disposable? = null
            call//.debounce(10000, TimeUnit.MILLISECONDS)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(object : Observer<Search> {
                        override fun onSubscribe(s: Disposable) {
                            dis = s
                            progressBar.visibility = View.VISIBLE
                            mySearchListAdapter.addDocs(mutableListOf<Doc>())
                        }

                        override fun onNext(search: Search) {

                            Log.e(TAG, " size: " + search.docs?.size + " start: " + search.start.toString() )
                            progressBar.visibility = View.INVISIBLE

                            mySearchListAdapter.addDocs(search?.docs?.toMutableList()?: mutableListOf<Doc>())

                            val numbers = search.start?.toInt()?.plus(search.docs?.size?:0)
                            searchStart = search.start?.toInt()?:0
                            searchNumberFound = search.numFound?.toInt()?:0

                            search_count.setText(getResources().getString(R.string.search_result, search.start.toString(), numbers.toString(),search.numFound.toString() ))
                            //search_count.setText("Start from #: " + search.start + " Shown: " + numbers +  " Found: "  + search.numFound)
                             //Log.e(TAG, " size: " + search.docs?.size + " search name: " + search.docs?.get(0)?.title)
                        }

                        override fun onComplete() {
                            progressBar.visibility = View.INVISIBLE
                            recycle.scrollToPosition(0)
                            if (dis != null && !(dis?.isDisposed()?: true)) {
                                dis?.dispose()
                            }
                        }

                        override fun onError(e: Throwable) {
                            if (e is HttpException) {
                                val code = e.code()
                                Log.e(TAG, "getSearch onError: response.code() " + code + " message: " + e.message())
                                Toast.makeText(this@MainActivity, "Network error, try to repeat request", Toast.LENGTH_LONG).show()
                            } else {
                                Log.e(TAG, "getSearch onError: response.code() " + e.toString())
                            }
                        }

                    })
        } else {
            Toast.makeText(this@MainActivity, " Network unreachable ", Toast.LENGTH_LONG).show()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    companion object {
        const val DEBOUNCE_DELAY: Long = 2000L
        val TAG = MainActivity::class.java.getSimpleName()
        private var mBundleConfigurationChangedState: Bundle? = null// = new Bundle();
        private var mBundleSaveInstanceState: Bundle? = null// = new Bundle();
    }


}
