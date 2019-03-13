package emre.com.weeklydatepicker;

import org.joda.time.DateTime;

public class DateModel {
    public boolean isSelected;
    public DateTime dateTime;

    public DateModel() {

    }

    public DateModel(DateTime dateTimeList, boolean isSelected) {
        this.dateTime = dateTimeList;
        this.isSelected = isSelected;
    }

    public DateModel(DateTime dateTime) {
        this.dateTime = dateTime;
        this.isSelected = false;
    }

    public DateTime getDateTime() {
        return dateTime;
    }

    public void setDateTimeList(DateTime dateTime) {
        this.dateTime = dateTime;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }


}
