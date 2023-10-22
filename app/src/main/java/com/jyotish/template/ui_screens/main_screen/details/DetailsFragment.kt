package com.jyotish.template.ui_screens.main_screen.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.jyotish.template.R
import com.jyotish.template.ui_screens.main_screen.DemoViewModel

class DetailsFragment : Fragment() {
    private val viewModel: DemoViewModel by activityViewModels()
    var oldPrice = 0
    var oldercent = 0
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.price.observe(viewLifecycleOwner) {price->
            //Use the price in view
            if (price>oldPrice) {
                //change color
            }
        }
        viewModel.percent.observe(viewLifecycleOwner) { percent->
            // USe the percent when update
            if (percent>oldercent) {
                //change color
            }
        }
    }

}