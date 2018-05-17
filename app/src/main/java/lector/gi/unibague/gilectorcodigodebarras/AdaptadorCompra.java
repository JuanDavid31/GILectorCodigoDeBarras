package lector.gi.unibague.gilectorcodigodebarras;

import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Juan David on 15/05/2018.
 */

class AdaptadorCompra extends RecyclerView.Adapter<AdaptadorCompra.ViewHolder> {

    private Cursor cursor;

    public AdaptadorCompra(Cursor cursor) {
        this.cursor = cursor;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_holder_compra, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        cursor.moveToPosition(position);
        Log.d("AdaptadorCompra", cursor.getString(1) + " " +
                cursor.getString(2) + " " + cursor.getInt(3) + " " + cursor.getInt(4));
        holder.tvFecha.setText(cursor.getString(1));
        holder.tvNombreCliente.setText(cursor.getString(2));
        holder.tvTotalPagado.setText(cursor.getInt(4) + "");
    }

    @Override
    public int getItemCount() {
        return cursor.getCount();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvFecha;
        private TextView tvNombreCliente;
        private TextView tvTotalPagado;

        public ViewHolder(View itemView) {
            super(itemView);
            tvFecha = itemView.findViewById(R.id.tv_fecha_compra);
            tvNombreCliente = itemView.findViewById(R.id.tv_nombre_cliente);
            tvTotalPagado = itemView.findViewById(R.id.tv_total_pagado);
        }
    }
}
