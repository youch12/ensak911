package com.ensak911.teachers.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.ensak911.R;
import com.ensak911.teachers.models.Prof;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by hp on 1/7/2018.
 */
public class TeachersRecyclerView extends RecyclerView.Adapter<TeachersRecyclerView.MyViewHolder> {
    private List<Prof> teachersList;
    Context contex;
    private ItemClickCallback itemClickCallback;



    public interface ItemClickCallback {
        void onItemClick(int p);
    }
    public void setItemClickCallback(final ItemClickCallback itemClickCallback) {
        this.itemClickCallback = itemClickCallback;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView name, email, phone,title;
        CircleImageView image;
        ImageButton sendEmail, call;

        public MyViewHolder(View view) {
            super(view);
            name =(TextView) view.findViewById(R.id.teacher_name_textview);
            email=(TextView) view.findViewById(R.id.teacher_email_textview);
            phone=(TextView) view.findViewById(R.id.teacher_phone_textview);
            title =(TextView) view.findViewById(R.id.teacher_title_textview);
            image =(CircleImageView) view.findViewById(R.id.teacher_pic);
            sendEmail=(ImageButton)view.findViewById(R.id.teacher_send_email_icon);
            call=(ImageButton)view.findViewById(R.id.teacher_call_icon);
            view.setOnClickListener(this);


        }

        @Override
        public void onClick(View v) {
                itemClickCallback.onItemClick(getAdapterPosition());

        }



    }


    public TeachersRecyclerView(Context context,List<Prof> teachersList) {
        this.teachersList = teachersList;
        this.contex=context;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(this.contex)
                .inflate(R.layout.activity_teachers_list_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final Prof prof = teachersList.get(position);
        holder.title.setText(prof.getTitle());
        holder.name.setText(prof.getNom()+contex.getResources().getString(R.string.space)+prof.getPrenom());
        holder.phone.setText(prof.getTel());
        holder.email.setText(prof.getEmail());
        Picasso.with(contex).load(prof.getImage()).into(holder.image);
        holder.sendEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
                emailIntent.setData(Uri.parse("mailto:"+prof.getEmail()));
                contex.startActivity(emailIntent);
            }
        });
        holder.call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse("tel:"+prof.getTel()));
                contex.startActivity(callIntent);
            }
        });


    }



    public void addItem(Prof item) {
        teachersList.add(item);
        notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
        return teachersList.size();
    }


}

