package com.example.verdaderoejercicio2sim;

import android.content.DialogInterface;
import android.os.Bundle;

import com.example.verdaderoejercicio2sim.adapter.LibrosAdapter;
import com.example.verdaderoejercicio2sim.modelos.Libro;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.verdaderoejercicio2sim.databinding.ActivityMainBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {


    // --- Recycler
    private ArrayList<Libro> libros;
    private LibrosAdapter adapter;

    private ActivityMainBinding binding;
    // -- Firebase
    private FirebaseDatabase database;
    private DatabaseReference misLibrosRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        libros = new ArrayList<>();
        adapter = new LibrosAdapter(this,R.layout.libro_model, libros);
        binding.contentMain.contenedor.setAdapter(adapter);
        binding.contentMain.contenedor.setLayoutManager(
                new LinearLayoutManager(this)
        );

        database = FirebaseDatabase.getInstance();
        misLibrosRef = database.getReference("libros");
        misLibrosRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
               if (dataSnapshot.exists()){
                   GenericTypeIndicator<ArrayList<Libro>> gti = new GenericTypeIndicator<ArrayList<Libro>>() {};
                   libros.clear();
                   libros.addAll(Objects.requireNonNull(dataSnapshot.getValue(gti)));
                   adapter.notifyItemRangeChanged(0,libros.size());

               }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addLibro().show();
            }
        });
    }

    private AlertDialog addLibro() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("ADD LIBRO");
        View alertView = getLayoutInflater().inflate(R.layout.libro_alert,null);
        builder.setView(alertView);
        EditText txtTitulo = alertView.findViewById(R.id.txtTituloAlert);
        EditText txtAutor = alertView.findViewById(R.id.txtAutorAlert);
        EditText txtPaginas = alertView.findViewById(R.id.txtPaginasAlert);
        builder.setCancelable(false);
        builder.setNegativeButton("CANCELAR",null);
        builder.setPositiveButton("GUARDAR", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Libro libro= new Libro(txtTitulo.getText().toString(),
                        txtAutor.getText().toString(),
                       Integer.parseInt(txtPaginas.getText().toString())
                );
                libros.add(libro);

                /**adapter.notifyItemInserted(libros.size()-1);**///Antes de firebase si depues no
                misLibrosRef.setValue(libros);//Despues de firebase
            }
        });
        return builder.create();


    }

}