package com.paigesoftware.googld_native_ad

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.ads.nativetemplates.NativeTemplateStyle
import com.google.android.gms.ads.*
import com.google.android.gms.ads.formats.NativeAdOptions
import com.google.android.gms.ads.formats.UnifiedNativeAd
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item_ad.view.*
import kotlinx.android.synthetic.main.item_list.view.*

class MyAdapter(
    private val context: Context,
    private val itemList: ArrayList<String?>
): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    lateinit var adLoader: AdLoader

    companion object {
        const val VIEW_TYPE_ITEM = 1
        const val VIEW_TYPE_AD = 2
    }

    class AdViewHolder(view: View): RecyclerView.ViewHolder(view) {}

    class ItemViewHolder(view: View): RecyclerView.ViewHolder(view){}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        val layoutInflater = LayoutInflater.from(parent.context)
        val view: View

        if(viewType == VIEW_TYPE_ITEM) {
            view = layoutInflater.inflate(R.layout.item_list, parent, false)
            return ItemViewHolder(view)
        } else {
            view = layoutInflater.inflate(R.layout.item_ad, parent, false)
            return AdViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        if(holder is AdViewHolder) {

            if(position % 20 == 0) {
                MobileAds.initialize(context)
                adLoader = AdLoader.Builder(context, "ca-app-pub-3940256099942544/2247696110")
                    .forUnifiedNativeAd { ad: UnifiedNativeAd ->
                        // Show the ad.
                        val styles = NativeTemplateStyle.Builder()
                            .withMainBackgroundColor(ColorDrawable(Color.WHITE)).build();

                        val template = holder.itemView.my_template
                        template.setStyles(styles);
                        template.setNativeAd(ad);

//                    if (context.isDestroyed) {
//                        ad.destroy()
//                        return@forUnifiedNativeAd
//                    }

                        if (adLoader.isLoading) {
                            // The AdLoader is still loading ads.
                            // Expect more adLoaded or onAdFailedToLoad callbacks.
                        } else {
                            // The AdLoader has finished loading ads.
                        }

                    }
                    .withAdListener(object : AdListener() {
                        override fun onAdFailedToLoad(adError: LoadAdError) {
                            // Handle the failure by logging, altering the UI, and so on.
                        }

                        override fun onAdOpened() {
                            super.onAdOpened()
                        }

                        override fun onAdClicked() {
                            super.onAdClicked()
                        }

                        override fun onAdClosed() {
                            super.onAdClosed()
                        }

                        override fun onAdImpression() {
                            super.onAdImpression()
                        }

                        override fun onAdLoaded() {
                            super.onAdLoaded()
                        }

                    })
                    .withNativeAdOptions(
                        NativeAdOptions.Builder()
                            // Methods in the NativeAdOptions.Builder class can be
                            // used here to specify individual options settings.
                            .build())
                    .build()
//                    adLoader.loadAd(AdRequest.Builder().build())
                adLoader.loadAds(AdRequest.Builder().build(), 3)
            }


        }

        if(holder is ItemViewHolder) {
            holder.itemView.textview.text = itemList[position]
        }

    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun getItemViewType(position: Int): Int {
        return if(itemList[position] == null) {
            VIEW_TYPE_AD
        } else {
            VIEW_TYPE_ITEM
        }
    }



}