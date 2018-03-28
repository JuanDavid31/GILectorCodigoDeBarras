package lector.gi.unibague.gilectorcodigodebarras.provider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Personal on 24/03/2018.
 */

public class AsistenteLectorCodigoDeBarras extends SQLiteOpenHelper {

    public final static int VERSION_BD = 1;
    public final static String NOMBRE_BD = "LectorCodigoDeBarras.db";

    public AsistenteLectorCodigoDeBarras(Context context) {
        super(context, NOMBRE_BD, null, VERSION_BD);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql = "CREATE TABLE " + ContratoLectorCodigoDeBarras.Producto.NOMBRE_TABLA + "( " +
                ContratoLectorCodigoDeBarras.Producto._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ContratoLectorCodigoDeBarras.Producto.COLUMNA_NOMBRE + " TEXT, " +
                ContratoLectorCodigoDeBarras.Producto.COLUMNA_CANTIDAD + " INTEGER );";

        sqLiteDatabase.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String sql = "DROP TABLE IF EXISTS " + ContratoLectorCodigoDeBarras.Producto.NOMBRE_TABLA;
        sqLiteDatabase.execSQL(sql);
        onCreate(sqLiteDatabase);
    }
}
