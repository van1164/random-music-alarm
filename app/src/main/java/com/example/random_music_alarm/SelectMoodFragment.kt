package com.example.random_music_alarm

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.random_music_alarm.databinding.FragmentFirstBinding
import com.example.random_music_alarm.databinding.FragmentSelectMoodBinding

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class SelectMoodFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var _binding: FragmentSelectMoodBinding? = null


    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val navController = findNavController()
        _binding = FragmentSelectMoodBinding.inflate(inflater, container, false)

        binding.quietButton.setOnClickListener {
            navController.navigate(R.id.action_select_mood_to_selectMusicFragment)
        }

        binding.loudButton.setOnClickListener {
            navController.navigate(R.id.action_select_mood_to_selectMusicFragment)
        }

        return binding.root

    }

}