package lector.gi.unibague.gilectorcodigodebarras;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import lector.gi.unibague.gilectorcodigodebarras.provider.ContratoLectorCodigoDeBarras;
import room.entidades.Producto;

/**
 * Created by Personal on 25/03/2018.
 */

public class AdaptadorProductoEnStock extends RecyclerView.Adapter<AdaptadorProductoEnStock.ViewHolder> {

    private List<Producto> productos;

    public AdaptadorProductoEnStock(List<Producto> productos){
        this.productos = productos;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.view_holder_producto_en_stock, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Producto p = productos.get(position);
        holder.tvNombreProducto.setText(p.nombre);
        holder.tvCantidadProducto.setText("Stock - " + p.cantidad);
        holder.tvPrecioUnitario.setText( "Precio unitario - $" + p.precio);
    }

    @Override
    public int getItemCount() {
        return productos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView tvNombreProducto;
        private TextView tvCantidadProducto;
        private TextView tvPrecioUnitario;

        public ViewHolder(View itemView) {
            super(itemView);
            tvNombreProducto = itemView.findViewById(R.id.tv_nombre_producto);
            tvCantidadProducto = itemView.findViewById(R.id.tv_cantidad_producto);
            tvPrecioUnitario = itemView.findViewById(R.id.tv_precio_unitario_producto_en_stock);
        }
    }


}
