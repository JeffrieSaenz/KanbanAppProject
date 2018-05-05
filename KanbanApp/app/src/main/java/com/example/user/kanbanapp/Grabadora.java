package com.example.user.kanbanapp;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Environment;

import java.io.IOException;

public class Grabadora {
    private MediaRecorder myRecorder;
    private MediaPlayer myPlayer;
    private String NombreArchivo = null;

    public String getNombreArchivo() {
        return NombreArchivo;
    }

    public void setNombreArchivo(String NombreArchivo) {
        this.NombreArchivo = NombreArchivo;
    }

    public Grabadora(String nombre){
        NombreArchivo = Environment.getExternalStorageDirectory().
                getAbsolutePath() +"/"+ nombre+".3gpp";

    }

    public void Grabar( ){
        myRecorder = new MediaRecorder();
        myRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        myRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        myRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
        myRecorder.setOutputFile(NombreArchivo);
        try {
            myRecorder.prepare();
            myRecorder.start();
        } catch (IllegalStateException e) {
            // start:it is called before prepare()
            // prepare: it is called after start() or before setOutputFormat()
            e.printStackTrace();
        } catch (IOException e) {
            // prepare() fails
            e.printStackTrace();
        }

    }

    public void PararGrabacion(){
        try {
            myRecorder.stop();
            myRecorder.release();
            myRecorder  = null;

        } catch (IllegalStateException e) {
            //  it is called before start()
            e.printStackTrace();
        } catch (RuntimeException e) {
            // no valid audio/video data has been received
            e.printStackTrace();
        }
    }

    public void ReproducirGrabacion( ) {
        try{
            myPlayer = new MediaPlayer();
            myPlayer.setDataSource(NombreArchivo);
            myPlayer.prepare();
            myPlayer.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void PararReproduccion( ) {
        try {

            if (myPlayer.isPlaying()) {
                myPlayer.release();
                myPlayer.stop();
                myPlayer.reset();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


