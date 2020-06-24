package com.formationandroid.fragments

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.formationandroid.fragments.MemosAdapter.MemoViewHolder
import com.killianrvt.memoapplication.MemoDTO
import java.util.Collections.swap


class MemosAdapter(
    mainActivity: MainActivity?,
    listeMemos: List<MemoDTO?>?
) :
    RecyclerView.Adapter<MemoViewHolder>() {
    // Activité :
    private var mainActivity: MainActivity? = null
    // Liste d'objets métier :
    private var listeMemos: List<MemoDTO>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MemoViewHolder {
        val viewMemo: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_memo, parent, false)
        return MemoViewHolder(viewMemo)
    }

    override fun onBindViewHolder(holder: MemoViewHolder, position: Int) {
        holder.textViewIntitule!!.text = listeMemos!![position].intitule
    }

    override fun getItemCount(): Int {
        return listeMemos!!.size
    }

    /**
     * Retourne le mémo à la position voulue.
     * @param position Position
     * @return Mémo
     */
    fun getItemParPosition(position: Int): MemoDTO {
        return listeMemos!![position]
    }

    /**
     * ViewHolder.
     */
    inner class MemoViewHolder(itemView: View) :
        ViewHolder(itemView) {
        // Vue intitulé mémo :
        var textViewIntitule: TextView? = null

        /**
         * Constructeur.
         * @param itemView Vue item
         */
        init {
            textViewIntitule = itemView.findViewById(R.id.memo_intitule)
            // listener :
            itemView.setOnClickListener { mainActivity?.onClicItem(adapterPosition) }
        }
    }

    /**
     * Constructeur.
     * @param mainActivity MainActivity
     * @param listeMemos Liste de mémos
     */
    init {
        this.mainActivity = mainActivity
        this.listeMemos = listeMemos as List<MemoDTO>?
    }

    // Appelé à chaque changement de position, pendant un déplacement.
    fun onItemMove(positionDebut: Int, positionFin: Int): Boolean {
        swap(listeMemos, positionDebut, positionFin)
        notifyItemMoved(positionDebut, positionFin)
        return true
    }

    // Appelé une fois à la suppression.
    fun onItemDismiss(position: Int) {
        if (position > -1) {
            listeMemos?.toMutableList()?.removeAt(position)
            notifyItemRemoved(position)
        }
    }

}