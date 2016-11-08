package org.grupovialibre.dev.retoocho;


import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import org.grupovialibre.dev.retoocho.addeditempresas.AddEditEmpresaActivity;
import org.grupovialibre.dev.retoocho.empresadetail.EmpresaDetailActivity;
import org.grupovialibre.dev.retoocho.entidades.ClientesDB;
import org.grupovialibre.dev.retoocho.entidades.ConstantesDB;
import org.grupovialibre.dev.retoocho.entidades.EmpresasCursorAdapter;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EmpresasFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EmpresasFragment extends Fragment {
    private ClientesDB mEmpresasDbHelper;
    public static final int REQUEST_UPDATE_DELETE_LAWYER = 2;


    private ListView mEmpresasList;
    private EmpresasCursorAdapter mEmpresasAdapter;
    private FloatingActionButton mAddButton;

    public EmpresasFragment() {
        // Required empty public constructor
    }


    public static EmpresasFragment newInstance() {
        return new EmpresasFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_empresas, container, false);

        // Referencias UI
        mEmpresasList = (ListView) root.findViewById(R.id.empresas_list);
        mEmpresasAdapter = new EmpresasCursorAdapter(getActivity(), null);
        mAddButton = (FloatingActionButton) getActivity().findViewById(R.id.fab);
        mAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddScreen();
            }
        });
        // Setup
        mEmpresasList.setAdapter(mEmpresasAdapter);

        // Instancia de helper
        mEmpresasDbHelper = new ClientesDB(getActivity());

        // Carga de datos
        loadEmpresas();

        // Eventos
        mEmpresasList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Cursor currentItem = (Cursor) mEmpresasAdapter.getItem(i);
                String currentLawyerId = currentItem.getString(
                        currentItem.getColumnIndex(ConstantesDB.EMPRE_ID));

                showDetailScreen(currentLawyerId);
            }
        });

        return root;
    }

    private void showDetailScreen(String lawyerId) {
        Intent intent = new Intent(getActivity(), EmpresaDetailActivity.class);
        intent.putExtra(EmpresasActivity.EXTRA_EMPRESA_ID, lawyerId);
        startActivityForResult(intent, REQUEST_UPDATE_DELETE_LAWYER);
    }

    private void showAddScreen() {
        Intent intent = new Intent(getActivity(), AddEditEmpresaActivity.class);
        startActivityForResult(intent, AddEditEmpresaActivity.REQUEST_ADD_EMPRESA);
    }

    private void loadEmpresas() {
        new EmpresasLoadTask().execute();
    }

    private class EmpresasLoadTask extends AsyncTask<Void, Void, Cursor> {

        @Override
        protected Cursor doInBackground(Void... voids) {
            return mEmpresasDbHelper.getAllEmpresas();
        }

        @Override
        protected void onPostExecute(Cursor cursor) {
            if (cursor != null && cursor.getCount() > 0) {
                mEmpresasAdapter.swapCursor(cursor);
            } else {
                // Mostrar empty state
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (Activity.RESULT_OK == resultCode) {
            switch (requestCode) {
                case AddEditEmpresaActivity.REQUEST_ADD_EMPRESA:
                    showSuccessfullSavedMessage();
                    loadEmpresas();
                    break;
                case REQUEST_UPDATE_DELETE_LAWYER:
                    loadEmpresas();
                    break;
            }
        }
    }

    private void showSuccessfullSavedMessage() {
        Toast.makeText(getActivity(),
                "Empresa guardada correctamente", Toast.LENGTH_SHORT).show();
    }
}
