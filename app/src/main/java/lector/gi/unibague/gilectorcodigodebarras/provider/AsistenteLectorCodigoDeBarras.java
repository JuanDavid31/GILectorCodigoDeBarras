package lector.gi.unibague.gilectorcodigodebarras.provider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class AsistenteLectorCodigoDeBarras extends SQLiteOpenHelper {

    public final static int VERSION_BD = 1;
    public final static String NOMBRE_BD = "LectorCodigoDeBarras.db";

    public AsistenteLectorCodigoDeBarras(Context context) {
        super(context, NOMBRE_BD, null, VERSION_BD);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String sqlCliente = "CREATE TABLE " + ContratoLectorCodigoDeBarras.Cliente.NOMBRE_TABLA + "( " +
                ContratoLectorCodigoDeBarras.Cliente._ID + " INTEGER PRIMARY KEY, " +
                ContratoLectorCodigoDeBarras.Cliente.COLUMNA_NOMBRE + " TEXT ); ";

        String sqlCompra = "CREATE TABLE " + ContratoLectorCodigoDeBarras.Compra.NOMBRE_TABLA + "( " +
                ContratoLectorCodigoDeBarras.Compra._ID + " INTEGER AUTOINCREMENT PRIMARY KEY, " +
                ContratoLectorCodigoDeBarras.Compra.COLUMNA_CEDULA + " INTEGER, " +
                ContratoLectorCodigoDeBarras.Compra.COLUMNA_FECHA + "TEXT );";

        String sqlCompraProducto = "CREATE TABLE " + ContratoLectorCodigoDeBarras.CompraProducto.NOMBRE_TABLA + "( " +
                ContratoLectorCodigoDeBarras.CompraProducto._ID + " INTEGER AUTOINCREMENT PRIMARY KEY," +
                ContratoLectorCodigoDeBarras.CompraProducto.COLUMNA_CODIGO_COMPRA + " INTEGER, " +
                ContratoLectorCodigoDeBarras.CompraProducto.COLUMNA_CODIGO_PRODUCTO + " INTEGER," +
                ContratoLectorCodigoDeBarras.CompraProducto.COLUMNA_CANTIDAD_VENDIDA + " INTEGER );" ;

        String sqlProducto = "CREATE TABLE " + ContratoLectorCodigoDeBarras.Producto.NOMBRE_TABLA + "( " +
                ContratoLectorCodigoDeBarras.Producto._ID + " INTEGER PRIMARY KEY, " +
                ContratoLectorCodigoDeBarras.Producto.COLUMNA_NOMBRE + " TEXT, " +
                ContratoLectorCodigoDeBarras.Producto.COLUMNA_CANTIDAD + " INTEGER );";

        String sqlAlteracionCompra = " ALTER TABLE " + ContratoLectorCodigoDeBarras.Compra.NOMBRE_TABLA +
                "ADD CONSTRAINT fk_cliente " + " FOREIGN KEY(" + ContratoLectorCodigoDeBarras.Compra.COLUMNA_CEDULA + ") " +
                 "REFERENCES " + ContratoLectorCodigoDeBarras.Cliente.NOMBRE_TABLA + "(" + ContratoLectorCodigoDeBarras.Cliente._ID + ") ON DELETE CASCADE;";

        String sqlAlteracionCompraProducto1 = " ALTER TABLE " + ContratoLectorCodigoDeBarras.CompraProducto.NOMBRE_TABLA +
                "ADD CONSTRAINT fk_compra " + " FOREIGN KEY(" + ContratoLectorCodigoDeBarras.CompraProducto.COLUMNA_CODIGO_COMPRA + ") " +
                "REFERENCES " + ContratoLectorCodigoDeBarras.Compra.NOMBRE_TABLA + "(" + ContratoLectorCodigoDeBarras.Compra._ID + ") ON DELETE CASCADE;";

        String sqlAlteracionCompraProducto2 = " ALTER TABLE " + ContratoLectorCodigoDeBarras.CompraProducto.NOMBRE_TABLA +
                "ADD CONSTRAINT fk_producto " + " FOREIGN KEY(" + ContratoLectorCodigoDeBarras.CompraProducto.COLUMNA_CODIGO_PRODUCTO + ") " +
                "REFERENCES " + ContratoLectorCodigoDeBarras.Producto.NOMBRE_TABLA + "(" + ContratoLectorCodigoDeBarras.Producto._ID + ") ON DELETE CASCADE;";

        sqLiteDatabase.execSQL(sqlCliente);
        sqLiteDatabase.execSQL(sqlCompra);
        sqLiteDatabase.execSQL(sqlProducto);
        sqLiteDatabase.execSQL(sqlCompraProducto);
        sqLiteDatabase.execSQL(sqlAlteracionCompraProducto1);
        sqLiteDatabase.execSQL(sqlAlteracionCompraProducto2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String dropCliente = "DROP TABLE IF EXISTS " + ContratoLectorCodigoDeBarras.Cliente.NOMBRE_TABLA;
        String dropCompra = "DROP TABLE IF EXISTS " + ContratoLectorCodigoDeBarras.Compra.NOMBRE_TABLA;
        String dropProducto = "DROP TABLE IF EXISTS " + ContratoLectorCodigoDeBarras.Producto.NOMBRE_TABLA;
        String dropCompraProducto = "DROP TABLE IF EXISTS " + ContratoLectorCodigoDeBarras.CompraProducto.NOMBRE_TABLA;

        sqLiteDatabase.execSQL(dropCliente);
        sqLiteDatabase.execSQL(dropCompra);
        sqLiteDatabase.execSQL(dropProducto);
        sqLiteDatabase.execSQL(dropCompraProducto);
        onCreate(sqLiteDatabase);
    }
}
