package xkcdreader.nilsnett.com.domainmodels

import android.net.Uri
import xkcdreader.nilsnett.com.networking.content.XkcdContentNetworkData
import java.lang.Exception
import java.util.*

/**
 * Represents the meta data about an XKCD comic
 */
data class ComicInfo(
    val publishedDate: Date,
    val description: String,
    val title: String,
    val uri: Uri,
    val dataOk: Boolean = true
) {
    companion object {
        val ERRONOUS_OBJECT = ComicInfo(
            Date(),
            ""
            ,""
            ,Uri.EMPTY,
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
                return ComicInfo(
                    date,
                    model.alt,
                    model.title,
                    uri
                )
            } catch (ex: Exception) {
                return ERRONOUS_OBJECT
            }
        }
    }
}

