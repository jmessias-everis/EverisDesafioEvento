package com.everis.persistenciaSQLite.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.everis.persistenciaSQLite.Domain.Contato;
import com.everis.persistenciaSQLite.Domain.Participante;
import com.everis.persistenciaSQLite.Infra.HelperDB;

import java.util.ArrayList;

public class ContatoDAO {

    public static final String TABLE_CONTATO = "contato";

    public static final String ID = "_id";
    public static final String NOME = "nome";
    public static final String TELEFONE = "telefone";
    public static final String ATIVO = "ativo";

    public static final String DATABASE_CREATE = "create table "
            + TABLE_CONTATO + "( "
            + ID + " integer primary key autoincrement, "
            + NOME + " text not null, "
            + TELEFONE + " text not null, "
            + ATIVO + " text not null);";

    private HelperDB helperDB;

    private String[] allColumns = {
            ContatoDAO.ID,
            ContatoDAO.NOME,
            ContatoDAO.TELEFONE,
            ContatoDAO.ATIVO
    };

    public ContatoDAO(Context context){
        helperDB = new HelperDB(context);
    }

    public boolean salvar(Contato contato){
        SQLiteDatabase database = helperDB.getWritableDatabase();

        ContentValues values = new ContentValues();
        if(contato.getId() != 0){
            values.put(ContatoDAO.ID, contato.getId());
        }
        values.put(ContatoDAO.NOME, contato.getNome());
        values.put(ContatoDAO.TELEFONE, contato.getTelefone());
        values.put(ContatoDAO.ATIVO, contato.isAtivo());

        long insertId = database.insert(ContatoDAO.TABLE_CONTATO, null, values);

        return insertId > 0 ? Boolean.TRUE : Boolean.FALSE;
    }

    public boolean deletarContato(Contato contato){
        SQLiteDatabase database = helperDB.getWritableDatabase();

        ContentValues values = new ContentValues();
        if(contato.getId() != 0){
            values.put(ContatoDAO.ID, contato.getId());
        }
        values.put(ContatoDAO.NOME, contato.getNome());
        values.put(ContatoDAO.TELEFONE, contato.getTelefone());
        values.put(ContatoDAO.ATIVO, contato.isAtivo());

        long insertId = database.insert(ContatoDAO.TABLE_CONTATO, null, values);

        return insertId > 0 ? Boolean.TRUE : Boolean.FALSE;
    }


    public Contato buscarPorId(long id){
        SQLiteDatabase database = helperDB.getReadableDatabase();

        Cursor cursor = database.query(ContatoDAO.TABLE_CONTATO, allColumns, ContatoDAO.ID + "='"
                + id + "'", null, null,null,null);
        cursor.moveToFirst();

        return cursorToObject(cursor);
    }

    public long buscarIdPorTelefone(String telefone){
        SQLiteDatabase database = helperDB.getReadableDatabase();
        Cursor cursor = database.query(ContatoDAO.TABLE_CONTATO, allColumns, ContatoDAO.TELEFONE + "='"
                + telefone + "'", null, null,null,null);
        cursor.moveToFirst();
        return cursorToObject(cursor).getId();
    }

    public Contato buscarPorTelefone(String telefone){
        SQLiteDatabase database = helperDB.getReadableDatabase();

        Cursor cursor = database.query(ContatoDAO.TABLE_CONTATO, allColumns, ContatoDAO.TELEFONE + "='"
                + telefone + "'", null, null,null,null);
        cursor.moveToFirst();

        return cursorToObject(cursor);
    }

    public boolean verificarSeContatoExiste(String telefone){
        SQLiteDatabase database = helperDB.getReadableDatabase();

        Cursor cursor = database.query(ContatoDAO.TABLE_CONTATO, allColumns, ContatoDAO.TELEFONE + "='"
                + telefone + "'", null, null,null,null);

        if(cursor.getCount() <= 0){
            cursor.close();
            return false;
        }
        cursor.close();
        return true;
    }

    public ArrayList<Contato> buscarTodos(){
        SQLiteDatabase database = helperDB.getReadableDatabase();

        Cursor cursor = database.query(ContatoDAO.TABLE_CONTATO, allColumns, null, null,null,null,null);
        ArrayList<Contato> contatos = new ArrayList<>();

        if (cursor.moveToFirst()) {
            int indId = cursor.getColumnIndex(ContatoDAO.ID);
            int indNome = cursor.getColumnIndex(ContatoDAO.NOME);
            int indTelefone = cursor.getColumnIndex(ContatoDAO.TELEFONE);
            int indAtivo = cursor.getColumnIndex(ContatoDAO.ATIVO);

            do {
                Contato contato = new Contato();

                contato.setId(cursor.getLong(indId));
                contato.setNome(cursor.getString(indNome));
                contato.setTelefone(cursor.getString(indTelefone));
                contato.setAtivo(cursor.getInt(indAtivo) > 0);

                contatos.add(contato);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return contatos;
    }

    private Contato cursorToObject(Cursor cursor){
        Contato contato = new Contato();
        contato.setId(cursor.getLong(0));
        contato.setNome(cursor.getString(1));
        contato.setTelefone(cursor.getString(2));
        contato.setAtivo(cursor.getInt(3)>0);
        return contato;
    }
}