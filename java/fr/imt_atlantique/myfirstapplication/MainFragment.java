package fr.imt_atlantique.myfirstapplication;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MainFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MainFragment extends Fragment {
    private EditText ETT, ETT2, ETT4;
    private Spinner departSpinner;
    List<EditText> phoneEditTextIds = new ArrayList<>();
    public TextView TVDate;
    private String nom;
    private String prenom;
    private String ville;
    private String tel_2;
    private String tel_3;
    private final String[] phoneNumbers = new String[3];
    private int depart, birthYear, birthMonth, birthDay;
    private OnVal listener;
    private OnExit listenerDisplay;
    private OnDate listenerDate;
    private static final String NOM_KEY = "Nom", BIRTH_YEAR = "year", BIRTH_MONTH = "month",
            BIRTH_DAY = "day", PRENOM_KEY = "Prenom", DEPART_KEY = "depart",
            VILLE_KEY = "Ville", TEL_1 = "tel_1", TEL_2 = "tel_2",
            TEL_3 = "tel_3";

    public MainFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static MainFragment newInstance(String prenom, String nom, int departement_naissance,
                                           String ville_naissance, int birthDay_2, int birthMonth_2,
                                           int birthYear_2, String tel_1, String tel_2,
                                           String tel_3) {
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();
        args.putString(PRENOM_KEY, prenom);
        args.putString(NOM_KEY, nom);
        args.putInt(DEPART_KEY, departement_naissance);
        args.putString(VILLE_KEY, ville_naissance);
        args.putInt(BIRTH_DAY, birthDay_2);
        args.putInt(BIRTH_MONTH, birthMonth_2);
        args.putInt(BIRTH_YEAR, birthYear_2);
        args.putString(TEL_1, tel_1);
        args.putString(TEL_2, tel_2);
        args.putString(TEL_3, tel_3);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context){
        super.onAttach(context);
        getActivity();
        if (context instanceof OnVal) {
            listener = (OnVal) context;
        }
        if (context instanceof OnDate) {
            listenerDate = (OnDate) context;
        }
        if (context instanceof OnExit) {
            listenerDisplay = (OnExit) context;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            prenom = getArguments().getString(PRENOM_KEY, "");
            nom = getArguments().getString(NOM_KEY, "");
            depart = getArguments().getInt(DEPART_KEY, 0);
            ville = getArguments().getString(VILLE_KEY, "");
            birthDay = getArguments().getInt(BIRTH_DAY, 1);
            birthMonth = getArguments().getInt(BIRTH_MONTH, 0);
            birthYear = getArguments().getInt(BIRTH_YEAR, 1900);
            phoneNumbers[0] = getArguments().getString(TEL_1, "");
            phoneNumbers[1] = getArguments().getString(TEL_2, "");
            phoneNumbers[2] = getArguments().getString(TEL_3, "");
            tel_2 = getArguments().getString(TEL_2, "");
            tel_3 = getArguments().getString(TEL_3, "");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        Log.i("Lifecycle", "onCreate method");
        super.onViewCreated(view, savedInstanceState);
        ETT = view.findViewById(R.id.ETT);
        ETT2 = view.findViewById(R.id.ETT2);
        ETT4 = view.findViewById(R.id.ETT4);
        departSpinner = view.findViewById(R.id.departement);
        TVDate = view.findViewById(R.id.TVDateDisplay);

        ETT.setText(nom);
        ETT2.setText(prenom);
        Log.i("ville Fragment", ville);
        ETT4.setText(ville);
        departSpinner.setSelection(depart);
        String date = birthYear
                + "-"
                + birthMonth + 1
                + "-"
                + birthDay;
        TVDate.setText(date);

        Button butTel = view.findViewById(R.id.button4);
        Button butVal = view.findViewById(R.id.button3);
        Button butExit = view.findViewById(R.id.BDisplay);
        Button butDate = view.findViewById(R.id.button);

        butTel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayout parentLayout = view.findViewById(R.id.linearLayoutTel);
                int childCount = parentLayout.getChildCount();

                if (childCount < 3) {
                    LinearLayout newPhoneLayout = new LinearLayout(requireContext());
                    newPhoneLayout.setLayoutParams(new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT
                    ));
                    newPhoneLayout.setOrientation(LinearLayout.HORIZONTAL);

                    EditText phoneEditText = new EditText(requireContext());
                    LinearLayout.LayoutParams editTextParams = new LinearLayout.LayoutParams(
                            0,
                            LinearLayout.LayoutParams.WRAP_CONTENT,
                            4
                    );

                    phoneEditText.setLayoutParams(editTextParams);
                    phoneEditText.setHint(getString(R.string.preview_phone));
                    phoneEditText.setInputType(InputType.TYPE_CLASS_TEXT);
                    phoneEditText.setInputType(InputType.TYPE_CLASS_PHONE);

                    Log.i("tel", phoneNumbers[childCount]);
                    phoneEditText.setText(phoneNumbers[childCount]);

                    phoneEditTextIds.add(phoneEditText);

                    Button callButton = new Button(requireContext());
                    LinearLayout.LayoutParams buttonCallParams = new LinearLayout.LayoutParams(
                            0,
                            LinearLayout.LayoutParams.WRAP_CONTENT,
                            1
                    );
                    callButton.setLayoutParams(buttonCallParams);
                    callButton.setText(getString(R.string.call_button));
                    callButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View arg0) {
                            String phone_no = "tel:" + phoneEditText.getText().
                                    toString().replaceAll("-", "");
                            Intent dialIntent = new Intent(Intent.ACTION_DIAL);
                            dialIntent.setData(Uri.parse(phone_no));
                            requireActivity().startActivity(dialIntent);
                        }
                    });

                    Button deleteButton = new Button(requireContext());
                    LinearLayout.LayoutParams buttonParams = new LinearLayout.LayoutParams(
                            0,
                            LinearLayout.LayoutParams.WRAP_CONTENT,
                            1
                    );
                    deleteButton.setLayoutParams(buttonParams);
                    deleteButton.setText(getString(R.string.delete_phone));
                    deleteButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            View parent = (View) v.getParent();
                            if (parent instanceof LinearLayout) {
                                LinearLayout parentLayout = (LinearLayout) requireView()
                                        .findViewById(R.id.linearLayoutTel);
                                Log.i("verif", String.valueOf(parentLayout == null));
                                assert parentLayout != null;
                                parentLayout.removeView(parent);
                            }
                        }

                    });

                    newPhoneLayout.addView(phoneEditText);
                    newPhoneLayout.addView(callButton);
                    newPhoneLayout.addView(deleteButton);

                    parentLayout.addView(newPhoneLayout);
                } else {
                    Snackbar.make(v, "Vous ne pouvez pas ajouter plus de 3 numéros de téléphone", Snackbar.LENGTH_SHORT).show();
                }
            }
        });

        butVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nom = ETT.getText().toString();
                String prenom = ETT2.getText().toString();
                String ville = ETT4.getText().toString();
                int depart = departSpinner.getSelectedItemPosition();
                String date = TVDate.getText().toString();
                int year = Integer.parseInt(date.substring(0, 4));
                int month = Integer.parseInt(date.substring(5, 6)) - 1;
                int day = Integer.parseInt(date.substring(7, 9));
                for (int i = 0; i < phoneEditTextIds.size(); i++) {
                    EditText editText = phoneEditTextIds.get(i);
                    if (editText != null) {
                        String str = editText.getText().toString();
                        if (!str.isEmpty()) {
                            phoneNumbers[i] = str;
                        }
                    }
                }

                User user = new User(prenom, nom, depart, ville, year, month, day, phoneNumbers[0],
                        phoneNumbers[1], phoneNumbers[2]);

                listener.onVal(user);
            }
        });

        butExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                String nom = ETT.getText().toString();
                String prenom = ETT2.getText().toString();
                String ville = ETT4.getText().toString();
                Log.i("ville Listener", ville);
                int depart = departSpinner.getSelectedItemPosition();
                String date = TVDate.getText().toString();
                int year = Integer.parseInt(date.substring(0, 4));
                int month = Integer.parseInt(date.substring(5, 6)) - 1;
                int day = Integer.parseInt(date.substring(7, 9));
                for (int i = 0; i < phoneEditTextIds.size(); i++) {
                    EditText editText = phoneEditTextIds.get(i);
                    if (editText != null) {
                        String str = editText.getText().toString();
                        if (!str.isEmpty()) {
                            phoneNumbers[i] = str;
                        }
                    }
                }
                phoneEditTextIds = new ArrayList<>();

                User user = new User(prenom, nom, depart, ville, year, month, day, phoneNumbers[0],
                        phoneNumbers[1], phoneNumbers[2]);

                listenerDisplay.onExit(user);
            }
        });

        butDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                if (listenerDate != null) {
                    listenerDate.onDate(birthYear, birthMonth, birthDay);
                }
            }
        });

    }
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState){
        super.onSaveInstanceState(outState);
    }

    public void setDate(int birthYear_2, int birthMonth_2, int birthDay_2) {
        birthYear = birthYear_2;
        birthMonth = birthMonth_2;
        birthDay = birthDay_2;
        String str = birthYear_2
                + "-"
                + String.valueOf((birthMonth_2 + 1))
                + "-"
                + birthDay_2;

        TVDate.setText(str);
    }

    public Bundle getInfo() {
        Bundle bundle = new Bundle();
        bundle.putString(NOM_KEY, ETT.getText().toString());
        bundle.putString(PRENOM_KEY, ETT2.getText().toString());
        bundle.putString(VILLE_KEY, ETT4.getText().toString());
        bundle.putInt(DEPART_KEY, departSpinner.getSelectedItemPosition());
        for (var i = 0; i < phoneEditTextIds.size(); i++) {
            String tel = "tel_" + String.valueOf(i);
            bundle.putString(tel, phoneEditTextIds.get(i).getText().toString());
        }
        bundle.putInt(BIRTH_DAY, birthDay);
        bundle.putInt(BIRTH_MONTH, birthMonth);
        bundle.putInt(BIRTH_YEAR, birthYear);
        return bundle;
    }

    public void resetAction(MenuItem menu) {
        Log.i("reset", "reset Fragment");
        ETT2.setText("");
        ETT.setText("");
        ETT4.setText("");
        TVDate.setText("1900-1-1");
        birthYear = 1900;
        birthMonth = 0;
        birthDay = 1;
        for (var i = 0; i < phoneEditTextIds.size(); i++) {
            phoneEditTextIds.get(i).setText("");
        }
        departSpinner.setSelection(0);
    }
    public String getWiki(MenuItem item) {
        return ETT4.getText().toString();
    }
    public String share(MenuItem menu) {
        return ETT4.getText().toString();
    }

    public interface OnDate {
        void onDate(int birthYear, int birthMonth, int birthDay);
    }

    public interface OnVal {
        void onVal(User user);
    }

    public interface OnExit {
        void onExit(User user);
    }
}
