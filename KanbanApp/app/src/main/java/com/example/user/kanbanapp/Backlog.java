package com.example.user.kanbanapp;

import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.NotificationCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import junit.framework.Test;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Backlog extends AppCompatActivity  implements NavigationView.OnNavigationItemSelectedListener, GoogleApiClient.OnConnectionFailedListener {

    /*
        TareaAdapter adapter;
        EditText editText;
        EditText editText2;
        List<Tarea> itemList;
        DatosVentanas vd;
    */
    DatosVentanas vd;

    public ViewPagerAdapter vpa;
    public ViewPager viewPager;
    public FbConnection conn;
    private ArrayList<Tab> tbs = new ArrayList<>();
    private ViewPagerAdapter vpa_aux;
    static EditText nombreImagen;


    private GoogleApiClient googleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_backlog);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



/*Nuevo*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);




        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();


        googleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this,this)
                .addApi(Auth.GOOGLE_SIGN_IN_API,gso).build();

        /*NUEVO*/


        viewPager = (ViewPager) findViewById(R.id.cont);
        initPagerAdapter();
        //viewPager = new ViewPager(this);

        //mViewPager.setAdapter(mSectionsPagerAdapter);
        //addFIni();
        viewPager.setAdapter(vpa);
        //verificar();
        findViewById(R.id.loadingPanel).setVisibility(View.VISIBLE);
        readTabs();

        //Mensaje("Regrese");

        //this.setTitle(vpa.getPageTitle(viewPager.getCurrentItem()));


        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                setTitle(vpa.getPageTitle(position));
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }

        });



    }

    @Override
    protected void onStart() {
        super.onStart();
        vd = DatosVentanas.getInstance();
TextView txt = (TextView) findViewById(R.id.nombreUser);
        Intent callingIntent = getIntent();
        //txt.setText(callingIntent.getStringExtra("user"));
        //txt2.setText(callingIntent.getStringExtra("email"));
        //Nuevo
        OptionalPendingResult<GoogleSignInResult> opr = Auth.GoogleSignInApi.silentSignIn(googleApiClient);
    if(opr.isDone()){
    //GoogleSignInResult result = opr.get();
    //handleSignInResult(result);

    }else{
    opr.setResultCallback(new ResultCallback<GoogleSignInResult>() {
    @Override
    public void onResult(@NonNull GoogleSignInResult googleSignInResult) {
        handleSignInResult(googleSignInResult);
    }
});
}
        updateTabs();

        Intent intent = new Intent(this, HelloIntentService.class);
        startService(intent);
    }

    private void handleSignInResult(GoogleSignInResult result) {
if(result.isSuccess()){

    TextView txt = (TextView)findViewById(R.id.nombreUser);
    TextView txt2 = (TextView)findViewById(R.id.correo);
    GoogleSignInAccount g = result.getSignInAccount();
    //Mensaje(g.getDisplayName()+", "+g.getEmail());
    txt.setText(g.getDisplayName());
    txt2.setText(g.getEmail());
}else{
    Mensaje("No entró success");
}
    }


    //Nuevo
    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
    //Nuevo


    @Override
    protected void onRestart() {
        super.onRestart();
        // addFragment();
        //addFIni();
        //Mensaje("Pase por onRestart");
    }

    @Override
    protected void onResume() {
        super.onResume();

        vd = DatosVentanas.getInstance();
        TextView txt = (TextView)findViewById(R.id.nombreUser);
        TextView txt2 = (TextView)findViewById(R.id.correo);
        if(txt != null) {
            txt.setText(vd.getUserlogged().getNombre());
            txt2.setText(vd.getUserlogged().getCorreo());
        }else{
            Mensaje("nulllll");
        }

    }

    public void Mensaje(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }

    Intent intento = new Intent();

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.header_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.add_tab:
                Mensaje("Primero");
                addNewTab();
                break;

            case R.id.editTab:
                if (vpa.getCount() == 0)
                    Mensaje("Please, create a new Tab");
                else
                    editTab();
                break;


            case R.id.video:
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse("https://youtu.be/CLgT_eRJbzM"));
                startActivity(i);
                //Intent intento = new Intent(getApplicationContext(), IngresoNuevaTarea.class);
                //startActivity(intento);


                break;


            default:
                Mensaje("No clasificado");
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void initPagerAdapter() {
        vpa = new ViewPagerAdapter(getSupportFragmentManager());
    }

    public void addFragment() {
        vpa.addFragments(new Main_Content(), "New one");
        vpa.notifyDataSetChanged();
        //viewPager.setAdapter(vpa);
    }

    public void addFIni() {
       /* conn = FbConnection.getInstance();
        //vd = DatosVentanas.getInstance();
        //vd.agregaInicial();

        //vpa.addFragments(new Main_Content(), "New one");
        Main_Content mc = new Main_Content();
        mc.setPosicion(0);
        vpa.addFragments(mc, "New Tab");
        ArrayList<Tarea> a = new ArrayList<Tarea>();
        a.add(new Tarea("MAMA", "aaa"));
        Tab t = new Tab("New Tab", 0, a);
        conn.addTabs(t);
        vpa.notifyDataSetChanged();
        //viewPager.setAdapter(vpa);*/
    }

    public void verificar() {
        conn = FbConnection.getInstance();
        //conn.readTabs();

        ArrayList<Tab> tbs = conn.getTabs();
        Mensaje("TABS:" + tbs.size());

        if (tbs.isEmpty())
            addFIni();
        else {
            for (Tab t : tbs) {
                //addFragment();
                Main_Content mc = new Main_Content();
                mc.setPosicion(t.getPos());
                mc.setTareas(t.getTareas());
                vpa.addFragments(mc, t.getTitle());
            }
        }
        vpa.notifyDataSetChanged();
    }

    public void editTab() {
        final EditText txtUrl = new EditText(this);

        // Set the default text to a link of the Queen
        //txtUrl.setHint("http://www.librarising.com/astrology/celebs/images2/QR/queenelizabethii.jpg");

        new AlertDialog.Builder(this)
                .setTitle("Edit tab")
                .setMessage(" Name ")
                .setView(txtUrl)
                .setPositiveButton("Accept", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        String url = txtUrl.getText().toString();
                        //vd.agregaInicial();
                        DatosVentanas vd = DatosVentanas.getInstance();

                        Tab t = new Tab(url, viewPager.getCurrentItem(), vd.getTab(viewPager.getCurrentItem()).getTareas());
                        conn.addTabs(t);
                        setTitle(t.getTitle());


                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                    }
                })
                .show();
        //vd.agregaInicial();
        //vpa.addFragments(new Main_Content(), "New one");
        //vpa.notifyDataSetChanged();
        //viewPager.setAdapter(vpa);
    }


    public void addNewTab() {
        final EditText txtUrl = new EditText(this);

        // Set the default text to a link of the Queen
        //txtUrl.setHint("http://www.librarising.com/astrology/celebs/images2/QR/queenelizabethii.jpg");

        new AlertDialog.Builder(this)
                .setTitle("Add new tab")
                .setMessage(" Name ")
                .setView(txtUrl)
                .setPositiveButton("Crear", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        String url = txtUrl.getText().toString();
                        //vd.agregaInicial();
                        Main_Content mc = new Main_Content();
                        vpa = (ViewPagerAdapter) viewPager.getAdapter();
                        mc.setPosicion(vpa.getCount());
                        Tab t = new Tab(url, vpa.getCount(), new ArrayList<Tarea>());
                        conn.addTabs(t);
                        viewPager.setCurrentItem(vpa.getCount() + 1);
                        setTitle(t.getTitle());
                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                    }
                })
                .show();
        //vd.agregaInicial();
        //vpa.addFragments(new Main_Content(), "New one");
        //vpa.notifyDataSetChanged();
        //viewPager.setAdapter(vpa);
    }

    public void readTabs() {
        tbs = new ArrayList<>();
        ViewPagerAdapter vpa_db = new ViewPagerAdapter(getSupportFragmentManager());
        DatosVentanas dv = DatosVentanas.getInstance();
        DatabaseReference mtabs = FirebaseDatabase.getInstance().getReference(dv.getUserlogged().getCorreo().split("@")[0]);
        mtabs.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (!dataSnapshot.hasChildren()) {
                    findViewById(R.id.loadingPanel).setVisibility(View.GONE);
                    mostrarDatosDeUsuario();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        ChildEventListener childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {

                Tab tab = dataSnapshot.getValue(Tab.class);
                //Mensaje("TAB: " + tab.getTitle());
                tbs.add(tab);
                Main_Content mc = new Main_Content();
                mc.setPosicion(tab.getPos());
                mc.setTareas(tab.getTareas());
                vpa.addFragments(mc, tab.getTitle());
                dv.getBacklog().add(tab);
                vpa.notifyDataSetChanged();
                if (tab.getPos() == 0)
                    setTitle(tab.getTitle());
                findViewById(R.id.loadingPanel).setVisibility(View.GONE);
                TextView txt = (TextView)findViewById(R.id.nombreUser);
                TextView txt2 = (TextView)findViewById(R.id.correo);
                mostrarDatosDeUsuario();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String previousChildName) {
                //Mensaje("Change");
                DatosVentanas dv = DatosVentanas.getInstance();
                ArrayList<Tab> tbs = dv.getBacklog();
                vpa.getItem(viewPager.getCurrentItem());
                vpa.notifyDataSetChanged();
                Tab t = dataSnapshot.getValue(Tab.class);
                vpa.setItem(t);
                vpa.notifyDataSetChanged();
                ((Main_Content) vpa.getItem(t.getPos())).verificarParaInsertar();

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

    public void updateTabs() {
        DatosVentanas dv = DatosVentanas.getInstance();
        ArrayList<Tab> tbs = dv.getBacklog();
        conn = FbConnection.getInstance();
        if (tbs.size() > 0)
            for (Tab t : tbs) {
                conn.addTabs(t);
                vpa.notifyDataSetChanged();
            }
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 3434 && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            uploadFiles(getImageUri(this.getBaseContext(),imageBitmap),data.getIntExtra("pos",0));
        }

        if (requestCode == 123 && resultCode == Activity.RESULT_OK) {
            // The document selected by the user won't be returned in the intent.
            // Instead, a URI to that document will be contained in the return intent
            // provided to this method as a parameter.
            // Pull that URI using resultData.getData().
            Uri uri = null;
            if (data != null) {
                uri = data.getData();
                Log.i("Get Files", "Uri: " + uri.toString());
                uploadFiles(uri,0);
                Mensaje("Concluido....");
            }
        }
    }

    private void uploadFiles(Uri uri,Integer p){
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();
        String type = getContentResolver().getType(uri).split("/")[1];
        // Mensaje("files/archivo"+type);
        Cursor returnCursor =
                getContentResolver().query(uri, null, null, null, null);
        int nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
        returnCursor.moveToFirst();
        String name = returnCursor.getString(nameIndex);
        String path = String.format("files/%s",name);
        StorageReference fileRef = storageRef.child(path);
        Uri file = uri;
        fileRef.putFile(file)
            .addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
                Mensaje(exception.getMessage());
            }})

            .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                //Uri downloadUrl = taskSnapshot.getDownloadUrl();
                Uri downloadUrl = taskSnapshot.getDownloadUrl();
                int x = viewPager.getCurrentItem();
                tbs.get(x).getTareas().get(p).getImagenes().add(new Image(name,downloadUrl.toString()));
                updateTabs();

                Mensaje("SUCCESS");

            }
        });
    }

    private Bitmap getBitmapFromUri(Uri uri) throws IOException {
        ParcelFileDescriptor parcelFileDescriptor =
                getContentResolver().openFileDescriptor(uri, "r");
        FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
        Bitmap image = BitmapFactory.decodeFileDescriptor(fileDescriptor);
        parcelFileDescriptor.close();
        return image;

    }


    public void dumpImageMetaData(Uri uri) {

        // The query, since it only applies to a single document, will only return
        // one row. There's no need to filter, sort, or select fields, since we want
        // all fields for one document.
        Cursor cursor = this.getContentResolver()
                .query(uri, null, null, null, null, null);

        try {
            // moveToFirst() returns false if the cursor has 0 rows.  Very handy for
            // "if there's anything to look at, look at it" conditionals.
            if (cursor != null && cursor.moveToFirst()) {

                // Note it's called "Display Name".  This is
                // provider-specific, and might not necessarily be the file name.
                String displayName = cursor.getString(
                        cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                Log.i("", "Display Name: " + displayName);

                int sizeIndex = cursor.getColumnIndex(OpenableColumns.SIZE);
                // If the size is unknown, the value stored is null.  But since an
                // int can't be null in Java, the behavior is implementation-specific,
                // which is just a fancy term for "unpredictable".  So as
                // a rule, check if it's null before assigning to an int.  This will
                // happen often:  The storage API allows for remote files, whose
                // size might not be locally known.
                String size = null;
                if (!cursor.isNull(sizeIndex)) {
                    // Technically the column stores an int, but cursor.getString()
                    // will do the conversion automatically.
                    size = cursor.getString(sizeIndex);
                } else {
                    size = "Unknown";
                }
                Log.i("", "Size: " + size);
            }
        } finally {
            cursor.close();
        }
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

//_-------------------------------------------------------------Nuevo


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {

        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void mostrarDatosDeUsuario(){
        TextView labelNombre = (TextView)findViewById(R.id.nombreUser);
        TextView labelCorreo = (TextView)findViewById(R.id.correo);
        if(labelNombre != null){
            labelNombre.setText(vd.getUserlogged().getNombre());
            labelCorreo.setText(vd.getUserlogged().getCorreo());
        }
    }

}
