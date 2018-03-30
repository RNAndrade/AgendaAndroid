package br.com.agendaandroid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import br.com.agendaandroid.dao.AlunoDAO;
import br.com.agendaandroid.model.Aluno;

public class FormActivity extends AppCompatActivity {

    private FormHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        helper = new FormHelper(this);

        Intent intent = getIntent();
        Aluno aluno = (Aluno)intent.getSerializableExtra("aluno");
        if (aluno != null){
            helper.popularForm(aluno);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_form, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_salvarAluno:

                Aluno aluno = helper.pegaAluno();
                AlunoDAO dao = new AlunoDAO(this);

                if (aluno.getId() != null){
                    dao.atualizar(aluno);
                    Toast.makeText(FormActivity.this, "Atualizado com sucesso!", Toast.LENGTH_SHORT).show();
                } else {
                    dao.inserir(aluno);
                    Toast.makeText(FormActivity.this, "Adicionado com sucesso!", Toast.LENGTH_SHORT).show();
                }
                dao.close();
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
