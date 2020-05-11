package com.example.bomber;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.telephony.SmsManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class SmsChecker extends Fragment {

    private Spinner phoneArea;
    private Spinner msgArea;
    private EditText msg;
    private Button envoi;
    private Button newMsg;
    private ArrayList<String> msgTypes= new ArrayList();

    public SmsChecker() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate( R.layout.fragment_sms_checker, container, false );

        phoneArea = view.findViewById( R.id.spinner);
        msg = view.findViewById( R.id.text_msg );
        msg.setVisibility( View.INVISIBLE );
        msgArea = view.findViewById( R.id.msg_types);
        envoi = view.findViewById( R.id.btn_envoi );
        newMsg = view.findViewById( R.id.new_msg_type );

        initTypeMsg();

        phoneArea.setAdapter( new ArrayAdapter( getActivity(), android.R.layout.simple_list_item_1, ContactList.arrayPhone ) );
        msgArea.setAdapter( new ArrayAdapter( getActivity(), android.R.layout.simple_list_item_1, msgTypes ) );

        createOnClickEnvoiButton();
        createOnClickNewMsgButton();
        return view;
    }

    private void createOnClickEnvoiButton(){
        envoi.setOnClickListener( new Button.OnClickListener() {
            public void onClick(View v){
                String selectedPhone = phoneArea.getSelectedItem().toString();
                String msgContent;
                if (msgArea.isShown()) {
                     msgContent = msgArea.getSelectedItem().toString();
                } else {
                    msgContent = msg.getText().toString();
                    msgTypes.add( msgContent );
                }
                SmsManager.getDefault().sendTextMessage(selectedPhone, null, msgContent,null, null );
            }
        });
    }

    private void createOnClickNewMsgButton(){
        newMsg.setOnClickListener( new Button.OnClickListener() {
            public void onClick(View v){
                msgArea.setVisibility( View.INVISIBLE );
                msg.setVisibility( View.VISIBLE );
            }
        });
    }

    private void initTypeMsg(){
        msgTypes.add( "J'ai manqué votre appel" );
        msgTypes.add( "Comment ça va ?" );
        msgTypes.add( "Je conduis !" );
        msgTypes.add( "Je suis en reunion !" );
        msgTypes.add( "Nous devons nous voir demain à 17h !" );
    }
}
