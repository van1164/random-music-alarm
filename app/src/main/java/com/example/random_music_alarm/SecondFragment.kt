package com.example.random_music_alarm

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.icu.util.Calendar
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import android.widget.Toolbar
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.random_music_alarm.databinding.FragmentSecondBinding
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.MaterialTimePicker.INPUT_MODE_KEYBOARD
import com.google.android.material.timepicker.TimeFormat
import kotlinx.coroutines.launch

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment() {

    private var _binding: FragmentSecondBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    val viewModel: AlarmCreateViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root

    }

    @SuppressLint("ScheduleExactAlarm", "ObsoleteSdkInt", "UnsafeRepeatOnLifecycleDetector",
        "SetTextI18n"
    )
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.clockUiState.collect{clockUiState->
                    binding.hour.text = clockUiState.hour
                    binding.miniute.text = clockUiState.minute
                    binding.ampmText.text = clockUiState.ampm
                }
            }

            }
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {

                viewModel.uiState.collect { alarmUiState->
                    if (alarmUiState.mondaySelected) {
                        binding.mondayButton.setImageResource(R.drawable.selected_monday_button)
                    } else {
                        binding.mondayButton.setImageResource(R.drawable.monday_button)
                    }

                    if (alarmUiState.tuesdaySelected) {
                        binding.tuesdayButton.setImageResource(R.drawable.selected_tuesday_button)
                    } else {
                        binding.tuesdayButton.setImageResource(R.drawable.tuesday_button)
                    }

                    if (alarmUiState.wednesdaySelected) {
                        binding.wednesdayButton.setImageResource(R.drawable.selected_wednes_day)
                    } else {
                        binding.wednesdayButton.setImageResource(R.drawable.wednesday_button)
                    }

                    if (alarmUiState.thursdaySelected) {
                        binding.thursdayButton.setImageResource(R.drawable.selected_tuesday_button)
                    } else {
                        binding.thursdayButton.setImageResource(R.drawable.tuesday_button)
                    }

                    if (alarmUiState.fridaySelected) {
                        binding.fridayButton.setImageResource(R.drawable.selected_friday_button)
                    } else {
                        binding.fridayButton.setImageResource(R.drawable.friday_button)
                    }

                    if (alarmUiState.saturdaySelected) {
                        binding.saturdayButton.setImageResource(R.drawable.selected_sat_sunday_button)
                    } else {
                        binding.saturdayButton.setImageResource(R.drawable.sat_sunday_button)
                    }

                    if (alarmUiState.sundaySelected) {
                        binding.sundayButton.setImageResource(R.drawable.selected_sat_sunday_button)
                    } else {
                        binding.sundayButton.setImageResource(R.drawable.sat_sunday_button)
                    }

                    // Update UI elements
                }
            }
        }


        val navController = findNavController()
        val picker =
            MaterialTimePicker.Builder()
                .setInputMode(INPUT_MODE_KEYBOARD)
                .setTimeFormat(TimeFormat.CLOCK_12H)
                .setHour(12)
                .setMinute(10)
                .setTitleText("Select Appointment time")
                .build()

        picker.show(requireFragmentManager(), "tag")
        binding.modifyButton.setOnClickListener {
            picker.show(requireFragmentManager(),"tag")
        }
        binding.finishButton.setOnClickListener {
            navController.navigate(R.id.action_CreateAlarm_to_FirstFragment)
        }
        buttonInit()
        picker.addOnPositiveButtonClickListener {
            lateinit var hour : String
            val minute =  picker.minute.toString()
            lateinit var ampm : String
            if(picker.hour.toString().toInt() >12 ){
                hour = (picker.hour -12).toString()
                ampm = getString(R.string.pm)
            }else if(picker.hour.toString().toInt() == 12 ){
                hour = picker.hour.toString()
                ampm = getString(R.string.pm)
            }
            else{
                hour = picker.hour.toString()
                ampm = getString(R.string.am)
            }
            viewModel.changeTime(hour,minute,ampm)


        }
        binding.finishButton.setOnClickListener {
            val clockValue = viewModel.clockUiState.value
            val calendarValue = viewModel.uiState.value
            val hour = clockValue.hour.toInt()
            val minute = clockValue.minute.toInt()
            val calendar = Calendar.getInstance()
            calendar.set(Calendar.HOUR_OF_DAY, hour)
            calendar.set(Calendar.MINUTE, minute)

            val alarmManager =
                requireContext().getSystemService(Context.ALARM_SERVICE) as AlarmManager
            val intent = Intent(requireContext(), AlarmReceiver::class.java)
            val pendingIntent =
                PendingIntent.getBroadcast(requireContext(), 0, intent, PendingIntent.FLAG_MUTABLE)
            // 알람 설정
            when {
                Build.VERSION.SDK_INT >= Build.VERSION_CODES.M -> alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)
                Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT -> alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)
                else -> alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)
            }
            Toast.makeText(context,"알람 설정 완료", Toast.LENGTH_LONG).show()
        }


    }

    private fun buttonInit() {

        binding.mondayButton.setOnClickListener {
            viewModel.selectDay(Day.MONDAY)
        }
        binding.tuesdayButton.setOnClickListener {
            viewModel.selectDay(Day.TUESDAY)
        }
        binding.wednesdayButton.setOnClickListener {
            viewModel.selectDay(Day.WEDNESDAY)
        }
        binding.thursdayButton.setOnClickListener {
            viewModel.selectDay(Day.THURSDAY)
        }
        binding.fridayButton.setOnClickListener {
            viewModel.selectDay(Day.FRIDAY)
        }
        binding.saturdayButton.setOnClickListener {
            viewModel.selectDay(Day.SATURDAY)
        }
        binding.sundayButton.setOnClickListener {
            viewModel.selectDay(Day.SUNDAY)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}