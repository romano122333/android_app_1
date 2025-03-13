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
import android.widget.DatePicker;

/**
 * A simple {@link Fragment} subclass.
 */
public class DateFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    DatePicker datePicker;
    Button butVal, butExit;
    private OnExit listenerExit;
    private OnSendDate listenerDate;
    public int year, month, day;
    private static final String BIRTH_YEAR = "year", BIRTH_MONTH = "month", BIRTH_DAY = "day";

    public DateFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_date, container, false);
    }

    public void onAttach(@NonNull Context context){
        super.onAttach(context);
        getActivity();
        if (context instanceof OnSendDate) {
            listenerDate = (OnSendDate) context;
        }
        if (context instanceof OnExit) {
            listenerExit = (OnExit) context;
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        datePicker = view.findViewById(R.id.dateP);
        butVal = view.findViewById(R.id.butVal);
        butExit = view.findViewById(R.id.butExit);

        butVal.setOnClickListener(arg0 -> {
            int year = datePicker.getYear();
            int month = datePicker.getMonth();
            int day = datePicker.getDayOfMonth();
            listenerDate.onSendDate(year, month, day);
        });

        butExit.setOnClickListener(arg0 -> {
            int year = datePicker.getYear();
            int month = datePicker.getMonth();
            int day = datePicker.getDayOfMonth();
            listenerExit.onExit(year, month, day);
        });

    }

    public void dateSet(int birthYear, int birthMonth, int birthDay) {
        Log.i("dates", String.valueOf(birthDay));
        datePicker.updateDate(birthYear, birthMonth, birthDay);
        year = birthYear;
        month = birthMonth;
        day = birthDay;
    }

    public Bundle getInfo() {
        Bundle bundle = new Bundle();
        bundle.putInt(BIRTH_YEAR, year);
        bundle.putInt(BIRTH_MONTH, month);
        bundle.putInt(BIRTH_DAY, day);
        return bundle;
    }

    interface OnSendDate{
        void onSendDate(int birthYear, int birthMonth, int birthDay);
    }
    interface OnExit{
        void onExit(int birthYear, int birthMonth, int birthDay);
    }
}