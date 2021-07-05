package ipvc.ecgm.NoteMap.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ipvc.ecgm.NoteMap.viewModel.CellClickListener
import ipvc.ecgm.NoteMap.R
import ipvc.ecgm.NoteMap.entities.Nota

class NotaAdapter internal constructor(context: Context, private val CellClickListener: CellClickListener) : RecyclerView.Adapter<NotaAdapter.NotaViewHolder>(){

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var notas = emptyList<Nota>()

    class NotaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val NotaItemView: TextView = itemView.findViewById(R.id.textViewName)
        val NotaItemViewDesc: TextView = itemView.findViewById(R.id.textViewDesc)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotaViewHolder {
        val itemView = inflater.inflate(R.layout.recyclerview_item, parent, false)
        return NotaViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: NotaViewHolder, position: Int) {
        val current = notas[position]
        holder.NotaItemView.text = current.titulo
        holder.NotaItemViewDesc.text = current.descricao

        //vai buscar interfc para o onclik
        holder.itemView.setOnClickListener {
            CellClickListener.onCellClickListener(notas[position])
        }
    }

    internal fun  setNotas(notas: List<Nota>){
        this.notas = notas
        notifyDataSetChanged()
    }

    override fun getItemCount() = notas.size
}

