package com.team4.sajochamchi.data.repository

import android.content.Context
import com.team4.sajochamchi.data.sharedpreferences.MySharedPreferences

class TotalRepositoryImpl(context: Context) : TotalRepository {
    private val mySharedPreferences: MySharedPreferences = MySharedPreferences(context)
}