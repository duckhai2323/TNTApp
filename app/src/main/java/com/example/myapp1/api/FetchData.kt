package com.example.myapp1.api

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class FetchData {
    var listDiaGioiHanhChinh: MutableList<DiaGioiHanhChinhVN> = mutableListOf()
    val gson: Gson
        get() = GsonBuilder()
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
            .create()

    private val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("https://raw.githubusercontent.com/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

    private val apiService = retrofit.create(ApiService::class.java)

    fun fetchData(): MutableList<DiaGioiHanhChinhVN> {
        apiService.getData().enqueue(object : retrofit2.Callback<MutableList<DiaGioiHanhChinhVN>> {
            override fun onResponse(
                call: Call<MutableList<DiaGioiHanhChinhVN>>?,
                response: Response<MutableList<DiaGioiHanhChinhVN>>?
            ) {
                if (response != null) {
                    listDiaGioiHanhChinh = response.body()
                }
            }

            override fun onFailure(call: Call<MutableList<DiaGioiHanhChinhVN>>?, t: Throwable?) {

            }
        })
        return listDiaGioiHanhChinh
    }
}