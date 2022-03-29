package com.example.androiddata.ui.main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.androiddata.data.Monster
import com.example.androiddata.databinding.MainFragmentBinding

class MainFragment : Fragment(), MonsterGridViewAdapter.MonsterItemListener {

    private lateinit var binding: MainFragmentBinding

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.i("LOG_TAG", "onCreateView: ")
        binding = MainFragmentBinding.inflate(layoutInflater)
        val recyclerView = binding.recyclerView
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        val swipeLayout = binding.swipeLayout.apply {
             setOnRefreshListener {
                 viewModel.refreshData()
             }
         }

        viewModel.monsterData.observe(viewLifecycleOwner, Observer {
            recyclerView.adapter = MonsterGridViewAdapter(it, this)
            swipeLayout.isRefreshing = false
        })


        return binding.root
    }

    override fun onMonsterItemClick(monster: Monster) {
        Toast.makeText(context, monster.name, Toast.LENGTH_SHORT).show()
    }


}
