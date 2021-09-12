package com.example.nasaapi.view

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.example.nasaapi.R
import com.example.nasaapi.model.EquilateralImageView
import com.example.nasaapi.model.appstate.PODLiveData
import com.example.nasaapi.viewmodel.PictureOfTheDayViewModel
import kotlinx.android.synthetic.main.picture_of_the_day_fragment.*

class PictureOfTheDayFragment : Fragment() {

    private lateinit var viewModel: PictureOfTheDayViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewModel = ViewModelProvider(this).get(PictureOfTheDayViewModel::class.java)
        viewModel.getData().observe(viewLifecycleOwner, Observer { renderData(it) })

        return inflater.inflate(R.layout.picture_of_the_day_fragment, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    private fun renderData(state: PODLiveData?) {
        when (state) {
            is PODLiveData.Success -> {
                val serverResponseData = state.serverResponseData
                val url = serverResponseData.url
                if (url.isNullOrEmpty()) {
                    // обработать ошибку
                } else {
                    // отобразить фото
                    Glide
                        .with(image_view)
                        .load(url)
                        .into(image_view)
                }
            }
            is PODLiveData.Loading -> {

            }
            is PODLiveData.Error -> {

            }
        }
    }

    companion object {
        fun newInstance() = PictureOfTheDayFragment()
    }

}