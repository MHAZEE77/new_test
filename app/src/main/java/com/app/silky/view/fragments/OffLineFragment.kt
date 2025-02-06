package com.app.silky.view.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.silky.R
import com.app.silky.databinding.FragmentOfflineBinding
import com.app.silky.utils.DataHandler
import com.app.silky.utils.LogData
import com.app.silky.view.adapter.NewsAdapter
import com.app.silky.viewModel.OfflineViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class OffLineFragment : Fragment(R.layout.fragment_offline) {

    lateinit var binding: FragmentOfflineBinding
    val viewModel: OfflineViewModel by viewModels()

    @Inject
    lateinit var newsAdapter: NewsAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentOfflineBinding.bind(view)
        init()

        viewModel.userList.observe(viewLifecycleOwner, Observer { dataHandler ->
            when (dataHandler) {
                is DataHandler.SUCCESS -> {
                    LogData("onViewCreated: SUCCESS  ${dataHandler.data?.get(0)?.name} ")
                    newsAdapter.differ.submitList(dataHandler.data?.toMutableList())
                }
                is DataHandler.ERROR -> {
                    LogData("onViewCreated: ERROR ${dataHandler.message}")
                }
                is DataHandler.LOADING -> {
                    LogData("onViewCreated: LOADING")
                }
            }
        })
    }

    private fun init() {

        binding.toolbarOffline.setNavigationOnClickListener{
            activity?.onBackPressed()
        }

        binding.recyclerView.addItemDecoration(
            DividerItemDecoration(
                context,
                LinearLayoutManager.VERTICAL
            )
        )

        binding.recyclerView.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }
}