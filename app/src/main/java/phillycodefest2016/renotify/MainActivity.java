package phillycodefest2016.renotify;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class MainActivity extends ActionBarActivity {

    private Spinner spinner;

    private String ACTION_TAG = "android.appwidget.action.NOTIF_UPDATE";
    String[] test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("SmsReceiver", "senderNum");
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        test = new String[] {"1", "30", "45", "60", "90", "120"};

        spinner = (Spinner)findViewById(R.id.static_spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this,
                android.R.layout.simple_spinner_item, test);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);


        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment(), ACTION_TAG)
                    .commit();
        }

        final MainActivity mainActivity = this;

        Button btnSnooze = (Button) findViewById(R.id.buttonSnooze);
        btnSnooze.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.w("btnSnooze", "clicked");
                Context context = getApplicationContext();

                // Get the drop down and get the selected value
                Spinner spinner = (Spinner) findViewById(R.id.static_spinner);
                String minutes = spinner.getSelectedItem().toString();
                long milliseconds = Long.parseLong(minutes) * 60000;

                // Create a new AlarmManager
                AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

                // Create our intent and assign it the needed information
                Intent intent = new Intent(SnoozeClass.ACTION);


                PendingIntent pendingIntent =
                        PendingIntent.getBroadcast(
                                context,
                                0,
                                intent,
                                PendingIntent.FLAG_UPDATE_CURRENT
                        );


                alarmManager.set(AlarmManager.RTC_WAKEUP, 5000, pendingIntent);
                Log.w("btnSnooze", "alarm created");

                mainActivity.finish();
            }
        });



    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }
    }



}