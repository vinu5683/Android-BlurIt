package com.vinod.blurit

import android.os.Build
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.vinod.blurit.blurview.BlurAlgorithm
import com.vinod.blurit.blurview.RenderEffectBlur
import com.vinod.blurit.blurview.RenderScriptBlur
import com.vinod.blurit.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    val adapter = ColorAdapter((1..100).map { (0..130).random() })
    private var offsetX = 0
    private var offsetY = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.rvColorImages.adapter = adapter
        adapter.notifyDataSetChanged()
        window?.decorView?.background?.let {
            val algorithm: BlurAlgorithm = getBlurAlgorithm()
            binding.bottomBlurLayout.setupWith(binding.rvColorImages, algorithm)
                .setBlurRadius(20f)
            binding.bottomBlurLayout.setBlurRadius(20f)

            binding.topBlurLayout.setupWith(binding.rvColorImages, algorithm)
                .setBlurRadius(20f)
            binding.topBlurLayout.setBlurRadius(20f)

            binding.draggableView.setupWith(binding.rvColorImages, algorithm)
                .setBlurRadius(20f)
            binding.draggableView.setBlurRadius(20f)
        }

        binding.seekbar.setOnSeekBarChangeListener(object :OnSeekBarChangeListener{
            override fun onProgressChanged(p0: SeekBar?, progress: Int, p2: Boolean) {
                var blurRadius: Float = progress / 4f
                blurRadius = blurRadius.coerceAtLeast(4f)
                binding.topBlurLayout.setBlurRadius(blurRadius)
                binding.bottomBlurLayout.setBlurRadius(blurRadius)
                binding.draggableView.setBlurRadius(blurRadius)
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {

            }

            override fun onStopTrackingTouch(p0: SeekBar?) {

            }

        })

        binding.draggableView.setOnTouchListener { view, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    offsetX = (view.x - event.rawX).toInt()
                    offsetY = (view.y - event.rawY).toInt()
                }

                MotionEvent.ACTION_MOVE -> {
                    val newX = event.rawX + offsetX
                    val newY = event.rawY + offsetY

                    // Update the view's position
                    updateViewPosition(newX, newY)
                }
            }
            true
        }

    }

    private fun getBlurAlgorithm(): BlurAlgorithm {
        val algorithm: BlurAlgorithm = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            RenderEffectBlur()
        } else {
            RenderScriptBlur(binding.root.context)
        }
        return algorithm
    }


    private fun updateViewPosition(newX: Float, newY: Float) {
        // Limit the view's position to stay within the ConstraintLayout
        val parentLayout = findViewById<ConstraintLayout>(R.id.mainContainerLayout)
        val maxX = parentLayout.width - binding.draggableView.width.toFloat()
        val maxY = parentLayout.height - binding.draggableView.height.toFloat()

        val clampedX = newX.coerceIn(0f, maxX)
        val clampedY = newY.coerceIn(0f, maxY)

        binding.draggableView.x = clampedX
        binding.draggableView.y = clampedY
    }
}