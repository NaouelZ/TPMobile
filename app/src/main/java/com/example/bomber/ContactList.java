package com.example.bomber;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.provider.ContactsContract;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

import static android.Manifest.permission.READ_PHONE_NUMBERS;
import static android.Manifest.permission.READ_PHONE_STATE;
import static android.Manifest.permission.READ_SMS;
import static androidx.constraintlayout.widget.Constraints.TAG;
import static java.lang.Integer.parseInt;


/**
 * A simple {@link Fragment} subclass.
 */
public class ContactList extends Fragment {

    ListView listView;
    ArrayList<String> arrayList = new ArrayList<>();
    public static ArrayList<String> arrayPhone = new ArrayList<>();

    public ContactList() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate( R.layout.fragment_contact_list, container, false );
        listView = view.findViewById( R.id.list_view );

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && getContext().checkSelfPermission( Manifest.permission.READ_CONTACTS ) != PackageManager.PERMISSION_GRANTED) {
            Log.w( "permission", "non accordé" );
            requestPermissions( new String[]{Manifest.permission.READ_CONTACTS}, 1 );
        } else {
            Log.w( "permission", " EST accordé" );
            getContact();
        }

        ArrayAdapter arrayAdapter = new ArrayAdapter( getActivity(), android.R.layout.simple_list_item_1, arrayList );
        listView.setAdapter( arrayAdapter );

        return view;
    }

    private void getContact() {
        Cursor cursor = getContext().getContentResolver().query( ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null
                , null, null, null );
        while (cursor.moveToNext()) {
            String name = cursor.getString( cursor.getColumnIndex( ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME ) );
            String mobile = cursor.getString( cursor.getColumnIndex( ContactsContract.CommonDataKinds.Phone.NUMBER ) );

            String info = name + "\n tel : " + mobile;

            arrayList.add( info );
            arrayPhone.add(mobile);
        }
    }

    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        if (requestCode == 1) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //Permisssion accordée
                getContact();
            }
        }
    }
}