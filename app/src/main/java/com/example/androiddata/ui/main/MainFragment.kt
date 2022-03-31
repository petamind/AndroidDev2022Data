package com.example.androiddata.ui.main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.androiddata.R
import com.example.androiddata.data.Monster
import com.example.androiddata.databinding.MainFragmentBinding
import com.example.androiddata.ui.shared.SharedViewModel

class MainFragment : Fragment(), MonsterGridViewAdapter.MonsterItemListener {

    private lateinit var binding: MainFragmentBinding
    private lateinit var navController: NavController
    private lateinit var viewModel: SharedViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.i("LOG_TAG", "onCreateView: ")

        binding = MainFragmentBinding.inflate(layoutInflater)
        val recyclerView = binding.recyclerView


        viewModel = ViewModelProvider(requireActivity())[SharedViewModel::class.java]
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

    override fun onResume() {
        super.onResume()

        navController = Navigation.findNavController(requireActivity(), R.id.nav_host)
        (requireActivity() as AppCompatActivity).run {
            supportActionBar?.setDisplayHomeAsUpEnabled(false)
        }
        setHasOptionsMenu(true)
    }

    override fun onMonsterItemClick(monster: Monster) {
        Toast.makeText(context, monster.name, Toast.LENGTH_SHORT).show()
        viewModel.selectedMonster.value = monster
        navController.navigate(R.id.action_mainFragment_to_detailFragment)
    }


}
