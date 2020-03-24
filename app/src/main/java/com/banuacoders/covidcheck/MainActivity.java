package com.banuacoders.covidcheck;

import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import com.banuacoders.covidcheck.adapter.ListMenuAdapter;
import com.banuacoders.covidcheck.object.MenuItem;
import com.github.rubensousa.gravitysnaphelper.GravitySnapHelper;

import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private RecyclerView rvMenu;
    private ArrayList<MenuItem> menus = new ArrayList<>();
    private TextView tvDate;
    private ImageView btnSync;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getComponents();
    }

    private void getComponents() {
        tvDate = findViewById(R.id.information_date_value);
        btnSync = findViewById(R.id.btn_sync);
        rvMenu = findViewById(R.id.rv_menu);
        rvMenu.setHasFixedSize(true);
        SnapHelper snapHelper = new GravitySnapHelper(Gravity.CENTER);
        snapHelper.attachToRecyclerView(rvMenu);
        menus.addAll(getAllMenu());
        bind();
    }

    private ArrayList<MenuItem> getAllMenu() {
        String[] dataTitle = getResources().getStringArray(R.array.array_title_menu);
        String[] dataIcon = getResources().getStringArray(R.array.array_icon_menu);
        String[] dataDesc = getResources().getStringArray(R.array.array_desc_menu);
        ArrayList<MenuItem> listMenu = new ArrayList<>();
        for (int i = 0; i < dataTitle.length; i++) {
            MenuItem menuItem = new MenuItem();
            menuItem.setTitle(dataTitle[i]);
            menuItem.setDesc(dataDesc[i]);
            menuItem.setIcon(getImage(dataIcon[i]));
            listMenu.add(menuItem);
        }
        return listMenu;
    }

    private void bind() {
        rvMenu.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        ListMenuAdapter adapter = new ListMenuAdapter(menus);
        rvMenu.setAdapter(adapter);
        setDateTime();
        btnSync.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rotateSync();
            }
        });
    }

    void setDateTime() {
        final Handler dateHandler = new Handler();
        dateHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                String currentDate = java.text.DateFormat.getDateTimeInstance().format(new Date());
                tvDate.setText(currentDate);
                dateHandler.postDelayed(this, 1000);
            }
        }, 10);
    }

    void rotateSync() {
        int mCurrRotation = 0;
        float fromRotation = mCurrRotation;
        float toRotation = mCurrRotation -= 180;

        final RotateAnimation rotateAnim = new RotateAnimation(
                fromRotation, toRotation, btnSync.getWidth() / 2, btnSync.getHeight() / 2);

        rotateAnim.setDuration(1000); // Use 0 ms to rotate instantly
        rotateAnim.setFillAfter(true); // Must be true or the animation will reset

        btnSync.startAnimation(rotateAnim);
    }

    public int getImage(String imageName) {

        return this.getResources().getIdentifier(imageName, "drawable", this.getPackageName());
    }
}
