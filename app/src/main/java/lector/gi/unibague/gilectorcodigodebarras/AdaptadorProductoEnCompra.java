package lector.gi.unibague.gilectorcodigodebarras;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatDialogFragment;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.NumberPicker;
import android.widget.TextView;

import java.util.ArrayList;

import lector.gi.unibague.gilectorcodigodebarras.modelo.Producto;
import lector.gi.unibague.gilectorcodigodebarras.provider.ContratoLectorCodigoDeBarras;

/**
 * Created by Juan David on 8/05/2018.
 */

public class AdaptadorProductoEnCompra extends
        RecyclerView.Adapter<AdaptadorProductoEnCompra.ViewHolder> {

    private ArrayList<Producto> productos;
    private Context context;

    public AdaptadorProductoEnCompra(ArrayList productos, Context context) {
        this.productos = productos;
        this.context = context;
    }

    @NonNull
    @Override
    public AdaptadorProductoEnCompra.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_holder_producto_en_compra, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final AdaptadorProductoEnCompra.ViewHolder holder, int position) {
        Producto producto = productos.get(position);
        holder.tvCantidadAVender.setText(producto.getCantidadVendida() + "");
        holder.tvNombreProducto.setText(producto.getNombre());
        holder.tvPrecioUnitario.setText("$" + producto.getPrecio() + "");
        cargarMenuOpciones(holder, position);
    }

    public void cargarMenuOpciones(final ViewHolder holder, final int posicion){
        TextView opciones = holder.tvOpciones;
        opciones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("MeOprimen","me oprimen");
                PopupMenu popup = new PopupMenu(context, holder.tvOpciones);
                popup.inflate(R.menu.menu_opciones_producto_en_compra);
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.m_cambiar_cantidad:
                                abrirDialogo(posicion);
                                return true;
                            case R.id.m_eliminar:
                                eliminarProducto(posicion);
                                return true;
                            default:
                                return false;
                        }
                    }
                });
                popup.show();

            }
        });
    }

    private void eliminarProducto(int posicion){
        ((CompraActivity)context).eliminarProducto(posicion);
    }

    private void abrirDialogo(int posicion){
        ((CompraActivity)context).abrirDialogo(posicion);
    }

    @Override
    public int getItemCount() {
        return productos.size();
    }

    private void cambiarCantidadAVender(){

    }
    //TODO: Cambiar cantidad y actualizar el producto Actual

    public class ViewHolder  extends  RecyclerView.ViewHolder{

        private TextView tvCantidadAVender;
        private TextView tvNombreProducto;
        private TextView tvPrecioUnitario;
        private TextView tvOpciones;

        public ViewHolder(View itemView) {
            super(itemView);
            tvCantidadAVender = itemView.findViewById(R.id.tv_cantidad_producto_en_compra);
            tvNombreProducto = itemView.findViewById(R.id.tv_nombre_producto_en_compra);
            tvPrecioUnitario = itemView.findViewById(R.id.tv_precio_unitario_producto_en_compra);
            tvOpciones = itemView.findViewById(R.id.tv_opciones_producto_en_compra);
        }
    }


}
