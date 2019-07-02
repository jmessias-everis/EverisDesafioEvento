package com.everis.persistenciaSQLite.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.everis.persistenciaSQLite.Domain.Usuario;
import com.everis.persistenciaSQLite.Infra.HelperDB;

public class UsuarioDAO {
    public static final String TABLE_USUARIO = "usuario";

    private static final String ID = "_id";
    private static final String MATRICULA = "matricula";
    private static final String EMAIL = "email";
    private static final String SENHA = "senha";
    private static final String ADMIN = "admin";

    public static final String DATABASE_CREATE = "create table "
            + TABLE_USUARIO + "( "
            + ID + " integer primary key autoincrement, "
            + MATRICULA + " integer not null, "
            + EMAIL + " text not null, "
            + SENHA + " text not null, "
            + ADMIN + " text not null);";

    private HelperDB helperDB;

    private String[] allColumns = {
            UsuarioDAO.ID,
            UsuarioDAO.MATRICULA,
            UsuarioDAO.EMAIL,
            UsuarioDAO.SENHA,
            UsuarioDAO.ADMIN
    };

    public UsuarioDAO(Context context){
        helperDB = new HelperDB(context);
    }

    public boolean salvar(Usuario usuario){
        SQLiteDatabase database = helperDB.getWritableDatabase();

        ContentValues values = new ContentValues();
        if(usuario.getId() != 0){
            values.put(UsuarioDAO.ID, usuario.getId());
        }
        values.put(UsuarioDAO.MATRICULA, usuario.getMatricula());
        values.put(UsuarioDAO.EMAIL, usuario.getEmail());
        values.put(UsuarioDAO.SENHA, usuario.getSenha());
        values.put(UsuarioDAO.ADMIN, usuario.isAdmin());
        long insertId = database.insert(UsuarioDAO.TABLE_USUARIO, null, values);

        return insertId > 0 ? Boolean.TRUE : Boolean.FALSE;
    }


    public boolean verificarSeUsuarioExiste(String email){
        SQLiteDatabase database = helperDB.getReadableDatabase();
        Cursor cursor = database.query(UsuarioDAO.TABLE_USUARIO, allColumns, UsuarioDAO.EMAIL + "='"
                + email + "'", null, null,null,null);
        if(cursor.getCount() <= 0){
            cursor.close();
            return false;
        }
        cursor.close();
        return true;
    }

    public Usuario buscarPorEmail(String email){
        SQLiteDatabase database = helperDB.getReadableDatabase();
        Cursor cursor = database.query(UsuarioDAO.TABLE_USUARIO, allColumns, UsuarioDAO.EMAIL + "='"
                + email + "'", null, null,null,null);
        cursor.moveToFirst();
        return cursorToObject(cursor);
    }

    public Usuario buscarPorId(long id){
        SQLiteDatabase database = helperDB.getReadableDatabase();
        Cursor cursor = database.query(UsuarioDAO.TABLE_USUARIO, allColumns, UsuarioDAO.ID + "='"
                + id + "'", null, null,null,null);

        cursor.moveToFirst();
        return cursorToObject(cursor);
    }

    private Usuario cursorToObject(Cursor cursor){
        Usuario usuario = new Usuario();
        usuario.setId(cursor.getLong(0));
        usuario.setMatricula(cursor.getInt(1));
        usuario.setEmail(cursor.getString(2));
        usuario.setSenha(cursor.getString(3));
        usuario.setAdmin(cursor.getInt(4) > 0);

        return usuario;
    }
}
