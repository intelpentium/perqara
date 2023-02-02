package kedaiapps.projeku.testandroidperqara.services.entity

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

//home
@Keep
data class ResponseHome(
    @SerializedName("id") val id: String,
    @SerializedName("background_image") val background_image: String,
    @SerializedName("name") val name: String,
    @SerializedName("released") val released: String,
    @SerializedName("rating") val rating: String,
)


//notif
@Keep
data class ResponseHomeDetail(
    @SerializedName("id") val id: String,
    @SerializedName("slug") val slug: String,
    @SerializedName("background_image") val background_image: String,
    @SerializedName("name") val name: String,
    @SerializedName("description") val description: String,
    @SerializedName("released") val released: String,
    @SerializedName("rating") val rating: String,
    @SerializedName("added_by_status") val added_by_status: ResponseHomeDetailPlaying,
)

@Keep
data class ResponseHomeDetailPlaying(
    @SerializedName("playing") val playing: String,
)


