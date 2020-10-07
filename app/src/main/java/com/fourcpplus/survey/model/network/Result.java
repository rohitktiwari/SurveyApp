package com.fourcpplus.survey.model.network;

/**
 * Created by Rohit on 26-03-2020.
 */
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Result {

    @SerializedName("1")
    @Expose
    private String _1;

    @SerializedName("-9")
    @Expose
    private String _9;

    @SerializedName("-5")
    @Expose
    private String _5;

    public String get1() {
        return _1;
    }

    public void set1(String _1) {
        this._1 = _1;
    }

    public String get_9() {
        return _9;
    }

    public void set_9(String _9) {
        this._9 = _9;
    }

    public String get_5() {
        return _5;
    }

    public void set_5(String _5) {
        this._5 = _5;
    }
}