package com.itsmerifz.activitysqlite.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.itsmerifz.activitysqlite.EditTeman;
import com.itsmerifz.activitysqlite.MainActivity;
import com.itsmerifz.activitysqlite.R;
import com.itsmerifz.activitysqlite.database.DBController;
import com.itsmerifz.activitysqlite.database.Teman;

import java.util.ArrayList;
import java.util.HashMap;

public class TemanAdapter extends RecyclerView.Adapter<TemanAdapter.TemanViewHolder> {
    private ArrayList<Teman> listData;
    private Context c;
    public TemanAdapter(ArrayList<Teman> listData){
        this.listData = listData;
    }

    @Override
    public TemanViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        c = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.row_data_teman,parent,false);
        return new TemanViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull TemanViewHolder holder, int position) {
        String nm,tlp,id;

        id = listData.get(position).getId();
        nm = listData.get(position).getNama();
        tlp = listData.get(position).getTelp();
        DBController db = new DBController(c);

        holder.nama.setTextColor(Color.BLUE);
        holder.nama.setTextSize(20);
        holder.nama.setText(nm);
        holder.telp.setText(tlp);

        holder.card.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                PopupMenu pop = new PopupMenu(c,holder.card);
                pop.inflate(R.menu.popup_menu);
                pop.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()){
                            case R.id.edData:
                                Intent i = new Intent(c, EditTeman.class);
                                i.putExtra("id",id);
                                i.putExtra("nama",nm);
                                i.putExtra("telp",tlp);
                                c.startActivity(i);
                                break;

                            case R.id.hpsData:
                                HashMap<String,String> val = new HashMap<>();
                                val.put("id",id);
                                db.deleteData(val);
                                Intent in = new Intent(c, MainActivity.class);
                                c.startActivity(in);
                                break;
                        }
                        return true;
                    }
                });
                pop.show();
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return listData != null ? listData.size() : 0;
    }

    public class TemanViewHolder extends RecyclerView.ViewHolder {
        private CardView card;
        private TextView nama,telp;
        public TemanViewHolder(View v) {
            super(v);
            card = v.findViewById(R.id.card);
            nama = v.findViewById(R.id.textNama);
            telp = v.findViewById(R.id.textTelp);
        }
    }
}
