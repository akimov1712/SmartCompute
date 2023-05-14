package com.example.jetpack.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.jetpack.R
import com.example.jetpack.databinding.FragmentChoiceLevelBinding
import com.example.jetpack.domain.entity.Level

class ChoiceLevelFragment : Fragment() {

    private var _binding: FragmentChoiceLevelBinding? = null
    private val binding:FragmentChoiceLevelBinding
        get() = _binding ?: throw RuntimeException("FragmentChoiceLevelBinding == null")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentChoiceLevelBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListenerButtons()
    }

    private fun setupListenerButtons() {
        binding.apply {
            btnLevelTest.setOnClickListener {
               launchGameFragment(Level.TEST)
            }
            btnLevelEasy.setOnClickListener {
               launchGameFragment(Level.EASY)
            }
            btnLevelNormal.setOnClickListener {
               launchGameFragment(Level.NORMAL)
            }
            btnLevelHard.setOnClickListener {
               launchGameFragment(Level.HARD)
            }
        }
    }

    private fun launchGameFragment(level: Level){
        requireActivity().supportFragmentManager
            .beginTransaction()
            .replace(R.id.main_container, GameFragment.newInstance(level))
            .addToBackStack(null)
            .commit()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    companion object{

        fun newInstance(): ChoiceLevelFragment{
            return ChoiceLevelFragment()
        }

    }

}