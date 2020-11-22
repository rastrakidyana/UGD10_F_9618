package com.rastrakidyana.ugd10_f_9618.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.rastrakidyana.ugd10_f_9618.Model.User;
import com.rastrakidyana.ugd10_f_9618.R;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class UserRecyclerViewAdapter extends RecyclerView.Adapter<UserRecyclerViewAdapter.UserViewHolder> {

    private User user;
    private Context context;
    private List<User> userList;

    public UserRecyclerViewAdapter(Context context, List<User> userList) {
        this.context = context;
        this.userList = userList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_user, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        user = userList.get(position);
        holder.tvNPM.setText(Integer.toString(user.getNpmU()));
        holder.tvNama.setText(user.getNamaU());
        holder.tvFakultas.setText(user.getFakultasU());
        holder.tvJurusan.setText(user.getJurusanU());
        holder.tvIPK.setText(Double.toString(user.getIpkU()));
        Glide.with(context)
                .load(user.getFotoU())
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .into(holder.ivFoto);
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public class UserViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvNPM, tvNama, tvFakultas, tvJurusan, tvIPK;
        ImageView ivFoto;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNPM = itemView.findViewById(R.id.npmUser);
            tvNama = itemView.findViewById(R.id.namaUser);
            tvFakultas = itemView.findViewById(R.id.fakultasUser);
            tvJurusan = itemView.findViewById(R.id.jurusanUser);
            tvIPK = itemView.findViewById(R.id.ipkUser);
            ivFoto = itemView.findViewById(R.id.fotoUser);
        }

        @Override
        public void onClick(View v) {

        }
    }

}
