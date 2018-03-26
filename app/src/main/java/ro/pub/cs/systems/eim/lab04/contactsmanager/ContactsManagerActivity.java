package ro.pub.cs.systems.eim.lab04.contactsmanager;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;

public class ContactsManagerActivity extends AppCompatActivity {

    Button save, cancel, show_hide;
    EditText name, phone, email, address, job, company, website, im;
    LinearLayout fields;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts_manager);

        save = findViewById(R.id.save);
        save.setOnClickListener(onClickListener);
        cancel = findViewById(R.id.cancel);
        cancel.setOnClickListener(onClickListener);
        show_hide = findViewById(R.id.show_hide);
        show_hide.setOnClickListener(onClickListener);

        name = findViewById(R.id.name);
        phone = findViewById(R.id.phone);
        email = findViewById(R.id.email);
        address = findViewById(R.id.address);
        job = findViewById(R.id.job);
        company = findViewById(R.id.company);
        website = findViewById(R.id.website);
        im = findViewById(R.id.im);

        fields = findViewById(R.id.fields);

        Intent intent = getIntent();
        if (intent != null) {
            String phoneNr = intent.getStringExtra("ro.pub.cs.systems.eim.lab04.contactsmanager.PHONE_NUMBER_KEY");
            if (phone != null) {
                phone.setText(phoneNr);
            } else {
                Toast.makeText(this, "Incorrect phone", Toast.LENGTH_LONG).show();
            }
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        switch(requestCode) {
            case 1:
                setResult(resultCode, new Intent());
                finish();
                break;
        }
    }

    private ButtonClickListener onClickListener = new ButtonClickListener();
    private class ButtonClickListener implements Button.OnClickListener {
        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.show_hide) {
                if (fields.getVisibility() == View.VISIBLE) {
                    fields.setVisibility(View.GONE);
                    show_hide.setText("Show Additional Details");
                }
                else {
                    fields.setVisibility(View.VISIBLE);
                    show_hide.setText("Hide Additional Details");
                }
            }

            if (v.getId() == R.id.save) {
                Intent intent = new Intent(ContactsContract.Intents.Insert.ACTION);
                intent.setType(ContactsContract.RawContacts.CONTENT_TYPE);
                if (name.getText() != null) {
                    intent.putExtra(ContactsContract.Intents.Insert.NAME, name.getText());
                }
                if (phone.getText() != null) {
                    intent.putExtra(ContactsContract.Intents.Insert.PHONE, phone.getText());
                }
                if (email.getText() != null) {
                    intent.putExtra(ContactsContract.Intents.Insert.EMAIL, email.getText());
                }
                if (address.getText() != null) {
                    intent.putExtra(ContactsContract.Intents.Insert.POSTAL, address.getText());
                }
                if (job.getText() != null) {
                    intent.putExtra(ContactsContract.Intents.Insert.JOB_TITLE, job.getText());
                }
                if (company.getText() != null) {
                    intent.putExtra(ContactsContract.Intents.Insert.COMPANY, company.getText());
                }
                ArrayList<ContentValues> contactData = new ArrayList<ContentValues>();
                if (website.getText() != null) {
                    ContentValues websiteRow = new ContentValues();
                    websiteRow.put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Website.CONTENT_ITEM_TYPE);
                    websiteRow.put(ContactsContract.CommonDataKinds.Website.URL, website.getText().toString());
                    contactData.add(websiteRow);
                }
                if (im.getText() != null) {
                    ContentValues imRow = new ContentValues();
                    imRow.put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Im.CONTENT_ITEM_TYPE);
                    imRow.put(ContactsContract.CommonDataKinds.Im.DATA, im.getText().toString());
                    contactData.add(imRow);
                }
                intent.putParcelableArrayListExtra(ContactsContract.Intents.Insert.DATA, contactData);
//                startActivity(intent);
                startActivityForResult(intent, 1);
            }

            if (v.getId() == R.id.cancel) {
                finish();
                setResult(Activity.RESULT_CANCELED, new Intent());
            }
        }
    }
}
