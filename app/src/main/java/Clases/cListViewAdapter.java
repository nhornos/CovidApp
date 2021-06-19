package Clases;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;

import static android.os.Build.VERSION_CODES.R;

public class cListViewAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<cListViewColumns> ejercicios;
    private ListView listView;

    public cListViewAdapter(Context context, ArrayList<cListViewColumns> ejercicios, ListView listView){
        this.context = context;
        this.ejercicios = ejercicios;
        this.listView = listView;
    }

    @Override
    public int getCount() {
        return this.ejercicios.size();
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
        numEjercicio.setText(String.valueOf(position+1));
        nombreEjercicio.setText(this.ejercicios.get(position).nombre);
        return convertView;
    }
}
