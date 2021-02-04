package hsj.shahram.film.util

import android.webkit.WebView
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

object BindingAdapter {


    @JvmStatic
    @BindingAdapter("loadContentImage")
    fun loadContentImage(imageView: ImageView, url: String?) {

        Glide.with(imageView.context).load(url).into(imageView)

    }


    @JvmStatic
    @BindingAdapter("loadWebView")
    fun loadWebView(webView: WebView, data: String?) {

        if (data == null) return
        webView.loadData(data, "text/html; charset=utf-8", "UTF-8")


    }
}