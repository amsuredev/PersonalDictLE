package pl.amsuredev.personaldictionary

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.format.DateFormat
import android.os.PersistableBundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.activity_post.*
import pl.amsuredev.personaldictionary.data.Post
import pl.amsuredev.personaldictionary.databinding.ActivityPostBinding
import pl.amsuredev.personaldictionary.ui.dialogs.DatePickerFragment
import pl.amsuredev.personaldictionary.viewmodels.PostDetailsViewModel
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

private const val UUID_TAG = "UUID"

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
private const val DATE_PATTERN = "MMMM dd yyyy"
private const val REQUEST_DATE = 0
private const val DIALOG_DATE = "DialogDate"

class PostActivity : AppCompatActivity(), DatePickerFragment.CallBacks {
    private var _bind: ActivityPostBinding? = null
    private var imageView: ImageView? = null
    private var dateButton: Button? = null
    private var descrText: EditText? = null
    private var post: Post? = null

    private val postDetailViewModel by lazy {
        ViewModelProviders.of(this).get(PostDetailsViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _bind = ActivityPostBinding.inflate(layoutInflater)
        setContentView(_bind!!.root)
        postDetailViewModel.loadCrime(intent.extras?.getSerializable(UUID_TAG) as UUID)
        imageView = _bind?.image
        dateButton = _bind?.date
        descrText = _bind?.description
        dateButton?.setOnClickListener {
            DatePickerFragment.newInstance(post!!.date).show(supportFragmentManager, DIALOG_DATE)
        }
    }


    override fun onResume() {
        super.onResume()
        postDetailViewModel.postLiveData.observe(this, androidx.lifecycle.Observer { post ->
            post?.let {
                this.post = post
                updateUI()
            }
        })

    }

    private fun updateUI() {
        descrText?.setText(post?.title.toString())
        dateButton?.setText(DateFormat.format(DATE_PATTERN, post?.date))
    }

    @SuppressLint("SimpleDateFormat")
    override fun onStop() {
        super.onStop()
        post?.title = descrText?.text.toString()
        post?.let { postDetailViewModel.updatePost(it) }
    }

    override fun onDestroy() {
        super.onDestroy()
        _bind = null
    }

    override fun onDateSelected(date: Date) {
        post?.date = date
        updateUI()
    }


}