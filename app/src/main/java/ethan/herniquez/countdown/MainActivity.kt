package ethan.herniquez.countdown

import android.os.Bundle
import android.os.CountDownTimer
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import android.widget.TextView
import android.widget.ImageButton

class MainActivity : AppCompatActivity() {

    private lateinit var startButton: Button
    private lateinit var pauseButton: Button
    private lateinit var resumeButton: Button
    private lateinit var timerTextView: TextView
    private lateinit var resetButton: ImageButton

    private var countDownTimer: CountDownTimer? = null
    private var timeLeftInMillis: Long = 120000 // 2 minutos en milisegundos
    private var isTimerRunning = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        startButton = findViewById(R.id.startButton)
        pauseButton = findViewById(R.id.pauseButton)
        resumeButton = findViewById(R.id.resumeButton)
        timerTextView = findViewById(R.id.timerTextView)
        resetButton = findViewById(R.id.resetButton)

        updateTimer()

        startButton.setOnClickListener {
            if (!isTimerRunning) {
                startTimer()
                startButton.visibility = Button.GONE
                pauseButton.visibility = Button.VISIBLE
                resetButton.visibility = Button.VISIBLE
            }
        }

        pauseButton.setOnClickListener {
            if (isTimerRunning) {
                pauseTimer()
                pauseButton.visibility = Button.GONE
                resumeButton.visibility = Button.VISIBLE
            }
        }

        resumeButton.setOnClickListener {
            if (!isTimerRunning) {
                startTimer()
                resumeButton.visibility = Button.GONE
                pauseButton.visibility = Button.VISIBLE
            }
        }

        resetButton.setOnClickListener {
            resetTimer()
            startButton.visibility = Button.VISIBLE
            pauseButton.visibility = Button.GONE
            resumeButton.visibility = Button.GONE
            resetButton.visibility = Button.GONE
        }
    }

    private fun startTimer() {
        countDownTimer = object : CountDownTimer(timeLeftInMillis, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                timeLeftInMillis = millisUntilFinished
                updateTimer()
            }

            override fun onFinish() {
                isTimerRunning = false
                startButton.visibility = Button.VISIBLE
                pauseButton.visibility = Button.GONE
                resumeButton.visibility = Button.GONE
                resetButton.visibility = Button.GONE
            }
        }.start()

        isTimerRunning = true
    }

    private fun pauseTimer() {
        countDownTimer?.cancel()
        isTimerRunning = false
    }

    private fun resetTimer() {
        timeLeftInMillis = 120000 // 2 minutos en milisegundos
        updateTimer()
        if (isTimerRunning) {
            pauseTimer()
        }
    }

    private fun updateTimer() {
        val minutes = (timeLeftInMillis / 1000) / 60
        val seconds = (timeLeftInMillis / 1000) % 60
        val timeFormatted = String.format("%02d:%02d", minutes, seconds)
        timerTextView.text = timeFormatted
    }
}