package com.example.hackjam
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.viewpager.widget.PagerAdapter
import java.util.*

class ViewPagerAdapter(val context: Context, val frameLayoutList: List<Int>) : PagerAdapter() {
    override fun getCount(): Int {
        return frameLayoutList.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object` as FrameLayout
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val mLayoutInflater =
            context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        // Inflate the FrameLayout container
        val itemView: View = mLayoutInflater.inflate(R.layout.image_slider_item, container, false)
        val frameLayout: FrameLayout = itemView.findViewById(R.id.idFrameLayout)

        // Inflate the actual layout and add it to the FrameLayout
        val layoutToAdd = mLayoutInflater.inflate(frameLayoutList[position], frameLayout, false)
        frameLayout.addView(layoutToAdd)

        container.addView(itemView)

        return itemView
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as FrameLayout)
    }
}