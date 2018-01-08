package com.ensak911.demandes.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.ensak911.ConnectionScreen;
import com.ensak911.R;
import com.ensak911.demandes.adapters.SentDemandsRecyclerView;
import com.ensak911.demandes.models.Demand;
import com.ensak911.demandes.models.DemandsList;
import com.ensak911.demandes.service.DemandeService;
import com.ensak911.emplois_annonces.activities.AnnoncesListActivity;
import com.ensak911.emplois_annonces.activities.EmploisListActivity;
import com.ensak911.repository.RetrofitInstance;
import com.ensak911.teachers.activities.TeachersListActivity;
import com.github.clans.fab.FloatingActionButton;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by hp on 1/7/2018.
 */
public class SentDemandsListActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private SentDemandsRecyclerView sentDemandsArrayAdapter;
    CoordinatorLayout coordinatorLayout;
    List<Demand> demandsList;
    FloatingActionButton addNewDemand;
    DrawerLayout drawerLayout;
    NavigationView navigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sent_demandes_list);
        final SharedPreferences pref=getSharedPreferences("Ensak911", MODE_PRIVATE);
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.sent_demands_list_coordinator);
        demandsList = new ArrayList<Demand>();
        recyclerView = (RecyclerView) findViewById(R.id.activity_sent_demands_list_view);
        addNewDemand=(FloatingActionButton)findViewById(R.id.add_new_demand);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        sentDemandsArrayAdapter = new SentDemandsRecyclerView(getApplicationContext(), demandsList);
        recyclerView.setAdapter(sentDemandsArrayAdapter);
        recyclerView.setLayoutManager(mLayoutManager);
        addNewDemand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addDemandIntent=new Intent(SentDemandsListActivity.this,AddDemandeActivity.class);
                startActivity(addDemandIntent);
            }
        });
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
                        intent = new Intent(SentDemandsListActivity.this, TeachersListActivity.class);
                        startActivity(intent);
                        finish();
                        return true;
                    case R.id.menu_annonce:
                        intent = new Intent(SentDemandsListActivity.this, AnnoncesListActivity.class);
                        startActivity(intent);
                        finish();
                        return true;
                    case R.id.menu_emploi:
                        intent = new Intent(SentDemandsListActivity.this, EmploisListActivity.class);
                        startActivity(intent);
                        finish();
                        return true;
                    case R.id.menu_logout:
                        pref.edit().clear().commit();
                        intent = new Intent(SentDemandsListActivity.this, ConnectionScreen.class);
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

                Toast.makeText(SentDemandsListActivity.this, t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    void  generateDemandeList(ArrayList<Demand> demands)
    {
        for(int i=0;i<demands.size();i++) {
            demandsList.add(demands.get(i));
            sentDemandsArrayAdapter.addItem(demands.get(i));

        }

    }

}
