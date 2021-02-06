package com.app.srestaurantapplication.ui.orderDescription

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.MediaController
import android.widget.TextView
import android.widget.VideoView
import androidx.fragment.app.Fragment
import com.app.srestaurantapplication.R
import com.app.srestaurantapplication.data.model.DishesDataDetails
import kotlinx.android.synthetic.main.fragment_description.*


class DescriptionFragment : Fragment() {
   var dishes: DishesDataDetails?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {


        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_description, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bundle = arguments
        val dishes: DishesDataDetails?=bundle!!.getParcelable("dishes")
        desc_fragmentDescription_textView.text = dishes!!.description

        ////video_view
        val mediaController = MediaController(activity)
        video_view.setVideoPath(dishes.image_url.plus(dishes.video))
        video_view.setMediaController(mediaController)
        video_view.start()
    }

}