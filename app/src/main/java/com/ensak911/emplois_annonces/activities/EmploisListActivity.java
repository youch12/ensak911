package com.ensak911.emplois_annonces.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
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
import com.ensak911.demandes.activities.ReceivedDemandsListActivity;
import com.ensak911.demandes.activities.SentDemandsListActivity;
import com.ensak911.emplois_annonces.adapters.EmploisRecyclerView;
import com.ensak911.emplois_annonces.models.Emploi;
import com.ensak911.emplois_annonces.models.EmploisList;
import com.ensak911.emplois_annonces.services.EmploiService;
import com.ensak911.repository.RetrofitInstance;
import com.ensak911.teachers.activities.TeachersListActivity;
import com.github.clans.fab.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by hp on 1/7/2018.
 */
public class EmploisListActivity extends AppCompatActivity {
    List<Emploi> emploisList;
    RecyclerView emploisRecyclerView;
    EmploisRecyclerView emploisRecyclerViewAdapter;
    FloatingActionButton modifyEmploiButton;
    NavigationView navigationView;
    DrawerLayout drawerLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emplois_list);
        emploisList=new ArrayList<Emploi>();
        modifyEmploiButton=(FloatingActionButton)findViewById(R.id.modify_emploi_button);
        final SharedPreferences pref=getSharedPreferences("Ensak911",MODE_PRIVATE);
        if(pref.getString("type","student").equals("student"))
            modifyEmploiButton.setVisibility(View.GONE);
        emploisRecyclerView=(RecyclerView) findViewById(R.id.activity_emplois_list_view);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        emploisRecyclerViewAdapter=new EmploisRecyclerView(EmploisListActivity.this,getApplicationContext(),emploisList);
        emploisRecyclerView.setAdapter(emploisRecyclerViewAdapter);
        emploisRecyclerView.setLayoutManager(mLayoutManager);
        modifyEmploiButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent modifyEmploiIntent=new Intent(EmploisListActivity.this,ModifiyScheduleActivity.class);
                startActivity(modifyEmploiIntent);

            }
        });
        navigationView=(NavigationView)findViewById(R.id.menu_view);
        navigationView.setCheckedItem(R.id.menu_emploi);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {

                if (item.isChecked()) item.setChecked(false);
                else item.setChecked(true);
                drawerLayout.closeDrawers();
                Intent intent;
                switch (item.getItemId()) {
                    case R.id.menu_demandes: {
                        if(pref.getString("type","student").equals("student")) {
                            intent = new Intent(EmploisListActivity.this, SentDemandsListActivity.class);
                            startActivity(intent);
                            finish();
                        }else{
                            intent = new Intent(EmploisListActivity.this, ReceivedDemandsListActivity.class);
                            startActivity(intent);
                            finish();
                        }
                        return true;
                    }
                    case R.id.menu_annuaire:
                        intent = new Intent(EmploisListActivity.this, TeachersListActivity.class);
                        startActivity(intent);
                        finish();
                        return true;
                    case R.id.menu_annonce:
                        intent = new Intent(EmploisListActivity.this, AnnoncesListActivity.class);
                        startActivity(intent);
                        finish();
                        return true;
                    case R.id.menu_logout:
                        pref.edit().clear().commit();
                        intent = new Intent(EmploisListActivity.this, ConnectionScreen.class);
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
        EmploiService service = RetrofitInstance.getRetrofitInstance().create(EmploiService.class);
        Call<EmploisList> call = service.getEmplois();
        call.enqueue(new Callback<EmploisList>() {
            @Override
            public void onResponse(Call<EmploisList> call, Response<EmploisList> response) {
                generateEmploisList(response.body().getEmploisList());
            }

            @Override
            public void onFailure(Call<EmploisList> call, Throwable t) {

                Toast.makeText(EmploisListActivity.this, t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    void  generateEmploisList(ArrayList<Emploi> emplois)
    {
        for(int i=0;i<emplois.size();i++) {
            emploisRecyclerViewAdapter.addItem(emplois.get(i));

        }

    }
}
