package com.example.projectandroidcrudfirebase;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Objects;

public class MainActivity extends AppCompatActivity implements CourseRVAdapter.CourseClickInterface {

    private RecyclerView courseRV;
    private ProgressBar loadingPB;
    private FloatingActionButton addFAB;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private ArrayList<CourseRVModel> courseRVModelArrayList;
    private RelativeLayout bottomSheetRl;
    private CourseRVAdapter courseRVAdapter;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.purple_400)));
        getSupportActionBar().setTitle("My Courses");
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.drawable.ic_baseline_school_24);


        courseRV = findViewById(R.id.idRVCourses);
        loadingPB = findViewById(R.id.idPBLoading);
        addFAB = findViewById(R.id.idAddFAB);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Courses");
        courseRVModelArrayList = new ArrayList<>();
        bottomSheetRl=findViewById(R.id.idRLBSheet);
        mAuth=FirebaseAuth.getInstance();
        courseRVAdapter = new CourseRVAdapter(courseRVModelArrayList, this, this);
        courseRV.setLayoutManager(new LinearLayoutManager(this));
        courseRV.setAdapter(courseRVAdapter);

        addFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, AddCourseActivity.class));
            }
        });
        getAllCourses();
    }
        private void getAllCourses(){
            courseRVModelArrayList.clear();
            databaseReference.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                    loadingPB.setVisibility(View.GONE);
                    courseRVModelArrayList.add(snapshot.getValue(CourseRVModel.class));
                    courseRVAdapter.notifyDataSetChanged();
                }

                @Override
                public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                        loadingPB.setVisibility(View.GONE);
                        courseRVAdapter.notifyDataSetChanged();
                }

                @Override
                public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                    loadingPB.setVisibility(View.GONE);
                    courseRVAdapter.notifyDataSetChanged();
                }

                @Override
                public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                    loadingPB.setVisibility(View.GONE);
                    courseRVAdapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }

    @Override
    public void onCourseClick(int position) {
        displayBottomSheet(courseRVModelArrayList.get(position));
    }

    private  void displayBottomSheet(CourseRVModel courseRVModel){
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        View  layout = LayoutInflater.from(this).inflate(R.layout.bottom_sheet,bottomSheetRl);
        bottomSheetDialog.setContentView(layout);
        bottomSheetDialog.setCancelable(false);
        bottomSheetDialog.setCanceledOnTouchOutside(true);
        bottomSheetDialog.show();

        TextView courseNameTv = layout.findViewById(R.id.idTVCourseName);
        TextView courseDescTv = layout.findViewById(R.id.idTVDescription);
        TextView courseSuitedForTV = layout.findViewById(R.id.idTVSuitedFor);
        TextView coursePriceTv = layout.findViewById(R.id.idTVPriseDetail);
        ImageView courseIV = layout.findViewById(R.id.idIVCourse);
        Button editBn = layout.findViewById(R.id.idBtnEdit);
        Button viewDetailsBtn = layout.findViewById(R.id.idBtnViewDetails);


        courseNameTv.setText(courseRVModel.getCourseName());
        courseDescTv.setText(courseRVModel.getCourseDesc());
        courseSuitedForTV.setText(courseRVModel.getCourseSuitedFor());
        coursePriceTv.setText(courseRVModel.getCoursePrice()+" DT ");
        courseNameTv.setText(courseRVModel.getCourseName());
        Picasso.get().load(courseRVModel.getCourseImg()).into(courseIV);

        editBn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,EditCourseActivity.class);
                Log.i("courseModel", "onCreate: "+courseRVModel.toString());
                i.putExtra("course",courseRVModel);
                startActivity(i);
            }
        });

        viewDetailsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(courseRVModel.getCourseLink()));
                startActivity(i);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        getMenuInflater().inflate(R.menu.map_menu, menu);
    /*    getMenuInflater().inflate(R.menu.search_menu,menu);
        MenuItem menuItem=menu.findItem(R.id.search_bar);

        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setQueryHint("Type here to Search");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });*/
        return true;

    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id=item.getItemId();
        switch (id){
            case R.id.idLogOut:
                Toast.makeText(this,"User Logged Out..",Toast.LENGTH_SHORT).show();
                mAuth.signOut();
                Intent i = new Intent(MainActivity.this,LoginActivity.class);
                startActivity(i);
                this.finish();
                return true;
            case R.id.localisation_bar:
                Intent j = new Intent(MainActivity.this,MapActivity.class);
                startActivity(j);
                return  true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}