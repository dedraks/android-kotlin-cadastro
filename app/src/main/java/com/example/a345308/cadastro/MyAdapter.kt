package com.example.a345308.cadastro

import android.content.Context
import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.View
import kotlinx.android.synthetic.main.aluno_item.view.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import android.widget.Filter
import android.widget.Filterable

class MyAdapter(private var alunosList: MutableList<Aluno>, private val context: Context): RecyclerView.Adapter<MyAdapter.MyViewHolder>(), Filterable {

    var mFilter: MyFilter? = null
    var mFilterList = mutableListOf<Aluno>()

    override fun getFilter(): Filter {
        if (mFilter == null) mFilter = MyFilter(this, alunosList)

        return mFilter!!
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.aluno_item, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount() = alunosList.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.binView(alunosList[position])
    }

    fun updateList(list: MutableList<Aluno>) {
        this.alunosList = list
        this.mFilterList = list.toMutableList()
    }

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val nome = itemView.textViewNome
        val endereco = itemView.textViewEndereco
        val telefone = itemView.textViewTelefone
        val email = itemView.textViewEmail

        fun binView(aluno: Aluno) {
            nome.text = aluno.nome
            endereco.text = aluno.endereco
            telefone.text = aluno.telefone
            email.text = aluno.email

            if (position %2 == 1) {
                itemView.setBackgroundColor(Color.parseColor("#FFDDFFEE"))
            }
            else {
                itemView.setBackgroundColor(Color.parseColor("#FFDDEEFF"))
            }

            itemView.onClick {
                MainActivity.self.createCreateAlunoDialog(aluno)
            }
        }
    }

    class MyFilter(val adapter: MyAdapter, val filterList: MutableList<Aluno>) : Filter() {
        override fun performFiltering(constraint: CharSequence?): FilterResults {
            var results = FilterResults()

            constraint?.let {
                if (it.length > 0) {
                    val filtered = mutableListOf<Aluno>()
                    for (item in filterList) {
                        if (item.nome.contains(constraint, true)) {
                            filtered.add(item)
                        }
                    }
                    results.values = filtered
                    results.count = filtered.size
                }
                else {
                    results.values = filterList
                    results.count = filterList.size
                }
            }


            return results
        }

        override fun publishResults(p0: CharSequence?, results: FilterResults?) {
            adapter.updateList(results?.values as MutableList<Aluno>)
            adapter.notifyDataSetChanged()
        }

    }
}

