package com.banuacoders.pico.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.banuacoders.pico.R;
import com.banuacoders.pico.data.model.DistrictPost;
import com.google.android.material.card.MaterialCardView;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.PostsViewHolder> {
    private List<DistrictPost> districtPostList = new ArrayList<>();
    private Context mContext;

    public PostsAdapter(Context mContext) {
        this.mContext = mContext;
        setHasStableIds(true);
    }

    public void setDistrictPostList(List<DistrictPost> districtPostList) {
        this.districtPostList = districtPostList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public PostsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_posts, parent, false);
        return new PostsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostsViewHolder holder, int position) {
        holder.setIsRecyclable(false);
        final DistrictPost posts = districtPostList.get(position);
        holder.tvNamePosts.setText(posts.getName());
        try {
            JSONArray jsonArray = new JSONArray(posts.getPosts());
            for (int i = 0; i < jsonArray.length(); i++) {
                holder.arrayNameCP[i].setText(jsonArray.getJSONObject(i).getString("nama"));
                holder.arrayNameCP[i].setVisibility(View.VISIBLE);
                JSONArray arrayPhone = jsonArray.getJSONObject(i).getJSONArray("no_hp");
                for (int k = 0; k < arrayPhone.length(); ++k) {
                    TextView tvPhoneNumber = holder.arrayPhoneNumberCP[i][k];
                    MaterialCardView phoneNumContainer = holder.arrayPhoneCP[i][k];
                    phoneNumContainer.setVisibility(View.VISIBLE);
                    tvPhoneNumber.setText(arrayPhone.getString(k));
                    phoneNumContainer.setOnClickListener(view -> {
                        String number = tvPhoneNumber.getText().toString().trim();
                        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + number));
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        mContext.startActivity(intent);
                    });
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return districtPostList.size();
    }

    static class PostsViewHolder extends RecyclerView.ViewHolder {
        Unbinder unbinder;

        @BindView(R.id.posts_name)
        TextView tvNamePosts;
        @BindView(R.id.cp_name_1)
        TextView tvName1;
        @BindView(R.id.cp_1_phone_number_1)
        TextView tvCp1Phone1;
        @BindView(R.id.cp_1_phone_number_2)
        TextView tvCp1Phone2;
        @BindView(R.id.cp_1_phone_number_3)
        TextView tvCp1Phone3;
        @BindView(R.id.cp_1_phone_1)
        MaterialCardView cp1Phone1;
        @BindView(R.id.cp_1_phone_2)
        MaterialCardView cp1Phone2;
        @BindView(R.id.cp_1_phone_3)
        MaterialCardView cp1Phone3;

        @BindView(R.id.cp_name_2)
        TextView tvName2;
        @BindView(R.id.cp_2_phone_number_1)
        TextView tvCp2Phone1;
        @BindView(R.id.cp_2_phone_number_2)
        TextView tvCp2Phone2;
        @BindView(R.id.cp_2_phone_number_3)
        TextView tvCp2Phone3;
        @BindView(R.id.cp_2_phone_1)
        MaterialCardView cp2Phone1;
        @BindView(R.id.cp_2_phone_2)
        MaterialCardView cp2Phone2;
        @BindView(R.id.cp_2_phone_3)
        MaterialCardView cp2Phone3;

        @BindView(R.id.cp_name_3)
        TextView tvName3;
        @BindView(R.id.cp_3_phone_number_1)
        TextView tvCp3Phone1;
        @BindView(R.id.cp_3_phone_number_2)
        TextView tvCp3Phone2;
        @BindView(R.id.cp_3_phone_number_3)
        TextView tvCp3Phone3;
        @BindView(R.id.cp_3_phone_1)
        MaterialCardView cp3Phone1;
        @BindView(R.id.cp_3_phone_2)
        MaterialCardView cp3Phone2;
        @BindView(R.id.cp_3_phone_3)
        MaterialCardView cp3Phone3;

        TextView[] arrayNameCP;
        TextView[][] arrayPhoneNumberCP;

        MaterialCardView[][] arrayPhoneCP;

        PostsViewHolder(@NonNull View itemView) {
            super(itemView);
            unbinder = ButterKnife.bind(this, itemView);
            arrayNameCP = new TextView[]{
                    tvName1,
                    tvName2,
                    tvName3
            };
            arrayPhoneNumberCP = new TextView[][]{
                    {
                            tvCp1Phone1,
                            tvCp1Phone2,
                            tvCp1Phone3
                    },
                    {
                            tvCp2Phone1,
                            tvCp2Phone2,
                            tvCp2Phone3
                    },
                    {
                            tvCp3Phone1,
                            tvCp3Phone2,
                            tvCp3Phone3
                    }
            };

            arrayPhoneCP = new MaterialCardView[][]{
                    {
                            cp1Phone1,
                            cp1Phone2,
                            cp1Phone3
                    },
                    {
                            cp2Phone1,
                            cp2Phone2,
                            cp2Phone3
                    },
                    {
                            cp3Phone1,
                            cp3Phone2,
                            cp3Phone3
                    }
            };
        }
    }
}
