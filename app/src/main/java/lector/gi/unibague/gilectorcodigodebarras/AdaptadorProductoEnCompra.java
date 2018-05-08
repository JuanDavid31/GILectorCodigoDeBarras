package lector.gi.unibague.gilectorcodigodebarras;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import lector.gi.unibague.gilectorcodigodebarras.modelo.Producto;
import lector.gi.unibague.gilectorcodigodebarras.provider.ContratoLectorCodigoDeBarras;

/**
 * Created by Juan David on 8/05/2018.
 */

public class AdaptadorProductoEnCompra extends RecyclerView.Adapter<AdaptadorProductoEnCompra.ViewHolder> {

    private ArrayList<Producto> productos;

    public AdaptadorProductoEnCompra(ArrayList productos){
        this.productos = productos;
    }

    @NonNull
    @Override
    public AdaptadorProductoEnCompra.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_holder_producto_en_compra, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AdaptadorProductoEnCompra.ViewHolder holder, int position) {
        Producto producto = productos.get(position); //TODO: Añadirle las variables al holder
//        holder.tvNombreProducto.setText(cursor.getString(cursor.getColumnIndex(ContratoLectorCodigoDeBarras.Producto.COLUMNA_NOMBRE)));
//        holder.tvCantidadProducto.setText(cursor.getInt(cursor.getColumnIndex(ContratoLectorCodigoDeBarras.Producto.COLUMNA_CANTIDAD)) + "");
    }

    @Override
    public int getItemCount() {
        return productos.size();
    }

    public class ViewHolder  extends  RecyclerView.ViewHolder{

        //TODO: Variables para añadir: cantidadAVender, nombreProducto, precio ( No sé si unitario o en total), el boton de opciones


        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
