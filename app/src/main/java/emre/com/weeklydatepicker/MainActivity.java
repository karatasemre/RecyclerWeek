package emre.com.weeklydatepicker;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;

import net.danlew.android.joda.JodaTimeAndroid;

import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements RecyclerWeek.ItemClickListener {
    Switch aSwitch;

    List<DateModel> dateModelList = new ArrayList<>();

    DateTime mNow, weekOfFirstDay;

    RecyclerWeek mWeekList;

    private int DAY_PLUS_LIMIT = 28;
    private boolean isWeekendEnable = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        JodaTimeAndroid.init(this);

        mWeekList = findViewById(R.id.date_list_view);
        aSwitch = findViewById(R.id.switch1);

        int screenWidth = HelperManager.getDeviceWidth(this);
        mNow = DateTime.now();
        weekOfFirstDay = mNow.dayOfWeek().withMinimumValue();

        dateModelList = getDateModelArray(weekOfFirstDay);

        mWeekList.load(mNow, screenWidth, dateModelList, false);

        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mWeekList.load(mNow, screenWidth, dateModelList, isChecked);
                } else {
                    mWeekList.load(mNow, screenWidth, dateModelList, isChecked);
                }
            }
        });
    }

    @Override
    public void onItemClick(View view, int position) {
        Log.e("asd", "asd");
    }

    public List<DateModel> getDateModelArray(DateTime weekOfFirstDay) {
        DateTime tmpDateTime = weekOfFirstDay;
        dateModelList = new ArrayList<>();
        for (int i = 0; i < DAY_PLUS_LIMIT; i++) {
            if (isWeekendEnable) {
                dateModelList.add(new DateModel(tmpDateTime.plusDays(i)));
            } else {
                if (tmpDateTime.plusDays(i).getDayOfWeek() != DateTimeConstants.SATURDAY &&
                        tmpDateTime.plusDays(i).getDayOfWeek() != DateTimeConstants.SUNDAY)
                    dateModelList.add(new DateModel(tmpDateTime.plusDays(i)));
            }
        }
        return dateModelList;
    }
}
