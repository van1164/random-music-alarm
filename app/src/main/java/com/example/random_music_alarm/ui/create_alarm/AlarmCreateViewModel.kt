package com.example.random_music_alarm.ui.create_alarm

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class ClockUiState(
    val hour : String ="0",
    val minute : String ="10",
    val ampm : String = "AM" ,
)

data class AlarmUiState(

    val mondaySelected: Boolean = false,
    val tuesdaySelected: Boolean = false,
    val wednesdaySelected: Boolean = false,
    val thursdaySelected: Boolean = false,
    val fridaySelected: Boolean = false,
    val saturdaySelected: Boolean = false,
    val sundaySelected: Boolean = false,
)

enum class Day{
    MONDAY,
    TUESDAY,
    WEDNESDAY,
    THURSDAY,
    FRIDAY,
    SATURDAY,
    SUNDAY
}

class AlarmCreateViewModel : ViewModel() {

    // Expose screen UI state
    private val _uiState = MutableStateFlow(AlarmUiState())
    val uiState: StateFlow<AlarmUiState> = _uiState.asStateFlow()


    private val _clockUiState = MutableStateFlow(ClockUiState())
    val clockUiState: StateFlow<ClockUiState> = _clockUiState.asStateFlow()
    // Handle business logic

    fun changeTime(hour: String,minute: String,ampm: String){
        _clockUiState.update { currentState ->

            currentState.copy(
                hour = hour,
                minute = minute,
                ampm = ampm
            )
        }
    }

    fun selectDay(selectedItem : Day) {
        var mondaySelected = _uiState.value.mondaySelected
        var tuesdaySelected = _uiState.value.tuesdaySelected
        var wednesdaySelected  = _uiState.value.wednesdaySelected
        var thursdaySelected = _uiState.value.thursdaySelected
        var fridaySelected = _uiState.value.fridaySelected
        var saturdaySelected  = _uiState.value.saturdaySelected
        var sundaySelected = _uiState.value.sundaySelected
        when(selectedItem){
            Day.MONDAY -> {
                mondaySelected= !mondaySelected
            }
            Day.TUESDAY ->{
                tuesdaySelected = !tuesdaySelected
            }
            Day.WEDNESDAY ->{
                wednesdaySelected = !wednesdaySelected
            }
            Day.THURSDAY ->{
                thursdaySelected = !thursdaySelected
            }
            Day.FRIDAY ->{
                fridaySelected = !fridaySelected
            }
            Day.SATURDAY ->{
                saturdaySelected = !saturdaySelected
            }
            Day.SUNDAY ->{
                sundaySelected = !sundaySelected
            }
        }
        _uiState.update { currentState ->

            currentState.copy(
                mondaySelected = mondaySelected,
                tuesdaySelected = tuesdaySelected,
                wednesdaySelected = wednesdaySelected,
                thursdaySelected = thursdaySelected,
                fridaySelected = fridaySelected,
                saturdaySelected =saturdaySelected,
                sundaySelected = sundaySelected
            )
        }
    }
}