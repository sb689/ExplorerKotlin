package com.example.explorer_kotlin.detail

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GestureDetectorCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.explorer_kotlin.databinding.FragmentDetailBinding
import com.example.explorer_kotlin.model.Item



class DetailFragment : Fragment() {

    private lateinit var detector: GestureDetectorCompat
    private var index: Int = -1
    private var lastIndex = -1
    private lateinit var result: Item
    private lateinit var viewModel: DetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?,
    ): View? {

        val binding = FragmentDetailBinding.inflate(inflater)
        binding.lifecycleOwner = this


        result = DetailFragmentArgs.fromBundle(requireArguments()).selectedResult

        val viewModelFactory = DetailViewModelFactory(result, requireActivity().application)
        viewModel = ViewModelProvider(this, viewModelFactory)
                .get(DetailViewModel::class.java)

        binding.viewModel = viewModel

        detector = GestureDetectorCompat(context, GestureListener())
        binding.svDetail .setOnTouchListener { v, event ->
            Log.d("DetailFragment", "touch event received on view")

            detector.onTouchEvent(event)

        }

        viewModel.savedData.observe(viewLifecycleOwner, Observer {
            index = viewModel.savedData.value?.indexOf(result)!!
            lastIndex = viewModel.savedData.value!!.size - 1
        })

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
       // Toast.makeText(context, "swiped up", Toast.LENGTH_LONG).show()
    }

    private fun onSwipeDown() {
       // Toast.makeText(context, "swiped down", Toast.LENGTH_LONG).show()
    }


    private fun onSwipeRight() {
        val item = viewModel.savedData.value?.get(getNextIndex())!!
        viewModel.changeDisplayedData(item)
    }

    private fun getNextIndex(): Int {
        index = if (index == lastIndex) {
            0
        } else {
            index + 1
        }
        return index
    }

    private fun onSwipeLeft() {
        val item = viewModel.savedData.value?.get(getPrevIndex())!!
        viewModel.changeDisplayedData(item)
    }

    private fun getPrevIndex(): Int {
        if (index == 0) {
            index = lastIndex
        } else {
            index = index - 1
        }
        return index
    }


    override fun onStop() {

        super.onStop()
        (activity as AppCompatActivity?)!!.supportActionBar!!.show()
    }

    override fun onResume() {

        super.onResume()
        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()
    }
}

