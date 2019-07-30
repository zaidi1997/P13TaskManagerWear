package sg.edu.rp.c346.p13_taskmanagerwear;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;

public class AddTaskActivity extends AppCompatActivity {

    int piReqCode = 12;

    private Button addButton, cancelButton;
    private EditText etName, etDesc, etRemind;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        addButton = findViewById(R.id.addTaskButton);
        cancelButton = findViewById(R.id.cancelTaskButton);

        etName = findViewById(R.id.etName);
        etDesc = findViewById(R.id.etDesc);
        etRemind = findViewById(R.id.etRemind);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int seconds = Integer.valueOf(etRemind.getText().toString());
                Calendar cal = Calendar.getInstance();
                cal.add(Calendar.SECOND, seconds);

                String name = etName.getText().toString();
                String desc = etDesc.getText().toString();

                DBHelper dbh = new DBHelper(AddTaskActivity.this);
                int id = (int) dbh.insertTask(name, desc);
                dbh.close();

                Intent i = new Intent();
                setResult(RESULT_OK, i);

                if (id != -1){
                    Toast.makeText(AddTaskActivity.this, "Insert successful",
                            Toast.LENGTH_SHORT).show();
                }

                //Create a new PendingIntent and add it .to the AlarmManager
                Intent iReminder = new Intent(AddTaskActivity.this, TaskReminderReceiver.class);

                iReminder.putExtra("id", id);
                iReminder.putExtra("name", name);
                iReminder.putExtra("desc", desc);

                PendingIntent pendingIntent = PendingIntent.getBroadcast(AddTaskActivity.this, piReqCode, iReminder, PendingIntent.FLAG_CANCEL_CURRENT);

                AlarmManager am = (AlarmManager) getSystemService(Activity.ALARM_SERVICE);
                am.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pendingIntent);

                setResult(RESULT_OK);
                finish();
            }
        });


        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_CANCELED);
                finish();

            }
        });


    }
}
