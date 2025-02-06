package com.app.silky.view.fragments

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.silky.R
import com.app.silky.databinding.FragmentOnlineBinding
import com.app.silky.model.Users
import com.app.silky.utils.DataHandler
import com.app.silky.utils.LogData
import com.app.silky.view.adapter.NewsAdapter
import com.app.silky.viewModel.OnlineViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class OnlineFragment : Fragment(R.layout.fragment_online) {

    private lateinit var binding: FragmentOnlineBinding

    @Inject
    lateinit var newsAdapter: NewsAdapter

    val viewModel: OnlineViewModel by viewModels()
    var mainList: MutableList<Users>? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentOnlineBinding.bind(view)
        init()

        viewModel.userList.observe(viewLifecycleOwner) { dataHandler ->
            when (dataHandler) {
                is DataHandler.SUCCESS -> {
                    binding.progressBar.visibility = View.GONE
                    mainList = dataHandler.data?.toMutableList()
                    newsAdapter.differ.submitList(mainList)
                }
                is DataHandler.ERROR -> {
                    binding.progressBar.visibility = View.GONE
                    LogData("onViewCreated: ERROR " + dataHandler.message)
                }
                is DataHandler.LOADING -> {
                    binding.progressBar.visibility = View.VISIBLE
                    LogData("onViewCreated: LOADING..")

                }
            }

        }
        viewModel.getUserList()

    }

    private fun init() {

        val closeBtnId = binding.searchSrcText.context.resources
            .getIdentifier("android:id/search_close_btn", null, null)
        val closeBtn = binding.searchSrcText.findViewById<ImageView>(closeBtnId)

        closeBtn?.setOnClickListener {
            binding.searchSrcText.onActionViewCollapsed()
            newsAdapter.differ.submitList(mainList)
        }

        binding.searchSrcText.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {

                val searchList = mainList?.filter {
                    it.name.contains(newText.toString(), ignoreCase = true) ||
                            it.username.contains(newText.toString(), ignoreCase = true) ||
                            it.phone.contains(newText.toString(), ignoreCase = true) ||
                            it.email.contains(newText.toString(), ignoreCase = true)

                }

                if (searchList.isNullOrEmpty()) {
                    Toast.makeText(context, "No Match found", Toast.LENGTH_LONG).show()
                    newsAdapter.differ.submitList(mainList)
                } else {
                    newsAdapter.differ.submitList(searchList)
                }
                return false
            }
        })



        binding.toolbarOnline.setNavigationOnClickListener {
            activity?.onBackPressed()
        }

        binding.recyclerView.addItemDecoration(
            DividerItemDecoration(
                context,
                LinearLayoutManager.VERTICAL
            )
        )

        newsAdapter.onArticleClicked {
            val bundle = Bundle().apply {
                putParcelable("user_data", it)

            }
            findNavController().navigate(
                R.id.action_onlineFragment_to_articleDetailsFragment,
                bundle
            )
        }

        binding.recyclerView.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(activity)
        }

    }
}