package com.app.silky.view.fragments

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.app.silky.databinding.FragmentHomeBinding
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.GraphRequest
import com.facebook.GraphResponse
import com.facebook.login.LoginResult
import dagger.hilt.android.AndroidEntryPoint
import org.json.JSONException
import org.json.JSONObject


@AndroidEntryPoint
class HomeFragment : Fragment(com.app.silky.R.layout.fragment_home) {

    lateinit var binding: FragmentHomeBinding
    val callbackManager = CallbackManager.Factory.create()
    val facebookPermissionList = listOf<String>("email")
    val EMAIL: String = "email"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentHomeBinding.bind(view)

        binding.btnOnline.setOnClickListener {
            findNavController().navigate(com.app.silky.R.id.action_homeFragment_to_onlineFragment)
        }

        binding.btnOffline.setOnClickListener {
            findNavController().navigate(com.app.silky.R.id.action_homeFragment_to_offLineFragment)
        }

        val accessToken: AccessToken? = AccessToken.getCurrentAccessToken()
        val isLoggedIn = accessToken != null && !accessToken.isExpired

        if (isLoggedIn) {
            accessToken?.let {
                useLoginInformation(it)
            }
        } else {
            Toast.makeText(context, "User not logged In", Toast.LENGTH_SHORT).show()

        }

        binding.loginButton.setPermissions(EMAIL)
        binding.loginButton.setFragment(this)

        binding.loginButton.registerCallback(callbackManager, object : FacebookCallback<LoginResult> {
            override fun onSuccess(loginResult: LoginResult) {
                Toast.makeText(context, "Successfully logged in", Toast.LENGTH_SHORT).show()
                useLoginInformation(loginResult.accessToken)
            }

            override fun onCancel() {
                Toast.makeText(context, "Cancelled", Toast.LENGTH_SHORT).show()
            }

            override fun onError(error: FacebookException) {
                Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show()
            }
        })

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        callbackManager.onActivityResult(requestCode, resultCode, data)
    }


    private fun useLoginInformation(accessToken: AccessToken) {

        val request = GraphRequest.newMeRequest(
            accessToken
        ) { obj, response ->
            try {
                val email = obj!!.getString("email")
                val firstName = obj.getString("first_name")
                val lastName = obj.getString("last_name")

                Toast.makeText(context, "Email: $email\n First name: $firstName", Toast.LENGTH_SHORT).show()
            } catch (e: JSONException) {
                Toast.makeText(
                    context,
                    "Facebook Authentication Failed.",
                    Toast.LENGTH_LONG
                ).show()
            }
        }

        val parameters = Bundle()
        parameters.putString("fields", "email,first_name,last_name")
        request.parameters = parameters
        request.executeAsync()

    }
}



