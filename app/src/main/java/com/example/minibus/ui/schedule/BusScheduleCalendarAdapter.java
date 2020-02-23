package com.example.minibus.ui.schedule;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.minibus.AppConstants;
import com.example.minibus.ui.R;
import com.example.minibus.helpers.AppDatesHelper;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

public class BusScheduleCalendarAdapter extends RecyclerView.Adapter<BusScheduleCalendarAdapter.BusScheduleCalendarViewHolder> {

    public interface OnItemClickListener {
        void onDateClick(View view, int position);
    }

    private final int DEFAULT_CALENDAR_DATE_POSITION = AppConstants.DEFAULT_CALENDAR_DATE_POSITION;
    private final int DEFAULT_CALENDAR_SIZE = AppConstants.DEFAULT_CALENDAR_SIZE;

    private Context context;
    private List<String> dates = new ArrayList<>();
    private int lastSelectedPos;
    private int selectedPos;
    private OnItemClickListener clickListener;

    public BusScheduleCalendarAdapter(Context context) {
        this.context = context;
        setupSelectedDatePosition(DEFAULT_CALENDAR_DATE_POSITION);
        setupCalendar(DEFAULT_CALENDAR_SIZE);
    }

    public void setOnItemClickListener(OnItemClickListener clickListener) {
        this.clickListener = clickListener;
    }

    private void setupSelectedDatePosition(int position) {
        this.selectedPos = position;
        this.lastSelectedPos = selectedPos;
    }

    private void setupCalendar(int calendarSize) {
        if (!dates.isEmpty()) clearCalendar();
        dates.addAll(AppDatesHelper.createWeekdays(calendarSize, AppDatesHelper.DatePattern.API_SCHEDULE_REQUEST));
    }

    private void clearCalendar() {
        dates.clear();
    }

    public String getSelectedDate(AppDatesHelper.DatePattern pattern) {
        return AppDatesHelper.formatDate(getSelectedDate(), AppDatesHelper.DatePattern.API_SCHEDULE_REQUEST, pattern);
    }

    public String getSelectedDate() {
        return getDate(selectedPos);
    }

    public String getDate(AppDatesHelper.DatePattern pattern, int position) {
        return AppDatesHelper.formatDate(getDate(position), AppDatesHelper.DatePattern.API_SCHEDULE_REQUEST, pattern);
    }

    public String getDate(int position) {
        String oldCurrentDate = dates.get(DEFAULT_CALENDAR_DATE_POSITION);
        String currentDate = AppDatesHelper.getTimestamp();

        // calculate days difference before update and after
        // to re-draw calendar if the date changes during the app session
        int diff = AppDatesHelper.getDaysDifference(oldCurrentDate, currentDate, AppDatesHelper.DatePattern.API_SCHEDULE_REQUEST);

        if (diff != 0) {
            Timber.d("The day has passed");

            setupCalendar(DEFAULT_CALENDAR_SIZE);
            lastSelectedPos = lastSelectedPos != 0 ? lastSelectedPos - 1 : 0;
            notifyDataSetChanged();
        } else {
            dates.set(DEFAULT_CALENDAR_DATE_POSITION, currentDate);
        }

        return dates.get(position);
    }

    @NonNull
    @Override
    public BusScheduleCalendarViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        int layoutIdForDatePickerItem = R.layout.view_calendar_date;

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(layoutIdForDatePickerItem, viewGroup, false);

        return new BusScheduleCalendarViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BusScheduleCalendarViewHolder viewHolder, int position) {
        viewHolder.bind(getDate(AppDatesHelper.DatePattern.CALENDAR, position).toUpperCase());

        viewHolder.itemView.setOnClickListener((View v) -> {
            lastSelectedPos = position;
            clickListener.onDateClick(v, position);

            notifyDataSetChanged();
        });

        if (lastSelectedPos == position) {
            selectedPos = position;
            viewHolder.radioButtonMonthDay.setChecked(true);
        } else {
            viewHolder.radioButtonMonthDay.setChecked(false);
        }
    }

    @Override
    public int getItemCount() {
        return dates.size();
    }

    class BusScheduleCalendarViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.radio_button_month_day) RadioButton radioButtonMonthDay;
        @BindView(R.id.text_week_day) TextView textWeekDay;

        public BusScheduleCalendarViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bind(String date) {
            String[] parts = date.split(" ");
            textWeekDay.setText(parts[0]);
            radioButtonMonthDay.setText(parts[1]);
        }
    }
}
