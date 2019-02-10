package com.gazilla.mihail.gazillaj.ui.main.stock.StockMusic;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;

import com.gazilla.mihail.gazillaj.R;
import com.gazilla.mihail.gazillaj.presentation.main.stock.StockMusic.StockMusicPresenter;
import com.gazilla.mihail.gazillaj.presentation.main.stock.StockMusic.StockMusicView;
import com.gazilla.mihail.gazillaj.ui.main.stock.StockMusic.adapter.SongsAdapter;
import com.gazilla.mihail.gazillaj.utils.POJO.Song;

import java.util.List;

public class StockMusicActivity extends AppCompatActivity implements StockMusicView {

    private StockMusicPresenter musicPresenter;
    private SongsAdapter songsAdapter;
    private ListView lvPlayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock_music);

        if (musicPresenter==null)
            musicPresenter = new StockMusicPresenter(this);

        lvPlayList = findViewById(R.id.lvPlayListPromoMusic);

        musicPresenter.getSong();

    }

    @Override
    public void setPlayList(List<Song> song) {
        songsAdapter = new SongsAdapter(this, song);
        lvPlayList.setAdapter(songsAdapter);
    }
}
