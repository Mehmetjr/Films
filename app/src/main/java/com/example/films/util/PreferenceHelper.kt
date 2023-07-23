package com.example.films.util

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import okhttp3.internal.cache2.Relay.Companion.edit
import java.lang.reflect.Type

class PreferenceHelper constructor(val context: Context) {


    val sharedPreferences by lazy {
        context.getSharedPreferences("Fav", Context.MODE_PRIVATE)
    }


    val gson by lazy(LazyThreadSafetyMode.NONE) {
        Gson()
    }


    private inline fun SharedPreferences.edit(operation: (SharedPreferences.Editor) -> Unit) {
        val editor = this.edit()
        operation(editor)
        editor.apply()
    }

    fun saveOrDelete(key: String, value: Any?) {
        sharedPreferences.edit(){

            when (value) {

                null -> it.remove(key)
                is String? -> it.putString(key, value)
                is Int -> it.putInt(key, value)
                is Boolean -> it.putBoolean(key, value)
                is Float -> it.putFloat(key, value)
                is Long -> it.putLong(key, value)
                else -> it.putString(key, gson.toJson(value))
            }
        }

    }

    @Suppress("unchecked_cast")
    fun <T> get(key: String, type: Type, defaultValue: T? = null): T? {
        sharedPreferences.apply {
            return when (type) {
                String::class -> getString(key, defaultValue as? String ?: "") as? T?
                Int::class -> getInt(key, defaultValue as? Int ?: -1) as? T?
                Boolean::class -> getBoolean(key, defaultValue as? Boolean ?: false) as? T?
                Float::class -> getFloat(key, defaultValue as? Float ?: -1f) as? T?
                Long::class -> getLong(key, defaultValue as? Long ?: -1) as? T?
                else -> {
                    val json = getString(key, defaultValue as? String)
                    try {
                        gson.fromJson(json, type)
                    } catch (e: JsonSyntaxException) {
                        null
                    }
                }
            }
        }
    }

    inline fun <reified T> getData(key: String, defaultValue: T? = null): T? {
        sharedPreferences.apply {
            return when (defaultValue) {
                is String -> getString(key, defaultValue as? String ?: "") as? T?
                is Int -> getInt(key, defaultValue as? Int ?: -1) as? T?
                is Boolean -> getBoolean(key, defaultValue as? Boolean ?: false) as? T?
                is Float -> getFloat(key, defaultValue as? Float ?: -1f) as? T?
                is Long -> getLong(key, defaultValue as? Long ?: -1) as? T?
                else -> {
                    val json = getString(key, defaultValue as? String)
                    try {
                        gson.fromJson(json, T::class.java)
                    } catch (e: JsonSyntaxException) {
                        null
                    }
                }
            }
        }
    }


}
