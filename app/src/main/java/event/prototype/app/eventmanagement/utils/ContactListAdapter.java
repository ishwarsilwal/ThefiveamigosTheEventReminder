package event.prototype.app.eventmanagement.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filterable;
import android.widget.TextView;
import event.prototype.app.eventmanagement.R;
import event.prototype.app.eventmanagement.addevent.AddEventActivity;

public class ContactListAdapter extends CursorAdapter implements Filterable {
    private ContentResolver mCR;

    public ContactListAdapter(Context context, Cursor c, boolean a) {
        super(context, c, true);
        mCR = context.getContentResolver();
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        TextView primaryHeading =  view.findViewById(R.id.primaryHeading);
        TextView secondaryHeading =  view.findViewById(R.id.secondaryHeading);

        primaryHeading.setText(cursor.getString(1));
        secondaryHeading.setText(cursor.getString(2));
    }


    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        final LayoutInflater inflater = LayoutInflater.from(context);
        return inflater.inflate( R.layout.search_phone_row, parent, false);
    }

    @Override
    public String convertToString(Cursor cursor) {
        return cursor.getString(2);
    }

    public Cursor runQueryOnBackgroundThread(CharSequence constraint) {
        if (getFilterQueryProvider() != null) {
            return getFilterQueryProvider().runQuery(constraint);
        }

        StringBuilder buffer = null;
        String[] args = null;
        if (constraint != null) {
            buffer = new StringBuilder();
            buffer.append("UPPER(");
            buffer.append(ContactsContract.CommonDataKinds.Phone.NUMBER);
            buffer.append(") GLOB ?");
            args = new String[] { constraint.toString().toUpperCase() + "*" };
        }

        return mCR.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, AddEventActivity.Companion.getPROJECTION() ,buffer == null ? null : buffer.toString(), args,
                null);
    }

}

