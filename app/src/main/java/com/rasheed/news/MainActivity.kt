package com.rasheed.news

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.rasheed.news.api.Api
import com.rasheed.news.models.Type
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity() {
/// 192.168.1.105/news/apis/news_api.php
    lateinit var tabLayout: TabLayout
    lateinit var viewPager: ViewPager2
    lateinit var adabter: PagerAdabter

    companion object{
          var categoryId:Int=0
    }
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tabLayout = findViewById(R.id.main_tab_layout)
        viewPager = findViewById(R.id.main_view_Pager)
        adabter = PagerAdabter(this)

       var  typeApi: Api =
           NewsGetter().api

       var listCall:Call<List<Type>> =typeApi.fetchNewsType()

        listCall.enqueue(object : Callback<List<Type>> {
            override fun onResponse(call: Call<List<Type>>, response: Response<List<Type>>) {

                adabter.addTab(TabsNews("global", NewsListFrag.newInstance(0)))

                var  typeList = response.body()?.forEach {
                   adabter.addTab(TabsNews(it.typeName, NewsListFrag.newInstance(it.typeId)))
              }
                viewPager.adapter = adabter
                TabLayoutMediator(tabLayout, viewPager) { tap, postion ->
                    tap.text=adabter.tabs[postion].tabName
                }.attach()

            }
            override fun onFailure(call: Call<List<Type>>, t: Throwable) {

            }
        })

}


    class PagerAdabter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {
        val tabs = ArrayList<TabsNews>()
        override fun getItemCount(): Int {
            return tabs.size

        }

        override fun createFragment(position: Int): Fragment {
            return tabs.get(position).fagment

        }


        fun addTab(tab: TabsNews) {
            tabs.add(tab)
        }

    }
}