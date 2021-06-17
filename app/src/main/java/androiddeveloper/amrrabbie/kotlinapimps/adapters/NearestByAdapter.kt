package androiddeveloper.amrrabbie.kotlinapimps.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androiddeveloper.amrrabbie.kotlinapimps.OnNearestByListClick
import androiddeveloper.amrrabbie.kotlinapimps.databinding.RestrantItemBinding
import androiddeveloper.amrrabbie.kotlinapimps.model.nearestby.ResultsItem
import androidx.recyclerview.widget.RecyclerView
import coil.load

class NearestByAdapter(val context:Context,val list:List<ResultsItem>,val onNearestByListClick: OnNearestByListClick) : RecyclerView.Adapter<NearestByAdapter.NearestByViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NearestByAdapter.NearestByViewHolder {
        return NearestByViewHolder(RestrantItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: NearestByAdapter.NearestByViewHolder, position: Int) {

        val item:ResultsItem=list.get(position)

        holder.binding.apply {
            name.text=item.name

            val types: List<String> = item.types

            var resttypes = ""

            for (i in types.indices) {
                resttypes = if (i < types.size - 1) {
                    resttypes + types[i] + " - "
                } else {
                    resttypes + types[i]
                }
            }

            type.text=resttypes;

            img.load(item.icon){
                crossfade(true)
                crossfade(1000)
            }

            holder.itemView.setOnClickListener { mview->
                onNearestByListClick.OnListClick(item)
            }

        }
    }

    override fun getItemCount()=list.size

    inner class NearestByViewHolder(val binding: RestrantItemBinding):
            RecyclerView.ViewHolder(binding.root)
}