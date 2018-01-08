package com.ensak911.demandes.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.ensak911.ConnectionScreen;
import com.ensak911.R;
import com.ensak911.demandes.adapters.ReceivedDemandsRecyclerView;
import com.ensak911.demandes.adapters.RecyclerItemTouchHelper;
import com.ensak911.demandes.models.Demand;
import com.ensak911.demandes.models.DemandsList;
import com.ensak911.demandes.service.DemandeService;
import com.ensak911.emplois_annonces.activities.AnnoncesListActivity;
import com.ensak911.emplois_annonces.activities.EmploisListActivity;
import com.ensak911.repository.RetrofitInstance;
import com.ensak911.teachers.activities.TeachersListActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ReceivedDemandsListActivity extends AppCompatActivity implements
        RecyclerItemTouchHelper.RecyclerItemTouchHelperListener{
    NavigationView navigationView;

    private RecyclerView recyclerView;
    private ReceivedDemandsRecyclerView demandsRecyclerViewAdapter;
    CoordinatorLayout coordinatorLayout;
    List<Demand> demandsList;
    DrawerLayout drawerLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demandes_list);
        coordinatorLayout = (CoordinatorLayout)findViewById(R.id.demande_list_coordinator);
        final SharedPreferences pref=getSharedPreferences("Ensak911",MODE_PRIVATE);
        demandsList=new ArrayList<Demand>();
        recyclerView=(RecyclerView) findViewById(R.id.activity_demandes_list_view);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        demandsRecyclerViewAdapter=new ReceivedDemandsRecyclerView(getApplicationContext(),demandsList);
        recyclerView.setAdapter(demandsRecyclerViewAdapter);
        recyclerView.setLayoutManager(mLayoutManager);
        ItemTouchHelper.SimpleCallback itemTouchHelperCallbackLeft = new RecyclerItemTouchHelper(0, ItemTouchHelper.LEFT, this);
        new ItemTouchHelper(itemTouchHelperCallbackLeft).attachToRecyclerView(recyclerView);
        ItemTouchHelper.SimpleCallback itemTouchHelperCallbackRight = new RecyclerItemTouchHelper(0, ItemTouchHelper.RIGHT, this);
        new ItemTouchHelper(itemTouchHelperCallbackRight).attachToRecyclerView(recyclerView);
        navigationView=(NavigationView)findViewById(R.id.menu_view);
        navigationView.setCheckedItem(R.id.menu_demandes);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {

                if (item.isChecked()) item.setChecked(false);
                else item.setChecked(true);
                drawerLayout.closeDrawers();
                Intent intent;
                switch (item.getItemId()) {
                    case R.id.menu_annuaire:
                        intent = new Intent(ReceivedDemandsListActivity.this, TeachersListActivity.class);
                        startActivity(intent);
                        finish();
                        return true;
                    case R.id.menu_annonce:
                        intent = new Intent(ReceivedDemandsListActivity.this, AnnoncesListActivity.class);
                        startActivity(intent);
                        finish();
                        return true;
                    case R.id.menu_emploi:
                        intent = new Intent(ReceivedDemandsListActivity.this, EmploisListActivity.class);
                        startActivity(intent);
                        finish();
                        return true;
                    case R.id.menu_logout:
                        pref.edit().clear().commit();
                        intent = new Intent(ReceivedDemandsListActivity.this, ConnectionScreen.class);
                        startActivity(intent);
                        finish();
                        return true;

                }

                return false;
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        DemandeService service = RetrofitInstance.getRetrofitInstance().create(DemandeService.class);
        Call<DemandsList> call = service.getDemandes();
        call.enqueue(new Callback<DemandsList>() {
            @Override
            public void onResponse(Call<DemandsList> call, Response<DemandsList> response) {
                generateDemandeList(response.body().getDemandesList());
            }

            @Override
            public void onFailure(Call<DemandsList> call, Throwable t) {

                Toast.makeText(ReceivedDemandsListActivity.this, t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

   void  generateDemandeList(ArrayList<Demand> demands)
    {
        for(int i=0;i<demands.size();i++) {
            demandsList.add(demands.get(i));
            demandsRecyclerViewAdapter.addItem(demands.get(i));

        }

    }


    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, final int position) {
        if (viewHolder instanceof ReceivedDemandsRecyclerView.MyViewHolder) {
            final Demand selectedDemand=demandsList.get(position);
            String messageString="";
            if(direction==ItemTouchHelper.LEFT)
            {
                messageString=getResources().getString(R.string.deleted_demand);
            }else{
                messageString=getResources().getString(R.string.archived_demand);
            }
            demandsRecyclerViewAdapter.removeItem(viewHolder.getAdapterPosition());
            Snackbar snackbar = Snackbar
                    .make(coordinatorLayout,  messageString, Snackbar.LENGTH_LONG);
            snackbar.setAction(getResources().getString(R.string.undo), new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    demandsRecyclerViewAdapter.restoreItem(selectedDemand, position);
                }
            });
            snackbar.setActionTextColor(Color.YELLOW);
            snackbar.show();

        }
    }



}
