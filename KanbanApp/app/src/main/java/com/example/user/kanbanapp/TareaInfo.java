package com.example.user.kanbanapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;

public class TareaInfo extends AppCompatActivity {

    public ViewPagerAdapter vpa;
    public ViewPager viewPager;
    private ArrayList<Tab> tbs = new ArrayList<>();
    ArrayList<File> images = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tarea_info);
        //      viewPager = (ViewPager) findViewById(R.id.cont);
        //    initPagerAdapter();
//        viewPager.setAdapter(vpa);
        readTabs();
        //addImages();
        //readImages();

        // alambramos el ImageView
        ImageView MiImageView = (ImageView) findViewById(R.id.imagen);
        //Programamos el evento onclick
    }

    public void initPagerAdapter() {
        vpa = new ViewPagerAdapter(getSupportFragmentManager());
    }

    public void readTabs() {
        Intent callingIntent = getIntent();
        //int tab = callingIntent.getIntExtra("tab", 1);
        //int tar = callingIntent.getIntExtra("pos", 1);
        tbs = new ArrayList<>();
        ViewPagerAdapter vpa_db = new ViewPagerAdapter(getSupportFragmentManager());
        DatabaseReference mtabs = FirebaseDatabase.getInstance().getReference(FirebaseAuth.getInstance().getCurrentUser().getEmail().split("@")[0]);
        mtabs.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (!dataSnapshot.hasChildren())
                    findViewById(R.id.loadingPanel).setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        ChildEventListener childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {
                Tab t = dataSnapshot.getValue(Tab.class);
                Intent callingIntent = getIntent();
                int posTarea = callingIntent.getIntExtra("pos", 1);
                int posTab = callingIntent.getIntExtra("tab", 1);
                if (t.getPos() == posTab) {
                    ArrayList<Tarea> tareas = t.getTareas();
                    if (tareas.get(posTarea) != null) {
                        ArrayList<File> imgs = tareas.get(posTarea).getListFiles();
                        for (int i = 0; i < imgs.size(); i++) {
                            File name = tareas.get(posTarea).getListFiles().get(i);
                            images.add(name);
                        }
                        addImages();
                    }
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String previousChildName) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                //Mensaje("Remove");
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String previousChildName) {
                //Mensaje("Moved");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                //Mensaje("Cancelled");
            }
        };
        mtabs.addChildEventListener(childEventListener);

    }

    public void addImages() {
        ArrayAdapter<File> adapter = new MyListAdapter();
        ListView list = (ListView) findViewById(R.id.listViewImage);
        list.setAdapter(adapter);
    }

    public void MensajeOK(String msg) {
        View v1 = getWindow().getDecorView().getRootView();
        AlertDialog.Builder builder1 = new AlertDialog.Builder(v1.getContext());
        builder1.setMessage(msg);
        builder1.setCancelable(true);
        builder1.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });
        AlertDialog alert11 = builder1.create();
        alert11.show();
        ;
    }

    ;

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap bitmap = null;
            try {
                URL url = new URL(urls[0]);
                bitmap = BitmapFactory.decodeStream((InputStream) url.getContent());
            } catch (IOException e) {
                //Log.e(TAG, e.getMessage());
            }
            return bitmap;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }

    private class MyListAdapter extends ArrayAdapter<File> {
        public MyListAdapter() {
            super(TareaInfo.this, R.layout.image, images);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // Make sure we have a view to work with (may have been given null)
            View itemView = convertView;
            if (itemView == null) {
                itemView = getLayoutInflater().inflate(R.layout.image, parent, false);
            }
            File ObjetoActual = images.get(position);
            // Fill the view
            ImageView im = (ImageView) itemView.findViewById(R.id.imagen);

            final URL imageUrl;
            HttpURLConnection conn = null;
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            try {
                imageUrl = new URL(ObjetoActual.getLink());
                conn = (HttpURLConnection) imageUrl.openConnection();
                conn.connect();
                Bitmap imagen = BitmapFactory.decodeStream(conn.getInputStream());
                im.setImageBitmap(imagen);
                // alambramos el ImageView

                // alambramos el ImageView
                //ImageView MiImageView = (ImageView) findViewById(R.id.btnNombreControl);

                //Programamos el evento onclick

                im.setOnClickListener(new View.OnClickListener(){

                    @Override

                    public void onClick(View arg0) {
                        // escriba lo que desea hacer
                        /*String query = imageUrl.getQuery();
                        Intent intento = new Intent(getApplicationContext(), ViewFile.class);
                        intento.putExtra("url", ObjetoActual.getLink());
                        //intento.putExtra("hijos", 3);
                        startActivity(intento);*/
                        Intent intent = new Intent(Intent.ACTION_VIEW);

                        intent.setDataAndType(Uri.parse( ObjetoActual.getLink()), "text/html");

                        startActivity(intent);
                    }

                });
            } catch (IOException e) {

                e.printStackTrace();
            }
            TextView elatributo01 = (TextView) itemView.findViewById(R.id.name);
            elatributo01.setText(ObjetoActual.getName());
            return itemView;
        }

    }

}
