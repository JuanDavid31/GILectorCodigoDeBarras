package lector.gi.unibague.gilectorcodigodebarras;

import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import room.entidades.DatosCompra;

/**
 * Created by Juan David on 15/05/2018.
 */

class AdaptadorCompra extends RecyclerView.Adapter<AdaptadorCompra.ViewHolder> {

    private List<DatosCompra> datosCompras;

    public AdaptadorCompra(List datosCompras) {
        this.datosCompras = datosCompras;
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
        DatosCompra dt = datosCompras.get(position);
        Log.d("AdaptadorCompra", dt.nombreCliente + " " +
                dt.fecha + " " + dt.totalCompra);
        holder.tvNombreCliente.setText("Cliente - " + dt.nombreCliente);
        holder.tvFecha.setText(dt.fecha);
        holder.tvTotalPagado.setText("Total pagado - $" + dt.totalCompra);
    }

    @Override
    public int getItemCount() {
        return datosCompras.size();
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
