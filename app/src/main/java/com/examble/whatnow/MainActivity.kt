package com.examble.whatnow


import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import com.examble.whatnow.API.ApiManager
import com.examble.whatnow.API.ArticlesItem
import com.examble.whatnow.API.NewsResponse
import com.examble.whatnow.API.Source
import com.examble.whatnow.API.SourcesPackage.NewsSources
import com.examble.whatnow.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayout
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    lateinit var viewbinding: ActivityMainBinding
    var currentSourceId: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        // https://newsapi.org/v2/top-headlines?country=us&category=general&apiKey=b18571bc6ee24e2ba006503342700cce&pageSize=30
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        viewbinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(viewbinding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        getSources()
        viewbinding.swipeRefresh.setOnRefreshListener {
            getNewsResponse(currentSourceId)
        }
    }
    private fun getSources(){
        viewbinding.progressBar.isVisible = true
        ApiManager
            .getApis()
            .getSources()
            .enqueue(object : Callback<NewsSources> {

                override fun onResponse(
                    call: Call<NewsSources?>?,
                    response: Response<NewsSources>
                ) {
                    viewbinding.progressBar.isVisible = false
                    viewbinding.swipeRefresh.isRefreshing=false
                    if (response.isSuccessful) {
                        bindTabs(response.body()?.sources)
                        return
                    }
                        val errorResponseFromJson = response.errorBody()?.string()
                        val response =
                            Gson().fromJson(errorResponseFromJson, NewsSources::class.java)
                        showMessage(
                            message = response.message ?: "Something Went Wrong",
                            posActionName = "Try Again",
                            posAction = { dialogInterface, i ->
                                getSources()
                                dialogInterface.dismiss()
                            },
                            negActionName = "cancel",
                            negAction = { dialogInterface, i ->
                                dialogInterface.dismiss()
                            },
                        )

                }



                override fun onFailure(
                    call: Call<NewsSources?>?,
                    t: Throwable
                ) {
                    viewbinding.progressBar.isVisible = false
                    viewbinding.swipeRefresh.isRefreshing=false
                  handleError(t){
                     getSources()
                  }
                }

            })
    }
    private fun getNewsResponse(sourceId :String) {
        currentSourceId = sourceId
        viewbinding.progressBar.isVisible = true
        viewbinding.swipeRefresh.isRefreshing=false
        ApiManager
            .getApis()
            .getNewsResponse( pageSize = 30, sources = sourceId)
            .enqueue(object : Callback<NewsResponse> {
                override fun onResponse(
                    call: Call<NewsResponse>,
                    response: Response<NewsResponse>
                ) {
                    viewbinding.progressBar.isVisible = false
                    if (response.isSuccessful) {
                        val articles= response.body()?.articles
                            ?.filter { it?.title != "[Removed]" }
                        setupRecycler(articles)
                    } else {
                        val errorResponseFromJson = response.errorBody()?.string()
                        val response =
                            Gson().fromJson(errorResponseFromJson, NewsResponse::class.java)
                      handleError(response.message){
                          getNewsResponse(sourceId)
                      }
                    }

                }
                override fun onFailure(
                    call: Call<NewsResponse?>?,
                    t: Throwable
                ) {
                    viewbinding.progressBar.isVisible = false
                    viewbinding.swipeRefresh.isRefreshing=false
                    handleError(t){
                        getNewsResponse(sourceId)
                    }
                }

            })
    }
    private fun bindTabs(sources: List<Source>?) {

        if (sources == null) return
        viewbinding.tablayout.removeAllTabs()
        sources.forEach {
            val tab = viewbinding.tablayout.newTab()
            tab.text = it.name
            tab.tag=it
            viewbinding.tablayout.addTab(tab)
        }
        viewbinding.tablayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
               val source = tab?.tag as Source
                getNewsResponse(source.id?:"")

            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
                val source = tab?.tag as Source
                getNewsResponse(source.id?:"")
            }

        })
        viewbinding.tablayout.getTabAt(0)?.select()

    }
    private fun setupRecycler(list: List<ArticlesItem?>?) {
        if (list == null) return
        viewbinding.recyclerView.apply {
            adapter = articlesAdapter(list) { item ->
               val i = Intent(Intent.ACTION_VIEW,item.url?.toUri())
                startActivity(i)
            }
        }
    }
    fun interface OnTryAgainClickListener{
       fun  onTryAgainClick()
    }
    fun handleError(t: Throwable,onClick: OnTryAgainClickListener){
        showMessage(
            message = t.localizedMessage ?: "Something Went Wrong",
            posActionName = "Try Again",
            posAction = { dialogInterface, i ->
                onClick.onTryAgainClick()
                dialogInterface.dismiss()
            },
            negActionName = "cancel",
            negAction = { dialogInterface, i ->
                dialogInterface.dismiss()
            },
        )
    }
    fun handleError(message:String?,onClick: OnTryAgainClickListener){
        showMessage(
            message = message ?: "Something Went Wrong",
            posActionName = "Try Again",
            posAction = { dialogInterface, i ->
                onClick.onTryAgainClick()
                dialogInterface.dismiss()
            },
            negActionName = "cancel",
            negAction = { dialogInterface, i ->
                dialogInterface.dismiss()
            },
        )
    }
}