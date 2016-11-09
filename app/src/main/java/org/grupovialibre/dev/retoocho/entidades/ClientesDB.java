package org.grupovialibre.dev.retoocho.entidades;

/**
 * Created by joan on 7/11/16.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class ClientesDB {


    private SQLiteDatabase db;
    private DBHelper dbHelper;

    public ClientesDB(Context context) {
        dbHelper = new DBHelper(context);
    }

    private void openReadableDB() {
        db = dbHelper.getReadableDatabase();
    }

    private void openWriteableDB() {
        db = dbHelper.getWritableDatabase();
    }

    private void closeDB() {
        if(db!=null){
            db.close();
        }
    }

    // CRUD...
    private ContentValues clienteMapperContentValues(Empresa empresa) {
        ContentValues cv = new ContentValues();
        cv.put(ConstantesDB.EMPRE_NOMBRE, empresa.getNombre());
        cv.put(ConstantesDB.EMPRE_URL, empresa.getUrl());
        cv.put(ConstantesDB.EMPRE_TELF, empresa.getTelefono());
        cv.put(ConstantesDB.EMPRE_MAIL, empresa.getEmail());
        cv.put(ConstantesDB.EMPRE_PRODS, empresa.getProductos_servicios());
        cv.put(ConstantesDB.EMPRE_ID_TIPO_EMPRESA, empresa.getTipoEmpresa().getId());
        return cv;
    }

    private ContentValues clienteMapperContentValues(TipoEmpresa tipoEmpresa) {
        ContentValues cv = new ContentValues();
        cv.put(ConstantesDB.TIPO_EMPRESA_NOMBRE, tipoEmpresa.getNombre());
        return cv;
    }


    //Insertar en la DB
    public long insertTipoEmpresa(TipoEmpresa tipoEmpresa) {
        this.openWriteableDB();
        long rowID = db.insert(ConstantesDB.TABLA_TIPO_EMPRESA, null, clienteMapperContentValues(tipoEmpresa));
        this.closeDB();
        return rowID;
    }

    public long insertEmpresa(Empresa empresa) {
        this.openWriteableDB();
        long rowID = db.insert(ConstantesDB.TABLA_EMPRESAS, null, clienteMapperContentValues(empresa));
        this.closeDB();
        return rowID;
    }

    //Actualizar en la DB
    public int updateEmpresa(Empresa empresa,String e_id) {
        this.openWriteableDB();
        String where = ConstantesDB.EMPRE_ID + "= ?";
        return db.update(ConstantesDB.TABLA_EMPRESAS, clienteMapperContentValues(empresa), where, new String[]{e_id});
        //db.close();
    }

    //Borrar de la DB
    public void deleteCliente(int id) {
        this.openWriteableDB();
        String where = ConstantesDB.EMPRE_ID + "= ?";
        db.delete(ConstantesDB.TABLA_EMPRESAS, where, new String[]{String.valueOf(id)});
        this.closeDB();
    }

    public int deleteEmpresa(String empresaId) {
        return dbHelper.getWritableDatabase().delete(
                ConstantesDB.TABLA_EMPRESAS,
                ConstantesDB.EMPRE_ID + " LIKE ?",
                new String[]{empresaId});
    }


    public Cursor getAllEmpresas() {
        return dbHelper.getReadableDatabase()
                .query(
                        ConstantesDB.TABLA_EMPRESAS,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null);
    }

    public Cursor getEmpresasById(String empresaId) {
        Cursor c = dbHelper.getReadableDatabase().query(
                ConstantesDB.TABLA_EMPRESAS,
                null,
                ConstantesDB.EMPRE_ID + " LIKE ?",
                new String[]{empresaId},
                null,
                null,
                null);
        return c;
    }

    //Leer todas las empresas
    public ArrayList<Empresa> loadAllEmpresas() {

        ArrayList<Empresa> list = new ArrayList<Empresa>();

        this.openReadableDB();
        String[] campos = new String[]{ ConstantesDB.EMPRE_ID,
                                        ConstantesDB.EMPRE_NOMBRE,
                                        ConstantesDB.EMPRE_URL,
                                        ConstantesDB.EMPRE_TELF,
                                        ConstantesDB.EMPRE_MAIL,
                                        ConstantesDB.EMPRE_PRODS,
                                        ConstantesDB.EMPRE_ID_TIPO_EMPRESA
                                        };
        Cursor c = db.query(ConstantesDB.TABLA_EMPRESAS, campos, null, null, null, null, null);

        try {
            while (c.moveToNext()) {
                Empresa empresa = new Empresa();
                empresa.setId(c.getInt(0));
                empresa.setNombre(c.getString(1));
                empresa.setUrl(c.getString(2));
                empresa.setTelefono(c.getString(3));
                empresa.setEmail(c.getString(4));
                empresa.setProductos_servicios(c.getString(5));
                empresa.setTipoEmpresa(new TipoEmpresa(c.getInt(6)));
                list.add(empresa);
            }
        } finally {
            c.close();
        }
        this.closeDB();

        return list;
    }

    public ArrayList<TipoEmpresa> loadAllTiposEmpresas() {

        ArrayList<TipoEmpresa> list = new ArrayList<TipoEmpresa>();

        this.openReadableDB();
        String[] campos = new String[]{ ConstantesDB.TIPO_EMPRESA_ID,
                ConstantesDB.TIPO_EMPRESA_NOMBRE
        };
        Cursor c = db.query(ConstantesDB.TABLA_TIPO_EMPRESA, campos, null, null, null, null, null);

        try {
            while (c.moveToNext()) {
                TipoEmpresa tipoEmpresa = new TipoEmpresa();
                tipoEmpresa.setId(c.getInt(0));
                tipoEmpresa.setNombre(c.getString(1));
                list.add(tipoEmpresa);
            }
        } finally {
            c.close();
        }
        this.closeDB();

        return list;
    }

    //Obtener un registro
    public Empresa buscarEmpresaPorNombre(String nombre) {
        Empresa cliente = new Empresa();
        this.openReadableDB();
        String where = ConstantesDB.EMPRE_NOMBRE + "= ?";
        String[] whereArgs = {nombre};
        Cursor c = db.query(ConstantesDB.TABLA_EMPRESAS, null, where, whereArgs, null, null, null);

        if( c != null || c.getCount() <=0) {
            c.moveToFirst();
            cliente.setId(c.getInt(0));
            cliente.setNombre(c.getString(1));
            cliente.setUrl(c.getString(2));
            cliente.setTelefono(c.getString(3));
            cliente.setEmail(c.getString(4));
            cliente.setProductos_servicios(c.getString(5));
            cliente.setTipoEmpresa(new TipoEmpresa(c.getInt(6)));
            c.close();
        }
        this.closeDB();
        return cliente;
    }

    private static class DBHelper extends SQLiteOpenHelper {

        public DBHelper(Context context) {
            super(context, ConstantesDB.DB_NAME, null, ConstantesDB.DB_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(ConstantesDB.TABLA_TIPO_EMPRESA_SQL);
            db.execSQL(ConstantesDB.TABLA_EMPRESAS_SQL);

            db.execSQL(ConstantesDB.INSERT_TIPO_EMPRESA_CONSULTORIA);
            db.execSQL(ConstantesDB.INSERT_TIPO_EMPRESA_DESARROLLO_A_LA_MEDIDA);
            db.execSQL(ConstantesDB.INSERT_TIPO_EMPRESA_FABRICA_SOFTWARE);

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }
}
