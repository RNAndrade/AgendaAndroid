package br.com.agendaandroid;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import br.com.agendaandroid.adapter.AlunosAdapter;
import br.com.agendaandroid.dao.AlunoDAO;
import br.com.agendaandroid.model.Aluno;

public class MainActivity extends AppCompatActivity {

    private ListView listaAlunos;

    @Override
        protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listaAlunos = findViewById(R.id.lista_alunos);
        listaAlunos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View item, int position, long id) {
                Aluno aluno = (Aluno) listaAlunos.getItemAtPosition(position);
                Intent formActivity = new Intent(MainActivity.this, FormActivity.class);
                formActivity.putExtra("aluno", aluno);
                startActivity(formActivity);
            }
        });

        FloatingActionButton buttonNewAluno = findViewById(R.id.main_bt_new);
        buttonNewAluno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentFormActivity = new Intent(MainActivity.this, FormActivity.class);
                startActivity(intentFormActivity);
            }
        });

        registerForContextMenu(listaAlunos);
    }

    private void carregarLista() {
        AlunoDAO dao = new AlunoDAO(this);
        List<Aluno> alunos = dao.listar();
        dao.close();

        AlunosAdapter adapter = new AlunosAdapter(this, alunos);
        listaAlunos.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        carregarLista();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, final ContextMenu.ContextMenuInfo menuInfo) {
        MenuItem ligar = menu.add("Ligar");
        MenuItem sms = menu.add("Enviar SMS");
        MenuItem localizar = menu.add("Localizar no Mapa");
        MenuItem site = menu.add("Ir para o Site");
        MenuItem apagar = menu.add("Apagar");
        apagar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)menuInfo;
                Aluno aluno = (Aluno)listaAlunos.getItemAtPosition(info.position);
                AlunoDAO dao = new AlunoDAO(MainActivity.this);
                dao.apagar(aluno);
                dao.close();
                carregarLista();
                Toast.makeText(MainActivity.this, "Aluno " + aluno.getNome() + " apagado!", Toast.LENGTH_SHORT).show();
                return false;
            }
        });
    }
}
