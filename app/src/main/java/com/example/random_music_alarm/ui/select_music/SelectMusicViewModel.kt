package com.example.random_music_alarm.ui.select_music

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update


data class MusicState(
        val playedIndex : Int? =null,
        val selectedIndex : Int? = null
)

class SelectMusicViewModel  : ViewModel(){
        private val _uiState = MutableStateFlow(MusicState())
        val uiState: StateFlow<MusicState> = _uiState.asStateFlow()

        fun selectMusic(index : Int){
                if(_uiState.value.selectedIndex == index) {
                        _uiState.update {
                                it.copy(
                                        selectedIndex = null
                                )
                        }
                }
                else{
                        _uiState.update {
                                it.copy(
                                        selectedIndex = index
                                )
                        }
                }
        }

        fun playMusic(index : Int){

                if(_uiState.value.playedIndex == index) {
                        _uiState.update {
                                it.copy(
                                        playedIndex = null
                                )
                        }
                }
                else{
                        _uiState.update {
                                it.copy(
                                        playedIndex = index
                                )
                        }
                }
        }

}