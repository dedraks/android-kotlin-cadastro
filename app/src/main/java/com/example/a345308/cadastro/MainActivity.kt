package com.example.a345308.cadastro

import android.content.DialogInterface
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.view.KeyEvent
import android.view.Menu
import android.view.MenuItem
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.aluno_item.view.*
import kotlinx.android.synthetic.main.content_main.*
import org.jetbrains.anko.*

class MainActivity : AppCompatActivity() {

    val list : MutableList<Aluno> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->

            createCreateAlunoDialog()
        }

        self = this


        recyclerView.adapter = MyAdapter(list, this)
        recyclerView.layoutManager = LinearLayoutManager(this)


        initSwipe()

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }


    fun createCreateAlunoDialog(aluno: Aluno? = null) {

        val index = list.indexOf(aluno)



       with(aluno) {
           lateinit var tNome : TextView
           lateinit var tEmail : TextView
           lateinit var tTelefone : TextView
           lateinit var tEndereco : TextView
           lateinit var dialog: DialogInterface

           fun submit() {
               if (aluno == null) {
                   list.add(Aluno(tNome.text.toString(), tEmail.text.toString(), tTelefone.text.toString(), tEndereco.text.toString()))
               }
               else {

                   list.set(index,Aluno(tNome.text.toString(), tEmail.text.toString(), tTelefone.text.toString(), tEndereco.text.toString()))
               }
               recyclerView.adapter.notifyDataSetChanged()
           }

           dialog = alert {
               title = if (aluno != null) "Editar" else "Cadastrar"
               isCancelable = false

               customView  {
                   verticalLayout {
                       tNome = editText {
                           hint = "Digite o nome"
                           setText( aluno?.nome ?: ""  ) // if (aluno != null) aluno.nome else ""
                           inputType = android.text.InputType.TYPE_CLASS_TEXT
                       }
                       tEndereco = editText {
                           hint = "Digite o endereço"
                           setText( aluno?.endereco ?: "" )
                           inputType = android.text.InputType.TYPE_CLASS_TEXT
                       }
                       tTelefone = editText {
                           hint = "Digite o telefone"
                           setText( aluno?.telefone ?: "" )
                           inputType = android.text.InputType.TYPE_CLASS_PHONE
                       }
                       tEmail = editText {
                           hint = "Digite o email"
                           setText( aluno?.email ?: "" )
                           inputType = android.text.InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
                           setOnEditorActionListener { v, actionId, event ->
                               if (actionId == EditorInfo.IME_NULL && event.action == KeyEvent.ACTION_DOWN) {
                                   submit()
                                   dialog.dismiss()
                               }
                               true
                           }
                       }
                   }
               }

               yesButton {
                   submit()
               }
               cancelButton {  }
           }.show()

       }
    }


    fun initSwipe() {
        val simpleItemTouchCallback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(recyclerView: RecyclerView?, viewHolder: RecyclerView.ViewHolder?, target: RecyclerView.ViewHolder?): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder?, direction: Int) {
                val position = viewHolder!!.adapterPosition


                alert("Tem certeza?", "Excluir") {
                    isCancelable = false
                    yesButton {
                        list.removeAt(position)
                        recyclerView.adapter.notifyDataSetChanged()
                    }
                    noButton {
                        recyclerView.adapter.notifyDataSetChanged()
                    }
                }.show()

                //recyclerView.adapter.notifyDataSetChanged()

            }
        }

        val itemTouchHelper = ItemTouchHelper(simpleItemTouchCallback)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }

    companion object {
        lateinit var self: MainActivity
    }
}
