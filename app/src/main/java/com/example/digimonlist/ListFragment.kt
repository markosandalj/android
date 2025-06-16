package com.example.digimonlist

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore

class ListFragment : Fragment() {

    private var listener: OnDigimonSelectedListener? = null
    private lateinit var db: FirebaseFirestore
    private lateinit var recyclerView: RecyclerView
    private lateinit var digimonAdapter: DigimonAdapter
    private var digimonList: MutableList<Digimon> = mutableListOf()

    interface OnDigimonSelectedListener {
        fun onDigimonSelected(digimon: Digimon)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnDigimonSelectedListener) {
            listener = context
        } else {
            throw RuntimeException("$context must implement OnDigimonSelectedListener")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_list, container, false)
        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(context)
        digimonAdapter = DigimonAdapter(digimonList) { digimon ->
            listener?.onDigimonSelected(digimon)
        }
        recyclerView.adapter = digimonAdapter

        db = FirebaseFirestore.getInstance()
        loadDigimonFromFirestore()

        return view
    }

    private fun loadDigimonFromFirestore() {
        db.collection("digimons")
            .get()
            .addOnSuccessListener { result ->
                digimonList.clear()
                for (document in result) {
                    val digimon = document.toObject(Digimon::class.java)
                    digimonList.add(digimon)
                }
                digimonAdapter.notifyDataSetChanged()
            }
            .addOnFailureListener { e ->
                Log.w("ListFragment", "Error getting documents", e)
            }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }
}
