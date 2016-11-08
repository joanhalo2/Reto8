package org.grupovialibre.dev.retoocho.entidades;

/**
 * Created by joan on 7/11/16.
 */

public class ConstantesDB {

    //General
    public static final String DB_NAME = "retoocho.db";
    public static final int DB_VERSION = 1;


    //TABLA TIPO EMPRESA
    public static final String TABLA_TIPO_EMPRESA = "tipo_empresa";
    public static final String TIPO_EMPRESA_ID = "_id";
    public static final String TIPO_EMPRESA_NOMBRE = "tipo_empresa_nombre";

    public static final String TABLA_TIPO_EMPRESA_SQL =
            "CREATE TABLE  " + TABLA_TIPO_EMPRESA + "(" +
                    TIPO_EMPRESA_ID + " INTEGER PRIMARY KEY," +
                    TIPO_EMPRESA_NOMBRE + " TEXT NOT NULL" +
                    ");"
    ;

    public static  final String INSERT_TIPO_EMPRESA_CONSULTORIA = "INSERT INTO tipo_empresa VALUES (1,'Consultoria')";
    public static  final String INSERT_TIPO_EMPRESA_DESARROLLO_A_LA_MEDIDA = "INSERT INTO tipo_empresa VALUES (2,'Desarrollo a la medida')";
    public static  final String INSERT_TIPO_EMPRESA_FABRICA_SOFTWARE = "INSERT INTO tipo_empresa VALUES (3,'Fabrica de Software')";



    //TABLA EMPRESAS
    public static final String TABLA_EMPRESAS = "empresas";
    public static final String EMPRE_ID = "_id";
    public static final String EMPRE_NOMBRE = "nombre";
    public static final String EMPRE_URL = "url";
    public static final String EMPRE_TELF = "telefono";
    public static final String EMPRE_MAIL = "email";
    public static final String EMPRE_PRODS = "productos_servicios";
    public static final String EMPRE_ID_TIPO_EMPRESA = "id_tipoempresa";

    public static final String TABLA_EMPRESAS_SQL =
            "CREATE TABLE  " + TABLA_EMPRESAS + "(" +
                    EMPRE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    EMPRE_NOMBRE + " TEXT NOT NULL," +
                    EMPRE_URL   + " TEXT NOT NULL," +
                    EMPRE_TELF   + " TEXT NOT NULL," +
                    EMPRE_MAIL   + " TEXT NOT NULL," +
                    EMPRE_PRODS   + " TEXT NOT NULL," +
                    EMPRE_ID_TIPO_EMPRESA   + " INTEGER NOT NULL," +
                    "FOREIGN KEY("+EMPRE_ID_TIPO_EMPRESA+") REFERENCES "+TABLA_TIPO_EMPRESA+"("+TIPO_EMPRESA_ID+")"+
                    ");"
    ;


}
