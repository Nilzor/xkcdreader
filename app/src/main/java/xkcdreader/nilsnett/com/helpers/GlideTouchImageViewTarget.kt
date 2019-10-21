package xkcdreader.nilsnett.com.helpers

import android.graphics.drawable.Drawable
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.ortiz.touchview.TouchImageView
import java.lang.ref.WeakReference

class GlideTouchImageViewTarget(imageView: TouchImageView) : CustomTarget<Drawable>() {
    val imageViewRef: WeakReference<TouchImageView> = WeakReference(imageView)
    override fun onResourceReady(resource: Drawable, transition: Transition<in Drawable>?) {
        imageViewRef.get()?.setImageDrawable(resource)
    }

    override fun onLoadCleared(placeholder: Drawable?) {
        imageViewRef.clear()
    }
}