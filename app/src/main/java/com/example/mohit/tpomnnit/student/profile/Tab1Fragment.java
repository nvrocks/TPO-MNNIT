package com.example.mohit.tpomnnit.student.profile;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.mohit.tpomnnit.R;
import com.example.mohit.tpomnnit.login_signup.TpoSignup;
import com.example.mohit.tpomnnit.student.StudentProfile;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import static com.example.mohit.tpomnnit.R.id.date;

/**
 * Created by User on 2/28/2017.
 */

public class    Tab1Fragment extends Fragment {
    private static final String TAG = "Tab1Fragment";
    EditText regno,name,dob,email,skype,linkedin,
            category,residential,guardian,presentaddress,permanentaddress,
            marital,state,country,parentcontact,personalcontact,datetime;
    Button save;
    ValueEventListener vel;
    RadioButton radioMale,radioFemale,radioYes,radioNo;
    private int Year, month, day,hours,minutes;
    Calendar calendar;
    private String registrationnum,userId,key,branchselected="n/a",courseselected="n/a",genderstr="n/a",pwd="n/a";
    private DatabaseReference mDatabase;
    Spinner spinnerbranch,spinnercourse;
    private RadioGroup radioSexGroup,phyradiogroup;
    int isverified1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab1_fragment, container, false);
        regno = (EditText) view.findViewById(R.id.location);
        name = (EditText) view.findViewById(R.id.name);
        dob = (EditText) view.findViewById(R.id.dob);
        email = (EditText) view.findViewById(R.id.email);
        skype = (EditText) view.findViewById(R.id.skypeid);
        linkedin = (EditText) view.findViewById(R.id.pwd);
//        gender = (EditText) view.findViewById(R.id.gender);
        category = (EditText) view.findViewById(R.id.category);
