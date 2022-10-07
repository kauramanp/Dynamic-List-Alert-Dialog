package com.aman.dynamiclist

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.aman.dynamiclist.databinding.FragmentDynamicBinding
import com.aman.dynamiclist.databinding.LayoutUpdateBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [DynamicFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DynamicFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var binding: FragmentDynamicBinding
    var arrayList = arrayListOf("one","two","three")
    lateinit var arrayAdapter: ArrayAdapter<String>
    lateinit var mainActivity: MainActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentDynamicBinding.inflate(layoutInflater)
        mainActivity = activity as MainActivity
        arrayAdapter = ArrayAdapter(mainActivity, android.R.layout.simple_expandable_list_item_1, arrayList)
        binding.listView.adapter = arrayAdapter
        binding.btnAdd.setOnClickListener {
            if(binding.etName.text.toString().isNullOrEmpty()){
                binding.etName.error = resources.getString(R.string.enter_item_to_add)
            }else{
                arrayList.add(binding.etName.text.toString())
                binding.etName.text.clear()
                arrayAdapter.notifyDataSetChanged()
            }
        }

        binding.listView.setOnItemClickListener { adapterView, view, i, l ->
            var dialogUpdateBinding: LayoutUpdateBinding = LayoutUpdateBinding.inflate(layoutInflater)
            var dialog = Dialog(mainActivity)
            dialog.setContentView(dialogUpdateBinding.root)
            dialog.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT)
            dialogUpdateBinding.etUpdateText.setText(arrayList.get(i))
            dialogUpdateBinding.btnUpdate.setOnClickListener {
                if(dialogUpdateBinding.etUpdateText.text.toString().isNullOrEmpty()){
                    dialogUpdateBinding.etUpdateText.error = resources.getString(R.string.enter_item_to_add)
                }else{
                    arrayList.set(i,dialogUpdateBinding.etUpdateText.text.toString())
                    arrayAdapter.notifyDataSetChanged()
                    dialog.dismiss()
                }
            }
            dialogUpdateBinding.btnDelete.setOnClickListener {
                arrayList.removeAt(i)
                arrayAdapter.notifyDataSetChanged()
                dialog.dismiss()
            }
            dialog.show()
        }
        return binding.root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment DynamicFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            DynamicFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}