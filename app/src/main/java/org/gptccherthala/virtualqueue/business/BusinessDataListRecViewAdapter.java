package org.gptccherthala.virtualqueue.business;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import org.gptccherthala.virtualqueue.R;

import java.util.ArrayList;

public class BusinessDataListRecViewAdapter extends RecyclerView.Adapter<BusinessDataListRecViewAdapter.ViewHolder> {

    private ArrayList<BusinessDatabase> businessDatabase = new ArrayList<>();
    private Context mContext;

    public BusinessDataListRecViewAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public BusinessDataListRecViewAdapter() {
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.business_data_list_recview_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txtName.setText(businessDatabase.get(position).getName());

        Glide.with(mContext)
                .asBitmap()
                .load(businessDatabase.get(position).getImageUrl()).into(holder.image);
    }

    @Override
    public int getItemCount() {
        return businessDatabase.size();
    }

    public void setBusinessDatabase(ArrayList<BusinessDatabase> businessDatabase) {
        this.businessDatabase = businessDatabase;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView txtName;
        private final ImageView image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.text_name);
            image = itemView.findViewById(R.id.image);
        }
    }
}
