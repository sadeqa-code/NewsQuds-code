package com.sadeqa.jamal.newsquds.api

import android.content.Context
import androidx.lifecycle.LiveData
import com.sadeqa.jamal.newsquds.model.News
import com.sadeqa.jamal.newsquds.model.NewsResponse
import com.sadeqa.jamal.newsquds.api.RetrofitInstance.Companion.api
import com.sadeqa.jamal.newsquds.db.NewsDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object Process {

    public fun getBreakingNews(countryCode: String, pageNumber: Int
                               ,onResponse: ((NewsResponse?) -> Unit)
                               ,onFailure: ((String?) -> Unit)) {
        api.getBreakingNews(countryCode,pageNumber).enqueue(object : Callback<NewsResponse> {
            override fun onResponse(call: Call<NewsResponse>?, response: Response<NewsResponse>?) {
                onResponse(response?.body())
            }

            override fun onFailure(call: Call<NewsResponse>?, t: Throwable?) {
                onFailure(t?.message)
            }
        })
    }


    public fun searchNews(searchQuery: String, pageNumber: Int
                               ,onResponse: ((NewsResponse?) -> Unit)
                               ,onFailure: ((String?) -> Unit)) {
        api.searchForNews(searchQuery,pageNumber).enqueue(object : Callback<NewsResponse> {
            override fun onResponse(call: Call<NewsResponse>?, response: Response<NewsResponse>?) {
                onResponse(response?.body())
            }

            override fun onFailure(call: Call<NewsResponse>?, t: Throwable?) {
                onFailure(t?.message)
            }
        })
    }

    public fun insertNews(context : Context,news: News){
        GlobalScope.launch(Dispatchers.IO) {
            NewsDatabase(context).getNewsDao().insertNews(news)
        }
    }
    public fun getAllNews(context : Context) : LiveData<List<News>> {
       return NewsDatabase(context).getNewsDao().getAllNews()
    }
    public fun deleteNews(context : Context,news: News){
        GlobalScope.launch(Dispatchers.IO) {
            NewsDatabase(context).getNewsDao().deleteNews(news)
        }
    }
}