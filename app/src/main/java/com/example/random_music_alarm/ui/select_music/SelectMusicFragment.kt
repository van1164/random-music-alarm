package com.example.random_music_alarm.ui.select_music

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.random_music_alarm.AlarmCreateViewModel
import com.example.random_music_alarm.R
import com.example.random_music_alarm.databinding.FragmentMusicSelectBinding
import com.example.random_music_alarm.databinding.FragmentSelectMoodBinding
import kotlinx.coroutines.launch

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

private var _binding: FragmentMusicSelectBinding? = null


private val binding get() = _binding!!
class SelectMusicFragment : Fragment() {
    val viewModel: SelectMusicViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    @SuppressLint("UnsafeRepeatOnLifecycleDetector")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val navController = findNavController()
        _binding = FragmentMusicSelectBinding.inflate(inflater, container, false)

        val recyclerView = binding.recyclerView
        val tempData = listOf<String>("AAAAAAA", "VVVVVV","XXXXXXX","CCCCCCC","SSSSSSS")
        val adapter = SelectMusicAdapter(tempData,
            onClickSelect = {
            viewModel.selectMusic(it)
        },
            onClickPlay= {
            viewModel.playMusic(it)
            }
        )
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter

        binding.nextButton.setOnClickListener {
            navController.navigate(R.id.action_selectMusicFragment_to_CreateAlarm)
        }


        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect{musicState->
                    (recyclerView.adapter as SelectMusicAdapter).setData(musicState.selectedIndex,musicState.playedIndex)
                }
            }

        }

        return binding.root
    }

}