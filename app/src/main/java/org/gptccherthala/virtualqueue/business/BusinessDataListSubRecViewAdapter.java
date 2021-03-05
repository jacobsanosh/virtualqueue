package org.gptccherthala.virtualqueue.business;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.gptccherthala.virtualqueue.R;

import java.util.ArrayList;

public class BusinessDataListSubRecViewAdapter extends RecyclerView.Adapter<BusinessDataListSubRecViewAdapter.ViewHolder> {
    private Context mContext;
    private ArrayList<BusinessDatabase> mDatabase = new ArrayList<>();

    public BusinessDataListSubRecViewAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public BusinessDataListSubRecViewAdapter() {
    }

    public void setDatabase(ArrayList<BusinessDatabase> mDatabase) {
        this.mDatabase = mDatabase;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.business_data_list_recview_sub_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.img.setImageResource(mDatabase.get(position).getImagePath());
        holder.tvType.setText(mDatabase.get(position).getType());
        holder.img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent businessDataListActivity = new Intent(mContext, BusinessDataListActivity.class);
                businessDataListActivity.putExtra("type", mDatabase.get(position).getType());
                businessDataListActivity.putExtra("category", mDatabase.get(position).getCategory());
                mContext.startActivity(businessDataListActivity);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDatabase.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageButton img;
        TextView tvType;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.image);
            tvType = itemView.findViewById(R.id.text_type);
        }
    }
}
