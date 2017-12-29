package Controller;

import javafx.beans.binding.BooleanBinding;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

public class IsMediaPlayingBinding extends BooleanBinding{

    private final MediaView mediaView;

    public IsMediaPlayingBinding(MediaView mediaView) {
        this.mediaView = mediaView;
    }

    @Override
    protected boolean computeValue() {
        //if (mediaView.getMediaPlayer() != null) System.out.println(mediaView.getMediaPlayer().getStatus());
        return mediaView.getMediaPlayer() != null && mediaView.getMediaPlayer().getStatus() == MediaPlayer.Status.PLAYING;
    }
}
