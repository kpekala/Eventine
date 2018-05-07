package com.racjonalnytraktor.findme3.ui.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.racjonalnytraktor.findme3.R
import com.racjonalnytraktor.findme3.data.model.Invitation
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.invitation_item.view.*


class InvitationsAdapter(val list: ArrayList<Invitation>, val context: Context) : RecyclerView.Adapter<InvitationsAdapter.MyHolder>() {
    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        val view =  LayoutInflater.from(parent.context).inflate(R.layout.invitation_item, parent, false)
        return MyHolder(view,context)
    }

    class MyHolder(itemView: View,val context: Context): RecyclerView.ViewHolder(itemView) {

        fun bind(invitation: Invitation){
            itemView.fieldGroupName.text = invitation.groupName
            itemView.fieldInvitationTitle.text = invitation.invitingPerson

            Picasso.get()
                    .load(invitation.imageUri)
                    .placeholder(R.drawable.image_placeholder)
                    .resize(50,50)
                    .centerCrop()
                    .into(itemView.imageFriend)

        }

    }

    fun addItem(invitation: Invitation){
        list.add(invitation)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return list.size
    }
}