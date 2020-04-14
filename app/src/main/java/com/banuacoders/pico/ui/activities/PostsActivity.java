package com.banuacoders.pico.ui.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.banuacoders.pico.R;
import com.banuacoders.pico.adapter.PostsAdapter;
import com.banuacoders.pico.data.model.DistrictPost;
import com.banuacoders.pico.network.NetworkClient;
import com.banuacoders.pico.ui.viewmodel.PostsViewModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostsActivity extends AppCompatActivity {

    private PostsViewModel postsViewModel;
    private PostsAdapter adapter;

    Unbinder unbinder;

    @BindView(R.id.rv_posts)
    RecyclerView rvPosts;
    @BindView(R.id.call_logo)
    ImageView call;
    @BindView(R.id.btn_sync)
    ImageView btnSync;
    @BindView(R.id.progress_posts)
    ProgressBar progressBar;
    @BindView(R.id.call_center_number)
    TextView tvNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posts);
        unbinder = ButterKnife.bind(this);
        adapter = new PostsAdapter(this);
        rvPosts.setLayoutManager(new LinearLayoutManager(this));
        postsViewModel = ViewModelProviders.of(this)
                .get(PostsViewModel.class);
        progressBar.setVisibility(View.VISIBLE);
        if (postsViewModel.getAllPosts() == null) {
            getData();
        } else {
            progressBar.setVisibility(View.GONE);
            postsViewModel.getAllPosts().observe(this, districtPosts -> {
                for (DistrictPost districtPost : districtPosts) {
                    adapter.setDistrictPostList(districtPosts);
                    adapter.notifyDataSetChanged();
                    rvPosts.setAdapter(adapter);
                }
            });
        }
        btnSync.setOnClickListener(view -> {
            rotateSync();
            if (postsViewModel.getAllPosts() != null) {
                postsViewModel.deleteAllPosts();
                getData();
            }
        });
        call.setOnClickListener(view -> {
            String number = tvNumber.getText().toString().trim();
            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + number));
            startActivity(intent);
        });
    }

    void getData() {
        Call<ResponseBody> call = NetworkClient.getInstance().getApiCoder().getAllPosts();
        progressBar.setVisibility(View.VISIBLE);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String responseBody = response.body().string();
                    JSONObject objectResponse = new JSONObject(responseBody);
                    if (objectResponse.getBoolean("success")) {
                        JSONArray jsonArray = objectResponse.getJSONArray("data");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            DistrictPost districtPost = new DistrictPost();
                            districtPost.setNo(jsonArray.getJSONObject(i).getInt("no"));
                            districtPost.setName(jsonArray.getJSONObject(i).getString("nama"));
                            districtPost.setPosts(jsonArray.getJSONObject(i).getString("posko"));
                            postsViewModel.insert(districtPost);
                        }
                        progressBar.setVisibility(View.GONE);
                    } else {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(PostsActivity.this,
                                objectResponse
                                        .getJSONObject("errors")
                                        .getString("message")
                                , Toast.LENGTH_SHORT).show();
                    }
                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                    progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
            }
        });
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
}
