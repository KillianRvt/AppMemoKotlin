package com.formationandroid.fragments

import AppDB.AppDatabaseHelper
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.formationandroid.memokotlin.ItemTouchHelperCallback
import com.killianrvt.memoapplication.MemoDTO


class MainActivity : AppCompatActivity() {
    // Vues :
    private var recyclerView: RecyclerView? = null
    private var editTextMemo: EditText? = null
    private var frameLayoutConteneurDetail: FrameLayout? = null
    // Adapter :
    private var memosAdapter: MemosAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        // init :
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // vues :
        recyclerView = findViewById(R.id.liste_memos)
        editTextMemo = findViewById(R.id.saisie_memo)
        frameLayoutConteneurDetail = findViewById(R.id.conteneur_detail)

        val itemTouchHelper =
            memosAdapter?.let { ItemTouchHelperCallback(it) }?.let { ItemTouchHelper(it) }
        itemTouchHelper?.attachToRecyclerView(recyclerView)


        val button1 =
            findViewById<View>(R.id.bouton_valider) as Button
        val editText = findViewById<View>(R.id.saisie_memo) as EditText

        // à ajouter pour de meilleures performances :
        recyclerView?.setHasFixedSize(true)

        // layout manager, décrivant comment les items sont disposés :
        val layoutManager = LinearLayoutManager(this)
        recyclerView?.setLayoutManager(layoutManager)

        // récupérer une liste de memos :
        val listeMemosDTO =
            AppDatabaseHelper.getDatabase(this).memosDAO()!!.getListeMemos()?.toMutableList()

        // adapter :
        memosAdapter = MemosAdapter(this, listeMemosDTO)
        recyclerView?.setAdapter(memosAdapter)

        button1.setOnClickListener(View.OnClickListener { v ->
            listeMemosDTO?.add(MemoDTO(editText.getText().toString()))
            val memoDTO = MemoDTO(editText.getText().toString())
            AppDatabaseHelper.getDatabase(v.context).memosDAO()!!.insert(memoDTO)
            val toast = Toast.makeText(
                v.context,
                "Votre mémo a bien été ajouté!",
                Toast.LENGTH_SHORT
            )
            toast.show()
            memosAdapter!!.notifyDataSetChanged()
            // animation de repositionnement de la liste (sinon on ne voit pas l'item ajouté) :
            recyclerView?.smoothScrollToPosition(AppDatabaseHelper.getDatabase(this).memosDAO()!!.countMemos().toInt())
            editTextMemo!!.setText("")


        })
    }

    /**
     * Appelé lors du clic sur un item de la liste, depuis l'adapter.
     * @param position Position dans la liste d'objets métier (position à partir de zéro !)
     */
    fun onClicItem(position: Int) { // récupération du mémo à cette position :
        val memo = memosAdapter!!.getItemParPosition(position)
        // affichage du détail :
        if (frameLayoutConteneurDetail != null) { // fragment :
            val fragment = DetailFragment()
            val bundle = Bundle()
            bundle.putString(DetailFragment.EXTRA_MEMO, memo.intitule)
            fragment.arguments = bundle
            // le conteneur de la partie détail est disponible, on est donc en mode "tablette" :
            supportFragmentManager.beginTransaction().replace(R.id.conteneur_detail, fragment)
                .commit()
        } else { // le conteneur de la partie détail n'est pas disponible, on est donc en mode "smartphone" :
            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra(DetailFragment.EXTRA_MEMO, memo.intitule)
            startActivity(intent)
        }
    }


}