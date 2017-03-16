package com.handsomexu.qianzhi.activity;

import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.handsomexu.qianzhi.R;
import com.handsomexu.qianzhi.fragments.BookmarkFragment;
import com.handsomexu.qianzhi.fragments.MainFragment;
import com.handsomexu.qianzhi.presenter.BookmarkPresenter;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private MainFragment mainFragment;
    private BookmarkFragment bookmarksFragment;

    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        //初始化控件
        initView();

        //恢复fragment状态
        if(savedInstanceState != null){
            mainFragment = (MainFragment) getSupportFragmentManager()
                    .getFragment(savedInstanceState,"MainFragment");
            bookmarksFragment = (BookmarkFragment) getSupportFragmentManager()
                    .getFragment(savedInstanceState,"BookmarkFragment");
        }else {
            mainFragment = MainFragment.newInstance();
            bookmarksFragment = BookmarkFragment.newInstance();
        }
        if(!mainFragment.isAdded()){
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.layout_fragment,mainFragment,"MainFragment")
                    .commit();
        }
        if(!bookmarksFragment.isAdded()){
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.layout_fragment,bookmarksFragment,"BookmarkFragment")
                     .commit();
        }

        //实例化BookmarkPresenter
        new BookmarkPresenter(MainActivity.this,bookmarksFragment);
        //默认显示首页内容
        showMainFragment();
    }

    private void initView() {

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this,drawerLayout,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
       drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    //显示MainFragment并设置title
    private void showMainFragment(){

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.show(mainFragment);
        fragmentTransaction.hide(bookmarksFragment);
        fragmentTransaction.commit();

        toolbar.setTitle(getResources().getString(R.string.app_name));
    }

    //显示BookmarkFragment并设置title
    private void showBookmarkFragment(){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.show(bookmarksFragment);
        fragmentTransaction.hide(mainFragment);
        fragmentTransaction.commit();

        toolbar.setTitle(getResources().getString(R.string.nav_bookmarks));
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        drawerLayout.closeDrawer(GravityCompat.START);
        int id = item.getItemId();
        switch (id){
            case R.id.nav_home:
                showMainFragment();
                break;
            case R.id.nav_bookmark:
                showBookmarkFragment();
                break;
            case R.id.nav_theme:
                break;
            case R.id.nav_setting:
                break;
            case R.id.nav_about:
                break;
        }
        return true;
    }

    //保存Fragment状态
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if(mainFragment.isAdded()){
            getSupportFragmentManager().putFragment(outState,"MainFragment",mainFragment);
        }
        if(bookmarksFragment.isAdded()){
            getSupportFragmentManager().putFragment(outState,"BookFragment",bookmarksFragment);
        }
    }
}
