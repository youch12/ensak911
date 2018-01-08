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
import com.ensak911.emplois_annonces.adapters.AnnoncesRecyclerView;
import com.ensak911.emplois_annonces.models.Annonce;
import com.ensak911.emplois_annonces.models.AnnoncesList;
import com.ensak911.emplois_annonces.services.AnnonceService;
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
public class AnnoncesListActivity extends AppCompatActivity {
    NavigationView navigationView;
    DrawerLayout drawerLayout;
    RecyclerView annoncesRecyclerView;
    AnnoncesRecyclerView annoncesRecyclerViewAdapter;
    List<Annonce> annonceList;
    FloatingActionButton addAnnonceButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_annonces_list);
        final SharedPreferences pref=getSharedPreferences("Ensak911",MODE_PRIVATE);
        annonceList=new ArrayList<Annonce>();
        annoncesRecyclerView=(RecyclerView) findViewById(R.id.activity_annonces_list_view);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        annoncesRecyclerViewAdapter=new AnnoncesRecyclerView(AnnoncesListActivity.this,getApplicationContext(),annonceList);
        annoncesRecyclerView.setAdapter(annoncesRecyclerViewAdapter);
        annoncesRecyclerView.setLayoutManager(mLayoutManager);
        navigationView=(NavigationView)findViewById(R.id.menu_view);
        navigationView.setCheckedItem(R.id.menu_annonce);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        addAnnonceButton=(FloatingActionButton)findViewById(R.id.add_annonce_button);
        if(pref.getString("type","student").equals("student"))
            addAnnonceButton.setVisibility(View.GONE);
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
                            intent = new Intent(AnnoncesListActivity.this, SentDemandsListActivity.class);
                            startActivity(intent);
                            finish();
                        }else{
                            intent = new Intent(AnnoncesListActivity.this, ReceivedDemandsListActivity.class);
                            startActivity(intent);
                            finish();
                        }
                        return true;
                    }
                    case R.id.menu_annuaire:
                        intent = new Intent(AnnoncesListActivity.this, TeachersListActivity.class);
                        startActivity(intent);
                        finish();
                        return true;
                    case R.id.menu_emploi:
                        intent = new Intent(AnnoncesListActivity.this, EmploisListActivity.class);
                        startActivity(intent);
                        finish();
                        return true;
                    case R.id.menu_logout:
                        pref.edit().clear().commit();
                        intent = new Intent(AnnoncesListActivity.this, ConnectionScreen.class);
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
        AnnonceService service = RetrofitInstance.getRetrofitInstance().create(AnnonceService.class);
        Call<AnnoncesList> call = service.getAnnonces();
        call.enqueue(new Callback<AnnoncesList>() {
            @Override
            public void onResponse(Call<AnnoncesList> call, Response<AnnoncesList> response) {
                generateTeachersList(response.body().getAnnoncesList());
            }

            @Override
            public void onFailure(Call<AnnoncesList> call, Throwable t) {

                Toast.makeText(AnnoncesListActivity.this, t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    void  generateTeachersList(ArrayList<Annonce> profs)
    {
        for(int i=0;i<profs.size();i++) {
            annoncesRecyclerViewAdapter.addItem(profs.get(i));

        }

    }
}
