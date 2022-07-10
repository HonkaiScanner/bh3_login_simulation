package com.github.haocen2004.login_simulation.activity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;

import androidx.core.content.FileProvider;

import com.github.haocen2004.login_simulation.databinding.ActivityCrashBinding;
import com.github.haocen2004.login_simulation.util.Tools;

import java.io.File;

public class CrashActivity extends BaseActivity {
    ActivityCrashBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCrashBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Tools.saveBoolean(this, "has_crash", false);
        binding.button.setOnClickListener(view -> openAssignFolder(getExternalFilesDir(null) + "/crash-report/" + Tools.getString(this, "crash-report-name")));
    }

    private void openAssignFolder(String path) {
        File file = new File(path);
        if (!file.exists()) return;
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        intent.putExtra(Intent.EXTRA_STREAM, FileProvider.getUriForFile(this, getPackageName() + ".fileprovider", file));

        intent.setType("text/plain");
        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.setFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);


        try {
            startActivity(intent);
//            startActivity(Intent.createChooser(intent,"选择浏览工具"));
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
        }
    }
}
