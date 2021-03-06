package edu.hkbu17225736.comp4097.infoday.ui.news

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso
import edu.hkbu17225736.comp4097.infoday.R
import edu.hkbu17225736.comp4097.infoday.data.News

/**
 * [RecyclerView.Adapter] that can display a [News].
 */
class NewsRecyclerViewAdapter(
    private val values: List<News>
) : RecyclerView.Adapter<NewsRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_news_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
//        holder.idView.text = item.id
//        holder.contentView.text = item.content
//        Picasso.get().load(item.image).into(holder.newsImageView)
        holder.titleTextView.text = item.title
        holder.detaiTextlView.text = item.detail

        if (item.image != "")
            Picasso.get().load(item.image).into(holder.newsImageView)
        else
            holder.newsImageView.setImageDrawable(holder.itemView.context.getDrawable(R.drawable.ic_baseline_cloud_download_24))

    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val titleTextView: TextView = view.findViewById(R.id.titleTextView)
        val detaiTextlView: TextView = view.findViewById(R.id.detailTextView)
        val newsImageView: ImageView = view.findViewById(R.id.newsImageView)

        override fun toString(): String {
            return super.toString() + " '" + titleTextView.text + "'"
        }
    }
}