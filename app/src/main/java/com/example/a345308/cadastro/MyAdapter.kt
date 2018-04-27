package com.example.a345308.cadastro

import android.content.Context
import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.View
import kotlinx.android.synthetic.main.aluno_item.view.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import kotlin.coroutines.experimental.CoroutineContext

class MyAdapter(private val alunos: List<Aluno>, private val context: Context): RecyclerView.Adapter<MyAdapter.MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.aluno_item, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount() = alunos.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.binView(alunos[position])
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
}

