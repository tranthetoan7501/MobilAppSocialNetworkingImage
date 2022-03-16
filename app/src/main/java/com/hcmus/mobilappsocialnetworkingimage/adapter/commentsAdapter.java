package adapter;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.hcmus.mobilappsocialnetworkingimage.R;
import com.squareup.picasso.Picasso;


import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class commentsAdapter extends RecyclerView.Adapter<commentsAdapter.commentsViewHolder>{
    Context context;
    List<String> description;
    List<String> date;
    List<String> num_likes;
    List<String> avatar;

    public commentsAdapter(Context context, List<String> description, List<String> date, List<String> num_likes, List<String> avatar) {
        this.context = context;
        this.description = description;
        this.date = date;
        this.num_likes = num_likes;
        this.avatar = avatar;
    }

    @NonNull
    @Override
    public commentsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.comment,parent,false);
        return new commentsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull commentsViewHolder holder, int position) {
        if (description.isEmpty()) return;

        Picasso.get().load(avatar.get(position)).into(holder.avatar);
        String str[] = description.get(position).split("\n");
        holder.description.setText(Html.fromHtml("<b>" + str[0]+"</b>" + description.get(position).replace(str[0]," ")));
        holder.num_likes.setText(num_likes.get(position));
        holder.date.setText(date.get(position));
    }

    @Override
    public int getItemCount() {
        return description.size();
    }

    class commentsViewHolder extends RecyclerView.ViewHolder{
        CircleImageView avatar;
        TextView description;
        TextView date;
        TextView num_likes;
        ImageButton like;
        public commentsViewHolder(@NonNull View itemView) {
            super(itemView);
            avatar = itemView.findViewById(R.id.avatar);
            description = itemView.findViewById(R.id.description);
            date = itemView.findViewById(R.id.date);
            num_likes = itemView.findViewById(R.id.num_likes);
            like = itemView.findViewById(R.id.like);
        }
    }
}