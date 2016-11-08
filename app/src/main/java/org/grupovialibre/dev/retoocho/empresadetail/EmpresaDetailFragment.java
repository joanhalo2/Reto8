package org.grupovialibre.dev.retoocho.empresadetail;


import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.grupovialibre.dev.retoocho.EmpresasActivity;
import org.grupovialibre.dev.retoocho.EmpresasFragment;
import org.grupovialibre.dev.retoocho.R;
import org.grupovialibre.dev.retoocho.addeditempresas.AddEditEmpresaActivity;
import org.grupovialibre.dev.retoocho.entidades.ClientesDB;
import org.grupovialibre.dev.retoocho.entidades.Empresa;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EmpresaDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EmpresaDetailFragment extends Fragment {
    private static final String ARG_EMPRESA_ID = "arg_empresa_id";
    private String mEmpresaId;

    private CollapsingToolbarLayout mCollapsingView;
    private TextView mURL;
    private TextView mPhoneNumber;
    private TextView mEmail;
    private TextView mProducts;
    private TextView mTipoEmpresa;

    private ClientesDB mEmpresasDbHelper;

    public EmpresaDetailFragment() {
        // Required empty public constructor
    }


    public static EmpresaDetailFragment newInstance(String empresaId) {
        EmpresaDetailFragment fragment = new EmpresaDetailFragment();
        Bundle args = new Bundle();
        args.putString(ARG_EMPRESA_ID, empresaId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        if (getArguments() != null) {
            mEmpresaId = getArguments().getString(ARG_EMPRESA_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_empresa_detail, container, false);
        mCollapsingView = (CollapsingToolbarLayout) getActivity().findViewById(R.id.toolbar_layout);
        //mAvatar = (ImageView) getActivity().findViewById(R.id.iv_avatar);
        mURL = (TextView) root.findViewById(R.id.tv_url);
        mPhoneNumber = (TextView) root.findViewById(R.id.tv_phone_number);
        mEmail = (TextView) root.findViewById(R.id.tv_email);
        mProducts = (TextView) root.findViewById(R.id.tv_products);
        mTipoEmpresa = (TextView) root.findViewById(R.id.tv_tipoEmpresa);

        mEmpresasDbHelper = new ClientesDB(getActivity());

        loadEmpresa();

        return root;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Acciones
    }

    private void loadEmpresa() {
        new GetEmpresaByIdTask().execute();
    }

    private void showEmpresa(Empresa empresa) {
        mCollapsingView.setTitle(empresa.getNombre());
        mURL.setText(empresa.getUrl());
        mPhoneNumber.setText(empresa.getTelefono());
        mEmail.setText(empresa.getEmail());
        mProducts.setText(empresa.getProductos_servicios());
        mTipoEmpresa.setText(empresa.getTipoEmpresa().getNombre());
    }

    private void showLoadError() {
        Toast.makeText(getActivity(),
                "Error al cargar información", Toast.LENGTH_SHORT).show();
    }

    private class GetEmpresaByIdTask extends AsyncTask<Void, Void, Cursor> {

        @Override
        protected Cursor doInBackground(Void... voids) {
            return mEmpresasDbHelper.getEmpresasById(mEmpresaId);
        }

        @Override
        protected void onPostExecute(Cursor cursor) {
            if (cursor != null && cursor.moveToLast()) {
                showEmpresa(new Empresa(cursor));
            } else {
                showLoadError();
            }
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_edit:
                showEditScreen();
                break;
            case R.id.action_delete:
                new DeleteEmpresaTask().execute();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private class DeleteEmpresaTask extends AsyncTask<Void, Void, Integer> {

        @Override
        protected Integer doInBackground(Void... voids) {
            return mEmpresasDbHelper.deleteEmpresa(mEmpresaId);
        }

        @Override
        protected void onPostExecute(Integer integer) {
            showEmpresasScreen(integer > 0);
        }

    }

    private void showEmpresasScreen(boolean requery) {
        if (!requery) {
            showDeleteError();
        }
        getActivity().setResult(requery ? Activity.RESULT_OK : Activity.RESULT_CANCELED);
        getActivity().finish();
    }

    private void showDeleteError() {
        Toast.makeText(getActivity(),
                "Error al eliminar empresa", Toast.LENGTH_SHORT).show();
    }

    private void showEditScreen() {
        Intent intent = new Intent(getActivity(), AddEditEmpresaActivity.class);
        intent.putExtra(EmpresasActivity.EXTRA_EMPRESA_ID, mEmpresaId);
        startActivityForResult(intent, EmpresasFragment.REQUEST_UPDATE_DELETE_LAWYER);
    }


}
