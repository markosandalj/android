package com.example.digimonlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import android.widget.ImageView
import android.widget.TextView

class DetailFragment : Fragment() {

    companion object {
        private const val ARG_DIGIMON = "digimon"

        fun newInstance(digimon: Digimon): DetailFragment {
            val fragment = DetailFragment()
            val args = Bundle()
            args.putParcelable(ARG_DIGIMON, digimon)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_detail, container, false)
        val digimon = arguments?.getParcelable<Digimon>(ARG_DIGIMON)

        val imageViewDetail: ImageView = view.findViewById(R.id.imageViewDetail)
        val textViewName: TextView = view.findViewById(R.id.textViewName)
        val textViewType: TextView = view.findViewById(R.id.textViewType)

        digimon?.let {
            val context = requireContext()
            val resId = context.resources.getIdentifier(it.imageResId, "drawable", context.packageName)
            imageViewDetail.setImageResource(resId)
            textViewName.text = it.name
            textViewType.text = it.type
        }

        return view
    }
}
