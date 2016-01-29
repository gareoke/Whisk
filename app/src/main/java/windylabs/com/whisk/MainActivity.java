package windylabs.com.whisk;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.codetroopers.betterpickers.calendardatepicker.CalendarDatePickerDialogFragment;
import com.codetroopers.betterpickers.radialtimepicker.RadialTimePickerDialogFragment;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import rx.Observable;
import windylabs.com.whisk.utils.WhiskConstants;
import windylabs.com.whisk.utils.WhiskHelper;
import windylabs.com.whisk.views.LocationActivity;

public class MainActivity extends AppCompatActivity{
    static final int REQUEST_CHOOSE_LOCATION = 0;
    static final int REQUEST_IMAGE_FROM_FILE = 1;

    private static final String TAG = MainActivity.class.getSimpleName();

    @InjectView(R.id.event_name) protected EditText mEventName;
    @InjectView(R.id.start_date) protected TextView mStartDate;
    @InjectView(R.id.start_time) protected TextView mStartTime;
    @InjectView(R.id.end_date) protected TextView mEndDate;
    @InjectView(R.id.end_time) protected TextView mEndTime;
    @InjectView(R.id.location) protected TextView mLocation;
    @InjectView(R.id.details) protected TextView mDetails;
    @InjectView(R.id.event_choose_image) protected ImageView chooseEventImage;
    @InjectView(R.id.event_image) protected ImageView choosenEventImage;
    @InjectView(R.id.event_fee) protected EditText eventFee;
    @InjectView(R.id.event_tags) protected EditText eventTags;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);

        setupActionBar();
        setupListeners();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return false;
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
        Log.d(TAG, "chooseEndTime -- START");

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

    @OnClick(R.id.location)
    public void searchLocation(){
        Log.d(TAG, "searchLocation -- START");
        Intent intent = new Intent(getApplicationContext(), LocationActivity.class);
        startActivityForResult(intent, REQUEST_CHOOSE_LOCATION);
    }

    @OnClick({R.id.event_choose_image, R.id.event_image})
    public void chooseEventImage(){
        Log.d(TAG, "chooseEventImage -- START");
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, REQUEST_IMAGE_FROM_FILE);
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

    // @param hour - the hour in military time 0 through 23
    // @param minute - the minute value 0 through 59
    // @return an Observable that emits the day the time of the day
    private Observable<String> getFormattedTime(int hour, int minute){
        StringBuilder fomrattedTime = new StringBuilder();
        String ampm = " am";

        if(hour > 12){
            hour -= 12;
            ampm = " pm";
        } else if( hour == 0) {
            hour +=12;
            ampm = " pm";
        }

        return Observable.just(fomrattedTime.append(hour).append(':').append(minute).append(ampm).toString());
    }

    private void setupActionBar(){
        ActionBar ab = getSupportActionBar();
        ab.setDisplayShowCustomEnabled(true);

        ab.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.create_event_background)));
        ab.setDisplayHomeAsUpEnabled(Boolean.TRUE);

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ab.setElevation(0);
        }

        ab.setCustomView(R.layout.custom_action_bar);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d(TAG, "onActivityResult -- START -- " + resultCode);

        if (resultCode == RESULT_OK) {
            switch(requestCode) {
                case REQUEST_CHOOSE_LOCATION:
                    String name = data.getStringExtra("name");
                    String location = data.getStringExtra("location");
                    Log.d(TAG, "location: " + location);

                    mLocation.setText(name + ", " + location);
                    break;
                case REQUEST_IMAGE_FROM_FILE:
                    Log.d(TAG, "onActivityResult -- REQUEST_IMAGE_FROM_FILE");
                    chooseEventImage.setVisibility(View.INVISIBLE);
                    mEventName.setTextColor(getResources().getColor(R.color.bpWhite));
                    mEventName.setHintTextColor(getResources().getColor(R.color.bpWhite));

                    Uri uri = data.getData();

                    if (uri != null) {
                        String mediaType = getApplicationContext().getContentResolver().getType(uri);
                        String extension = MimeTypeMap.getSingleton().getExtensionFromMimeType(mediaType);
                        String mediaFilename = uri.getLastPathSegment() + "." + extension;
                        String mediaUploadUrl = WhiskConstants.IMAGE_UPLOAD_PREFIX + mediaFilename;
                        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());

                        Log.d(TAG, "mediaType: " + mediaType + "-- upload URL: " + mediaUploadUrl);
                        mediaFilename = "Event_Image" + "_" + timeStamp;

                        int orientation = getOrientation(WhiskHelper.getRealPathFromURI(MainActivity.this, uri));
                        Log.d(TAG, "orientation: " + orientation);

                        loadImageIntoHolder(uri, orientation);
                    } else {
                        Log.d(TAG, "onActivityResult -- REQUEST_IMAGE_FROM_FILE -- mUri is null!");
                    }

                    break;
                default:
            }
        }
    }

    private int getOrientation(String path){
        Log.d(TAG, "getOrientation -- START -- " + path);
        ExifInterface exif = null;
        try {
            exif = new ExifInterface(path);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return exif.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                ExifInterface.ORIENTATION_UNDEFINED);
    }

    private void loadImageIntoHolder(Uri uri, int orienatation){
        switch(orienatation){
            case ExifInterface.ORIENTATION_ROTATE_90:
                Picasso.with(getApplicationContext()).load(uri).rotate(90.0f).into(choosenEventImage);
                break;
            case ExifInterface.ORIENTATION_ROTATE_180:
                Picasso.with(getApplicationContext()).load(uri).rotate(180.0f).into(choosenEventImage);
                break;
            case ExifInterface.ORIENTATION_ROTATE_270:
                Picasso.with(getApplicationContext()).load(uri).rotate(270.0f).into(choosenEventImage);
                break;
            default:
                Picasso.with(getApplicationContext()).load(uri).fit().centerCrop().into(choosenEventImage);
                break;
        }
    }

    private void setupListeners(){
        eventFee.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.d(TAG, "afterTextChanged -- START -- " + s.length());
                if(s!=null && s.length() <= 1) {
                    eventFee.removeTextChangedListener(this);
                    if (s.subSequence(0, 0)!=null && !s.subSequence(0, 0).equals("$")) {
                        s.insert(0, "$");
                    }
                    eventFee.addTextChangedListener(this);
                }
            }
        });

        eventTags.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}