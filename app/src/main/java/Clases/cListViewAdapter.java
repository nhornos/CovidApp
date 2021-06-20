package Clases;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.respirapp.ActivityEjercicio;
import com.example.respirapp.ActivityLogin;
import com.example.respirapp.R;

import java.lang.reflect.Array;
import java.util.ArrayList;

import static android.os.Build.VERSION_CODES.R;

public class cListViewAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<cListViewColumns> arrayEjercicios;
    private ListView listView;

    public cListViewAdapter(Context context, ArrayList<cListViewColumns> arrayEjercicios, ListView listView){
        this.context = context;
        this.arrayEjercicios = arrayEjercicios;
        this.listView = listView;
    }

    @Override
    public int getCount() {
        return this.arrayEjercicios.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            LayoutInflater layoutInflater = LayoutInflater.from(this.context);
            int idLayout = this.context.getResources().getIdentifier("layout_lista_ejercicios_item", "layout", this.context.getPackageName());
            convertView = layoutInflater.inflate(idLayout, parent, false);
        }
        TextView numEjercicio = (TextView) convertView.findViewById(this.context.getResources().getIdentifier("text_num_ejercicio", "id", this.context.getPackageName()));
        TextView nombreEjercicio = (TextView) convertView.findViewById(this.context.getResources().getIdentifier("text_nombre_ejercicio", "id", this.context.getPackageName()));
        TextView imagenEjercicio = (TextView) convertView.findViewById(this.context.getResources().getIdentifier("text_imagen_ejercicio", "id", this.context.getPackageName()));
        TextView explicacionEjercicio = (TextView) convertView.findViewById(this.context.getResources().getIdentifier("text_explicacion", "id", this.context.getPackageName()));
        TextView contraindicacionesEjercicio = (TextView) convertView.findViewById(this.context.getResources().getIdentifier("text_contraindicaciones", "id", this.context.getPackageName()));

        numEjercicio.setText(String.valueOf(position+1));
        nombreEjercicio.setText(this.arrayEjercicios.get(position).nombre);
        imagenEjercicio.setText(this.arrayEjercicios.get(position).imagen);
        explicacionEjercicio.setText(this.arrayEjercicios.get(position).descripcion);
        contraindicacionesEjercicio.setText(this.arrayEjercicios.get(position).info);
        int num = getCount();

        //On click en el ejercicio
        convertView.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                TextView textNombreEjercicio = (TextView) v.findViewById(context.getResources().getIdentifier("text_nombre_ejercicio", "id", context.getPackageName()));
                TextView textImagenEjercicio = (TextView) v.findViewById(context.getResources().getIdentifier("text_imagen_ejercicio", "id", context.getPackageName()));
                TextView textExplicacionEjercicio = (TextView) v.findViewById(context.getResources().getIdentifier("text_explicacion", "id", context.getPackageName()));
                TextView textContraindicacionesEjercicio = (TextView) v.findViewById(context.getResources().getIdentifier("text_contraindicaciones", "id", context.getPackageName()));
                Intent intent = new Intent(context, ActivityEjercicio.class);
                intent.putExtra("nombreEjercicio", String.valueOf(textNombreEjercicio.getText()));
                intent.putExtra("nombreImagenEjercicio", String.valueOf(textImagenEjercicio.getText()));
                intent.putExtra("explicacionEjercicio", String.valueOf(textExplicacionEjercicio.getText()));
                intent.putExtra("contraindicacionesEjercicio", String.valueOf(textContraindicacionesEjercicio.getText()));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
        return convertView;
    }

    public void pasarSiguienteEjercicio(int id){

    }
}
