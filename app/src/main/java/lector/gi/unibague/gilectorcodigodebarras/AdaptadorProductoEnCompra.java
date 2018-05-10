package lector.gi.unibague.gilectorcodigodebarras;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import lector.gi.unibague.gilectorcodigodebarras.modelo.Producto;
import lector.gi.unibague.gilectorcodigodebarras.provider.ContratoLectorCodigoDeBarras;

/**
 * Created by Juan David on 8/05/2018.
 */

public class AdaptadorProductoEnCompra extends RecyclerView.Adapter<AdaptadorProductoEnCompra.ViewHolder> {

    private ArrayList<Producto> productos;

    public AdaptadorProductoEnCompra(ArrayList productos) {
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
        holder.tvCantidadAVender.setText(1 + "");
        holder.tvNombreProducto.setText(producto.getNombre());
        holder.tvPrecioUnitario.setText(producto.getPrecio() + "");
    }

    @Override
    public int getItemCount() {
        return productos.size();
    }

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
