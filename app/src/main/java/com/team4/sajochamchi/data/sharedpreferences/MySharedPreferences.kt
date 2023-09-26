package com.team4.sajochamchi.data.sharedpreferences

import android.content.Context
import android.content.SharedPreferences
import com.team4.sajochamchi.R

class MySharedPreferences(context: Context) {
    companion object {
        //private const val KEY = ""
    }

    private val mySharedPreferences: SharedPreferences =
        context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE)

    /*fun getListPefs(): List<SampleItem> {
        val stringPrefs = mySharedPreferences.getString(KEY, null)
        return if (stringPrefs != null && stringPrefs != "[]") {
            GsonBuilder().create().fromJson(
                stringPrefs,
                object : TypeToken<ArrayList<SampleItem>>() {}.type
            )
        } else listOf()
    }*/

    /*fun saveListPrefs(list: List<SampleItem>){
        val arrayList = arrayListOf<ClipData.Item>().apply {
            addAll(list)
        }
        val mEditPrefs = mySharedPreferences.edit()
        val stringPrefs = GsonBuilder().create().toJson(
            arrayList,
            object : TypeToken<ArrayList<SampleItem>>() {}.type
        )
        mEditPrefs.putString(KEY,stringPrefs).apply()
    }*/
}