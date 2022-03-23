package bi.hogi.e_miagefestival;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class CardGroup extends RecyclerView.Adapter<CardGroup.GroupModelItem>{
    private MainActivity activity;
    private ArrayList<GroupModel> groups;

    public CardGroup(MainActivity activity, ArrayList<GroupModel> groups) {
        this.activity = activity;
        this.groups = groups;
    }

    @NonNull
    @Override
    public GroupModelItem onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_groupe, parent, false);
        return new GroupModelItem(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final GroupModelItem holder, int position) {
        final GroupModel group = groups.get(position);
        holder.txt_artiste.setText(group.artiste);
        holder.txt_details.setText(group.texte.substring(0, 100));
        Glide.with(activity).load(HOST.URL+"/"+group.image).into(holder.img_group_logo);
        if(group.is_favorite){
            holder.btn_make_fav.setVisibility(View.GONE);
            holder.btn_make_unfav.setVisibility(View.VISIBLE);
        } else {
            holder.btn_make_fav.setVisibility(View.VISIBLE);
            holder.btn_make_unfav.setVisibility(View.GONE);
        }
        holder.btn_make_fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        holder.btn_make_unfav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
    }

    @Override
    public int getItemCount() {
        return groups.size();
    }

    public class GroupModelItem extends RecyclerView.ViewHolder {
        TextView txt_artiste, txt_details;
        ImageView img_group_logo;
        ImageButton btn_make_fav, btn_make_unfav;
        public GroupModelItem(@NonNull View itemView) {
            super(itemView);
            img_group_logo = itemView.findViewById(R.id.img_group_logo);
            txt_artiste = itemView.findViewById(R.id.txt_artiste);
            txt_details = itemView.findViewById(R.id.btn_make_fav);
            btn_make_fav = itemView.findViewById(R.id.btn_make_unfav);
            btn_make_unfav = itemView.findViewById(R.id.txt_details);
        }
    }
}