package br.com.agendaandroid;

import android.widget.EditText;
import android.widget.RatingBar;

import br.com.agendaandroid.model.Aluno;

public class FormHelper {

    private final EditText campoNome;
    private final EditText campoTelefone;
    private final EditText campoSite;
    private final RatingBar campoNota;
    private Aluno aluno;


    public FormHelper(FormActivity activity){

        campoNome = (EditText)activity.findViewById(R.id.form_nome);
        campoTelefone = (EditText)activity.findViewById(R.id.form_telefone);
        campoSite = (EditText)activity.findViewById(R.id.form_site);
        campoNota = (RatingBar)activity.findViewById(R.id.form_nota);
        aluno = new Aluno();
    }

    public Aluno pegaAluno() {
        aluno.setNome(campoNome.getText().toString());
        aluno.setTelefone(campoTelefone.getText().toString());
        aluno.setSite(campoSite.getText().toString());
        aluno.setNota(Double.valueOf(campoNota.getProgress()));
        return aluno;
    }

    public void popularForm(Aluno aluno) {
        campoNome.setText(aluno.getNome());
        campoTelefone.setText(aluno.getTelefone());
        campoSite.setText(aluno.getSite());
        campoNota.setProgress(aluno.getNota().intValue());
        this.aluno = aluno;

    }
}
