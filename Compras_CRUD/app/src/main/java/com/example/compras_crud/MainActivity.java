package com.example.compras_crud;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.compras_crud.DbHelper.BancoDados;
import com.example.compras_crud.DbHelper.Cliente;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    EditText editCodigo, editNome, editTelefone, editEmail;
    Button btnCad, btnConta;

    ListView listViewClientes;

    ArrayAdapter<String> adapter;
    ArrayList<String> arrayList;

    BancoDados db = new BancoDados(this);

    InputMethodManager imn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editCodigo = (EditText)findViewById(R.id.editCodigo);
        editNome = (EditText)findViewById(R.id.editNome);
        editTelefone = (EditText)findViewById(R.id.editTelefone);
        editEmail = (EditText)findViewById(R.id.editEmail);
        btnCad = (Button)findViewById(R.id.btnCad);
        btnConta = (Button)findViewById(R.id.btnConta);

        listViewClientes = (ListView) findViewById(R.id.listViewClientes);

        btnCad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, ActCad.class);
                startActivity(i);
            }
        });
        btnConta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, ActContas.class);
                startActivity(i);
            }
        });

        escondeTeclado();
        //listarCliente();
    }

    public void escondeTeclado(){
        imn.hideSoftInputFromWindow(editNome.getWindowToken(), 0);
    }

    public void listarCliente() {
        List<Cliente> clientes = db.listaTodosClientes();

        arrayList = new ArrayList<String>();

        adapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, arrayList);

        listViewClientes.setAdapter(adapter);

        for (Cliente c : clientes) {
            Log.d("Lista", "\nID: " + c.getCodigo() + " Nome: " + c.getNome());
            arrayList.add(c.getCodigo() + "-" + c.getNome());
            adapter.notifyDataSetChanged();
        }
    }


}
