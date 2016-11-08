package org.grupovialibre.dev.retoocho.addeditempresas;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import org.grupovialibre.dev.retoocho.EmpresasActivity;
import org.grupovialibre.dev.retoocho.R;

public class AddEditEmpresaActivity extends AppCompatActivity {

    public static final int REQUEST_ADD_EMPRESA = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_empresa);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String empresaId = getIntent().getStringExtra(EmpresasActivity.EXTRA_EMPRESA_ID);

        setTitle(empresaId == null ? "Añadir empresa" : "Editar empresa");

        AddEditEmpresaFragment addEditLawyerFragment = (AddEditEmpresaFragment)
                getSupportFragmentManager().findFragmentById(R.id.add_edit_empresa_container);
        if (addEditLawyerFragment == null) {
            addEditLawyerFragment = AddEditEmpresaFragment.newInstance(empresaId);
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.add_edit_empresa_container, addEditLawyerFragment)
                    .commit();
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
