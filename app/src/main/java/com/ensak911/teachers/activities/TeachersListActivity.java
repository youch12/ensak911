package com.ensak911.teachers.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.ensak911.ConnectionScreen;
import com.ensak911.R;
import com.ensak911.demandes.activities.ReceivedDemandsListActivity;
import com.ensak911.demandes.activities.SentDemandsListActivity;
import com.ensak911.emplois_annonces.activities.AnnoncesListActivity;
import com.ensak911.emplois_annonces.activities.EmploisListActivity;
import com.ensak911.repository.RetrofitInstance;
import com.ensak911.teachers.adapters.TeachersRecyclerView;
import com.ensak911.teachers.models.Prof;
import com.ensak911.teachers.models.TeachersList;
import com.ensak911.teachers.service.TeacherService;
import com.github.clans.fab.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by hp on 1/7/2018.
 */
public class TeachersListActivity extends AppCompatActivity implements TeachersRecyclerView.ItemClickCallback {
    private TeachersRecyclerView teachersRecyclerViewAdapter;
    private RecyclerView teachersRecyclerView;
    List<Prof> teachersList;
    FloatingActionButton addTeacherFlatButton;
    NavigationView navigationView;
    DrawerLayout drawerLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teachers_list);
        final SharedPreferences pref=getSharedPreferences("Ensak911",MODE_PRIVATE);
        teachersList = new ArrayList<Prof>();
        teachersRecyclerView = (RecyclerView) findViewById(R.id.activity_teachers_list_recyclerview);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        teachersRecyclerViewAdapter = new TeachersRecyclerView(getApplicationContext(), teachersList);
        teachersRecyclerView.setAdapter(teachersRecyclerViewAdapter);
        teachersRecyclerView.setLayoutManager(mLayoutManager);
        addTeacherFlatButton=(FloatingActionButton)findViewById(R.id.add_teacher_float);
        teachersRecyclerViewAdapter.setItemClickCallback(TeachersListActivity.this);
        addTeacherFlatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddTeacher();

            }
        });
        navigationView=(NavigationView)findViewById(R.id.menu_view);
        if(pref.getString("type","student").equals("student"))
            addTeacherFlatButton.setVisibility(View.GONE);
        navigationView.setCheckedItem(R.id.menu_annuaire);
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
                            intent = new Intent(TeachersListActivity.this, SentDemandsListActivity.class);
                            startActivity(intent);
                            finish();
                        }else{
                            intent = new Intent(TeachersListActivity.this, ReceivedDemandsListActivity.class);
                            startActivity(intent);
                            finish();
                        }
                        return true;
                    }
                    case R.id.menu_annonce:
                        intent = new Intent(TeachersListActivity.this, AnnoncesListActivity.class);
                        startActivity(intent);
                        finish();
                        return true;
                    case R.id.menu_emploi:
                        intent = new Intent(TeachersListActivity.this, EmploisListActivity.class);
                        startActivity(intent);
                        finish();
                        return true;
                    case R.id.menu_logout:
                        pref.edit().clear().commit();
                        intent = new Intent(TeachersListActivity.this, ConnectionScreen.class);
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
        TeacherService service = RetrofitInstance.getRetrofitInstance().create(TeacherService.class);
        Call<TeachersList> call = service.getTeachers();
        call.enqueue(new Callback<TeachersList>() {
            @Override
            public void onResponse(Call<TeachersList> call, Response<TeachersList> response) {
                generateTeachersList(response.body().getTeachersList());
            }

            @Override
            public void onFailure(Call<TeachersList> call, Throwable t) {

                Toast.makeText(TeachersListActivity.this, t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    void  generateTeachersList(ArrayList<Prof> profs)
    {
        for(int i=0;i<profs.size();i++) {
            teachersRecyclerViewAdapter.addItem(profs.get(i));

        }

    }


    void showTeacherDetails(Prof teacher)
    {
        LayoutInflater factory = LayoutInflater.from(this);

        final View teacherLayout = factory.inflate(R.layout.add_prof_dialog_layout, null);
        final EditText teacherFirstName=(EditText)teacherLayout.findViewById(R.id.teacher_first_name_edittext);
        final EditText teacherLastName=(EditText)teacherLayout.findViewById(R.id.teacher_last_name_edittext);
        final EditText teacherTitle=(EditText)teacherLayout.findViewById(R.id.teacher_title_edittext);
        final EditText teacherEmail=(EditText)teacherLayout.findViewById(R.id.teacher_email_edittext);
        final EditText teacherPhone=(EditText)teacherLayout.findViewById(R.id.teacher_tel_edittext);
        teacherFirstName.setText(teacher.getPrenom());
        teacherLastName.setText(teacher.getNom());
        teacherTitle.setText(teacher.getTitle());
        teacherEmail.setText(teacher.getEmail());
        teacherPhone.setText(teacher.getTel());
        final AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setIcon(R.drawable.ic_account_circle_black_24dp1).setTitle(getResources().getString(R.string.teacher_details)).setView(teacherLayout).setPositiveButton("Enregister",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,
                                        int whichButton) {

                    }
                }).setNegativeButton(getResources().getString(R.string.undo),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,
                                        int whichButton) {

                    }
                });
        alert.show();


    }
    @Override
    public void onItemClick(int p) {
         Prof teacher = teachersList.get(p);
        showTeacherDetails(teacher);
    }

    void showAddTeacher()
    {
        LayoutInflater factory = LayoutInflater.from(this);

        final View teacherLayout = factory.inflate(R.layout.add_prof_dialog_layout, null);



        final AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setIcon(R.drawable.ic_account_circle_black_24dp1).setTitle(getResources().getString(R.string.teacher_details)).setView(teacherLayout).setPositiveButton("Save",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,
                                        int whichButton) {

                    }
                }).setNegativeButton(getResources().getString(R.string.undo),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,
                                        int whichButton) {

                    }
                });
        alert.show();
    }
}
