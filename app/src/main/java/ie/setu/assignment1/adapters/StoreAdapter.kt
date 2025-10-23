package ie.setu.assignment1.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ie.setu.assignment1.databinding.CardStoreBinding
import ie.setu.assignment1.models.StoreModel

interface StoreListener {
    fun onStoreClick(store: StoreModel)
}

class StoreAdapter(private var stores: List<StoreModel>, private val listener: StoreListener) :
    RecyclerView.Adapter<StoreAdapter.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        val binding = CardStoreBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return MainHolder(binding)
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val store = stores[holder.adapterPosition]
        holder.bind(store, listener)
    }

    override fun getItemCount(): Int = stores.size

    class MainHolder(private val binding : CardStoreBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(store: StoreModel, listener: StoreListener) {
            binding.storeName.text = store.name
            binding.description.text = store.description
            binding.root.setOnClickListener { listener.onStoreClick(store) }
        }
    }
}