package kedaiapps.projeku.testandroidperqara.ui.home.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import kedaiapps.projeku.testandroidperqara.R
import kedaiapps.projeku.testandroidperqara.databinding.ItemHomeBinding
import kedaiapps.projeku.testandroidperqara.ext.inflate
import kedaiapps.projeku.testandroidperqara.services.entity.ResponseHome

class AdapterHome (
    private val onClick: (ResponseHome) -> Unit
) : RecyclerView.Adapter<AdapterHome.ViewHolder>() {

    var items: MutableList<ResponseHome> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(parent.inflate(R.layout.item_home))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        (holder as? ViewHolder)?.bind(items.getOrNull(position)!!)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    inner class ViewHolder(containerView: View) : RecyclerView.ViewHolder(containerView){
        private var binding = ItemHomeBinding.bind(containerView)

        fun bind(data: ResponseHome){
            with(binding){

                Glide.with(itemView.rootView).load(data.background_image)
                    .apply(
                        RequestOptions()
                            .transform(RoundedCorners(16))
                            .skipMemoryCache(true)
                            .diskCacheStrategy(DiskCacheStrategy.NONE)
                            .dontAnimate()
                    ).into(binding.image)

                binding.judul.text = data.name
                binding.date.text = "Release date "+data.released
                binding.rating.text = data.rating

                line.setOnClickListener {
                    onClick(data)
                }
            }
        }
    }

    fun insertData(data : List<ResponseHome>){
        data.forEach {
            items.add(it)
            notifyDataSetChanged()
        }
    }

    fun clearData() {
        if (items.isNotEmpty()) {
            items.clear()
            notifyDataSetChanged()
        }
    }
}