package com.jyotish.template.helper

import android.app.Activity
import android.app.Dialog
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.graphics.Rect
import android.graphics.drawable.ColorDrawable
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.provider.Settings
import android.text.TextUtils
import android.util.Log
import android.util.Patterns
import android.util.TypedValue
import android.view.Gravity
import android.view.TouchDelegate
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.ColorRes
import androidx.annotation.DimenRes
import androidx.annotation.RawRes
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import androidx.core.view.isVisible
import androidx.core.widget.ImageViewCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.lifecycleScope
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.transition.Transition
import androidx.transition.TransitionManager
import androidx.viewpager.widget.ViewPager
import com.bumptech.glide.Glide
import com.jyotish.template.application.AppController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.IOException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.Calendar
import java.util.Locale

fun showDebugLog(msg: String) {
    //Log.d("GSK", "showDebugLog: $msg")
}

fun showErrorLog(msg: String) {
    //Log.e("GSK", "showErrorLog: $msg")
}

fun showApiErrorLog(msg: String) {
    //Log.e("API_ERROR", "showErrorLog: $msg")
}

fun showExceptionLog(msg: String) {
    //Log.e("EXCEPTION", msg)
}

fun showResponseLog(response: Any) {
    //Log.e("GSK", "showResponseLog: " + Gson().toJson(response))
}

fun showAdLog(msg: String) {
    //Log.e("AD", "showAdLog: $msg")
}

fun showEventLog(msg: String) {
    //Log.e("EVENT", "Event logged: $msg")
}


// Any


fun getStringExt(@StringRes resId: Int, input: Any?): String {
    return AppController.instance.getString(resId, input)
}

fun isValidEmail(target: CharSequence?): Boolean {
    return !TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches()
}

inline fun runWithoutException(block: () -> Unit) {
    try {
        block()
    } catch (e: Exception) {
        Log.e("runWithoutException " , e.errorMessage)
    }
}

fun rand(start: Int, end: Int): Int {
    require(start <= end) { "Illegal Argument" }
    return (start..end).shuffled().first()
}

fun runForeground(block: () -> Unit) {
    Handler(Looper.getMainLooper()).post { block() }
}

fun <T> ifTrue(isTrue: Boolean?, block: () -> T): T? = if (isTrue != null && isTrue) {
    block()
} else {
    null
}

fun Any?.ifNull(block: () -> Unit) {
    if (this == null) {
        block()
    }
}

fun getStringExt(@StringRes resId: Int): String {
    return AppController.instance.getString(resId)
}

fun getDimenExt(@DimenRes resId: Int): Float = AppController.instance.resources.getDimension(resId)


fun copyToClipBoard(context: Context, text: String) {
    val clipboard: ClipboardManager? = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager?
    val clip: ClipData = ClipData.newPlainText("", text)
    clipboard?.setPrimaryClip(clip)
}




/*
* [Activity] Extension Function
*/
fun AppCompatActivity.makeToast(text: String?) {
    text?.let { Toast.makeText(this, text, Toast.LENGTH_SHORT).show() }
}

fun AppCompatActivity.requireActivity() = this

fun AppCompatActivity.updateStatusBarColor(color: Int, lightStatusBar: Boolean = false) {
    val decorView = window.decorView
    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
    WindowCompat.getInsetsController(window, decorView)?.let {
        it.isAppearanceLightStatusBars = lightStatusBar
    }
    window.statusBarColor = color
}

val Activity.actionbarSize: Int
    get() {
        val tv = TypedValue()
        return if (theme.resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
            TypedValue.complexToDimensionPixelSize(tv.data, resources.displayMetrics)
        } else {
            48.dp
        }
    }


fun AppCompatActivity.hideKeyboard() {
    val view: View? = this.currentFocus
    if (view != null) {
        val imm: InputMethodManager? = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager?
        imm?.hideSoftInputFromWindow(view.windowToken, 0)
    }
}



/*
* Bitmap
* */

val Bitmap.cardBackgroundColor: Int
    get() {
        return getPixel(12, 12)
    }


/*
* CharSequence
* */
fun CharSequence?.isNotNullOrEmpty() = !isNullOrEmpty()
fun CharSequence?.isNotNullOrBlank() = !isNullOrBlank()

/*
* Context
* */


fun Context.playRawMedia(@RawRes id: Int) {
    try {
        val player = MediaPlayer.create(this, id)
        player.setOnCompletionListener { it.release() }
        player.start()
    } catch (e: Exception) {
        e.message?.let { Log.e("playRawMedia", it) }
    }
}


