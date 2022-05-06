package com.example.afinal.ui.notifications;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.afinal.DBHandler;
import com.example.afinal.MainActivity;
import com.example.afinal.R;
import java.util.ArrayList;
import java.util.List;

public class NotificationsFragment extends Fragment {

    private View binding;
    private EditText ContactNumEdt, ContactMsgEdt;
    private Button Save,Edit,Test;
    private Spinner spinner;
    private DBHandler DB;

    public View onCreateView(@NonNull LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {
        binding = inflater.inflate(R.layout.fragment_notifications, container, false);
        ContactNumEdt = binding.findViewById(R.id.Contact);
        ContactMsgEdt = binding.findViewById(R.id.Message);
        Save = binding.findViewById(R.id.button_first2);
        Edit = binding.findViewById(R.id.button_first3);
        Test = binding.findViewById(R.id.button_first4);
        spinner=binding.findViewById(R.id.spinner);
        DB = new DBHandler(requireActivity().getApplicationContext());
        return binding;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        loadSpinnerData();
        ArrayList<String> arrayList1 = new ArrayList<>();
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(getActivity(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, arrayList1);  // layout for spinner itself
        spinner.setAdapter(dataAdapter);
        Test.setOnClickListener(v -> ((MainActivity) requireActivity()).DemandePermissionSMS());
        loadSpinnerData();
        Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                String PhoneNum = ContactNumEdt.getText().toString();
                String PhoneMsg = ContactMsgEdt.getText().toString();
                if (PhoneMsg.trim().length() > 0 && PhoneNum.trim().length() > 0 ) {
                    if (!DB.checkdupli(PhoneNum)) {
                        DB.insert(PhoneNum, PhoneMsg);
                        ContactNumEdt.setText("");
                        ContactMsgEdt.setText("");
                        InputMethodManager imm = (InputMethodManager) requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(ContactNumEdt.getWindowToken(), 0);
                        imm.hideSoftInputFromWindow(ContactMsgEdt.getWindowToken(), 0);
                        loadSpinnerData();
                    } else {
                        Toast.makeText(requireActivity().getApplicationContext(), "Numero deja entrée",
                                Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(requireActivity().getApplicationContext(), "Entrer une valeur dans les 2 champs",
                        Toast.LENGTH_SHORT).show();
                }
            }
        });
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView < ? > parent,View view,int position, long id){
                String label = parent.getItemAtPosition(position).toString();
                if (!label.equals("Rajouter des valeurs")) {
                    String MSG = DB.get(label, "message", "number", "labels");
                    String NUM = DB.get(MSG, "number", "message", "labels"); // Aime pas espace dans message des fois non jsp
                    ContactNumEdt.setText(NUM);
                    ContactMsgEdt.setText(MSG);
                    //Edit.setVisibility(View.INVISIBLE);
                } else {
                    ContactNumEdt.setText("");
                    ContactMsgEdt.setText("");
                }
            }

            public void onNothingSelected (AdapterView < ? > arg0){
                // TODO Auto-generated method stub

            }
        });
    }


    private void loadSpinnerData() {
        List<String> Numero= new ArrayList<String>();
        Numero.add("Rajouter des valeurs");
        Numero.addAll(DB.getNumLabels());
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String> (getContext(), android.R.layout.simple_spinner_item, Numero);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);
        spinner.setSelection( dataAdapter.getPosition("Rajouter des valeurs"));

    }

    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}