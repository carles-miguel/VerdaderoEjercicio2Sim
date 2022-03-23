package com.example.verdaderoejercicio2sim.adapter;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.verdaderoejercicio2sim.R;
import com.example.verdaderoejercicio2sim.modelos.Libro;

import java.text.NumberFormat;
import java.util.List;

public class LibrosAdapter extends RecyclerView.Adapter<LibrosAdapter.LibroVH> {

    private  Context context;
    private  int resource;
    private List<Libro> libros;

    public LibrosAdapter(Context context, int resource, List<Libro> libros) {
        this.context = context;
        this.resource = resource;
        this.libros = libros;
    }

    @NonNull
    @Override
    public LibroVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View libroView = LayoutInflater.from(context).inflate(resource,null);
       libroView.setLayoutParams(new RecyclerView.LayoutParams(
               ViewGroup.LayoutParams.MATCH_PARENT,
               ViewGroup.LayoutParams.WRAP_CONTENT
       ));
        return new LibroVH(libroView);
    }


    @Override
    public void onBindViewHolder(@NonNull LibroVH holder, int position) {

        Libro libro = libros.get(position);
        NumberFormat nf = NumberFormat.getNumberInstance();
        holder.lblTitulo.setText(libro.getTitulo());
        holder.lblAutor.setText(libro.getAutor());
        holder.lblPaginas.setText(nf.format(libro.getPaginas()));



    }

    @Override
    public int getItemCount() {
        return libros.size();
    }

    public static class LibroVH extends  RecyclerView.ViewHolder{

        TextView lblTitulo, lblAutor, lblPaginas;
        public LibroVH(@NonNull View itemView) {
            super(itemView);
            lblTitulo = itemView.findViewById(R.id.lblTitutloModel);
            lblAutor = itemView.findViewById(R.id.lblAutorModel);
            lblPaginas = itemView.findViewById(R.id.lblPaginasModel);


        }
    }
}
