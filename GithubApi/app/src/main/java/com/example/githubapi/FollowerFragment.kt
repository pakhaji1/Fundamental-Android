package com.example.githubapi

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubapi.adapter.FollowsAdapter
import com.example.githubapi.databinding.FragmentFollowBinding

class FollowerFragment : Fragment() {
    private var _binding: FragmentFollowBinding? = null
    private val binding get() = _binding!!
    private lateinit var mainViewModel: MainViewModel
    private var login: String? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentFollowBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        login = arguments?.getString(UserDetailActivity.EXTRA_LOGIN)

        mainViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[MainViewModel::class.java]
        mainViewModel.getFollowersData(login)
        mainViewModel.listFollowers.observe(viewLifecycleOwner) {
            if (it != null) {
                setFollowsData(it)
            }
        }
        mainViewModel.isLoading.observe(viewLifecycleOwner) { showLoading(it) }
    }

    private fun setFollowsData(listUser: List<User>) {
        val layoutManager = LinearLayoutManager(requireActivity())
        val followsAdapter = FollowsAdapter(listUser)

        binding.apply {
            rvItemFollows.layoutManager = layoutManager
            rvItemFollows.adapter = followsAdapter
        }
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressbar.visibility = View.VISIBLE
        } else {
            binding.progressbar.visibility = View.INVISIBLE
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}