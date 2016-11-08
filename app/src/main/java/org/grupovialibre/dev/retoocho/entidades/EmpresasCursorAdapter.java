package org.grupovialibre.dev.retoocho.entidades;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import org.grupovialibre.dev.retoocho.R;

/**
 * Created by joan on 7/11/16.
 */

public class EmpresasCursorAdapter extends CursorAdapter {

    public EmpresasCursorAdapter(Context context, Cursor c) {
        super(context, c, 0);
    }
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        LayoutInflater inflater = LayoutInflater.from(context);
        return inflater.inflate(R.layout.list_item_empresa, viewGroup, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        // Referencias UI.
        TextView nameText = (TextView) view.findViewById(R.id.tv_name);

        // Get valores.
        String name = cursor.getString(cursor.getColumnIndex(ConstantesDB.EMPRE_NOMBRE));
        nameText.setText(name);
        //String avatarUri = cursor.getString(cursor.getColumnIndex(LawyerEntry.AVATAR_URI));
    }
}
