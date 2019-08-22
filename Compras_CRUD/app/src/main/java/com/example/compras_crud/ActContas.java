package com.example.compras_crud;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.compras_crud.DbHelper.BancoDados;
import com.example.compras_crud.DbHelper.Cliente;

import java.util.ArrayList;
import java.util.List;

public class ActContas extends AppCompatActivity {
    EditText editCodigo, editCodigo2, editNome, editTelefone, editEmail;
    Button btnExcluir;
    ListView listViewClientes;

    BancoDados db = new BancoDados(this);

    ArrayAdapter<String> adapter;
    ArrayList<String> arrayList;

    InputMethodManager imn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_contas);

        editCodigo = (EditText)findViewById(R.id.editCodigo);
        editCodigo2 = (EditText)findViewById(R.id.editCodigo2);
        editNome = (EditText)findViewById(R.id.editNome);
        editTelefone = (EditText)findViewById(R.id.editTelefone);
        editEmail = (EditText)findViewById(R.id.editEmail);

        btnExcluir = (Button)findViewById(R.id. btnExcluir);
        listViewClientes = (ListView) findViewById(R.id.listViewClientes);

        escondeTeclado();
        //listarCliente();

        btnExcluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String codigo = editCodigo.getText().toString();

                if(codigo.isEmpty()){
                    Toast.makeText(ActContas.this, "Nenhum cliente está selecionado", Toast.LENGTH_LONG).show();
                } else {
                    Cliente cliente = new Cliente();
                    cliente.setCodigo(Integer.parseInt(codigo));
                    db.apagarCLiente(cliente);

                    Toast.makeText(ActContas.this, "Cliente excluido com sucesso", Toast.LENGTH_LONG).show();
                    /*limpaCampos();
                    listarCliente();*/
                }
            }
        });

        listViewClientes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

                String conteudo = (String) listViewClientes.getItemAtPosition(position);

                //Toast.makeText(MainActivity.this, "Select: " + conteudo, Toast.LENGTH_LONG).show();
                String codigo = conteudo.substring(0 , conteudo.indexOf("-"));
                Cliente cliente = db.SelecionarCliente(Integer.parseInt(codigo));

                Intent i = new Intent(ActContas.this, ActCad.class);

                editCodigo2.setText(String.valueOf(cliente.getCodigo()));
                editCodigo.setText(String.valueOf(cliente.getCodigo()));
                editNome.setText(cliente.getNome());
                editTelefone.setText(cliente.getTelefone());
                editEmail.setText(cliente.getEmail());

                //startActivity(i);

            }
        });
    }

    void escondeTeclado(){
        imn.hideSoftInputFromWindow(editNome.getWindowToken(), 0);
    }

    /*public void limpaCampos(){
        editCodigo.setText("");
        editNome.setText("");
        editTelefone.setText("");
        editEmail.setText("");

        editNome.requestFocus();
    }*/

    /*public void listarCliente() {
        List<Cliente> clientes = db.listaTodosClientes();

        arrayList = new ArrayList<String>();

        adapter = new ArrayAdapter<String>(ActContas.this,
                android.R.layout.simple_list_item_1,
                arrayList);

        listViewClientes.setAdapter(adapter);

        for(Cliente c : clientes){
            //Log.d("Lista", "\nID: " + c.getCodigo() + " Nome: " + c.getNome());
            arrayList.add(c.getCodigo() + "-" + c.getNome());
            adapter.notifyDataSetChanged();
        }
    }*/

}