fun Context.openAppSettings() {
    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
        data = Uri.fromParts("package", packageName, null)
    }
    startActivity(intent)
}

inline fun <reified T> Context.launchActivity(bundle: Bundle? = null) {
    startActivity(Intent(this, T::class.java).apply { bundle?.let { putExtras(it) } })
}

inline fun <reified T> Context.clearStackAndStartActivity(bundle: Bundle? = null) {
    startActivity(Intent(this, T::class.java).apply {
        flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        if (bundle != null) {
            putExtras(bundle)
        }
    })
}

fun Context.makeToast(text: String?) {
    text?.let { Toast.makeText(this, text, Toast.LENGTH_SHORT).show() }
}

/*
* Dialog
* */

fun Dialog.makeWindowTransparent() {
    window?.requestFeature(Window.FEATURE_NO_TITLE)
    window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
}

fun Dialog.movePositionToBottom() {
    try {
        val params = window?.attributes
        params?.gravity = Gravity.BOTTOM
        params?.flags = params!!.flags and WindowManager.LayoutParams.FLAG_DIM_BEHIND.inv()
        window?.attributes = params
    } catch (e: Exception) {
        //ignored
    }
}

/*
* Exception
* */

val Exception.errorMessage: String
    get() {
        return when(this) {
            is ConnectException, is UnknownHostException -> "Unable to connect to the server. Please check your connection"
            is SocketTimeoutException -> "Connection timeout please try again"
            is IOException -> this.message ?: "Server error please try again"
            else -> "Error occurred please try again"
        }
    }

/*
* [Fragment] Extension Function
*/

fun Fragment.makeToast(text: String?) {
    this.context?.let { context ->
        text?.let { Toast.makeText(context, text, Toast.LENGTH_SHORT).show() }
    }
}

inline fun <reified T> Fragment.launchActivity(bundle: Bundle? = null) {
    startActivity(Intent(requireContext(), T::class.java).apply { bundle?.let { putExtras(it) } })
}



fun Fragment.updateStatusBarColor(color: Int, lightStatusBar: Boolean = false) {
    val window = requireActivity().window
    val decorView = window.decorView
    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
    WindowCompat.getInsetsController(window, decorView)?.let {
        it.isAppearanceLightStatusBars = lightStatusBar
    }
    window.statusBarColor = color
}



val Fragment.viewLifecycleScope: CoroutineScope
    get() = viewLifecycleOwner.lifecycleScope

/*
* ImageView
* */

fun ImageView.setTint(@ColorRes colorRes: Int) {
    ImageViewCompat.setImageTintList(this, ColorStateList.valueOf(ContextCompat.getColor(context, colorRes)))
}

fun ImageView.load(url: String) {
    Glide.with(this).load(url).into(this)
}

fun ImageView.load(imageResource: Int) {
    Glide.with(this).load(imageResource).into(this)
}


fun Context.changeActivity(target: Class<*>, bundle: Bundle = Bundle()) =
    startActivity(Intent(this, target).putExtras(bundle))

/*
* [List] Extension Function
*/

fun <T> List<T>.clone(): MutableList<T> {
    val updateList = mutableListOf<T>()
    updateList.addAll(this)
    return updateList
}

fun <T> MutableList<T>.replaceItems(updatedList: List<T>) {
    clear()
    addAll(updatedList)
}

/*
* Lifecycle
* */
fun LifecycleCoroutineScope.launchSafe(block: suspend () -> Unit) {
    launch {
        try {
            block()
        } catch (e: Exception) {
            e.message?.let { Log.e("lunchSafe", it) }
        }
    }
}

/*
* Number
* */

val Int.dp: Int
    get() = (this * Resources.getSystem().displayMetrics.density).toInt()

fun Double.formatAmount(): String {
    val formatter = NumberFormat.getInstance(Locale.US) as DecimalFormat
    formatter.applyPattern("#,###.##")
    return formatter.format(this)
}

fun Int.formatAmount(): String {
    val formatter = NumberFormat.getInstance(Locale.US) as DecimalFormat
    formatter.applyPattern("#,###.##")
    return formatter.format(this)
}

/*
* String
* */

fun String.toFourDigitYear(): String {
    var year = 0
    if (this.length == 2) {
        val currentYear = Calendar.getInstance().get(Calendar.YEAR) //2022
        val firstTwoDigitsOfCurrentYear = currentYear.toString().substring(0, 2) //20
        year = (firstTwoDigitsOfCurrentYear + this).toInt() //2021
        //if (year < currentYear) year += 100 //2121
    }
    return year.toString()
}

