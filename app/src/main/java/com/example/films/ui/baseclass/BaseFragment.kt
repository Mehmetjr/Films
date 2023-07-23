package com.example.films.ui.baseclass

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment

abstract class BaseFragment(@LayoutRes layoutRes : Int) : Fragment(layoutRes) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("screen_fragment",this::class.java.name)
    }

}