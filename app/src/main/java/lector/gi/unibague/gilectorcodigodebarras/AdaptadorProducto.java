package lector.gi.unibague.gilectorcodigodebarras;

import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import lector.gi.unibague.gilectorcodigodebarras.provider.ContratoLectorCodigoDeBarras;

/**
 * Created by Personal on 25/03/2018.
 */

public class AdaptadorProducto extends RecyclerView.Adapter<AdaptadorProducto.ViewHolder> {

    private Cursor cursor;

    public AdaptadorProducto(Cursor cursor){
        this.cursor = cursor;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.view_holder_producto, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        cursor.move(position + 1);
        holder.tvNombreProducto.setText(cursor.getString(cursor.getColumnIndex(ContratoLectorCodigoDeBarras.Producto.COLUMNA_NOMBRE)));
        holder.tvCantidadProducto.setText(cursor.getInt(cursor.getColumnIndex(ContratoLectorCodigoDeBarras.Producto.COLUMNA_CANTIDAD)) + "");
    }

    @Override
    public int getItemCount() {
        return cursor.getCount();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView tvNombreProducto;
        private TextView tvCantidadProducto;

        public ViewHolder(View itemView) {
            super(itemView);
            tvNombreProducto = itemView.findViewById(R.id.tv_nombre_producto);
            tvCantidadProducto = itemView.findViewById(R.id.tv_cantidad_producto);
        }
    }


}
