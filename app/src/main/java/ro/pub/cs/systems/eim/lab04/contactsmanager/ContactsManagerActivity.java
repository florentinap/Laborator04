package ro.pub.cs.systems.eim.lab04.contactsmanager;

import android.content.ContentValues;
import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import java.util.ArrayList;

public class ContactsManagerActivity extends AppCompatActivity {

    private Button showDetails;
    private Button save;
    private Button cancel;

    private LinearLayout hideContainer;

    private EditText nameEditText;
    private EditText phoneEditText;
    private EditText emailEditText;
    private EditText addressEditText;
    private EditText jobEditText;
    private EditText companyEditText;
    private EditText websiteEditText;
    private EditText imEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts_manager);

        showDetails = (Button)findViewById(R.id.button2);
        showDetails.setOnClickListener(buttonClickListener);
        save = (Button)findViewById(R.id.button5);
        save.setOnClickListener(buttonClickListener);
        cancel = (Button)findViewById(R.id.button4);
        cancel.setOnClickListener(buttonClickListener);

        hideContainer = (LinearLayout)findViewById(R.id.extraContainer);

        nameEditText = (EditText)findViewById(R.id.editText4);
        phoneEditText = (EditText)findViewById(R.id.editText3);
        emailEditText = (EditText)findViewById(R.id.editText2);
        addressEditText = (EditText)findViewById(R.id.editText);
        jobEditText = (EditText)findViewById(R.id.editText5);
        companyEditText = (EditText)findViewById(R.id.editText6);
        websiteEditText = (EditText)findViewById(R.id.editText7);
        imEditText = (EditText)findViewById(R.id.editText8);

    }

    private ButtonClickListener buttonClickListener = new ButtonClickListener();
    private class ButtonClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.button2) {
                if (hideContainer.getVisibility() == View.VISIBLE) {
                    showDetails.setText("Show Additional Details");
                    hideContainer.setVisibility(View.INVISIBLE);
                } else {
                    showDetails.setText("Hide Additional Details");
                    hideContainer.setVisibility(View.VISIBLE);
                }
            }
            if (v.getId() == R.id.button5) {

                String name = nameEditText.getText().toString();
                String phone = phoneEditText.getText().toString();
                String email = emailEditText.getText().toString();
                String address = addressEditText.getText().toString();
                String job = jobEditText.getText().toString();
                String company = companyEditText.getText().toString();
                String website = websiteEditText.getText().toString();
                String im = imEditText.getText().toString();

                Intent intent = new Intent(ContactsContract.Intents.Insert.ACTION);
                intent.setType(ContactsContract.RawContacts.CONTENT_TYPE);
                if (name != null) {
                    intent.putExtra(ContactsContract.Intents.Insert.NAME, name);
                }
                if (phone != null) {
                    intent.putExtra(ContactsContract.Intents.Insert.PHONE, phone);
                }
                if (email != null) {
                    intent.putExtra(ContactsContract.Intents.Insert.EMAIL, email);
                }
                if (address != null) {
                    intent.putExtra(ContactsContract.Intents.Insert.POSTAL, address);
                }
                if (job != null) {
                    intent.putExtra(ContactsContract.Intents.Insert.JOB_TITLE, job);
                }
                if (company != null) {
                    intent.putExtra(ContactsContract.Intents.Insert.COMPANY, company);
                }
                ArrayList<ContentValues> contactData = new ArrayList<ContentValues>();
                if (website != null) {
                    ContentValues websiteRow = new ContentValues();
                    websiteRow.put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Website.CONTENT_ITEM_TYPE);
                    websiteRow.put(ContactsContract.CommonDataKinds.Website.URL, website);
                    contactData.add(websiteRow);
                }
                if (im != null) {
                    ContentValues imRow = new ContentValues();
                    imRow.put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Im.CONTENT_ITEM_TYPE);
                    imRow.put(ContactsContract.CommonDataKinds.Im.DATA, im);
                    contactData.add(imRow);
                }
                intent.putParcelableArrayListExtra(ContactsContract.Intents.Insert.DATA, contactData);
                startActivity(intent);
            }

            if (v.getId() == R.id.button4) {
                finish();
            }
        }
    }
}
