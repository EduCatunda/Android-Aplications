package com.example.compras_crud;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.compras_crud.DbHelper.BancoDados;
import com.example.compras_crud.DbHelper.Cliente;

import java.util.ArrayList;
import java.util.List;

public class ActCad extends AppCompatActivity {
    EditText editCodigo, editNome, editTelefone, editEmail;
    Button btnLimpar, btnSalvar;

    ListView listViewClientes;
    BancoDados db = new BancoDados(this);

    ArrayAdapter<String> adapter;
    ArrayList<String> arrayList;

    InputMethodManager imn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_cad);

        editCodigo = (EditText)findViewById(R.id.editCodigo);
        editNome = (EditText)findViewById(R.id.editNome);
        editTelefone = (EditText)findViewById(R.id.editTelefone);
        editEmail = (EditText)findViewById(R.id.editEmail);

        btnLimpar = (Button)findViewById(R.id.btnLimpar);
        btnSalvar = (Button)findViewById(R.id.btnSalvar);

        imn = (InputMethodManager) this.getSystemService(Service.INPUT_METHOD_SERVICE);

        listViewClientes = (ListView) findViewById(R.id.listViewClientes);

        escondeTeclado();
        //listarCliente();

        btnLimpar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                limpaCampos();
            }
        });

        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String codigo = editCodigo.getText().toString();
                String nome = editNome.getText().toString();
                String telefone = editTelefone.getText().toString();
                String email = editEmail.getText().toString();

                if (nome.isEmpty()){
                    editNome.setError("Este campo é obrigatorio");

                }else if(codigo.isEmpty()){
                    //insert
                    Intent i = new Intent(ActCad.this, MainActivity.class);
                    db.addCliente(new Cliente(nome,telefone,email));
                    Toast.makeText(ActCad.this, "Cliente adicionado com sucesso", Toast.LENGTH_LONG).show();

                    limpaCampos();
                    //listarCliente();
                    escondeTeclado();

                    startActivity(i);

                }else {
                    //update
                    db.atualizarCliente(new Cliente(Integer.parseInt(codigo),nome,telefone,email));
                    Toast.makeText(ActCad.this, "Cliente atualizado com sucesso", Toast.LENGTH_LONG).show();

                    limpaCampos();
                    listarCliente();
                    escondeTeclado();
                }
            }
        });
    }

    public void escondeTeclado(){
        imn.hideSoftInputFromWindow(editNome.getWindowToken(), 0);
    }


    public void limpaCampos(){
        editCodigo.setText("");
        editNome.setText("");
        editTelefone.setText("");
        editEmail.setText("");

        editNome.requestFocus();
    }

    /*public void listarCliente() {
        List<Cliente> clientes = db.listaTodosClientes();

        arrayList = new ArrayList<String>();

        adapter = new ArrayAdapter<String>(ActCad.this, android.R.layout.simple_list_item_1, arrayList);

        listViewClientes.setAdapter(adapter);

        for (Cliente c : clientes) {
            //Log.d("Lista", "\nID: " + c.getCodigo() + " Nome: " + c.getNome());
            arrayList.add(c.getCodigo() + "-" + c.getNome());
            adapter.notifyDataSetChanged();
        }
    }*/

}

