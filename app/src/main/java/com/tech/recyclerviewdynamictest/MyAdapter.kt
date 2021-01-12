package com.tech.recyclerviewdynamictest

import android.app.Dialog
import android.graphics.BitmapFactory
import android.media.ThumbnailUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import de.hdodenhof.circleimageview.CircleImageView
import java.io.File


class MyAdapter(var mainActivity: MainActivity, var listfiles: Array<File>?) :
    RecyclerView.Adapter<MyAdapter.MyHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {

        var inflater = LayoutInflater.from(parent.context)
        var view = inflater.inflate(R.layout.item_view, parent, false)
        return MyHolder(view)
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {


        var file = listfiles!![position]
        var bmp = BitmapFactory.decodeFile(file.path)
        var cmp = ThumbnailUtils.extractThumbnail(bmp, 110, 110)
        holder.circle_image.setImageBitmap(cmp)

        holder.txt_name.text = file.name
        holder.txt_size.text = file.length().toString()
        holder.button_delete.setOnClickListener {

            var dialog = Dialog(mainActivity)
            dialog.setTitle("message")
            dialog.setContentView(R.layout.cust_dailog)

            val layoutParams = dialog.window!!.attributes
            dialog.window!!.setLayout(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT
            )
            dialog.window!!.attributes = layoutParams
            dialog.show()


            var button_yes = dialog.findViewById<Button>(R.id.button_yes)
            var button_no = dialog.findViewById<Button>(R.id.button_no)

            button_yes.setOnClickListener {
                if (file.delete()) {

                    Toast.makeText(mainActivity, "image deleted successfully", Toast.LENGTH_SHORT)
                        .show()
                    dialog.dismiss()
                    notifyDataSetChanged()//it notifiess us whenever any changes happened

                } else {
                    Toast.makeText(mainActivity, "unable to delete image", Toast.LENGTH_SHORT)
                        .show()
                    dialog.dismiss()
                }
            }

            button_no.setOnClickListener {
                dialog.dismiss()
            }


        }

    }

    override fun getItemCount(): Int {
        return listfiles!!.size
    }


    class MyHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var circle_image = itemView.findViewById<CircleImageView>(R.id.image_circle)
        var txt_name = itemView.findViewById<TextView>(R.id.txt_image_name)
        var txt_size = itemView.findViewById<TextView>(R.id.txt_image_size)
        var button_delete = itemView.findViewById<ImageView>(R.id.button_delete)

    }
}