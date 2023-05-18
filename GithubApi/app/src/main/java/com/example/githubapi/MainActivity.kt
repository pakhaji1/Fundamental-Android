package com.example.githubapi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubapi.adapter.UserAdapter
import com.example.githubapi.databinding.ActivityMainBinding
import com.example.githubapi.favorite.UserFavoriteActivity
import com.example.githubapi.settings.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var mainViewModel: MainViewModel
    private lateinit var adapter: UserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val btSearch = binding.search
        mainViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[MainViewModel::class.java]
        btSearch.setIconifiedByDefault(false)
        btSearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) mainViewModel.getUser(query)
                return true
            }

            override fun onQueryTextChange(text: String?): Boolean {
                if (text != null) mainViewModel.getUser(text)
                return true
            }
        })

        mainViewModel.listUsers.observe(this) { user -> setUserData(user) }
        mainViewModel.isLoading.observe(this) { showLoading(it) }

        val settingPreferences = SettingTheme.getInstance(dataStore)
        val settingPreferencesViewModel = ViewModelProvider(this, SettingThemeViewModelFactory(settingPreferences))[SettingThemeViewModel::class.java]

        settingPreferencesViewModel.themeSetting().observe(this){ isDarkModeActive: Boolean ->
            if (isDarkModeActive){
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            }
            else{
                btSearch.setBackgroundColor(ContextCompat.getColor(btSearch.context, R.color.transparan))
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }
    }

    private fun setUserData(listUser: List<User>) {

        val layoutManager = LinearLayoutManager(this)
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        adapter = UserAdapter(listUser, object: UserAdapter.OnItemClickListener {
            override fun onClick(avatarUrl: String, login: String, id: Int) {
                val intent = Intent(this@MainActivity, UserDetailActivity::class.java).apply {
                    putExtra(UserDetailActivity.EXTRA_LOGIN, login)
                    putExtra(UserDetailActivity.EXTRA_AVATAR, avatarUrl)
                    putExtra(UserDetailActivity.EXTRA_ID, id)
                }
                startActivity(intent)
            }
        })
        binding.rvList.layoutManager = layoutManager
        binding.rvList.addItemDecoration(itemDecoration)
        binding.rvList.adapter = adapter
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressbar.visibility = View.VISIBLE
        } else {
            binding.progressbar.visibility = View.INVISIBLE
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_setting, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.setting -> {
                val intent = Intent(this@MainActivity, SettingActivity::class.java)
                startActivity(intent)
                true
            }

            R.id.favorite -> {
                val intent = Intent(this@MainActivity, UserFavoriteActivity::class.java)
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}