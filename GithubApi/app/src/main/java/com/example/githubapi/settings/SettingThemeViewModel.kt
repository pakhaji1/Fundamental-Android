package com.example.githubapi.settings

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class SettingThemeViewModel(private val pref: SettingTheme) : ViewModel() {

    fun themeSetting(): LiveData<Boolean> {
        return pref.getThemeSetting().asLiveData()
    }

    fun themeSetting(isDarkModeActive: Boolean) {
        viewModelScope.launch {
            pref.saveThemeSetting(isDarkModeActive)
        }
    }
}