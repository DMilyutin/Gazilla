package com.gazilla.mihail.gazillaj.ui.main.stock.StockMusic.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.gazilla.mihail.gazillaj.R;
import com.gazilla.mihail.gazillaj.utils.POJO.Song;

import java.util.List;

public class SongsAdapter extends BaseAdapter {

    private Context context;
    private List<Song> songList;

    public SongsAdapter(Context context, List<Song> songList) {
        this.context = context;
        this.songList = songList;
    }

    @Override
    public int getCount() {
        return songList.size();
    }

    @Override
    public Song getItem(int position) {
        return songList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return songList.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.for_list_songs, null);
        }

        TextView nameSong   =convertView.findViewById(R.id.tvNameSong);
        TextView nameArtist =convertView.findViewById(R.id.tvNameArtist);
        TextView lengthSong =convertView.findViewById(R.id.tvLengthSong);

        nameSong.setText(getItem(position).getName());
        nameArtist.setText(getItem(position).getArtist());
        lengthSong.setText(getItem(position).getLength());

        return convertView;
    }


}
