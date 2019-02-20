package com.gazilla.mihail.gazillaj.ui.main.stock.StockMusic;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.gazilla.mihail.gazillaj.R;
import com.gazilla.mihail.gazillaj.presentation.main.stock.StockMusic.StockMusicPresenter;
import com.gazilla.mihail.gazillaj.presentation.main.stock.StockMusic.StockMusicView;
import com.gazilla.mihail.gazillaj.ui.main.stock.StockMusic.adapter.SongsAdapter;
import com.gazilla.mihail.gazillaj.utils.AppDialogs;
import com.gazilla.mihail.gazillaj.utils.POJO.Song;

import java.util.List;

public class NewMySongActivity extends AppCompatActivity implements StockMusicView {

    private ListView lvOllSong;
    private SongsAdapter songsAdapter;
    private StockMusicPresenter musicPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_my_song);

        if (musicPresenter==null)
            musicPresenter = new StockMusicPresenter(this);

        lvOllSong = findViewById(R.id.lvOllSong);

        musicPresenter.getOllSongForNextSong();

        lvOllSong.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Song song = songsAdapter.getItem(position);
                musicPresenter.sendMyNextSong(song);
            }
        });
    }

    @Override
    public void setPlayList(List<Song> song) {
        songsAdapter = new SongsAdapter(this, song);
        lvOllSong.setAdapter(songsAdapter);
    }

    @Override
    public void showErrorMes(String error) {

        new AppDialogs().warningDialog(this, error);
    }

    @Override
    public void backToPlaylist() {
        onBackPressed();
    }
}
