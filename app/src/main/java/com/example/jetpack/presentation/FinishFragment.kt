package com.example.jetpack.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.jetpack.R
import com.example.jetpack.databinding.FragmentFinishBinding
import com.example.jetpack.domain.entity.GameResult

class FinishFragment : Fragment() {

    private lateinit var gameResult: GameResult

    private var _binding: FragmentFinishBinding? = null
    private val binding: FragmentFinishBinding
        get() = _binding ?: throw RuntimeException("FinishFragment == null")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parseArgs()

    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFinishBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupClickListeners()
        setupValueInViews()
    }

    private fun setupValueInViews(){
        val winner = gameResult.winner
        val countRightAnswers = gameResult.countRightAnswers
        val countQuestions = gameResult.countQuestions
        val requireAnswers = gameResult.gameSettings.minCount
        val requirePercent = gameResult.gameSettings.minPercent
        val resultPercent = ((countRightAnswers / countQuestions.toDouble()) * 100).toInt()
        if (winner){
            binding.ivResult.setImageResource(R.drawable.icon_tick)
        } else {
            binding.ivResult.setImageResource(R.drawable.icon_stop)
        }
        binding.tvRequireAnswers.text = requireContext().getString(R.string.require_right_answer) + " $requireAnswers"
        binding.tvRequirePercent.text = requireContext().getString(R.string.require_percent_answers) + " $requirePercent%"
        binding.tvResultPoints.text = requireContext().getString(R.string.result_right_answer) + " $countRightAnswers"
        binding.tvResultPercent.text = requireContext().getString(R.string.result_percent_answers) + " $resultPercent%"
    }

    private fun setupClickListeners(){
        binding.btnStartGame.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }
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