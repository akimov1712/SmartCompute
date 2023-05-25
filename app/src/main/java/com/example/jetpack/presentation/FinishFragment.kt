package com.example.jetpack.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.jetpack.databinding.FragmentFinishBinding
import com.example.jetpack.domain.entity.GameResult

class FinishFragment : Fragment() {

    private lateinit var gameResult: GameResult

    private var _binding: FragmentFinishBinding? = null
    private val binding: FragmentFinishBinding
        get() = _binding ?: throw RuntimeException("FinishFragment == null")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFinishBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parseArgs()
    }

    private fun parseArgs(){
        requireArguments().getParcelable<GameResult>(KEY_GAME_RESULT)?.let {
            gameResult = it
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object{

        private const val KEY_GAME_RESULT = "game_result"

        fun newInstance(gameResult: GameResult):FinishFragment {
            return FinishFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(KEY_GAME_RESULT,gameResult)
                }
            }
        }
    }

}