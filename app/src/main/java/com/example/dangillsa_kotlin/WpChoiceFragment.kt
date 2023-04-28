package com.example.dangillsa_kotlin

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.google.android.material.navigation.NavigationView

class WpChoiceFragment : Fragment() {

    private lateinit var button: Button
    private lateinit var spinner: Spinner
    private lateinit var adapter: ArrayAdapter<String>
    private lateinit var editText: EditText
    private var menu: Menu? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_wp_choice, container, false)

        val list: MutableList<String> = ArrayList()
//        for (i in 1..1)
        list.add("첨단 스마트빌아파트")

        spinner = view.findViewById(R.id.spn)
        adapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, list)
        spinner.adapter = adapter

        adapter.insert("선택해주세요.", 0)

        button = view.findViewById(R.id.wpchoice_btn)
        button.visibility = View.INVISIBLE


        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if (position != 0) {
                    button.visibility = View.VISIBLE
                } else {
                    button.visibility = View.INVISIBLE
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        val addBtn: TextView = view.findViewById(R.id.add_wp_btn)
        addBtn.setOnClickListener {
            showAddDialog()
        }



        button.setOnClickListener {
            val selectedPosition = spinner.selectedItemPosition
            if (selectedPosition > 0) {
                val nextFragment = MainFragment()
                parentFragmentManager.beginTransaction()
                    .replace(R.id.fragment, nextFragment)
                    .addToBackStack(null)
                    .commit()
            }

            //    <-- 스피너에서 선택된 위치가 0보다 크면 다음 화면으로 이동하는 코드 -->
        }

        setHasOptionsMenu(true)
        return view
    }



    private fun showAddDialog() {
        val list: MutableList<String> = ArrayList()
//        for (i in 1..1) // 다이어로그의 리스트
            list.add("봉선 리츠오피스텔")

        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("현장구역 목록")
        builder.setItems(list.toTypedArray()) { _, position ->
            val newItemTitle = list[position]
            adapter.add(newItemTitle)

            updateMenu(newItemTitle, true) // Set the visibility of nav_new_item to true
            spinner.setSelection(0)

            Toast.makeText(requireContext(), "현장구역이 추가되었습니다.", Toast.LENGTH_SHORT).show()
        }
        builder.show()
    }



    private fun updateMenu(newItemTitle: String, isVisible: Boolean) {
        // Get the NavigationView in the MainActivity
        val navView = (requireActivity() as MainActivity).findViewById<NavigationView>(R.id.navigationView)
        // Get the menu from the NavigationView
        val menu = navView.menu
        // Find the new_item menu item and set its title
        val newItem = menu.findItem(R.id.nav_new_item)
        newItem?.title = newItemTitle
        newItem?.isVisible = isVisible // Set the visibility of nav_new_item to the value of isVisible parameter
    }






    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.nav_check -> {
                Toast.makeText(requireContext(), "Home clicked", Toast.LENGTH_SHORT).show()
                true
            }
            R.id.nav_my -> {
                Toast.makeText(requireContext(), "My clicked", Toast.LENGTH_SHORT).show()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }



//    private fun showAddDialog() {
//        val builder = AlertDialog.Builder(requireContext())
//        val inflater = layoutInflater
//        val dialogLayout = inflater.inflate(R.layout.dialog_add_item, null)
//        editText = dialogLayout.findViewById(R.id.add_item_title)
//        builder.setView(dialogLayout)
//
//        builder.setPositiveButton("OK") { _, _ ->
//            val newWpCode = editText.text.toString()
//            if (newWpCode.isNotEmpty()) {
//                adapter.add(newWpCode)
//                updateMenu(newWpCode) // newItemTitle 전달
//            } else {
//                Toast.makeText(
//                    requireContext(),
//                    "현장코드를 입력하세요.",
//                    Toast.LENGTH_SHORT
//                ).show()
//            }
//        }
//        builder.setNegativeButton("Cancel") { dialog, _ -> dialog.cancel() }
//
//        builder.show()
//    }
//   <--  현장코드 추가해주는 다이어로그  -->

    }
}