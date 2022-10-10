package com.catnip.cowboyshoot.ui.menu

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.catnip.cowboyshoot.R
import com.catnip.cowboyshoot.databinding.ActivityGameModeBinding

class GameModeActivity : AppCompatActivity() {
    private val binding: ActivityGameModeBinding by lazy {
        ActivityGameModeBinding.inflate(layoutInflater)
    }

    companion object {
        const val EXTRA_NAME: String = "extra_name"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        supportActionBar?.hide()
        setTitleName()
        setOnClickListener()
    }

    private fun setTitleName() {
        val name = intent.getStringExtra(EXTRA_NAME)
        binding.tvName.text = buildString {
            append("hi ")
            append(name)
        }
    }

    private fun setOnClickListener() {
        binding.ivVsComputer.setOnClickListener {
            Toast.makeText(this,"Player vs computer", Toast.LENGTH_SHORT).show()
        }
        binding.ivVsPlayer.setOnClickListener {
            Toast.makeText(this,"Player vs player", Toast.LENGTH_SHORT).show()
        }
    }
}