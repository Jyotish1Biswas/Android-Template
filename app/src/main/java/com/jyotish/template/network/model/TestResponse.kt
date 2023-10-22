package com.jyotish.template.network.model


import com.google.gson.annotations.SerializedName

data class TestResponse(
    @SerializedName("data")
    val `data`: List<Data>,
    @SerializedName("status")
    val status: Int
) {
    data class Data(
        @SerializedName("channel")
        val channels: List<Channel>,
        @SerializedName("competition_title")
        val competitionTitle: String,
        @SerializedName("format_str")
        val formatStr: String,
        @SerializedName("id")
        val id: Int,
        @SerializedName("isNimble")
        val isNimble: Int,
        @SerializedName("isVisible")
        val isVisible: Int,
        @SerializedName("match_id")
        val matchId: Int,
        @SerializedName("subtitle")
        val subtitle: String,
        @SerializedName("team_a")
        val teamA: String,
        @SerializedName("team_a_logo")
        val teamALogo: String,
        @SerializedName("team_b")
        val teamB: String,
        @SerializedName("team_b_logo")
        val teamBLogo: String,
        @SerializedName("timestamp_start")
        val timestampStart: Int,
        @SerializedName("title_short")
        val titleShort: String,
        @SerializedName("type")
        val type: String
    ) {
        data class Channel(
            @SerializedName("id")
            val id: Int,
            @SerializedName("position")
            val position: Any,
            @SerializedName("quality")
            val quality: String,
            @SerializedName("signed_stream")
            val signedStream: String,
            @SerializedName("stream_type")
            val streamType: String,
            @SerializedName("url")
            val url: String
        )
    }
}