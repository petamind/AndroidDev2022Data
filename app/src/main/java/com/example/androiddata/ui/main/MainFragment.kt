package com.example.androiddata.ui.main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.androiddata.databinding.MainFragmentBinding

class MainFragment : Fragment() {

    private lateinit var binding: MainFragmentBinding
    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.i("LOG_TAG", "onCreateView: ")
        binding = MainFragmentBinding.inflate(layoutInflater)
        val recyclerView = binding.recyclerView

        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        viewModel.monsterData.observe(viewLifecycleOwner, Observer {
            recyclerView.adapter = MonsterGridViewAdapter(it)
        })


        return binding.root
    }


}
