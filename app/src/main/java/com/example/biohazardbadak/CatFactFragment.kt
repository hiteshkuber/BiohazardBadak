package com.example.biohazardbadak

import android.media.MediaPlayer
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CatFactFragment : Fragment() {

    lateinit var factTextView: TextView
    lateinit var nextButton: Button
    private val soundList = listOf(
        R.raw.meow_1,
        R.raw.meow_2,
        R.raw.meow_3,
        R.raw.meow_4,
        R.raw.meow_5,
        R.raw.meow_6,
        R.raw.meow_7
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cat_fact, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViews(view)

        nextButton.setOnClickListener {
            it.isClickable = false

            //meow sound logic
            val soundRes = soundList[(0..6).random()]
            val mediaPlayer = MediaPlayer.create(context, soundRes)
            mediaPlayer.start()

            val retrofit = Retrofit.Builder().baseUrl("https://catfact.ninja/")
                .addConverterFactory(GsonConverterFactory.create()).build()

            val catFactApi = retrofit.create(CatFactApi::class.java)

            val call = catFactApi.getPosts()

            call.enqueue(
                object : Callback<Fact?> {
                    override fun onResponse(call: Call<Fact?>, response: Response<Fact?>) {
                        it.isClickable = true

                        if (!response.isSuccessful) {
                            factTextView.text = "Code: " + response.code()
                            return
                        }

                        factTextView.text = response.body()?.fact
                    }

                    override fun onFailure(call: Call<Fact?>, t: Throwable) {
                        factTextView.text = t.message
                        it.isClickable = true
                    }
                }
            )
        }
    }

    private fun setupViews(view: View) {
        factTextView = view.findViewById(R.id.fact_text)
        nextButton = view.findViewById(R.id.next_fact)
    }
}