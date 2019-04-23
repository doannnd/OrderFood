package com.nguyendinhdoan.orderfood.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.nguyendinhdoan.orderfood.R;
import com.nguyendinhdoan.orderfood.listener.OnItemClickListener;
import com.nguyendinhdoan.orderfood.model.Category;
import com.nguyendinhdoan.orderfood.viewholder.MenuViewHolder;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static final String TAG = "HOME_ACTIVITY";
    public static final String CATEGORY_TABLE_NAME = "categorys";

    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private FloatingActionButton fab;
    private RecyclerView menuRecyclerView;
    private TextView nameTextView;
    private CoordinatorLayout rootLayout;

    private DatabaseReference categoryTable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        initViews();
        setupUI();
        addEvents();
    }

    private void initViews() {
        toolbar = findViewById(R.id.toolbar);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        fab = findViewById(R.id.fab);
        menuRecyclerView = findViewById(R.id.menu_recycler_view);
        rootLayout = findViewById(R.id.root_layout);
    }

    private void setupUI() {
        setupToolbar();
        setupFab();
        setupNavigationDrawer();
        setupNameUser();
        setupFirebase();
        setupRecyclerView();
    }

    private void addEvents() {
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void setupToolbar() {
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(getString(R.string.menu_main));
        }
    }

    private void setupFab() {
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    private void setupNavigationDrawer() {
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        View view = navigationView.getHeaderView(0);
        nameTextView = view.findViewById(R.id.user_name_text_view);
    }

    private void setupNameUser() {
        String name = getIntent().getStringExtra(LoginActivity.USER_NAME_KEY);
        Log.d(TAG, "user name: " + name);
        nameTextView.setText(name);
    }

    private void setupFirebase() {
        categoryTable = FirebaseDatabase.getInstance().getReference(CATEGORY_TABLE_NAME);
    }

    private void setupRecyclerView() {
        menuRecyclerView.setHasFixedSize(true);
        menuRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        setupMenuAdapter();
    }

    private void setupMenuAdapter() {
        FirebaseRecyclerAdapter<Category, MenuViewHolder> adapter =
                new FirebaseRecyclerAdapter<Category, MenuViewHolder>(
                        Category.class,
                        R.layout.item_menu_recycler_view,
                        MenuViewHolder.class,
                        categoryTable
                ) {
            @Override
            protected void populateViewHolder(final MenuViewHolder menuViewHolder, final Category category, int position) {
                // set data menu
                menuViewHolder.menuName.setText(category.getName());
                Picasso.get().load(category.getImage()).into(menuViewHolder.menuImage, new Callback() {
                    @Override
                    public void onSuccess() {
                        menuViewHolder.progressBar.setVisibility(View.INVISIBLE);
                    }

                    @Override
                    public void onError(Exception e) {
                        Snackbar.make(rootLayout, e.getMessage(), Snackbar.LENGTH_LONG).show();
                    }
                });
                // set event
                menuViewHolder.setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClickListener(View view, int position) {
                        Toast.makeText(HomeActivity.this, "name: " + category.getName(),
                                Toast.LENGTH_SHORT).show();
                    }
                });
            }
        };
        // set adapter
        menuRecyclerView.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();

        switch (id) {
            case R.id.nav_menu: {
                break;
            }
            case R.id.nav_order: {
                break;
            }
            case R.id.nav_cart: {
                break;
            }
            case R.id.nav_logout: {
                break;
            }
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}