fun String.trimComma(): String {
    return this.replace(",", "")
}

fun CharSequence.trimComma(): CharSequence {
    return toString().trimComma()
}

fun String.toCardMask(): String {
    return "xxxx xxxx xxxx " + this.trim().replace(" ", "").substring(12)
}

fun String.maskEmail(): String {
    return this.replace("(^[^@]{3}|(?!^)\\G)[^@]".toRegex(), "$1*")
}

fun String.toCardValidation(): String {
    return this.trim().replace(" ", "")
}

fun String.toHideMask(): String {
    var mask = ""
    for (i in this.indices) {
        mask += "*"
    }
    return mask
}

fun String.formatWithThreeSpaces(): String {
    val formattedContact = StringBuilder()
    val modifiedNumber = if (startsWith("+")) {
        formattedContact.append("+")
        substring(1, length)
    } else {
        this
    }
    modifiedNumber.forEachIndexed { index, ch ->
        formattedContact.append(ch)
        if ((index + 1) % 3 == 0 && index != this.length - 1) {
            formattedContact.append(" ")
        }
    }
    return formattedContact.toString()
}

fun Double.toTwoDecimalFormat(): String {
    return String.format("%.2f", this)
}

fun String.toTrimCurrency(currency: String) : String{
    return this.replace(currency, "").replace(" ", "")
}

fun String.toTrimComma() : String{
    return this.replace(",", "")
}

/*
* SwipeRefreshLayout
* */
fun SwipeRefreshLayout.setRefreshing() {
    isRefreshing = true
}

fun SwipeRefreshLayout.setNotRefreshing() {
    isRefreshing = false
}

/*
* TextView
* */

fun TextView.setDrawableColor(@ColorRes color: Int) {
    compoundDrawablesRelative.filterNotNull().forEach {
        it.colorFilter = PorterDuffColorFilter(ContextCompat.getColor(this.context, color), PorterDuff.Mode.SRC_IN)
    }
}

/*
* View
* */

fun View.gone() {
    this.visibility = View.GONE
}

fun gone(vararg views: View) {
    views.forEach {
        it.isVisible = false
    }
}

fun View.visible() {
    this.visibility = View.VISIBLE
}

fun View.invisible() {
    this.visibility = View.INVISIBLE
}

fun View.disable() {
    this.isEnabled = false
}

fun View.disable(lifecycleScope: LifecycleCoroutineScope, timeInMillis: Long) {
    lifecycleScope.launch {
        runWithoutException {
            this@disable.isEnabled = false
            delay(timeInMillis)
            this@disable.isEnabled = true
        }
    }
}

fun View.enable() {
    this.isEnabled = true
}

fun View.expandTouch(amount: Int = 50) {
    Handler(Looper.getMainLooper()).post {
        val rect = Rect()
        this.getHitRect(rect)
        rect.top -= amount
        rect.right += amount
        rect.bottom += amount
        rect.left -= amount
        (this.parent as View).touchDelegate = TouchDelegate(rect, this)
    }
}




inline fun <reified T : ViewGroup.LayoutParams> View.layoutParams(block: T.() -> Unit) {
    if (layoutParams is T) block(layoutParams as T)
}

fun View.setMargins(l: Int, t: Int, r: Int, b: Int) {
    if (this.layoutParams is ViewGroup.MarginLayoutParams) {
        val p = this.layoutParams as ViewGroup.MarginLayoutParams
        p.setMargins(l, t, r, b)
        this.requestLayout()
    }
}

fun View.setMargins(start: Int? = null, top: Int? = null, end: Int? = null, bottom: Int? = null) {
    if (this.layoutParams is ViewGroup.MarginLayoutParams) {
        val currentParam = this.layoutParams as ViewGroup.MarginLayoutParams
        currentParam.setMargins(
            start ?: currentParam.marginStart,
            top ?: currentParam.topMargin,
            end ?: currentParam.marginEnd,
            bottom ?: currentParam.bottomMargin
        )
        this.requestLayout()
    }
}

fun View.fadeVisibility(visibility: Int, duration: Long = 400) {
    val transition: Transition = androidx.transition.Fade()
    transition.duration = duration
    transition.addTarget(this)
    TransitionManager.beginDelayedTransition(this.parent as ViewGroup, transition)
    this.visibility = visibility
}

fun ViewPager.nextPage() {
    val totalItems = adapter?.count ?: 0
    val newPosition = if (currentItem == totalItems - 1) {
        0
    } else {
        currentItem + 1
    }
    setCurrentItem(newPosition, true)
}
