package com.example.user.kanbanapp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DatosNuevaTarea.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DatosNuevaTarea#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DatosNuevaTarea extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    // UI references.
    private AutoCompleteTextView txtNombre;
    private EditText txtDescripcion;

    private View mProgressView;
    private View mLoginFormView;
    boolean editar;
    Integer posItem;
    Integer posPestana;
    private Button btnAgregar;

    private OnFragmentInteractionListener mListener;

    public DatosNuevaTarea() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment datos_nuevatarea.
     */
    // TODO: Rename and change types and number of parameters
    public static DatosNuevaTarea newInstance(String param1, String param2) {
        DatosNuevaTarea fragment = new DatosNuevaTarea();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.datos_nuevatarea, container, false);

        View view =inflater.inflate(R.layout.datos_nuevatarea, container, false);
        txtNombre = (AutoCompleteTextView) view.findViewById(R.id.nombreTarea);
        //this.setTitle("Add Item");
        btnAgregar = (Button)view.findViewById(R.id.btnAgregar);

        //populateAutoComplete();

        txtDescripcion = (EditText) view.findViewById(R.id.descripionTarea);
        Button MiButton = (Button) view.findViewById(R.id.btnAgregar);
        verificarEditar();
        OnclickDelButton(view.findViewById(R.id.btnAgregar));
        OnclickDelButton(view.findViewById(R.id.btnCancelar1));
        return view;

    }

    public void verificarEditar(){
        Intent callingIntent = getActivity().getIntent();
        posPestana = callingIntent.getIntExtra("posPestana", -1);
        posItem = callingIntent.getIntExtra("posItem", -1);
        DatosVentanas dv = DatosVentanas.getInstance();
        this.editar = posPestana != -1;
        //Mensaje(posPestana+", "+posItem+", "+editar);


        if(editar == true) {
            if(dv.getTab(posPestana).getTareas() != null) {
                txtNombre.setText(dv.getTab(posPestana).getTareas().get(posItem).getNombre());
                txtDescripcion.setText(dv.getTab(posPestana).getTareas().get(posItem).getDescripcion());
                dv.setDate(dv.getTab(posPestana).getTareas().get(posItem).getFecha());
                getActivity().setTitle(dv.getTab(posPestana).getTareas().get(posItem).getNombre());
                btnAgregar.setText(R.string.btnActualizar);
            }

        }
    }

////////////////////////////////////////////////////////////////////////////////////////7777

    public void OnclickDelButton(View view) {
        // Ejemplo  OnclickDelButton(R.id.MiButton);

        Button miButton = (Button)  view;
        //  final String msg = miButton.getText().toString();       // 2.  Programar el evento onclick
        miButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                DatosVentanas dv = DatosVentanas.getInstance();
                Intent callingIntent = getActivity().getIntent();
                Integer pos = callingIntent.getIntExtra("pos", 0);
                Intent intento = new Intent(getContext(), Backlog.class);
                // if(msg.equals("Texto")){Mensaje("Texto en el botón ");};
                switch (v.getId()) {

                    case R.id.btnAgregar:
                        //Se agrega la tarea al Backlog
                        //Main_Content mc = (Main_Content)b.vpa.getItem(pos);
                        if(txtNombre.getText().toString().length() > 0) {
                            if (editar == false) {
                                dv.agregarTareaBacklog(
                                        new Tarea(
                                                txtNombre.getText().toString(),
                                                txtDescripcion.getText().toString(),new ArrayList<>(), new Date(ReminderNuevaTarea.cv.getDate()))
                                        , pos
                                );
                            } else {
                                if (dv.getTab(posPestana) != null) {

                                    dv.getTab(posPestana).getTareas().get(posItem).setNombre(txtNombre.getText().toString());
                                    dv.getTab(posPestana).getTareas().get(posItem).setDescripcion(txtDescripcion.getText().toString());
                                    dv.getTab(posPestana).getTareas().get(posItem).setFecha(new Date(ReminderNuevaTarea.cv.getDate()));
                                    dv.setDate(new Date(ReminderNuevaTarea.cv.getDate()));
                                }
                            }
                            startActivity(intento);
                        }else{
                            txtNombre.setError(getString(R.string.error_field_required));
                        }

                        break;

                    case R.id.btnCancelar1:
                        startActivity(intento);
                        break;

                    default:break; }// fin de casos
            }// fin del onclick
        });
    }

////////////////////////////////////////////////////////////////////////////////////////7777


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }


    public void Mensaje(String msg){
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();};
}
