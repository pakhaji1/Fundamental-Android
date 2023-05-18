package com.example.githubapi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.githubapi.adapter.SectionPagerAdapter
import com.example.githubapi.databinding.ActivityUserDetailBinding
import com.example.githubapi.favorite.UserFavoriteActivity
import com.example.githubapi.settings.SettingActivity
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class UserDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUserDetailBinding
    private lateinit var userViewModel: UserViewModel
    private lateinit var bundle: Bundle
    private var avatarUrl: String? = null
    private var login: String? = null
    private var id: Int? = null

    companion object {
        const val EXTRA_AVATAR = "EXTRA AVATAR"
        const val EXTRA_LOGIN = "EXTRA LOGIN"
        const val EXTRA_ID = "EXTRA ID"
        @StringRes
        private val TAB_ICONS = intArrayOf(
            R.drawable.ic_followers,
            R.drawable.ic_following
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityUserDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        avatarUrl = intent.getStringExtra(EXTRA_AVATAR)
        login = intent.getStringExtra(EXTRA_LOGIN)
        id = intent.getIntExtra(EXTRA_ID, 0)

        userViewModel = ViewModelProvider(this)[UserViewModel::class.java]
        userViewModel.isLoading.observe(this) { showLoading(it) }
        userViewModel.getUserDetail(login)
        userViewModel.userDetail.observe(this) { if(it != null) setDetailUser(it) }
        supportActionBar?.elevation = 0f

        bundle = Bundle().apply { putString(EXTRA_LOGIN, login) }

        supportActionBar?.elevation = 0f

        setPagerAdapter(bundle)
        verif()

    }

    private fun setPagerAdapter(bundle: Bundle){
        val sectionsPagerAdapter = SectionPagerAdapter(this@UserDetailActivity,bundle)
        val viewPager: ViewPager2 = binding.vp
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = binding.tabLayout
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.setIcon(TAB_ICONS[position])
        }.attach()

        supportActionBar?.elevation = 0f
    }

    private fun verif(){
        var verify: Boolean
        val fbFavorite = binding.fbFavoriteButton
        val verif = userViewModel.verifyUserFavorite(id!!)
        verify = verif > 0
        fbFavorite.setOnClickListener {
            verify = !verify
            if(verify) {
                fbFavorite.setImageDrawable(ContextCompat.getDrawable(fbFavorite.context, R.drawable.ic_favorites))
                userViewModel.addUserFavorite(id, login, avatarUrl)
            } else {
                fbFavorite.setImageDrawable(ContextCompat.getDrawable(fbFavorite.context, R.drawable.ic_favorites_delete))
                userViewModel.deleteUserFavorite(id)
            }
        }
    }

    private fun setDetailUser(user: ResponseUserDetail) {

        binding.apply {
            Glide.with(this@UserDetailActivity)
                .load(user.avatar_url)
                .apply(RequestOptions.circleCropTransform())
                .into(ivPhoto)
            tvNameUsers.text = user.name
            tvUserName.text = login
            tvFollower.text = user.followers.toString()
            tvFollowing.text = user.following.toString()
            followers.visibility = View.VISIBLE
            following.visibility = View.VISIBLE
        }
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
                val intent = Intent(this@UserDetailActivity, SettingActivity::class.java)
                startActivity(intent)
                true
            }

            R.id.favorite -> {
                val intent = Intent(this@UserDetailActivity, UserFavoriteActivity::class.java)
                startActivity(intent)
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }
}