package com.maxfour.music.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.maxfour.appthemehelper.ThemeStore;
import com.maxfour.music.R;
import com.maxfour.music.ui.activities.base.AbsBaseActivity;
import com.maxfour.music.ui.activities.intro.AppIntroActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

@SuppressWarnings("FieldCanBeLocal")
public class AboutActivity extends AbsBaseActivity implements View.OnClickListener {

    private static String GITHUB = "https://github.com/MaxFour/Music-Player";

    private static String QIWI= "https://qiwi.com/payment/form/99?extra%5B%E2%80%98account%E2%80%99%5D=998997524609";

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.app_version)
    TextView appVersion;
    @BindView(R.id.intro)
    LinearLayout intro;
    @BindView(R.id.write_an_email)
    LinearLayout writeAnEmail;
    @BindView(R.id.fork_on_github)
    LinearLayout forkOnGitHub;
    @BindView(R.id.qiwi)
    LinearLayout qiwi;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        setDrawUnderStatusbar();
        ButterKnife.bind(this);

        setStatusbarColorAuto();
        setNavigationbarColorAuto();
        setTaskDescriptionColorAuto();

        setUpViews();
    }

    private void setUpViews() {
        setUpToolbar();
        setUpAppVersion();
        setUpOnClickListeners();
    }

    private void setUpToolbar() {
        toolbar.setBackgroundColor(ThemeStore.primaryColor(this));
        setSupportActionBar(toolbar);
        //noinspection ConstantConditions
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void setUpAppVersion() {
        appVersion.setText(getCurrentVersionName(this));
    }

    private void setUpOnClickListeners() {
        intro.setOnClickListener(this);
        forkOnGitHub.setOnClickListener(this);
        writeAnEmail.setOnClickListener(this);
        qiwi.setOnClickListener(this);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private static String getCurrentVersionName(@NonNull final Context context) {
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return "Unknown";
    }

    @Override
    public void onClick(View v) {
        if (v == intro) {
            startActivity(new Intent(this, AppIntroActivity.class));
        } else if (v == forkOnGitHub) {
            openUrl(GITHUB);
        } else if (v == writeAnEmail) {
            Intent intent = new Intent(Intent.ACTION_SENDTO);
            intent.setData(Uri.parse("mailto:mansurov.maksud@gmail.com"));
            intent.putExtra(Intent.EXTRA_EMAIL, "mansurov.maksud@gmail.com");
            intent.putExtra(Intent.EXTRA_SUBJECT, "Music");
            startActivity(Intent.createChooser(intent, "E-Mail"));
        } else if (v == qiwi) {
            openUrl(QIWI);
        }
    }

    private void openUrl(String url) {
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
    }

}
