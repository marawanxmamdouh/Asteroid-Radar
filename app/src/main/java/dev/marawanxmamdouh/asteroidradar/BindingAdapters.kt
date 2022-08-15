package dev.marawanxmamdouh.asteroidradar

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import dev.marawanxmamdouh.asteroidradar.api.imageoftheday.ImageOfTheDay
import dev.marawanxmamdouh.asteroidradar.main.RecyclerViewAdapter
import dev.marawanxmamdouh.asteroidradar.model.Asteroid

@BindingAdapter("asteroidsList")
fun RecyclerView.setAsteroids(data: List<Asteroid>?) {
    val adapter = adapter as RecyclerViewAdapter
    adapter.submitList(data)
}

@BindingAdapter("statusIcon")
fun bindAsteroidStatusImage(imageView: ImageView, isHazardous: Boolean) {
    if (isHazardous) {
        imageView.setImageResource(R.drawable.ic_status_potentially_hazardous)
    } else {
        imageView.setImageResource(R.drawable.ic_status_normal)
    }
}

@BindingAdapter("asteroidStatusImage")
fun bindDetailsStatusImage(imageView: ImageView, isHazardous: Boolean) {
    if (isHazardous) {
        imageView.setImageResource(R.drawable.asteroid_hazardous)
    } else {
        imageView.setImageResource(R.drawable.asteroid_safe)
    }
}

@BindingAdapter("astronomicalUnitText")
fun bindTextViewToAstronomicalUnit(textView: TextView, number: Double) {
    val context = textView.context
    textView.text = String.format(context.getString(R.string.astronomical_unit_format), number)
}

@BindingAdapter("kmUnitText")
fun bindTextViewToKmUnit(textView: TextView, number: Double) {
    val context = textView.context
    textView.text = String.format(context.getString(R.string.km_unit_format), number)
}

@BindingAdapter("velocityText")
fun bindTextViewToDisplayVelocity(textView: TextView, number: Double) {
    val context = textView.context
    textView.text = String.format(context.getString(R.string.km_s_unit_format), number)
}

/**
 * Binding adapter for image of the day
 */
@BindingAdapter("imageUrl")
fun ImageView.bindImageView(imageOfTheDay: ImageOfTheDay?) {
    imageOfTheDay?.url?.let {
        if (imageOfTheDay.media_type == "image") {
            Picasso.get()
                .load(imageOfTheDay.url)
                .into(this)
        }
    }
}

@BindingAdapter("imageDescription")
fun ImageView.bindImageDescription(imageOfTheDay: ImageOfTheDay?) {
    imageOfTheDay?.title?.let {
        if (imageOfTheDay.media_type == "image") {
            contentDescription = imageOfTheDay.title
        }
    }
}
