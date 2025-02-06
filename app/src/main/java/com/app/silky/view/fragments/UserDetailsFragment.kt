package com.app.silky.view.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.app.silky.R
import com.app.silky.databinding.FragmentArticleDetailsBinding
import com.app.silky.viewModel.OfflineViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserDetailsFragment : Fragment(R.layout.fragment_article_details) {

    val viewModel: OfflineViewModel by viewModels()

    private lateinit var binding: FragmentArticleDetailsBinding
    val args: UserDetailsFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val users = args.userData
        binding = FragmentArticleDetailsBinding.bind(view)

        binding.toolbarDetails.setNavigationOnClickListener{
            activity?.onBackPressed()
        }


        binding.tvUserDetails.text = "First name: ${users?.username}" +
                "\nLast name: ${users?.name}" +
                "\nCompany: ${users?.company?.name}"  +
                "\nPhone: ${users?.phone}"  +
                "\nEmail: ${users?.email}"

        binding.fab.setOnClickListener {
            if (users != null) {
                viewModel.insertArticle(users)
            }
            Snackbar.make(binding.root, "User Saved !", Snackbar.LENGTH_LONG).show()
        }


    }
}