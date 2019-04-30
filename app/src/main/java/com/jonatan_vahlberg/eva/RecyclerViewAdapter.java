package com.jonatan_vahlberg.eva;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import io.realm.Realm;
import io.realm.RealmResults;

public class RecyclerViewAdapter extends  RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Realm realm;
    private Context context;
    private RealmResults<Message> messages;

    public RecyclerViewAdapter(Context context, RealmResults<Message> messages){
        this.context = context;
        this.messages = messages;
        realm = Realm.getDefaultInstance();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        if(i == 0){
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_list_message, viewGroup,false);
            ViewHolderEVA holderEVA = new ViewHolderEVA(view);
            return  holderEVA;
        }
        else{
            View view2 = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_list_message_self, viewGroup,false);
            ViewHolderSelf holderSelf = new ViewHolderSelf(view2);
            return  holderSelf;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
            if (getItemViewType(i) == 0){
                ViewHolderEVA holderEVA = (ViewHolderEVA) viewHolder;

                holderEVA.message.setText(messages.get(i).getMessage());
                holderEVA.icon.setImageResource(R.drawable.ic_launcher_foreground);

            }
            else{
                ViewHolderSelf holderSelf = (ViewHolderSelf) viewHolder;

                holderSelf.message.setText(messages.get(i).getMessage());
                holderSelf.icon.setImageResource(R.drawable.ic_launcher_foreground);
            }
    }

    @Override
    public int getItemViewType(int position) {
        return (messages.get(position).getSender().equals("EVA"))? 0 : 1;
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    public class ViewHolderSelf extends RecyclerView.ViewHolder {
        TextView message;
        ImageView icon;

        public ViewHolderSelf (View itemView){
            super(itemView);
            this.message = itemView.findViewById(R.id.text_view_self);
            this.icon = itemView.findViewById(R.id.image_view_self);

        }

    }

    public class ViewHolderEVA extends RecyclerView.ViewHolder {
        TextView message;
        ImageView icon;
        LinearLayout layout;

        public ViewHolderEVA (View itemView){
            super(itemView);
            this.message = itemView.findViewById(R.id.text_view_eva);
            this.icon = itemView.findViewById(R.id.image_view_eva);
            this.layout = itemView.findViewById(R.id.linear_layout_eva);

        }

    }
}
