package com.example.afinal.ui.notifications;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.afinal.DBHandler;
import com.example.afinal.MainActivity;
import com.example.afinal.R;
import com.example.afinal.databinding.FragmentNotificationsBinding;

import java.util.ArrayList;

public class NotificationsFragment extends Fragment {

    private FragmentNotificationsBinding binding;
    private EditText ContactNumEdt, ContactMsgEdt;
    private DBHandler dbHandler;

    public View onCreateView(@NonNull LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentNotificationsBinding.inflate(inflater, container, false);
        ContactNumEdt = requireView().findViewById(R.id.Contact);
        ContactMsgEdt = requireView().findViewById(R.id.Message);
        dbHandler = new DBHandler(NotificationsFragment.this);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Spinner spinner = requireView().findViewById(R.id.spinner);
        ArrayList<String> arrayList1 = new ArrayList<>();
        arrayList1.add("Default");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(getActivity(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, arrayList1);  // layout for spinner itself
        dataAdapter.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item); // layout for spinner dropdown items
        spinner.setAdapter(dataAdapter);
        Button buttonSend = requireView().findViewById(R.id.button_first4);
        buttonSend.setOnClickListener(v -> ((MainActivity) requireActivity()).askPermissionAndSendSMS());
        Button buttonSave = requireView().findViewById(R.id.button_first2);
        buttonSave.setOnClickListener(v -> ((MainActivity) requireActivity()).SaveSpinner());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}