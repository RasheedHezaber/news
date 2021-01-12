package com.rasheed.news


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
//import com.bumptech.glide.
import com.rasheed.news.models.News
import java.util.*


private val TAP_INDEX="IndexTap"
class NewsListFrag : Fragment() {
    private lateinit var newsView: NewsView
    private lateinit var typeView: TypeViewModle
    lateinit var newsRecycelerView:RecyclerView
    var tabIndex:Int = 0
    private var adapter = NewsAdapter(emptyList())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let { tabIndex=  it.getInt(TAP_INDEX)
            MainActivity.categoryId =tabIndex

        }
        newsView = ViewModelProviders.of(this).get(NewsView::class.java)
      typeView = ViewModelProviders.of(this).get(TypeViewModle::class.java)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view=inflater.inflate(R.layout.fragment_news, container, false)

        newsRecycelerView=view.findViewById(R.id.news_recycler) as RecyclerView
        newsRecycelerView.layoutManager=LinearLayoutManager(context)
        newsRecycelerView.adapter=adapter

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        newsView.newsLiveData.observe(
            this,
            Observer {
                newsRecycelerView.adapter=NewsAdapter(it)

            }
        )
    }

    companion object {

        @JvmStatic
        fun newInstance(tapIndex:Int) =
            NewsListFrag().apply {
                arguments = Bundle().apply {
                    putInt(TAP_INDEX,tapIndex)

                }
            }
    }
    private inner class NewsHolder(view: View) : RecyclerView.ViewHolder(view) ,View.OnClickListener {
        val imageButton = view.findViewById(R.id.image) as ImageButton
        val cardv = view.findViewById(R.id.cview) as CardView
        val titleTextView = view.findViewById(R.id.title) as TextView
        val contentTextView = view.findViewById(R.id.details) as TextView
        val dateTextView = view.findViewById(R.id.date) as TextView

        var news= News()
        fun bindData(news: News){
            this.news=news
            val url="http://192.168.1.105/news/images/"
            val img=url+this.news.image;


            cardv.background= Glide.with(this.itemView).load(img).into(imageButton).view.background



            if (this.news.title.length>40)
                titleTextView.text=this.news.title
            else titleTextView.text=this.news.title

            if (this.news.details.length>50)
            contentTextView.text=this.news.details
            else contentTextView.text=this.news.details

            dateTextView.text=setDateAndTime(this.news.date)

        }
        fun setDateAndTime(dateTime: Date):String {
            val calendar = Calendar.getInstance()
            calendar.time = dateTime

            val stringDate = "${calendar.get(Calendar.YEAR)}-" +
                    "${calendar.get(Calendar.MONTH)}-" +
                    "${calendar.get(Calendar.DAY_OF_MONTH)}"+
                    "${calendar.get(Calendar.HOUR_OF_DAY)}"+
                    "${calendar.get(Calendar.MINUTE)}"
            return stringDate
        }


        override fun onClick(v: View?) {

        }
    }
    private inner class NewsAdapter(val newsList:List<News>):RecyclerView.Adapter<NewsHolder>(){
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsHolder {
            var view: View
            view = layoutInflater.inflate(R.layout.news_list, parent, false)
            return NewsHolder(view)

        }

        override fun onBindViewHolder(holder: NewsHolder, position: Int) {
            val news=newsList[position]
            holder.bindData(news)

        }

        override fun getItemCount(): Int {
            return newsList.size
        }

    }

    }