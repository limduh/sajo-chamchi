package com.team4.sajochamchi.data.sharedpreferences

import android.content.ClipData
import android.content.Context
import android.content.SharedPreferences
import android.provider.Contacts.SettingsColumns.KEY
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.team4.sajochamchi.R
import com.team4.sajochamchi.data.model.SaveCategory

class MySharedPreferences(context: Context) {
    companion object {
        private const val CATEGORY_KEY = "CATEGRORY_KEY"
        private const val SEARCH_HISTORY_KEY = "SEARCH_HISTORY_KEY"

        private const val SAVE_NAME = "SAVE_NAME"
        private const val SAVE_DESCRIPTION = "SAVE_DESCRIPTION"
    }

    private val mySharedPreferences: SharedPreferences =
        context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE)

    fun getCateoryListPefs(): List<SaveCategory> {
        val stringPrefs = mySharedPreferences.getString(CATEGORY_KEY, null)
        return if (stringPrefs != null && stringPrefs != "[]") {
            GsonBuilder().create().fromJson(
                stringPrefs,
                object : TypeToken<ArrayList<SaveCategory>>() {}.type
            )
        } else listOf()
    }

    fun saveCateoryListPrefs(list: List<SaveCategory>) {
        val arrayList = arrayListOf<SaveCategory>().apply {
            addAll(list)
        }
        val mEditPrefs = mySharedPreferences.edit()
        val stringPrefs = GsonBuilder().create().toJson(
            arrayList,
            object : TypeToken<ArrayList<SaveCategory>>() {}.type
        )
        mEditPrefs.putString(CATEGORY_KEY, stringPrefs).apply()
    }

    fun getSearchHistoryListPefs(): List<String> {
        val stringPrefs = mySharedPreferences.getString(SEARCH_HISTORY_KEY, null)
        return if (stringPrefs != null && stringPrefs != "[]") {
            GsonBuilder().create().fromJson(
                stringPrefs,
                object : TypeToken<ArrayList<String>>() {}.type
            )
        } else listOf()
    }

    fun saveSearchHistoryListPrefs(list: List<String>) {
        val arrayList = arrayListOf<String>().apply {
            addAll(list)
        }
        val mEditPrefs = mySharedPreferences.edit()
        val stringPrefs = GsonBuilder().create().toJson(
            arrayList,
            object : TypeToken<ArrayList<String>>() {}.type
        )
        mEditPrefs.putString(SEARCH_HISTORY_KEY, stringPrefs).apply()
    }

    fun getNamePrefs(): String {
        val stringPrefs = mySharedPreferences.getString(SAVE_NAME, null)
        return stringPrefs.orEmpty()
    }

    fun getDiscriptionPrefs(): String {
        val stringPrefs = mySharedPreferences.getString(SAVE_DESCRIPTION, null)
        return stringPrefs.orEmpty()
    }

    fun saveNamePrefs(str: String) {
        mySharedPreferences.edit().putString(SAVE_NAME, str).apply()
    }

    fun saveDescriptionPrfs(str: String) {
        mySharedPreferences.edit().putString(SAVE_DESCRIPTION, str).apply()
    }
}