//        pwd = (EditText) view.findViewById(R.id.pwd);
        residential = (EditText) view.findViewById(R.id.residential);
        guardian = (EditText) view.findViewById(R.id.guardian);
        presentaddress = (EditText) view.findViewById(R.id.presentAddress);
        permanentaddress = (EditText) view.findViewById(R.id.permanentAddress);
        marital = (EditText) view.findViewById(R.id.marital);
        state = (EditText) view.findViewById(R.id.state);
        country = (EditText) view.findViewById(R.id.country);
        parentcontact = (EditText) view.findViewById(R.id.parentcontact);
        personalcontact = (EditText) view.findViewById(R.id.personalcontact);
        save = (Button) view.findViewById(R.id.save);
        radioMale=(RadioButton)view.findViewById(R.id.radioMale);
        radioFemale=(RadioButton)view.findViewById(R.id.radioFemale);
        radioYes=(RadioButton)view.findViewById(R.id.radioYes);
        radioNo=(RadioButton)view.findViewById(R.id.radioNo);
        mDatabase = FirebaseDatabase.getInstance().getReference("userdata");
        userId = mDatabase.push().getKey();
        MyProfile myProfile = (MyProfile) getActivity();
        registrationnum = myProfile.getRegno();
        regno.setEnabled(false);
        name.setEnabled(false);
        spinnerbranch = (Spinner) view.findViewById(R.id.spinnerbranch);
        spinnercourse = (Spinner) view.findViewById(R.id.spinnercourse);
        datetime      = (EditText) view.findViewById(R.id.dob);
        radioSexGroup = (RadioGroup) view.findViewById(R.id.radioSex);
        phyradiogroup = (RadioGroup) view.findViewById(R.id.radiophy);
        dob.setFocusable(false);

        dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //setDate(v);
                DialogFragment newFragment = new SelectDateFragment();
                newFragment.show(getFragmentManager(), "DatePicker");
            }
        });
        branchspinner();
        coursespinner();
        gender();
        physicallychallenged();

        vel = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                UserData user = dataSnapshot.getValue(UserData.class);
                for (DataSnapshot userDetails : dataSnapshot.getChildren()) {
                    System.out.println(userDetails.child("regnum").getValue().toString());
                    if (registrationnum.equals(userDetails.child("regnum").getValue().toString())) {
                        System.out.println("in in in ini ni nin in in ");
                        name.setText(userDetails.child("name").getValue().toString());
//                        branch.setText(userDetails.child("branch").getValue().toString());
                        regno.setText(registrationnum);
//                        course.setText(userDetails.child("course").getValue().toString());
                        dob.setText(userDetails.child("dob").getValue().toString());
                        email.setText(userDetails.child("email").getValue().toString());
                        skype.setText(userDetails.child("skypeid").getValue().toString());
                        linkedin.setText(userDetails.child("linkedinid").getValue().toString());
//                        gender.setText(userDetails.child("gender").getValue().toString());
                        category.setText(userDetails.child("category").getValue().toString());
//                        pwd.setText(userDetails.child("phychal").getValue().toString());
                        name.setText(userDetails.child("name").getValue().toString());
                        residential.setText(userDetails.child("residentialstatus").getValue().toString());
                        guardian.setText(userDetails.child("guardian").getValue().toString());
                        permanentaddress.setText(userDetails.child("permanentadd").getValue().toString());
                        presentaddress.setText(userDetails.child("presentadd").getValue().toString());
                        state.setText(userDetails.child("state").getValue().toString());
                        country.setText(userDetails.child("country").getValue().toString());
                        parentcontact.setText(userDetails.child("guardianmobile").getValue().toString());
                        personalcontact.setText(userDetails.child("mobileno").getValue().toString());
                        marital.setText(userDetails.child("maritalstatus").getValue().toString());
                        isverified1=Integer.parseInt(userDetails.child("isverified").getValue().toString());
                        String gend,catr;
                        gend=userDetails.child("gender").getValue().toString();
                        catr=userDetails.child("phychal").getValue().toString();
                        if(gend.equals("Female"))
                            radioMale.setSelected(true);
                        if(catr.equals("No"))
                            radioNo.setSelected(true);

                        key=userDetails.getKey();
                        //Access all data

                    }
                }
                setVerification(isverified1);
                //mDatabase.removeEventListener(vel);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        mDatabase.addListenerForSingleValueEvent(vel);


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDatabase.child(key).child("branch").setValue(branchselected);
                mDatabase.child(key).child("course").setValue(courseselected);
                mDatabase.child(key).child("dob").setValue(dob.getText().toString().trim());
                mDatabase.child(key).child("email").setValue(email.getText().toString().trim());
                mDatabase.child(key).child("skypeid").setValue(skype.getText().toString().trim());
                mDatabase.child(key).child("linkedinid").setValue(linkedin.getText().toString().trim());
                mDatabase.child(key).child("gender").setValue(genderstr);
                mDatabase.child(key).child("category").setValue(category.getText().toString().trim());
                mDatabase.child(key).child("phychal").setValue(pwd);
                mDatabase.child(key).child("residentialstatus").setValue(residential.getText().toString().trim());
                mDatabase.child(key).child("guardian").setValue(guardian.getText().toString().trim());
                mDatabase.child(key).child("presentadd").setValue(presentaddress.getText().toString().trim());
                mDatabase.child(key).child("permanentadd").setValue(permanentaddress.getText().toString().trim());
                mDatabase.child(key).child("maritalstatus").setValue(marital.getText().toString().trim());
                mDatabase.child(key).child("state").setValue(state.getText().toString().trim());
                mDatabase.child(key).child("country").setValue(country.getText().toString().trim());
                mDatabase.child(key).child("mobileno").setValue(personalcontact.getText().toString().trim());
                mDatabase.child(key).child("guardianmobile").setValue(parentcontact.getText().toString().trim());
                Context applicationContext = MyProfile.getContextOfApplication();
                Toast.makeText(applicationContext, "Saved", Toast.LENGTH_LONG).show();
               // mDatabase.child(key).child("state").setValue(state.getText().toString().trim());
            }
        });
        return view;
    }

    private void setVerification(int ver)
    {
        if(ver==1)
        {
            regno.setFocusable(false);
            name.setFocusable(false);
            dob.setFocusable(false);
            email.setFocusable(false);
            skype.setFocusable(false);
            linkedin.setFocusable(false);
//        gender = (EditText) view.findViewById(R.id.gender);
            category.setFocusable(false);
//        pwd = (EditText) view.findViewById(R.id.pwd);
            residential.setFocusable(false);
            guardian.setFocusable(false);
            presentaddress.setFocusable(false);
            permanentaddress.setFocusable(false);
            marital.setFocusable(false);
            state.setFocusable(false);
            country.setFocusable(false);
            parentcontact.setFocusable(false);
            personalcontact.setFocusable(false);
            save.setFocusable(false);
            save.setVisibility(View.GONE);
            regno.setEnabled(false);
            name.setEnabled(false);
            /*spinnerbranch = (Spinner) view.findViewById(R.id.spinnerbranch);
            spinnercourse = (Spinner) view.findViewById(R.id.spinnercourse);
            datetime      = (EditText) view.findViewById(R.id.dob);
            radioSexGroup = (RadioGroup) view.findViewById(R.id.radioSex);
            phyradiogroup = (RadioGroup) view.findViewById(R.id.radiophy);*/
            dob.setFocusable(false);

        }
    }
    private void branchspinner()
    {

        // Spinner click listener
        List<String> branches = new ArrayList<String>();
        branches.add("CSE");
        branches.add("IT");
        branches.add("ECE");
        branches.add("EE");
        branches.add("ME");
        branches.add("PIE");
        branches.add("CHE");
        branches.add("BIO");
        branches.add("CIVIL");
        branches.add("MCA");

        ArrayAdapter<String> dataAdapterbranch;
        dataAdapterbranch = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item,branches);

        // Drop down layout style - list view with radio button
        dataAdapterbranch.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinnerbranch.setAdapter(dataAdapterbranch);

        spinnerbranch.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                branchselected = parent.getItemAtPosition(position).toString();
                // TODO Auto-generated method stub

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub

            }
        });
    }
    private void coursespinner()
    {

        // Spinner click listener
        List<String> courses = new ArrayList<String>();
        courses.add("BTech");
        courses.add("MTech");
        courses.add("MCA");
        courses.add("PhD");
        courses.add("MBA");

        ArrayAdapter<String> dataAdapterbranch;
        dataAdapterbranch = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item,courses);

        // Drop down layout style - list view with radio button
        dataAdapterbranch.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinnercourse.setAdapter(dataAdapterbranch);

        spinnercourse.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                courseselected = parent.getItemAtPosition(position).toString();
                // TODO Auto-generated method stub

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub

            }
        });
    }
    private void gender()
    {
        radioSexGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // checkedId is the RadioButton selected

                switch(checkedId) {
                    case R.id.radioMale:
                        genderstr = "Male";
                        break;
                    case R.id.radioFemale:
                        genderstr = "Female";
                        // Fragment 2
                        break;
                }
            }
        });
        // find the radiobutton by returned id
    }

    private void physicallychallenged()
    {
        phyradiogroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // checkedId is the RadioButton selected

                switch(checkedId) {
                    case R.id.radioYes:
                        pwd = "Yes";
                        break;
                    case R.id.radioNo:
                        pwd = "No";
                        // Fragment 2
                        break;
                }
            }
        });
    }

}