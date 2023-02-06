package com.example.biohazardbadak;

import com.google.gson.annotations.SerializedName;

public class Fact {
    private String fact;

    @SerializedName("length")
    private int fact_length;

    public String getFact() {
        return fact;
    }

    public int getFact_length() {
        return fact_length;
    }
}
