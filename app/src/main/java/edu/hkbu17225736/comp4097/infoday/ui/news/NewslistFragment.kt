package edu.hkbu17225736.comp4097.infoday.ui.news

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import edu.hkbu17225736.comp4097.infoday.Network
import edu.hkbu17225736.comp4097.infoday.R
import edu.hkbu17225736.comp4097.infoday.data.News
import kotlinx.coroutines.*
import java.io.IOException


/**
 * A fragment representing a list of Items.
 */
class NewslistFragment : Fragment() {

    private var columnCount = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            columnCount = it.getInt(ARG_COLUMN_COUNT)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        val recyclerView = inflater.inflate(R.layout.fragment_news_list, container, false) as RecyclerView
//
//        // Set the adapter
//        recyclerView.layoutManager = LinearLayoutManager(context)
//        reload(recyclerView)
//        return recyclerView
        val recyclerView = inflater.inflate(R.layout.fragment_news_list, null, false) as RecyclerView
        val swipeLayout = SwipeRefreshLayout(requireContext())
        swipeLayout.addView(recyclerView)
        swipeLayout.setOnRefreshListener {
            swipeLayout.isRefreshing = true
            reloadData(recyclerView)
            swipeLayout.isRefreshing = false
        }
        recyclerView.layoutManager = LinearLayoutManager(context)
        reloadData(recyclerView)
        return swipeLayout

    }

//    private fun reloadData(recyclerView: RecyclerView) {
//        val NEWS_URL = "https://api.npoint.io/256da2ee7badc12b0ec2"
//        CoroutineScope(Dispatchers.IO).launch {
//            try {
//                val json = async {
//                    try {
//                        Network.getTextFromNetwork(NEWS_URL)
//                    }catch (e: Exception){
//                        println("Error in getTextFromNetwork: $e")
//                        null
//                    }
//                }
//                withTimeout(1500L){
//                    val news =
//                        Gson().fromJson<List<News>>(json.await(), object : TypeToken<List<News>>() {}.type)
//                    if (news == null)
//                        throw IOException("getting error in network")
//                    CoroutineScope(Dispatchers.Main).launch {
//                        recyclerView.adapter = NewsRecyclerViewAdapter(news)
//                    }
//                }
//
//            } catch (e: Exception) {
//                Log.d("NewsListFragment", "Error in loading data")
//                val news = listOf(News("", "Cannot fetch news", "Please check your network connection,"))
//                CoroutineScope(Dispatchers.Main).launch {
//                    recyclerView.adapter = NewsRecyclerViewAdapter(news)
//                }
//            }
//        }
//    }
    private fun reloadData(recyclerView: RecyclerView) {
        val db = FirebaseFirestore.getInstance()

        db.collection("news").addSnapshotListener { value, error ->
            value?.let {
                CoroutineScope(Dispatchers.Main).launch {
                    recyclerView.adapter = NewsRecyclerViewAdapter(it.documents.map { doc ->
                        News(doc.getString("image")!!,
                            doc.getString("title")!!,
                            doc.getString("detail")!!)
                    })
                }
            }
        }
    }

    companion object {

        // TODO: Customize parameter argument names
        const val ARG_COLUMN_COUNT = "column-count"

        // TODO: Customize parameter initialization
        @JvmStatic
        fun newInstance(columnCount: Int) =
            NewslistFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_COLUMN_COUNT, columnCount)
                }
            }
    }
}