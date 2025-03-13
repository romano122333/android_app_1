package fr.imt_atlantique.myfirstapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity implements MainFragment.OnVal,
        MainFragment.OnExit,
        MainFragment.OnDate,
        DisplayFragment.OnEdit,
        DateFragment.OnSendDate,
        DateFragment.OnExit {
    private DateFragment dateFragment;
    private DisplayFragment displayFragment;
    private MainFragment mainFragment;
    private static final String PREFS_NAME = "MESDONNES", NOM_KEY = "Nom", BIRTH_YEAR = "year",
            BIRTH_MONTH = "month", BIRTH_DAY = "day", PRENOM_KEY = "Prenom", VILLE_KEY = "Ville",
            DEPART_KEY = "depart", TEL_1 = "tel_1",
            TEL_2 = "tel_2", TEL_3 = "tel_3";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.main_activity);

        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);

        Bundle bundle = new Bundle();
        bundle.putString(PRENOM_KEY, prefs.getString(PRENOM_KEY, ""));
        bundle.putString(NOM_KEY, prefs.getString(NOM_KEY, ""));
        bundle.putInt(DEPART_KEY, prefs.getInt(DEPART_KEY, 0));
        bundle.putString(VILLE_KEY, prefs.getString(VILLE_KEY, ""));
        bundle.putInt(BIRTH_YEAR, prefs.getInt(BIRTH_YEAR, 1900));
        bundle.putInt(BIRTH_MONTH, prefs.getInt(BIRTH_MONTH, 0));
        bundle.putInt(BIRTH_DAY, prefs.getInt(BIRTH_DAY, 1));
        bundle.putString(TEL_1, prefs.getString(TEL_1, ""));
        bundle.putString(TEL_2, prefs.getString(TEL_2, ""));
        bundle.putString(TEL_3, prefs.getString(TEL_3, ""));

        if (savedInstanceState == null) {

            mainFragment = new MainFragment();
            mainFragment.setArguments(bundle);

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.LLmain, mainFragment, "mainFragment")
                    .commit();

            displayFragment = new DisplayFragment();
            dateFragment = new DateFragment();
        } else {
            mainFragment = (MainFragment) getSupportFragmentManager().findFragmentByTag("mainFragment");
            displayFragment = (DisplayFragment) getSupportFragmentManager().findFragmentByTag("displayFragment");
            dateFragment = (DateFragment) getSupportFragmentManager().findFragmentByTag("dateFragment");
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        if (displayFragment != null && displayFragment.isAdded()) {
            outState.putString("frag", "displayFragment");
            Bundle bundle = displayFragment.getInfo();
            outState.putString(PRENOM_KEY, bundle.getString(PRENOM_KEY, ""));
            outState.putString(NOM_KEY, bundle.getString(NOM_KEY, ""));
            outState.putInt(DEPART_KEY, bundle.getInt(DEPART_KEY, 0));
            outState.putString(VILLE_KEY, bundle.getString(VILLE_KEY, ""));
            outState.putInt(BIRTH_YEAR, bundle.getInt(BIRTH_YEAR, 1900));
            outState.putInt(BIRTH_MONTH, bundle.getInt(BIRTH_MONTH, 0));
            outState.putInt(BIRTH_DAY, bundle.getInt(BIRTH_DAY, 1));
            outState.putString(TEL_1, bundle.getString(TEL_1, ""));
            outState.putString(TEL_2, bundle.getString(TEL_2, ""));
            outState.putString(TEL_3, bundle.getString(TEL_3, ""));

        } else if (mainFragment != null && mainFragment.isAdded()) {
            outState.putString("frag", "mainFragment");
            Bundle bundle = mainFragment.getInfo();
            outState.putString(PRENOM_KEY, bundle.getString(PRENOM_KEY, ""));
            outState.putString(NOM_KEY, bundle.getString(NOM_KEY, ""));
            outState.putInt(DEPART_KEY, bundle.getInt(DEPART_KEY, 0));
            outState.putString(VILLE_KEY, bundle.getString(VILLE_KEY, ""));
            outState.putInt(BIRTH_YEAR, bundle.getInt(BIRTH_YEAR, 1900));
            outState.putInt(BIRTH_MONTH, bundle.getInt(BIRTH_MONTH, 0));
            outState.putInt(BIRTH_DAY, bundle.getInt(BIRTH_DAY, 1));
            outState.putString(TEL_1, bundle.getString(TEL_1, ""));
            outState.putString(TEL_2, bundle.getString(TEL_2, ""));
            outState.putString(TEL_3, bundle.getString(TEL_3, ""));

        } else if (dateFragment != null && dateFragment.isAdded()) {
            outState.putString("frag", "dateFragment");
            Bundle bundle = dateFragment.getInfo();
            outState.putInt(BIRTH_DAY, bundle.getInt(BIRTH_DAY, 1));
            outState.putInt(BIRTH_MONTH, bundle.getInt(BIRTH_MONTH, 0));
            outState.putInt(BIRTH_YEAR, bundle.getInt(BIRTH_YEAR, 1900));

        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    public void resetAction(MenuItem menu){
        mainFragment.resetAction(menu);
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        editor.putString(NOM_KEY, "");
        editor.putString(PRENOM_KEY, "");
        editor.putString(VILLE_KEY, "");
        editor.putInt(DEPART_KEY, 0);
        editor.putInt(BIRTH_YEAR, 1900);
        editor.putInt(BIRTH_MONTH, 0);
        editor.putInt(BIRTH_DAY, 1);

        editor.apply();
    }
    public void share(MenuItem menu) {
        String ville = mainFragment.share(menu);
        if (!ville.isEmpty()) {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_TEXT, ville);
            startActivity(intent);
        }
    }
    public void wiki(MenuItem menu){
        String ville = mainFragment.getWiki(menu);
        if (!ville.isEmpty()) {
            String url = "http://fr.wikipedia.org/?search=" + ville;
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(intent);
        }
    }

    public void onStart() {
        super.onStart();
    }

    public void onResume() {
        super.onResume();
    }

    protected void onPause() {
        super.onPause();
    }

    public void onDestroy() {
        super.onDestroy();
    }

    protected void onStop() {
        super.onStop();
    }

    @Override
    public void onVal(User user) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        savePrefs(user);
        transaction.commit();
    }

    public void savePrefs(User user){
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        editor.putString(NOM_KEY, user.getNom());
        editor.putString(PRENOM_KEY, user.getPrenom());
        editor.putString(VILLE_KEY, user.getVille_naissance());
        editor.putInt(DEPART_KEY, user.getDepartementNaissance());
        editor.putInt(BIRTH_YEAR, user.getBirthYear());
        editor.putInt(BIRTH_MONTH, user.getBirthMonth());
        editor.putInt(BIRTH_DAY, user.getBirthDay());

        editor.apply();

        Log.i("ville Sauvegarde", prefs.getString(VILLE_KEY, ""));
    }

    @Override
    public void onDate(int birthYear, int birthMonth, int birthDay){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.remove(mainFragment);

        Log.i("dates", String.valueOf(birthDay));

        transaction.add(R.id.LLmain, dateFragment, "dateFragment");
        transaction.runOnCommit(() -> dateFragment.dateSet(birthYear, birthMonth, birthDay));
//        transaction.addToBackStack("");
        transaction.commit();

    }

    @Override
    public void onExit(User user) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.remove(mainFragment);

        savePrefs(user);

        transaction.add(R.id.LLmain, displayFragment, "displayFragment");
        transaction.runOnCommit(() -> displayFragment.setDisplay(user));
//        transaction.addToBackStack("");
        transaction.commit();
    }
    @Override
    public void onEdit() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.remove(displayFragment);

        transaction.add(R.id.LLmain, mainFragment, "mainFragment");
//        transaction.addToBackStack("");
        transaction.commit();
    }
    @Override
    public void onExit(int birthYear, int birthMonth, int birthDay) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.remove(dateFragment);

        transaction.add(R.id.LLmain, mainFragment, "mainFragment");
        transaction.runOnCommit(() -> mainFragment.setDate(birthYear, birthMonth, birthDay));
//        transaction.addToBackStack("");
        transaction.commit();
    }

    @Override
    public void onSendDate(int birthYear, int birthMonth, int birthDay) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.remove(dateFragment);

        transaction.add(R.id.LLmain, mainFragment, "mainFragment");
        transaction.runOnCommit(() -> mainFragment.setDate(birthYear, birthMonth, birthDay));
//        transaction.addToBackStack("");
        transaction.commit();
    }
}