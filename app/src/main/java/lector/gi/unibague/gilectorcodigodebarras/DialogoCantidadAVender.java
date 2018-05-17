package lector.gi.unibague.gilectorcodigodebarras;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.NumberPicker;

/**
 * Created by Juan David on 16/05/2018.
 */

public class DialogoCantidadAVender extends AppCompatDialogFragment {

    private NumberPicker npCantidad;
    private DialogListener dl;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder((getActivity()));

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_cantidad_a_vender, null);

        builder.setView(view)
                .setTitle("Cantidad")
                .setNegativeButton("Deje así", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dl.accion();
                    }
                })
                .setPositiveButton("De una", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
        npCantidad = view.findViewById(R.id.np_cantidad_a_vender);
        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        dl = (DialogListener) context;
    }

    public interface DialogListener{
        public void accion();
    }
}
