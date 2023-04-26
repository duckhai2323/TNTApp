package com.example.myapp1.api

import retrofit2.http.GET

interface ApiService {
    @GET("kenzouno1/DiaGioiHanhChinhVN/master/data.json")
    fun getData() :retrofit2.Call<MutableList<DiaGioiHanhChinhVN>>

}