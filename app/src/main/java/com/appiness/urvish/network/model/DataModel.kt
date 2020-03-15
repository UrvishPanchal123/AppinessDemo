package com.appiness.urvish.network.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class DataModel {

    @SerializedName("s.no")
    @Expose
    var s_no: String? = null

    @SerializedName("amt.pledged")
    @Expose
    var amt_pledged: String? = null

    @SerializedName("blurb")
    @Expose
    var blurb: String? = null

    @SerializedName("by")
    @Expose
    var by: String? = null

    @SerializedName("country")
    @Expose
    var country: String? = null

    @SerializedName("currency")
    @Expose
    var currency: String? = null

    @SerializedName("end.time")
    @Expose
    var end_time: String? = null

    @SerializedName("location")
    @Expose
    var location: String? = null

    @SerializedName("percentage.funded")
    @Expose
    var percentage_funded: String? = null

    @SerializedName("num.backers")
    @Expose
    var num_backers: String? = null

    @SerializedName("state")
    @Expose
    var state: String? = null

    @SerializedName("title")
    @Expose
    var title: String? = null

    @SerializedName("type")
    @Expose
    var type: String? = null

    @SerializedName("url")
    @Expose
    var url: String? = null
}