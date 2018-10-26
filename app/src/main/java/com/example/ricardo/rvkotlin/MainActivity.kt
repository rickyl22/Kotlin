package com.example.ricardo.rvkotlin

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.widget.Toast

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.android.schedulers.AndroidSchedulers

import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.Retrofit



import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), RecipeAdapter.Listener {

    private val TAG = MainActivity::class.java.simpleName

    private val BASE_URL = "http://www.recipepuppy.com"

    private var mCompositeDisposable: CompositeDisposable? = null

    private var mAndroidArrayList: ArrayList<Recipe>? = null

    private var mAdapter: RecipeAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mCompositeDisposable = CompositeDisposable()

        initRecyclerView()

        loadJSON()
    }

    private fun initRecyclerView() {

        rv_recipes.setHasFixedSize(true)
        val layoutManager : RecyclerView.LayoutManager = LinearLayoutManager(this)
        rv_recipes.layoutManager = layoutManager
    }

    private fun loadJSON() {

        val requestInterface = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(RecipeAPI::class.java)

        mCompositeDisposable?.add(requestInterface.getData()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponse, this::handleError))

    }

    private fun handleResponse(androidList: JSONResponse) {

        mAndroidArrayList = ArrayList(androidList.results)
        mAdapter = RecipeAdapter(mAndroidArrayList!!, this)

        rv_recipes.adapter = mAdapter
    }

    private fun handleError(error: Throwable) {

        Log.d(TAG, error.localizedMessage)

        Toast.makeText(this, "Error ${error.localizedMessage}", Toast.LENGTH_SHORT).show()
    }

    override fun onItemClick(android: Recipe) {

        Toast.makeText(this, "${android.title} Clicked !", Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        mCompositeDisposable?.clear()
    }
}