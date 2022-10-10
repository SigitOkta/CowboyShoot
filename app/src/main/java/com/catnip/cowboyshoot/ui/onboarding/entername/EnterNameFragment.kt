package com.catnip.cowboyshoot.ui.onboarding.entername

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.catnip.cowboyshoot.databinding.FragmentEnterNameBinding
import com.catnip.cowboyshoot.ui.game.GameActivity
import com.catnip.cowboyshoot.ui.menu.GameModeActivity
import com.catnip.cowboyshoot.ui.menu.GameModeActivity.Companion.EXTRA_NAME
import com.catnip.cowboyshoot.ui.onboarding.OnFinishNavigateListener

class EnterNameFragment : Fragment() , OnFinishNavigateListener {
    private lateinit var binding : FragmentEnterNameBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEnterNameBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onFinishNavigateListener() {
        val name = binding.etName.text.toString().trim()
        if (name.isEmpty()){
            Toast.makeText(requireContext(),"Please Input Your Name !", Toast.LENGTH_SHORT).show()
        } else{
            navigateToMenu(name)
        }
    }

    private fun navigateToMenu(name: String) {
        val intent = Intent(requireContext(),GameModeActivity::class.java)
        intent.putExtra(EXTRA_NAME,name)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        Toast.makeText(requireContext(),"name : $name",Toast.LENGTH_SHORT).show()

    }
}

