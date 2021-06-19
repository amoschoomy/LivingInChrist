package com.amoschoojs.livinginchrist

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ScrollView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SwitchCompat
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView


class MainActivity : AppCompatActivity() {
    private lateinit var mainDrawer : DrawerLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.nav_drawer)
        val toolbar:androidx.appcompat.widget.Toolbar = findViewById(R.id.toolbar)
        mainDrawer=findViewById(R.id.maindrawer)
        setSupportActionBar(toolbar)
        val toggle =
            ActionBarDrawerToggle(this, mainDrawer, toolbar, R.string.open_menu, R.string.close_menu)
        mainDrawer.addDrawerListener(toggle)
        toggle.syncState()
        val navigationView = findViewById<NavigationView>(R.id.navview)
//        navigationView.setNavigationItemSelectedListener(MyNavigationViewListener())
        mainDrawer.closeDrawer(GravityCompat.START)

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.app_menu, menu)
        val appBarSwitch = menu?.findItem(R.id.app_bar_switch)
        val scrollView: ScrollView = findViewById(R.id.scrollview)
        val nightModeToggle : SwitchCompat=appBarSwitch?.actionView?.findViewById(R.id.nightmode) as SwitchCompat
        nightModeToggle.setOnClickListener {
            when (nightModeToggle.isChecked) {
                true -> {
                    scrollView.setBackgroundColor(
                        ContextCompat.getColor(
                            applicationContext,
                            R.color.black
                        )
                    )
                }
                false -> scrollView.setBackgroundColor(
                    ContextCompat.getColor(
                        applicationContext,
                        R.color.white
                    )
                )

            }
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return super.onOptionsItemSelected(item)
    }
}
//class MyNavigationViewListener : NavigationView.OnNavigationItemSelectedListener {
//    override fun onNavigationItemSelected(item: MenuItem) {
//        val mainDrawer : DrawerLayout =
//    }
//}

//    @Override
//    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//        int id = item.getItemId();
//        switch (id) {
//            case R.id.addcar:
//            addCar();
//            EditText maker = findViewById(R.id.makertext);
//            EditText model = findViewById(R.id.modeltext);
//            EditText year = findViewById(R.id.yeartext);
//            EditText color = findViewById(R.id.colortext);
//            EditText seats = findViewById(R.id.seattext);
//            EditText price = findViewById(R.id.priceno);
//            Car car =
//            new Car(
//                    maker.getText().toString(),
//            model.getText().toString(),
//            Integer.parseInt(year.getText().toString()),
//            color.getText().toString(),
//            Integer.parseInt(seats.getText().toString()),
//            Integer.parseInt(price.getText().toString()));
//            carViewModel.insert(car);
//            myRef.push().setValue(car);
//
//            break;
//            case R.id.removelast:
//            break;
//            case R.id.removeall:
//            carViewModel.deleteAll();
//            myRef.removeValue();
//            break;
//
//            case R.id.listall:
//            Intent myIntent = new Intent(MainActivity.this, ListCars.class);
//            MainActivity.this.startActivity(myIntent);
//            break;
//        }
//        DrawerLayout drawer = findViewById(R.id.drawer);
//        drawer.closeDrawer(GravityCompat.START);
//        return true;
//    }
//}
//}
