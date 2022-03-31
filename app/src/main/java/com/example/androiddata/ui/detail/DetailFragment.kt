package com.example.androiddata.ui.detail

import android.app.AppComponentFactory
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.androiddata.R
import com.example.androiddata.databinding.FragmentDetailBinding
import com.example.androiddata.ui.shared.SharedViewModel
import com.example.androiddata.utils.LOG_TAG


/**
 * A simple [Fragment] subclass.
 * Use the [DetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DetailFragment : Fragment() {

    private lateinit var viewModel: SharedViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(requireActivity())[SharedViewModel::class.java]
        viewModel.selectedMonster.observe(viewLifecycleOwner, Observer {
            Log.i(LOG_TAG, "onCreateView: ${it.name}")
        })
        // Inflate the layout for this fragment
        val binding = FragmentDetailBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = this@DetailFragment
            viewModel = this@DetailFragment.viewModel
        }

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        (requireActivity() as AppCompatActivity).run {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
    }

}