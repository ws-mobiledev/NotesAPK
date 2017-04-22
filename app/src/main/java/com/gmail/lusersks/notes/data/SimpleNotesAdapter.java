package com.gmail.lusersks.notes.data;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.gmail.lusersks.notes.R;

public class SimpleNotesAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater inflater;

    public SimpleNotesAdapter(Context context) {
        this.context = context;
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return NotesData.getItems().size();
    }

    @Override
    public Object getItem(int position) {
        return NotesData.getNote(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.list_view, parent, false);
//            convertView.setTag(textView);
        }

        TextView textView = (TextView) convertView.findViewById(R.id.tv_note_title);;

        String text = (String) getItem(position);
        textView.setText(text);

        return convertView;
    }
}