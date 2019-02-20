package com.gazilla.mihail.gazillaj.ui.main.stock.StockMusic;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.ListView;

import com.gazilla.mihail.gazillaj.R;
import com.gazilla.mihail.gazillaj.presentation.main.stock.StockMusic.StockMusicPresenter;
import com.gazilla.mihail.gazillaj.presentation.main.stock.StockMusic.StockMusicView;
import com.gazilla.mihail.gazillaj.ui.main.stock.StockMusic.adapter.SongsAdapter;
import com.gazilla.mihail.gazillaj.utils.AppDialogs;
import com.gazilla.mihail.gazillaj.utils.POJO.Song;

import java.util.List;

public class StockMusicActivity extends AppCompatActivity implements StockMusicView {

    private StockMusicPresenter musicPresenter;
    private SongsAdapter songsAdapter;
    private ListView lvPlayList;
    private Button btMyNextSong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock_music);

        if (musicPresenter==null)
            musicPresenter = new StockMusicPresenter(this);

        lvPlayList = findViewById(R.id.lvPlayListPromoMusic);
        btMyNextSong = findViewById(R.id.btMyNextSong);

        musicPresenter.getPlayList();

        btMyNextSong.setOnClickListener(v -> {
            if (musicPresenter.getPlaying()){
                Intent intent = new Intent(StockMusicActivity.this, NewMySongActivity.class);
                startActivity(intent);
            }
            else
                new AppDialogs().warningDialog(this, "Плеер сейчас выключен");
        });
    }

    @Override
    public void setPlayList(List<Song> song) {
        songsAdapter = new SongsAdapter(this, song);
        lvPlayList.setAdapter(songsAdapter);
    }

    @Override
    public void showErrorMes(String error) {
        new AppDialogs().warningDialog(this, error);
    }

    @Override
    public void backToPlaylist() {

    }


    @Override
    protected void onResume() {
        super.onResume();
        musicPresenter.getPlayList();
    }
}
