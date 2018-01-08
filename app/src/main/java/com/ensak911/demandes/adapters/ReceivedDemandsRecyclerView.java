package com.ensak911.demandes.adapters;

/**
 * Created by hp on 1/6/2018.
 */


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ensak911.R;
import com.ensak911.demandes.models.Demand;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by HP on 30-Aug-16.
 */
public class ReceivedDemandsRecyclerView extends RecyclerView.Adapter<ReceivedDemandsRecyclerView.MyViewHolder> {
    private List<Demand> demandsList;
    Context contex;



    public class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView titre, dateCreation, status,dateLivraison;
        LinearLayout dateLivraisionLinearLayout;
        public RelativeLayout viewBackground, viewForeground;
        Button respondButton,sendReceiptButton;
        LinearLayout respondLayout,sendReceiptLayout;

        public MyViewHolder(View view) {
            super(view);
            titre =(TextView) view.findViewById(R.id.demande_titre);
            viewBackground=(RelativeLayout) view.findViewById(R.id.demande_view_background);
            viewForeground=(RelativeLayout) view.findViewById(R.id.demande_view_foreground);
            dateCreation =(TextView) view.findViewById(R.id.demande_dateCreation);
            status =(TextView) view.findViewById(R.id.demande_status);
            dateLivraison =(TextView) view.findViewById(R.id.demande_dateLivraison);
            dateLivraisionLinearLayout=(LinearLayout)view.findViewById(R.id.demande_dateLivraison_linear_layout);
            respondButton=(Button)view.findViewById(R.id.respond_button);
            sendReceiptButton=(Button)view.findViewById(R.id.send_receipt_button);
            respondLayout=(LinearLayout)view.findViewById(R.id.respond_layout);
            sendReceiptLayout=(LinearLayout)view.findViewById(R.id.send_receipt_layout);

        }

    }


    public ReceivedDemandsRecyclerView(Context context, List<Demand> demandsList) {
        this.demandsList = demandsList;
        this.contex=context;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(this.contex)
                .inflate(R.layout.activity_demandes_list_item_view, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final Demand demand = demandsList.get(position);

        holder.titre.setText(demand.getTitre());
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        String dateString = format.format(demand.getDateCreation());
        holder.dateCreation.setText(dateString);
        if(demand.getDateLivraison()==null)
        {
            holder.status.setText(R.string.demande_status_false);
        }else{

            holder.dateLivraisionLinearLayout.setVisibility(View.VISIBLE);
            dateString = format.format(demand.getDateLivraison());
            holder.dateLivraison.setText(dateString);

        }
        if(demand.getAccuseReception()==null)
        {
            holder.respondLayout.setVisibility(View.GONE);
            holder.sendReceiptLayout.setVisibility(View.VISIBLE);

        }else{
            holder.respondLayout.setVisibility(View.VISIBLE);
            holder.sendReceiptButton.setVisibility(View.GONE);
        }
        holder.sendReceiptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                demandsList.get(position).setAccuseReception(new Date());
                holder.sendReceiptLayout.setVisibility(View.GONE);
                holder.respondLayout.setVisibility(View.VISIBLE);
                Toast.makeText(contex,contex.getResources().getString(R.string.receipt_sent_message),Toast.LENGTH_LONG).show();
            }
        });
        holder.respondButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(contex,"Allez répondre",Toast.LENGTH_LONG).show();

            }
        });
    }
    public void removeItem(int position) {
        demandsList.remove(position);
        notifyItemRemoved(position);
    }

    public void restoreItem(Demand item, int position) {
        demandsList.add(position, item);
        notifyItemInserted(position);
    }
    public void addItem(Demand item) {
        demandsList.add(item);
        notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
        return demandsList.size();
    }


}


