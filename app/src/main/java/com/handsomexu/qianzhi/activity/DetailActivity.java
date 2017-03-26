package com.handsomexu.qianzhi.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.handsomexu.qianzhi.R;
import com.handsomexu.qianzhi.bean.BeanType;
import com.handsomexu.qianzhi.fragments.DetailFragment;
import com.handsomexu.qianzhi.presenter.DetailPresenter;

/**
 * Created by HandsomeXu on 2017/3/16.
 */

public class DetailActivity extends AppCompatActivity {

    private DetailFragment mFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.frame);
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {
            mFragment = (DetailFragment) getSupportFragmentManager().getFragment(savedInstanceState, "datailFragment");

        } else {
            mFragment = new DetailFragment();
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container, mFragment)
                    .commit();
        }

        Intent intent = getIntent();

        DetailPresenter presenter = new DetailPresenter(DetailActivity.this, mFragment);

        presenter.setBeanType((BeanType) intent.getSerializableExtra("type"));
        presenter.setId(intent.getIntExtra("id", 0));
        presenter.setTitle(intent.getStringExtra("title"));
        String coverUrl = intent.getStringExtra("coverUrl");
        if (coverUrl != null) {
            presenter.setCoverUrl(coverUrl);
        }

    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        if (mFragment.isAdded()) {
            getSupportFragmentManager().putFragment(outState, "detailFragment", mFragment);
        }
    }
}
