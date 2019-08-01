package com.maxfour.music.ui.activities;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;

import com.kabouzeid.appthemehelper.ThemeStore;
import com.maxfour.music.R;
import com.maxfour.music.ui.activities.base.AbsBaseActivity;
import com.maxfour.music.ui.activities.intro.AppIntroActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

@SuppressWarnings("FieldCanBeLocal")
public class AboutActivity extends AbsBaseActivity implements View.OnClickListener {

    private static String GITHUB = "https://github.com/MaxFour/Music-Player";

    private static String WebMoney="https://www.webmoney.ru/eng/";
    private static String YandexMoney="https://money.yandex.ru/to/410015372205898";

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
    @BindView(R.id.webmoney)
    LinearLayout webmoney;
    @BindView(R.id.ruble_button)
    Button rubleButton;
    @BindView(R.id.dollar_button)
    Button dollarButton;
    @BindView(R.id.yandex_money)
    LinearLayout yandexMoney;


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
        toolbar.setTitleTextAppearance(this, R.style.ProductSansTextAppearace);
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
        webmoney.setOnClickListener(this);
        rubleButton.setOnClickListener(this);
        dollarButton.setOnClickListener(this);
        yandexMoney.setOnClickListener(this);
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
        } else if (v == webmoney) {
            openUrl(WebMoney);
        } else if (v == rubleButton) {
            ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clipData = ClipData.newPlainText("WMR", "R282056119931");
            clipboard.setPrimaryClip(clipData);
            Toast.makeText(getApplicationContext(), R.string.clipboard_ruble_wallet_number_copied, Toast.LENGTH_LONG).show();
        } else if (v == dollarButton) {
            ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clipData = ClipData.newPlainText("WMZ", "Z776114750889");
            clipboard.setPrimaryClip(clipData);
            Toast.makeText(getApplicationContext(), R.string.clipboard_dollar_wallet_number_copied, Toast.LENGTH_LONG).show();
        } else if (v == yandexMoney) {
            openUrl(YandexMoney);
        }
    }

    private void openUrl(String url) {
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
    }

}
