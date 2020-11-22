package com.rastrakidyana.ugd10_f_9618;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.rastrakidyana.ugd10_f_9618.Database.DatabaseClient;
import com.rastrakidyana.ugd10_f_9618.Model.User;

public class CreateFragment extends Fragment {

    TextInputEditText tNama, tNPM, tFoto, tIPK;
    String selectedFakultas, selectedProdi;
    AutoCompleteTextView jFakultas, jProdi;
    Button addBtn, cancelBtn;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create, container, false);
        tNPM = view.findViewById(R.id.input_npm);
        tNama = view.findViewById(R.id.input_nama);
        tIPK = view.findViewById(R.id.input_ipk);
        jFakultas = view.findViewById(R.id.input_fakultas);
        jProdi = view.findViewById(R.id.input_prodi);
        tFoto = view.findViewById(R.id.input_url);
        addBtn = view.findViewById(R.id.btn_add);
        cancelBtn = view.findViewById(R.id.btn_cancel);

        final String[] FakultasArray = getResources().getStringArray(R.array.fakultas);
        final String[] ProdiArray = getResources().getStringArray(R.array.prodi);

        selectedFakultas = FakultasArray[0];
        selectedProdi = ProdiArray[0];

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.fakultas, android.R.layout.simple_spinner_item);
        jFakultas.setAdapter(adapter);
        jFakultas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                selectedFakultas = jFakultas.getEditableText().toString();
            }
        });

        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(getContext(),
                R.array.prodi, android.R.layout.simple_spinner_item);
        jProdi.setAdapter(adapter2);
        jProdi.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                selectedProdi = jProdi.getEditableText().toString();
            }
        });



        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validate()){
                    addUser();
                }else{
                    Toast.makeText(getActivity().getApplicationContext(), "Invalid", Toast.LENGTH_SHORT).show();
                }
            }
        });

        cancelBtn.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.hide(CreateFragment.this).commit();
            }
        });

    }

    private void addUser(){
        final int npm = Integer.parseInt(tNPM.getText().toString());
        final String nama = tNama.getText().toString();
        final String fakultas = jFakultas.getText().toString();
        final String prodi = jProdi.getText().toString();
        final String url = tFoto.getText().toString();
        final Double ipk = Double.parseDouble(tIPK.getText().toString());

        class AddUser extends AsyncTask<Void, Void, Void> {

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                Toast.makeText(getActivity().getApplicationContext(), "User saved", Toast.LENGTH_SHORT).show();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.hide(CreateFragment.this).commit();
            }

            @Override
            protected Void doInBackground(Void... voids) {
                User user = new User();
                user.setNpmU(npm);
                user.setNamaU(nama);
                user.setFakultasU(fakultas);
                user.setJurusanU(prodi);
                user.setIpkU(ipk);
                user.setFotoU(url);

                DatabaseClient.getInstance(getActivity().getApplicationContext()).getDatabase()
                        .userDAO()
                        .insert(user);
                return null;
            }
        }

        AddUser add = new AddUser();
        add.execute();
    }

    private boolean validate() {
        if (!tNama.getText().toString().equals("")  && !tNPM.getText().toString().equals("")  && !tIPK.getText().toString().equals("")
               &&  !tFoto.getText().toString().equals("")  && !jFakultas.getText().toString().equals("")  && !jProdi.getText().toString().equals("")) {
            return true;
        } else {
            return false;
        }
    }


}