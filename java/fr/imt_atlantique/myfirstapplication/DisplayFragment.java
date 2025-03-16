package fr.imt_atlantique.myfirstapplication;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 */
public class DisplayFragment extends Fragment {
    EditText editTextNom, editTextPrenom, editTextVille, editTextTel1, editTextTel2, editTextTel3;
    Spinner spinnerDepart;
    Button butValidate;
    TextView textViewDateNaiss;
    private User usr;
    private static final String NOM_KEY = "Nom", BIRTH_YEAR = "year",
            BIRTH_MONTH = "month", BIRTH_DAY = "day", PRENOM_KEY = "Prenom", VILLE_KEY = "Ville",
            DEPART_KEY = "depart", TEL_1 = "tel_1",
            TEL_2 = "tel_2", TEL_3 = "tel_3";
    private OnEdit listener;
    public DisplayFragment() {
        // Required empty public constructor
    }
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     */
    // TODO: Rename and change types and number of parameters
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_display, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);

        editTextNom = view.findViewById(R.id.editTextNom);
        editTextPrenom = view.findViewById(R.id.editTextLastName);
        editTextVille = view.findViewById(R.id.editTextVille);
        editTextTel1 = view.findViewById(R.id.editTextTel1);
        editTextTel2 = view.findViewById(R.id.editTextTel2);
        editTextTel3 = view.findViewById(R.id.editTextTel3);
        spinnerDepart = view.findViewById(R.id.spinnerVille);
        textViewDateNaiss = view.findViewById(R.id.textDateNaissance);

        butValidate = view.findViewById(R.id.butValidate);
        butValidate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                listener.onEdit();
            }
        });

    }
    public void onAttach(@NonNull Context context){
        super.onAttach(context);
        getActivity();
        if (context instanceof OnEdit) {
            listener = (OnEdit) context;
        }
    }
    public void setDisplay(User user) {
        usr = user;
        editTextNom.setText(user.getNom());
        editTextPrenom.setText(user.getPrenom());
        editTextVille.setText(user.getVille_naissance());
        spinnerDepart.setSelection(user.getDepartementNaissance());
        editTextTel1.setText(user.getTel_1());
        editTextTel2.setText(user.getTel_2());
        editTextTel3.setText(user.getTel_3());
        String date = user.getBirthYear()
                + "-"
                + String.valueOf(user.getBirthMonth() + 1)
                + "-"
                + user.getBirthDay();
        textViewDateNaiss.setText(date);
    }
    public Bundle getInfo() {
        Bundle bundle = new Bundle();
        bundle.putString(PRENOM_KEY, usr.getPrenom());
        bundle.putString(NOM_KEY, usr.getNom());
        bundle.putInt(DEPART_KEY, usr.getDepartementNaissance());
        bundle.putString(VILLE_KEY, usr.getVille_naissance());
        bundle.putInt(BIRTH_YEAR, usr.getBirthYear());
        bundle.putInt(BIRTH_MONTH, usr.getBirthMonth());
        bundle.putInt(BIRTH_DAY, usr.getBirthMonth());
        bundle.putString(TEL_1, usr.getTel_1());
        bundle.putString(TEL_2, usr.getTel_2());
        bundle.putString(TEL_3, usr.getTel_3());
        return bundle;
    }
    interface OnEdit{
        void onEdit();
    }
}