package com.catnip.cowboyshoot.manager

import com.catnip.cowboyshoot.R
import com.catnip.cowboyshoot.enum.GameState
import com.catnip.cowboyshoot.enum.PlayerPosition
import com.catnip.cowboyshoot.enum.PlayerSide
import com.catnip.cowboyshoot.enum.PlayerState
import com.catnip.cowboyshoot.model.Player
import kotlin.random.Random

interface CowboyGameManager {
    fun initGame()
    fun movePlayerToTop()
    fun movePlayerToBottom()
    fun startOrRestartGame()
}

interface CowboyGameListener {
    fun onPlayerStatusChanged(player: Player, iconDrawableRes: Int)
    fun onGameStateChanged(gameState: GameState)
    fun onGameFinished(gameState: GameState, winner: Player)
}

class ComputerEnemyCowboyGameManager(private val listener: CowboyGameListener) : CowboyGameManager {
    private lateinit var playerOne: Player
    private lateinit var playerTwo: Player
    private lateinit var gameState: GameState

    //inisiasi game pertama muncul
    override fun initGame() {
        setGameState(GameState.IDLE)
        playerOne = Player(PlayerSide.PLAYER_ONE, PlayerState.IDLE, PlayerPosition.MIDDLE)
        playerTwo = Player(PlayerSide.PLAYER_TWO, PlayerState.IDLE, PlayerPosition.MIDDLE)
        //listener perubahan data player saat memulai game
        notifyPlayerDataChanged()
        //gameState berubah jadi started
        setGameState(GameState.STARTED)
    }

    //
    private fun notifyPlayerDataChanged() {
        listener.onPlayerStatusChanged(
            playerOne,
            getPlayerOneDrawableByState(playerOne.playerState)
        )
        listener.onPlayerStatusChanged(
            playerTwo,
            getPlayerTwoDrawableByState(playerTwo.playerState)
        )
    }

    //move up
    override fun movePlayerToTop() {
        //jika gameState ada di finish dan posisi sekarang dari playerOne lebih besar dari TOP atau 0
        if (gameState != GameState.FINISHED && playerOne.playerPosition.ordinal > PlayerPosition.TOP.ordinal) {
            // ambil posisi sekarang
            val currentIndex = playerOne.playerPosition.ordinal
            //set player Movement dari getPlayerPositionByOrdinal
            setPlayerOneMovement(getPlayerPositionByOrdinal(currentIndex - 1), PlayerState.IDLE)
        }
    }

    override fun movePlayerToBottom() {
        if (gameState != GameState.FINISHED && playerOne.playerPosition.ordinal < PlayerPosition.BOTTOM.ordinal) {
            val currentIndex = playerOne.playerPosition.ordinal
            setPlayerOneMovement(getPlayerPositionByOrdinal(currentIndex + 1), PlayerState.IDLE)
        }
    }


    //set player movement
    private fun setPlayerOneMovement(
        //dari getPlayerPositionByOrdinal
        playerPosition: PlayerPosition = playerOne.playerPosition,
        playerState: PlayerState = playerOne.playerState
    ) {
        playerOne.apply {
            //set ke Player Class -> playerPosition dan PlayerState
            this.playerPosition = playerPosition
            this.playerState = playerState
        }
        listener.onPlayerStatusChanged(
            playerOne,
            getPlayerOneDrawableByState(playerOne.playerState)
        )
    }

    private fun setPlayerTwoMovement(
        playerPosition: PlayerPosition = playerTwo.playerPosition,
        playerState: PlayerState = playerTwo.playerState
    ) {
        playerTwo.apply {
            this.playerPosition = playerPosition
            this.playerState = playerState
        }
        listener.onPlayerStatusChanged(
            playerTwo,
            getPlayerTwoDrawableByState(playerTwo.playerState)
        )
    }

    // ambil posisi index dari player position ordinal
    private fun getPlayerPositionByOrdinal(index: Int): PlayerPosition {
        return PlayerPosition.values()[index]
    }

    //mengambil drawable setiap state untuk player one
    private fun getPlayerOneDrawableByState(playerState: PlayerState): Int {
        return when (playerState) {
            PlayerState.IDLE -> R.drawable.ic_cowboy_left_shoot_false
            PlayerState.SHOOT -> R.drawable.ic_cowboy_left_shoot_true
            PlayerState.DEAD -> R.drawable.ic_cowboy_left_dead
        }
    }

    private fun getPlayerTwoDrawableByState(playerState: PlayerState): Int {
        return when (playerState) {
            PlayerState.IDLE -> R.drawable.ic_cowboy_right_shoot_false
            PlayerState.SHOOT -> R.drawable.ic_cowboy_right_shoot_true
            PlayerState.DEAD -> R.drawable.ic_cowboy_right_dead
        }
    }

    //set GameState
    private fun setGameState(newGameState: GameState) {
        gameState = newGameState
        //listener perubahan gamestate
        listener.onGameStateChanged(gameState)
    }

    //start game
    private fun startGame() {
        //get player two random position dengan fungsi getPlayerTwoPosition
        playerTwo.apply {
            playerPosition = getPlayerTwoPosition()
        }
        //check winner
        checkPlayerWinner()
    }

    private fun checkPlayerWinner() {
        val winner = if (playerOne.playerPosition == playerTwo.playerPosition) {
            //LOSE
            setPlayerOneMovement(playerState = PlayerState.DEAD)
            setPlayerTwoMovement(playerState = PlayerState.SHOOT)
            playerOne
        } else {
            //WIN
            setPlayerOneMovement(playerState = PlayerState.SHOOT)
            setPlayerTwoMovement(playerState = PlayerState.DEAD)
            playerTwo
        }
        setGameState(GameState.FINISHED)
        //listener perubahan winner
        listener.onGameFinished(gameState, winner)
    }

    //reset
    private fun resetGame() {
        initGame()
    }

    //random position playerTwo
    private fun getPlayerTwoPosition(): PlayerPosition {
        val randomPosition = Random.nextInt(0, until = PlayerPosition.values().size)
        return getPlayerPositionByOrdinal(randomPosition)
    }

    //start or Restart
    override fun startOrRestartGame() {
        if (gameState != GameState.FINISHED) {
            startGame()
        } else {
            resetGame()
        }
    }
}