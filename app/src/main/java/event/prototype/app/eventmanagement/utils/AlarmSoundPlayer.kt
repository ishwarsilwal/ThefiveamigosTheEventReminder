package event.prototype.app.eventmanagement.utils


import android.content.Context
import android.media.MediaPlayer

class AlarmSoundPlayer {

    private lateinit var  mContext: Context
    private var mMediaPlayer: MediaPlayer? = null

    /*
     * not allowing to call default constructor so private
     */
    private constructor() {

    }

    constructor(soundTrack: Int, mContext: Context) : this(soundTrack, mContext, null) {}

    /*
     * not allowing to call this constructor so private
     */
    private constructor(soundTrack: Int, mContext: Context, mMediaPlayer: MediaPlayer?) {
        this.mContext = mContext
        if (mMediaPlayer == null) {
            this.mMediaPlayer = MediaPlayer.create(mContext, soundTrack)
            this.mMediaPlayer!!.setOnCompletionListener(TrackCompletionListener())
        }
    }

    /*
     * start playing sound
     */
    fun startPlayingSound() {
        if (!this.mMediaPlayer!!.isPlaying) {
            this.mMediaPlayer!!.start()
        }
    }

    /*
     * pause playing sound
     */
    fun pausePlayingSound() {
        if (this.mMediaPlayer!!.isPlaying) {
            this.mMediaPlayer!!.pause()
        }
    }


    /*
     * stop sound and release media player
     */
    fun stopPlayingSound() {
        if (this.mMediaPlayer != null) {
            this.mMediaPlayer!!.stop()
            this.mMediaPlayer!!.release()
        }
    }


    /*
     * Continuous playing sound after completion
     */
    private inner class TrackCompletionListener : MediaPlayer.OnCompletionListener {

        override fun onCompletion(mediaPlayer: MediaPlayer) {
            mediaPlayer.start()
        }
    }


}
