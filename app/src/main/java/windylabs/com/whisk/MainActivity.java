package windylabs.com.whisk;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import com.codetroopers.betterpickers.calendardatepicker.CalendarDatePickerDialogFragment;
import com.codetroopers.betterpickers.radialtimepicker.RadialTimePickerDialogFragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.observers.Observers;

public class MainActivity extends AppCompatActivity{
    private static final String TAG = MainActivity.class.getSimpleName();

    @InjectView(R.id.event_name) protected EditText mEventName;
    @InjectView(R.id.start_date) protected TextView mStartDate;
    @InjectView(R.id.start_time) protected TextView mStartTime;
    @InjectView(R.id.end_date) protected TextView mEndDate;
    @InjectView(R.id.end_time) protected TextView mEndTime;
    @InjectView(R.id.location) protected TextView mLocation;
    @InjectView(R.id.details) protected TextView mDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.start_date)
    public void chooseStartDate() {
        Log.d(TAG, "chooseStartDate -- START");
        // Create date picker listener.
        CalendarDatePickerDialogFragment.OnDateSetListener dateSetListener = new CalendarDatePickerDialogFragment.OnDateSetListener() {
            @Override
            public void onDateSet(CalendarDatePickerDialogFragment dialog, int year, int monthOfYear, int dayOfMonth) {
                Log.d(TAG, "onDateSet -- START -- " + year + " -- " + monthOfYear + " -- " + dayOfMonth);
                getDayOfWeek(year, monthOfYear, dayOfMonth).subscribe(dayOfWeek -> mStartDate.setText(dayOfWeek));
            }
        };

        CalendarDatePickerDialogFragment dialog = new CalendarDatePickerDialogFragment();
        dialog.setOnDateSetListener(dateSetListener);
        dialog.setThemeDark(Boolean.FALSE);
        dialog.show(getSupportFragmentManager(), "DATE_PICKER_TAG");
    }

    @OnClick(R.id.start_time)
    public void chooseStartTime() {
        // Create dismiss listener.
        RadialTimePickerDialogFragment.OnTimeSetListener onTimeSetListener = new RadialTimePickerDialogFragment.OnTimeSetListener(){
            @Override
            public void onTimeSet(RadialTimePickerDialogFragment radialTimePickerDialogFragment, int hour, int minute) {
                Log.d(TAG, "onTimeSet -- START");
                getFormattedTime(hour, minute).subscribe(time -> mStartTime.setText(time));
            }
        };

        RadialTimePickerDialogFragment rtpd = new RadialTimePickerDialogFragment()
                .setOnTimeSetListener(onTimeSetListener)
                .setStartTime(10, 10);
        rtpd.show(getSupportFragmentManager(), "TIME_PICKER_TAG");
    }

    @OnClick(R.id.end_date)
    public void chooseEndDate() {
        Log.d(TAG, "chooseStartDate -- START");
        // Create date picker listener.
        CalendarDatePickerDialogFragment.OnDateSetListener dateSetListener = new CalendarDatePickerDialogFragment.OnDateSetListener() {
            @Override
            public void onDateSet(CalendarDatePickerDialogFragment dialog, int year, int monthOfYear, int dayOfMonth) {
                Log.d(TAG, "onDateSet -- START -- " + year + " -- " + monthOfYear + " -- " + dayOfMonth);
                getDayOfWeek(year, monthOfYear, dayOfMonth).subscribe(dayOfWeek -> mEndDate.setText(dayOfWeek));
            }
        };

        // Show date picker dialog.
        CalendarDatePickerDialogFragment dialog = new CalendarDatePickerDialogFragment();
        dialog.setOnDateSetListener(dateSetListener);
        dialog.setThemeDark(false);
        dialog.show(getSupportFragmentManager(), "DATE_PICKER_TAG");
    }

    @OnClick(R.id.end_time)
    public void chooseEndTime() {
        // Create dismiss listener.
        RadialTimePickerDialogFragment.OnTimeSetListener onTimeSetListener = new RadialTimePickerDialogFragment.OnTimeSetListener(){
            @Override
            public void onTimeSet(RadialTimePickerDialogFragment radialTimePickerDialogFragment, int hour, int minute) {
                getFormattedTime(hour, minute).subscribe(time -> mEndTime.setText(time));
            }
        };

        RadialTimePickerDialogFragment rtpd = new RadialTimePickerDialogFragment()
                .setOnTimeSetListener(onTimeSetListener)
                .setStartTime(10, 10);
        rtpd.show(getSupportFragmentManager(), "TIME_PICKER_TAG");

    }

    // @param year - an integer representing the calendar year
    // @param monthOfYear - an integer representing the calendar month in the year 0 to 11
    // @param dayOfMonth - an integer representing the calendar day in the month
    // @return an Observable that emits the day of the week (Monday, Tuesday, Wednesday, etc.) based on the date (month, day, year)
    private Observable<String> getDayOfWeek(int year, int monthOfYear, int dayOfMonth){
        Calendar date = Calendar.getInstance();
        date.set(Calendar.YEAR, year);
        date.set(Calendar.MONTH, monthOfYear);
        date.set(Calendar.DAY_OF_MONTH, dayOfMonth);

        String weekDay;
        SimpleDateFormat dayFormat = new SimpleDateFormat("EEEE", Locale.US);
        weekDay = dayFormat.format(date.getTime());

        return Observable.just(weekDay);
    }

    private Observable<String> getFormattedTime(int hour, int minute){
        StringBuilder fomrattedTime = new StringBuilder();
        String ampm = " am";

        if(hour > 12){
            hour -= 12;
            ampm = " pm";
        }

        return Observable.just(fomrattedTime.append(hour).append(':').append(minute).append(ampm).toString());
    }
}