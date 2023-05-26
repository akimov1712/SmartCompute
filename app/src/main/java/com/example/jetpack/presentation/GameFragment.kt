package com.example.jetpack.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.jetpack.R
import com.example.jetpack.databinding.FragmentGameBinding
import com.example.jetpack.domain.entity.GameResult
import com.example.jetpack.domain.entity.GameSettings
import com.example.jetpack.domain.entity.Level

class GameFragment : Fragment() {

    private lateinit var level: Level
    private val viewModel: GameViewModel by lazy {
        ViewModelProvider(this)[GameViewModel::class.java]
    }

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
    ): View {
        _binding = FragmentGameBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupButtonListeners()
        observeViewModel()
        viewModel.startGame(level)
    }

    private fun setupButtonListeners(){
        binding.btnOption1.setOnClickListener {
            val answer = binding.btnOption1.text.toString()
            viewModel.chooseAnswer(answer.toInt())
        }
        binding.btnOption2.setOnClickListener {
            val answer = binding.btnOption2.text.toString()
            viewModel.chooseAnswer(answer.toInt())
        }
        binding.btnOption3.setOnClickListener {
            val answer = binding.btnOption3.text.toString()
            viewModel.chooseAnswer(answer.toInt())
        }
        binding.btnOption4.setOnClickListener {
            val answer = binding.btnOption4.text.toString()
            viewModel.chooseAnswer(answer.toInt())
        }
        binding.btnOption5.setOnClickListener {
            val answer = binding.btnOption5.text.toString()
            viewModel.chooseAnswer(answer.toInt())
        }
        binding.btnOption6.setOnClickListener {
            val answer = binding.btnOption6.text.toString()
            viewModel.chooseAnswer(answer.toInt())
        }
    }

    private fun observeViewModel(){
        viewModel.countRight.observe(viewLifecycleOwner) {
            binding.tvCorrect.text = it
        }
        viewModel.countWrong.observe(viewLifecycleOwner) {
            binding.tvIncorrect.text = it
        }
        viewModel.question.observe(viewLifecycleOwner){
            binding.tvSum.text = it.sum.toString()
            binding.tvLeftNumber.text = it.visibleNumber.toString()
            binding.btnOption1.text = it.options[3].toString()
            binding.btnOption2.text = it.options[1].toString()
            binding.btnOption3.text = it.options[4].toString()
            binding.btnOption4.text = it.options[0].toString()
            binding.btnOption5.text = it.options[2].toString()
            binding.btnOption6.text = it.options[5].toString()
        }
        viewModel.percentRightAnswers.observe(viewLifecycleOwner){
            binding.tvPercent.text = "$it %"
            binding.pBarNextLvl.progress = it
        }
        viewModel.minPercent.observe(viewLifecycleOwner){
            binding.pBarNextLvl.secondaryProgress = it
        }
        viewModel.enoughCount.observe(viewLifecycleOwner){

        }
        viewModel.enoughPercent.observe(viewLifecycleOwner){
            if (it){
                binding.pBarNextLvl.progressDrawable = ContextCompat.getDrawable(requireContext(),R.drawable.style_correct_progress_bar)
            } else {
                binding.pBarNextLvl.progressDrawable = ContextCompat.getDrawable(requireContext(),R.drawable.style_incorrect_progress_bar)
            }
        }
        viewModel.formattedTime.observe(viewLifecycleOwner){
            binding.tvTime.text = it
        }
        viewModel.gameResult.observe(viewLifecycleOwner){
            launchFinishFragment(it)
        }
    }

    private fun launchFinishFragment(gameResult: GameResult){
        fragmentManager?.popBackStack()
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
        requireArguments().getParcelable<Level>(KEY_LEVEL)?.let {
            level = it
        }
    }

    companion object{

        private const val KEY_LEVEL = "level"

        fun newInstance(level: Level): GameFragment{
            return GameFragment().apply{
                arguments = Bundle().apply {
                    putParcelable(KEY_LEVEL, level)
                }
            }
        }

    }

}