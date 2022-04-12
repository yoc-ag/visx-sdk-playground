package com.yoc.demoappshowcaseandroidkotlin.examples.universal_recycler_view

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.recyclerview.widget.RecyclerView
import com.yoc.demoappshowcaseandroidkotlin.R
import com.yoc.demoappshowcaseandroidkotlin.databinding.ItemListArticleBinding
import com.yoc.demoappshowcaseandroidkotlin.databinding.ItemListCreativeAdBinding
import com.yoc.visx.sdk.VisxAdManager
import timber.log.Timber

class InfeedAdapter(val context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var articleList = mutableListOf<ListItem>()
    private var adViewContainer: View? = null

    companion object {
        private const val INFEED_ADAPTER_TAG = "---> VISX.Feed.Adapter"
        private const val LIST_AD_POSITION_INDEX = 6
        private const val CONTENT = 0
        private const val AD = 1
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == CONTENT) {
            ContentHolder(
                ItemListArticleBinding.inflate(
                    LayoutInflater.from(viewGroup.context),
                    viewGroup,
                    false
                )
            )
        } else {
            AdHolder(
                ItemListCreativeAdBinding.inflate(
                    LayoutInflater.from(viewGroup.context),
                    viewGroup,
                    false
                )
            )
        }
    }

    fun addAll(results: List<ListItem>?) {
        if (results?.size != 0) {
            articleList.clear()
            results?.let { articleList.addAll(it) }
            notifyDataSetChanged()
        }
    }

    fun displayCreative(visxAdManager: VisxAdManager?) {
        if (visxAdManager != null && visxAdManager.adContainer != null) {
            (context as Activity).runOnUiThread {
                adViewContainer = visxAdManager.adContainer
                Timber.i("$INFEED_ADAPTER_TAG AD Ready for ADAPTER")
                notifyDataSetChanged()
            }
        }
    }

    override fun getItemCount(): Int {
        var additionalContent = 0
        if (articleList.size > 0 && LIST_AD_POSITION_INDEX > 0 && articleList.size > LIST_AD_POSITION_INDEX) {
            additionalContent = articleList.size / LIST_AD_POSITION_INDEX
        }
        return articleList.size + additionalContent
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        if (viewHolder is ContentHolder) {
            viewHolder.bind(articleList[getRealPosition(position)], getRealPosition(position))
        } else if (viewHolder is AdHolder) {
            viewHolder.setIsRecyclable(false)
            viewHolder.bind()
        }
    }

    private fun getRealPosition(position: Int): Int {
        return if (LIST_AD_POSITION_INDEX == 0) {
            position
        } else {
            position - position / LIST_AD_POSITION_INDEX
        }
    }

    override fun getItemViewType(position: Int): Int {
        if (position > 0 && position % LIST_AD_POSITION_INDEX == 0) {
            return AD
        }
        return CONTENT
    }

    inner class ContentHolder(private val binding: ItemListArticleBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private lateinit var listItem: ListItem
        private var pos: Int = 0

        fun bind(listItem: ListItem, position: Int) {
            this.listItem = listItem
            this.pos = position

            Timber.i("$INFEED_ADAPTER_TAG ViewHolder == DISPLAY CONTENT")
            binding.dummyText.text = listItem.dummyText
        }
    }

    inner class AdHolder(private val binding: ItemListCreativeAdBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind() {
            Timber.i("$INFEED_ADAPTER_TAG ViewHolder == DISPLAY AD")
            if (adViewContainer != null) {
                binding.containerItemCreative.visibility = View.VISIBLE
                binding.containerItemCreative.contentDescription = "visx_ad"
                val container =
                    binding.root.findViewById<RelativeLayout>(R.id.containerItemCreative)
                if (adViewContainer!!.parent != null) {
                    (adViewContainer!!.parent as ViewGroup).removeView(adViewContainer)
                }
                container.addView(adViewContainer)
            } else {
                Timber.w("Ad View Container is NULL")
            }
        }
    }
}