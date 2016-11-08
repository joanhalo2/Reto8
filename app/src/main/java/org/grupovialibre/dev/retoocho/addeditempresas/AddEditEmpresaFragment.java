package org.grupovialibre.dev.retoocho.addeditempresas;


import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.grupovialibre.dev.retoocho.R;
import org.grupovialibre.dev.retoocho.entidades.ClientesDB;
import org.grupovialibre.dev.retoocho.entidades.Empresa;
import org.grupovialibre.dev.retoocho.entidades.TipoEmpresa;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddEditEmpresaFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddEditEmpresaFragment extends Fragment {
    private static final String ARG_EMPRESA_ID = "arg_empresa_id";


    private String mEmpresaId;

    private ClientesDB mEmpresasDbHelper;

    private FloatingActionButton mSaveButton;

    private TextInputEditText mNameField;
    private TextInputEditText mURLField;
    private TextInputEditText mPhoneNumberField;
    private TextInputEditText mEmailField;
    private TextInputEditText mProductsField;
    private TextInputEditText mTipoEmpresaField;

    private TextInputLayout mNameLabel;
    private TextInputLayout mURLLabel;
    private TextInputLayout mPhoneNumberLabel;
    private TextInputLayout mEmailLabel;
    private TextInputLayout mProductsLabel;
    private TextInputLayout mTipoEmpresaLabel;


    public AddEditEmpresaFragment() {
        // Required empty public constructor
    }

    public static AddEditEmpresaFragment newInstance(String empresaId) {
        AddEditEmpresaFragment fragment = new AddEditEmpresaFragment();
        Bundle args = new Bundle();
        args.putString(ARG_EMPRESA_ID, empresaId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mEmpresaId = getArguments().getString(ARG_EMPRESA_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_add_edit_empresa, container, false);

        // Referencias UI
        mSaveButton = (FloatingActionButton) getActivity().findViewById(R.id.fab);

        mNameField = (TextInputEditText) root.findViewById(R.id.et_name);
        mNameLabel = (TextInputLayout) root.findViewById(R.id.til_name);

        mURLField = (TextInputEditText) root.findViewById(R.id.et_url);
        mURLLabel = (TextInputLayout) root.findViewById(R.id.til_url);

        mPhoneNumberField = (TextInputEditText) root.findViewById(R.id.et_phone_number);
        mPhoneNumberLabel = (TextInputLayout) root.findViewById(R.id.til_phone_number);

        mEmailField = (TextInputEditText) root.findViewById(R.id.et_email);
        mEmailLabel = (TextInputLayout) root.findViewById(R.id.til_email);

        mProductsField = (TextInputEditText) root.findViewById(R.id.et_products);
        mProductsLabel = (TextInputLayout) root.findViewById(R.id.til_products);

        mTipoEmpresaField = (TextInputEditText) root.findViewById(R.id.et_tipo);
        mTipoEmpresaLabel = (TextInputLayout) root.findViewById(R.id.til_tipo);

        // Eventos
        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addEditEmpresa();
            }
        });

        mEmpresasDbHelper = new ClientesDB(getActivity());

        // Carga de datos
        if (mEmpresaId != null) {
            loadEmpresa();
        }

        return root;
    }

    private class AddEditEmpresaTask extends AsyncTask<Empresa, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Empresa... empresas) {
            if (mEmpresaId != null) {
                //return mEmpresasDbHelper.updateEmpresa(empresas[0], mEmpresaId); > 0;
                return mEmpresasDbHelper.updateEmpresa(empresas[0]) > 0;

            } else {
                return mEmpresasDbHelper.insertEmpresa(empresas[0]) > 0;
            }

        }

        @Override
        protected void onPostExecute(Boolean result) {
            showLawyersScreen(result);
        }

        private void showLawyersScreen(Boolean requery) {
            if (!requery) {
                showAddEditError();
                getActivity().setResult(Activity.RESULT_CANCELED);
            } else {
                getActivity().setResult(Activity.RESULT_OK);
            }

            getActivity().finish();
        }

        private void showAddEditError() {
            Toast.makeText(getActivity(),
                    "Error al agregar nueva información", Toast.LENGTH_SHORT).show();
        }
    }

    private void loadEmpresa() {
        // AsyncTask
    }

    private void addEditEmpresa() {
        boolean error = false;

        String name = mNameField.getText().toString();
        String url = mURLField.getText().toString();
        String phoneNumber = mPhoneNumberField.getText().toString();
        String eMail = mEmailField.getText().toString();
        String products = mProductsField.getText().toString();
        String tipoEmpresa = mTipoEmpresaField.getText().toString();

        if (TextUtils.isEmpty(name)) {
            mNameLabel.setError(getString(R.string.field_error));
            error = true;
        }

        if (TextUtils.isEmpty(url)) {
            mURLLabel.setError(getString(R.string.field_error));
            error = true;
        }

        if (TextUtils.isEmpty(phoneNumber)) {
            mPhoneNumberLabel.setError(getString(R.string.field_error));
            error = true;
        }

        if (TextUtils.isEmpty(eMail)) {
            mEmailLabel.setError(getString(R.string.field_error));
            error = true;
        }

        if (TextUtils.isEmpty(products)) {
            mProductsLabel.setError(getString(R.string.field_error));
            error = true;
        }


        if (TextUtils.isEmpty(tipoEmpresa)) {
            mTipoEmpresaLabel.setError(getString(R.string.field_error));
            error = true;
        }

        if (error) {
            return;
        }

        TipoEmpresa tipoEmpresa1 = new TipoEmpresa(new Integer(tipoEmpresa));
        Empresa nuevaEmpresa = new Empresa(name,url,phoneNumber,eMail,products,tipoEmpresa1);

        new AddEditEmpresaTask().execute(nuevaEmpresa);

    }

}
