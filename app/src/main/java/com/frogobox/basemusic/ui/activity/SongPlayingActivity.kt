package com.frogobox.basemusic.ui.activity

import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import com.frogobox.basemusic.R
import com.frogobox.basemusic.base.admob.BaseAdmobActivity
import com.frogobox.basemusic.model.Song
import com.frogobox.basemusic.util.ConstHelper.Extra.EXTRA_SONG
import com.frogobox.basemusic.util.RawDataHelper
import kotlinx.android.synthetic.main.activity_song_playing.*

class SongPlayingActivity : BaseAdmobActivity() {

    private var mMediaPlayer: MediaPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_song_playing)
        setupDetailActivity("")
        setupComponentView()
        setupShowAdsBanner(ads_banner)
    }


    private fun arraySongData(lyric: Int): String {
        val lyricArrayString = RawDataHelper()
            .fetchData(this, lyric)
        var lyrics = ""
        for (i in lyricArrayString) {
            lyrics = lyrics + i + "\n"
        }
        return lyrics
    }

    private fun setupComponentView() {
        val extraSong = baseGetExtraData<Song>(EXTRA_SONG)

        seekBar.isEnabled = false
        song_name.text = extraSong.songName
        tv_lyrics.text = arraySongData(extraSong.songLyric)
        setupButton(extraSong.songMusic)
    }

    private fun setupButton(song: Int) {
        play.setOnClickListener { playSong(song) }
        stop.setOnClickListener { stopSong() }
        pause.setOnClickListener { pauseSong() }

        seekBar.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            var seeked_progess = 0
            override fun onProgressChanged(seekBar: SeekBar, i: Int, b: Boolean) {
                seeked_progess = i
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {}
            override fun onStopTrackingTouch(seekBar: SeekBar) {
                mMediaPlayer!!.seekTo(seeked_progess * 1000 % mMediaPlayer!!.duration)
            }
        })

    }

    private fun playSong(songMusic: Int) {
        seekBar.isEnabled = true
        if (mMediaPlayer == null) {
            mMediaPlayer = MediaPlayer.create(this, songMusic)
            mMediaPlayer!!.start()
            seekBar.max = mMediaPlayer!!.duration / 1000
        } else {
            mMediaPlayer!!.start()
            seekBar.max = mMediaPlayer!!.duration / 1000
        }
        runOnUiThread(
            object : Runnable {
                override fun run() {
                    if (mMediaPlayer != null) {
                        val mCurrentPosition = mMediaPlayer!!.currentPosition / 1000
                        seekBar.progress = mCurrentPosition
                    }
                    Handler().postDelayed(this, 1000);
                }
            }
        )
    }


    private fun pauseSong() {
        seekBar.isEnabled = false
        if (mMediaPlayer != null) {
            mMediaPlayer!!.pause()
        }
        setupShowAdsInterstitial()
    }

    private fun stopSong() {
        seekBar.isEnabled = false
        seekBar.progress = 0
        if (mMediaPlayer != null) {
            mMediaPlayer!!.stop()
            releaseMediaPlayer()
        }
        setupShowAdsInterstitial()
    }

    private fun releaseMediaPlayer() {
        if (mMediaPlayer != null) {
            mMediaPlayer!!.release()
            mMediaPlayer = null
        }
    }

    override fun onBackPressed() {
        releaseMediaPlayer()
        finish()
    }

}