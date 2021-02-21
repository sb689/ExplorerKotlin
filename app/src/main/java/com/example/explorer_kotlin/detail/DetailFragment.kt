package com.example.explorer_kotlin.detail

import android.os.Bundle
import android.text.method.Touch.onTouchEvent
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.core.view.GestureDetectorCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.explorer_kotlin.databinding.FragmentDetailBinding


class DetailFragment : Fragment() {

    private lateinit var detector: GestureDetectorCompat

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?,
    ): View? {

        val binding = FragmentDetailBinding.inflate(inflater)
        binding.lifecycleOwner = this
        val result = DetailFragmentArgs.fromBundle(requireArguments()).selectedResult
        val viewModel by viewModels<DetailViewModel> {
            DetailViewModelFactory(result)
        }
        binding.viewModel = viewModel

        detector = GestureDetectorCompat(context, GestureListener())
        binding.svDetail.setOnTouchListener { v, event ->
            Log.d("DetailFragment", "touch event received on view")

            detector.onTouchEvent(event)
            true
        }
        return binding.root
    }


    inner class GestureListener : GestureDetector.SimpleOnGestureListener() {

        private val SWIPE_THRESHOLD = 50F
        private val SWIPE_VELOCITY_THRESHOLD = 70F

        override fun onFling(downEvent: MotionEvent?, moveEvent: MotionEvent?, velocityX: Float, velocityY: Float): Boolean {

            Log.d("DetailFragment", "touch event received - onFling")
            val diffX = moveEvent?.x?.minus(downEvent?.x!!) ?: 0.0F
            val diffY = downEvent?.let { moveEvent?.y?.minus(it.y) } ?: 0.0F

            return if (Math.abs(diffX) > Math.abs(diffY)) {
                // this is a left or right swipe
                if (Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                    if (diffX > 0) {
                        // it is a right swipe
                        onSwipeRight()
                    } else {
                        // it is a left swipe
                        onSwipeLeft()
                    }
                    true
                } else {
                    super.onFling(downEvent, moveEvent, velocityX, velocityY)
                }
            } else {
                // this is a top or bottom swipe
                if (Math.abs(diffY) > SWIPE_THRESHOLD && Math.abs(velocityY) > SWIPE_VELOCITY_THRESHOLD) {
                    if (diffY > 0) {
                        // it is a topDown swipe
                        onSwipeDown()
                    } else {
                        // it is a bottomUp swipe
                        onSwipeUp()
                    }
                    true
                } else {
                    super.onFling(downEvent, moveEvent, velocityX, velocityY)
                }
            }
        }
    }

        private fun onSwipeUp() {
            Toast.makeText(context, "swiped up", Toast.LENGTH_LONG).show()
        }

        private fun onSwipeDown() {
            Toast.makeText(context, "swiped down", Toast.LENGTH_LONG).show()
        }


        private fun onSwipeRight() {
            Toast.makeText(context, "swiped right", Toast.LENGTH_LONG).show()
        }

        private fun onSwipeLeft() {
            Toast.makeText(context, "swiped left", Toast.LENGTH_LONG).show()
        }

    }

