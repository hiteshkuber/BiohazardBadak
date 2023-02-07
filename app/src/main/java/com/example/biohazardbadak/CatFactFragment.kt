package com.example.biohazardbadak

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

    lateinit var factTextView : TextView
    lateinit var nextButton : Button

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

        val retrofit = Retrofit.Builder().baseUrl("https://catfact.ninja/")
            .addConverterFactory(GsonConverterFactory.create()).build()

        val catFactApi = retrofit.create(CatFactApi::class.java)

        val call = catFactApi.getPosts()

        call.enqueue(
            object : Callback<Fact?> {
                override fun onResponse(call: Call<Fact?>, response: Response<Fact?>) {
                    it.isClickable = true

                    if(!response.isSuccessful) {
                        factTextView.text = "Code: " + response.code()
                        return
                    }

                    val fact = response.body()
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