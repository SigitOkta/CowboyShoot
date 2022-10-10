package com.catnip.cowboyshoot.model

import com.catnip.cowboyshoot.enum.PlayerPosition
import com.catnip.cowboyshoot.enum.PlayerSide
import com.catnip.cowboyshoot.enum.PlayerState


data class Player(
    val playerSide: PlayerSide,
    var playerState: PlayerState,
    var playerPosition: PlayerPosition
) 