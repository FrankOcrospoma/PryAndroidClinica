package com.example.pryandroidclinica;

import androidx.annotation.DrawableRes;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import com.example.pryandroidclinica.fragments.PageFragment;
import com.example.pryandroidclinica.fragments.RecyclerViewFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    private static final String SELECTION = "SELECTION";

    private BottomNavigationView bottomNavigationView;
    EditText username;
    EditText password;
    Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupBottomMenu(savedInstanceState);

        username = findViewById(R.id.txtIngresarUsuario);
        password = findViewById(R.id.txtIngresarContraseña);
        loginButton = findViewById(R.id.btnIniciarSesion);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (username.getText().toString().equals("user") && password.getText().toString().equals("1234")) {
                    Toast.makeText(MainActivity.this, "Login Successful!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Login Failed!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void setupBottomMenu(Bundle savedInstanceState) {
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnItemSelectedListener(this::onItemSelectedListener);

        if (savedInstanceState == null) {
            //displays the initial fragment when app starts
            bottomNavigationView.setSelectedItemId(R.id.nav_home);
        } else {
            //restores the item selected before rotating
            bottomNavigationView.setSelectedItemId(savedInstanceState.getInt(SELECTION));
        }

        bottomNavigationView.getOrCreateBadge(R.id.nav_notificacion).setNumber(1000);
        bottomNavigationView.getOrCreateBadge(R.id.nav_historial).setVisible(true);
    }

    private boolean onItemSelectedListener(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.nav_home) {
            showPageFragment(R.drawable.home, R.string.title_home);
            return true;
        }
        else if (itemId == R.id.nav_notificacion) {
            showPageFragment(R.drawable.notificacion, R.string.title_notificacion);
            return true;
        } else if (itemId == R.id.nav_historial) {
            showPageFragment(R.drawable.historia, R.string.title_historial);
            return true;
        } else if (itemId == R.id.nav_profile) {
            showPageFragment(R.drawable.usuario, R.string.title_profile);
            return true;
        } else {
            throw new IllegalArgumentException("item not implemented : " + item.getItemId());
        }
    }

    private void showPageFragment(@DrawableRes int iconId, @StringRes int title) {
        showFragment(PageFragment.newInstance(iconId), title);
    }

    private void showFragment(Fragment frg, @StringRes int title) {
        getSupportFragmentManager()
                .beginTransaction()
                .setCustomAnimations(R.anim.bottom_nav_enter, R.anim.bottom_nav_exit)
                .replace(R.id.container, frg)
                .commit();
        setTitle(title);
    }
}
