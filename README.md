Main Activity

# Basic Step By Step

### Gradle - app level

```kotlin
implementation 'com.google.android.gms:play-services-ads:19.6.0'
```

### Manifest

- `<uses-permission android:name="android.permission.INTERNET" />`
- `meta data`
- `hardwareAccelerated=true`

```xml
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    package="com.paigesoftware.googld_native_ad">

    <uses-permission android:name="android.permission.INTERNET" />
    <application
        android:hardwareAccelerated="true"
        android:allowBackup="true"
        android:icon="@mipmap/ic_launcher"
        android:label="@string/app_name"
        android:roundIcon="@mipmap/ic_launcher_round"
        android:supportsRtl="true"
        android:theme="@style/Theme.Googld_native_ad">
        <activity android:name=".MainActivity">
            <intent-filter>
                <action android:name="android.intent.action.MAIN" />

                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>
        </activity>

        <!-- Sample AdMob app ID: ca-app-pub-3940256099942544~3347511713 -->
        <meta-data
            android:name="com.google.android.gms.ads.APPLICATION_ID"
            android:value="ca-app-pub-3940256099942544~3347511713" />

    </application>

</manifest>
```

### Import Templates

- [https://github.com/googleads/googleads-mobile-android-native-templates](https://github.com/googleads/googleads-mobile-android-native-templates)

![https://s3-us-west-2.amazonaws.com/secure.notion-static.com/f9c775ad-3692-4c49-80f8-7275969c218e/Untitled.png](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/f9c775ad-3692-4c49-80f8-7275969c218e/Untitled.png)

- app level gradle

```kotlin
implementation project(':nativetemplates')
```

### Layout

- `@layout/gnt_small_template_view`
- `@layout/gnt_medium_template_view`

```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".MainActivity"
    android:orientation="vertical"
    tools:showIn="@layout/activity_main" >

    <!--  This is your template view -->
    <com.google.android.ads.nativetemplates.TemplateView
        android:id="@+id/my_template"
        app:gnt_template_type="@layout/gnt_small_template_view"
        android:layout_width="match_parent"
        android:layout_height="wrap_content" />

    <TextView
        android:text="hello wrold"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"/>

</LinearLayout>
```

### On Activity

```kotlin
class MainActivity : AppCompatActivity() {

    lateinit var adLoader: AdLoader

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        MobileAds.initialize(this)
        adLoader = AdLoader.Builder(this, "ca-app-pub-3940256099942544/2247696110")
                .forUnifiedNativeAd { ad: UnifiedNativeAd ->
                    // Show the ad.
                    val styles = NativeTemplateStyle.Builder()
                        .withMainBackgroundColor(ColorDrawable(Color.WHITE)).build();

                    val template = my_template
                    template.setStyles(styles);
                    template.setNativeAd(ad);

                    if (isDestroyed) {
                        ad.destroy()
                        return@forUnifiedNativeAd
                    }

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
                .withNativeAdOptions(NativeAdOptions.Builder()
                        // Methods in the NativeAdOptions.Builder class can be
                        // used here to specify individual options settings.
                     .build())
                .build()

//        adLoader.loadAd(AdRequest.Builder().build())
        adLoader.loadAds(AdRequest.Builder().build(), 3)

    }

}
```

# With RecyclerView

### Initialize data with `%` operator

```kotlin
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val arrayList = ArrayList<String?>()

        for(i in 0..200) {
            if(i % 30 == 0) {
                arrayList.add(null)
            } else {
                arrayList.add("text $i")
            }
        }

        val adapter = MyAdapter(this, arrayList)
        recyclerview.adapter = adapter
        recyclerview.layoutManager = LinearLayoutManager(this)

    }
    
}
```

### adapter with `viewtype`

```kotlin
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
```