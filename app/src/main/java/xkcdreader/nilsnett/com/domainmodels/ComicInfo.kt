package xkcdreader.nilsnett.com.domainmodels

import android.net.Uri
import android.os.Parcelable
import kotlinx.android.parcel.IgnoredOnParcel
import kotlinx.android.parcel.Parcelize
import xkcdreader.nilsnett.com.networking.content.XkcdContentNetworkData
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

/**
 * Represents the meta data about an XKCD comic
 */
@Parcelize
data class ComicInfo(
    val publishedDate: Date,
    val description: String,
    val title: String,
    val uri: Uri,
    val transcript: String,
    val number: Int,
    val dataOk: Boolean = true
) : Parcelable {

    @IgnoredOnParcel
    val formatter = lazy { SimpleDateFormat("d MMM yyyy", Locale.getDefault()) }

    fun humanReadableDate(): String {
        return formatter.value.format(publishedDate)
    }

    companion object {
        val ERRONOUS_OBJECT = ComicInfo(
            Date(),
            ""
            ,""
            ,Uri.EMPTY,
            "",
            -1,
            false
        )

        fun fromNetworkModel(model: XkcdContentNetworkData?): ComicInfo {
            if (model == null) return ERRONOUS_OBJECT
            try {
                val uri = Uri.parse(model.img)
                val date = Date(
                    model.year.toInt() - 1900,
                    model.month.toInt() - 1,
                    model.day.toInt()
                )
                val transcript = model.transcript
                    .replace("[[", "")
                    .replace("]]", "")
                return ComicInfo(
                    date,
                    model.alt,
                    model.title,
                    uri,
                    transcript,
                    model.num
                )
            } catch (ex: Exception) {
                return ERRONOUS_OBJECT
            }
        }
    }
}

