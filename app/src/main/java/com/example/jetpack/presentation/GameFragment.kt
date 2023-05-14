package com.example.jetpack.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.jetpack.R
import com.example.jetpack.databinding.FragmentGameBinding
import com.example.jetpack.domain.entity.GameResult
import com.example.jetpack.domain.entity.GameSettings
import com.example.jetpack.domain.entity.Level

class GameFragment : Fragment() {

    private lateinit var level: Level

    private var _binding: FragmentGameBinding? = null
    private val binding: FragmentGameBinding
        get() = _binding ?: throw RuntimeException("FragmentGameBinding == null")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parseArgs()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentGameBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnOption1.setOnClickListener {
            launchFinishFragment()
        }
    }

    private fun launchFinishFragment(){
        fragmentManager?.popBackStack()
        val gameSetting = GameSettings(30,34,33,1)
        val gameResult = GameResult(true,5,2,gameSetting)
        requireActivity().supportFragmentManager
            .beginTransaction()
            .replace(R.id.main_container, FinishFragment.newInstance(gameResult))
            .addToBackStack(null)
            .commit()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun parseArgs(){
        level = requireArguments().getSerializable(KEY_LEVEL) as Level
    }

    companion object{

        private val KEY_LEVEL = "level"

        fun newInstance(level: Level): GameFragment{
            return GameFragment().apply{
                arguments = Bundle().apply {
                    putSerializable(KEY_LEVEL, level)
                }
            }
        }

    }

}