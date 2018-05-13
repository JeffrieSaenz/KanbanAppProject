package com.example.user.kanbanapp;

/**
 * Created by User on 10/04/2018.
 */

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

public class TareaAdapter extends ArrayAdapter<Tarea> {
    private final Context context;
    private ArrayList<Tarea> data;
    private final int layoutResourceId;
    private Integer posPestana;
    private DatosVentanas dv;
    private int rec = 0;
    private static final int READ_REQUEST_CODE = 42;
    Context mContext;


    public TareaAdapter(Context context, int layoutResourceId, ArrayList<Tarea> data, Integer pos) {
        super(context, layoutResourceId, data);
        this.context = context;
        this.data = data;
        this.layoutResourceId = layoutResourceId;
        this.posPestana = pos;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        mContext = parent.getContext();
        ViewHolder holder = null;
        dv = DatosVentanas.getInstance();
        if (row == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
            holder = new ViewHolder();
            holder.textView1 = (TextView) row.findViewById(R.id.nombre);
            holder.textView2 = (TextView) row.findViewById(R.id.descripcion);
            holder.borrar = (ImageButton) row.findViewById(R.id.eliminarTarea);
            holder.borrar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    System.out.println("pos en listView: " + position + ", posicion en pestanas: " + posPestana);
                    //System.out.println("adap: "+data.size()+", global: "+ dv.getBacklog().get(posPestana).size());
                    data.remove(position);
                    dv.getTab(posPestana).getTareas().remove(position);
                    DatosVentanas dv = DatosVentanas.getInstance();
                    ArrayList<Tab> tbs = dv.getBacklog();
                    FbConnection conn = FbConnection.getInstance();
                    if (tbs.size() > 0)
                        for (Tab t : tbs)
                            conn.addTabs(t);
                    //dv.getBacklog().get(posPestana).remove(position);
                    notifyDataSetChanged();
                }
            });

            row.setTag(holder);
        } else {
            holder = (ViewHolder) row.getTag();
        }

        Tarea person = data.get(position);

        holder.textView1.setText(person.getNombre());
        holder.textView2.setText(person.getDescripcion());

        //holder.mimageview = (ImageView) row.findViewById(R.id.mimageView);
        holder.mimageview = (ImageView) row.findViewById(R.id.me);
        try {
            holder.mimageview.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {


                    switch (v.getId()) {
                        case R.id.me:

                            PopupMenu popup = new PopupMenu(getContext().getApplicationContext(), v);
                            popup.getMenuInflater().inflate(R.menu.itemenu,
                                    popup.getMenu());
                            popup.show();
                            popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                                @Override
                                public boolean onMenuItemClick(MenuItem item) {

                                    switch (item.getItemId()) {
                                        case R.id.cam:
                                            /* AQUI ES DONDE HAY QUE PASARLO */
                                            checkPermission();
                                            /*
                                            String path = Environment.getExternalStorageDirectory() + File.separator + "fotos" +
                                                File.separator + "pic.jpg";
                                            File imagesFolder = new File(
                                                    Environment.getExternalStorageDirectory(), "fotos");
                                            imagesFolder.mkdirs();
                                            File fileImage = new File(path);*/
                                            //Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                            //intent.putExtra(MediaStore.EXTRA_OUTPUT,Uri.fromFile(fileImage));
                                            //intent.putExtra("pos",position);
                                            //((Activity) context).startActivityForResult(intent,3434);
                                            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                            if (takePictureIntent.resolveActivity(((Activity) context).getPackageManager()) != null) {
                                                ((Activity) context).startActivityForResult(takePictureIntent, 3434);
                                            }

                                            break;
                                        case R.id.viewTarea:

                                            Intent intento = new Intent(((Activity) context).getApplicationContext(), TareaInfo.class);
                                            intento.putExtra("pos", position);
                                            intento.putExtra("name", dv.getTab(posPestana).getTareas().get(position).getNombre());
                                            intento.putExtra("tab", posPestana);
                                            ((Activity) context).startActivity(intento);

                                            break;
                                        case R.id.filechooser:
                                            filechooserEvent(position);
                                            break;
                                        default:
                                            break;
                                    }

                                    return true;
                                }
                            });

                            break;

                        default:
                            break;
                    }


                }
            });

        } catch (Exception e) {

            e.printStackTrace();
        }

        return row;
    }


    @TargetApi(Build.VERSION_CODES.M)
    private void checkPermission() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            int permission = ((Activity) context).checkSelfPermission("Manifest.permission.CAMERA");
            permission += ((Activity) context).checkSelfPermission("Manifest.permission.CAMERA");
            if (permission != 0) {
                ((Activity) context).requestPermissions(new String[]{Manifest.permission.CAMERA}, 1001);
            }
        }

    }



    static class ViewHolder {
        TextView textView1;
        TextView textView2;
        ImageButton borrar;
        ImageView record;
        ImageView tareaInfo;
        ImageView mimageview;

    }

    public void filechooserEvent(int position){
        Intent intent = new Intent()
                .setType("*/*")
                .setAction(Intent.ACTION_GET_CONTENT);
        intent.putExtra("pos",position);
        ((Activity) context).startActivityForResult(Intent.createChooser(intent, "Buscar un archivo"), 123);

    }
}
