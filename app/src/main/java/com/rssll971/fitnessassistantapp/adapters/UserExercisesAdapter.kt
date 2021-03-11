package com.rssll971.fitnessassistantapp.adapters

import android.content.Context
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.rssll971.fitnessassistantapp.modelclasses.ExerciseModelClass
import com.rssll971.fitnessassistantapp.R
import com.rssll971.fitnessassistantapp.fragments.ExerciseCatalogFragment
import com.rssll971.fitnessassistantapp.fragments.StartWorkoutFragment

/**
 * Next class show all users exercises
 */
class UserExercisesAdapter(val context: Context,
                           private val userExerciseList: ArrayList<ExerciseModelClass>, currentFragment: Fragment) :
    RecyclerView.Adapter<UserExercisesAdapter.MyViewHolder>() {
    private var selectedItemPositionList = ArrayList<Int>()
    private val fragment: Fragment = currentFragment
    /**
     * Class with item components
     */
    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var tvUserExerciseName: TextView = itemView.findViewById(R.id.tv_item_activity_selectable_name)
        val llUserExercise: LinearLayout = itemView.findViewById(R.id.ll_item_activity_selectable)
        val ivEditExercise: ImageView = itemView.findViewById(R.id.iv_item_activities_edit)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(context).inflate(R.layout.item_activities_selectable, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item: ExerciseModelClass = userExerciseList[position]
        //val rowIndex = position
        //show exercise name
        holder.tvUserExerciseName.text = item.getName()
        /**
         * Next statement responsible for correct text size and show edit icon,
         * when user want to change own exercise
         */
        if (fragment is ExerciseCatalogFragment){
            holder.ivEditExercise.visibility = View.VISIBLE
            holder.tvUserExerciseName.setTextSize(TypedValue.COMPLEX_UNIT_SP, 24F)
        }
        else if (fragment is StartWorkoutFragment){
            holder.ivEditExercise.visibility = View.GONE
            holder.tvUserExerciseName.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18f)
        }
        /**
         * Next listener change exercise background when user choose exercise for doing
         * or
         * go to another dialog while user want change exercise
         */
        holder.llUserExercise.setOnClickListener {
            if (fragment is ExerciseCatalogFragment){
                fragment.showUserExerciseDialog(false, item)
            }
            else if (fragment is StartWorkoutFragment){
                /**
                 * As so responsibility for creating future exercise list was annotated in another class and
                 *  also implemented align with another color for selected exercise in rV, all position for selected exercise
                 *  adding to selectedItemPositionList
                 *
                 * Firstly work function under clickListener. It correspond for correct background.
                 *  If element was chosen before it marks them like selected and vise versa.
                 *  After that next if/else statement determine selected state and run corresponding method
                 */
                //delete item from list for preparation

                if (holder.llUserExercise.isSelected){
                    fragment.prepareExerciseList(position, false)
                    holder.llUserExercise.isSelected = false
                    //find index of corresponding item in local list
                    val index = selectedItemPositionList.indexOf(position)
                    //remove it from list
                    selectedItemPositionList.removeAt(index)
                }
                //add item in list for preparation
                else{
                    fragment.prepareExerciseList(position, true)
                    holder.llUserExercise.isSelected = true
                    //add item in local list
                    selectedItemPositionList.add(position)
                }
            }


        }
        //doesn't matter exactly context
        holder.llUserExercise.isSelected = selectedItemPositionList.contains(position)
    }

    override fun getItemCount(): Int {
        return userExerciseList.size
    }


}