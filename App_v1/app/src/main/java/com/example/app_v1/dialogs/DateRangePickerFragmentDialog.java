package com.example.app_v1.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import com.example.app_v1.R;
import com.example.app_v1.utils.DateTimeConverterHelper;
import com.squareup.timessquare.CalendarCellDecorator;
import com.squareup.timessquare.CalendarPickerView;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DateRangePickerFragmentDialog extends AppCompatDialogFragment
{
    private CalendarPickerView calendarPickerView;
    private String dates;
    private DateRangePickerFragmentDialogListener dialogListener;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_custom_calendarpicker, null);

        builder.setView(view).setTitle("Please select date range from & to:")
        .setNegativeButton("Cancel", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        })
        .setPositiveButton("Confirm", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                List<Date> datesList = calendarPickerView.getSelectedDates();
                Date firstDate = datesList.get(0);
                Date lastDate = datesList.get(datesList.size() - 1);

                Calendar firstDateCal = Calendar.getInstance();
                Calendar lastDateCal = Calendar.getInstance();

                firstDateCal.setTime(firstDate);
                lastDateCal.setTime(lastDate);

                String firstDateString = DateTimeConverterHelper.
                        convertDatePickerValuesToString(firstDateCal.get(Calendar.YEAR),
                                firstDateCal.get(Calendar.MONTH),
                                firstDateCal.get(Calendar.DAY_OF_MONTH));

                String lastDateString = DateTimeConverterHelper.convertDatePickerValuesToString(lastDateCal.get(Calendar.YEAR),
                        lastDateCal.get(Calendar.MONTH),
                        lastDateCal.get(Calendar.DAY_OF_MONTH));

                dates = String.format(Locale.ENGLISH,"%s - %s",firstDateString,lastDateString);

                dialogListener.returnDates(dates);
            }
        });

        calendarPickerView = view.findViewById(R.id.calendarPickerView);

        ArrayList<Date> initDates = new ArrayList<>();

        //Set minDate and MaxDate
        Calendar oneMonthAgo = Calendar.getInstance();
        oneMonthAgo.add(Calendar.MONTH, -1);

        Calendar todayMax = Calendar.getInstance();
        todayMax.add(Calendar.DAY_OF_MONTH, 1);

        //Init with selected dates from today to today - 1 day ago
        Calendar oneWeekAgo = Calendar.getInstance();
        oneWeekAgo.add(Calendar.DAY_OF_MONTH, -1);
        initDates.add(oneWeekAgo.getTime());

        Calendar today = Calendar.getInstance();
        initDates.add(today.getTime());

        calendarPickerView.setDecorators(Collections.<CalendarCellDecorator>emptyList());

        calendarPickerView.init(oneMonthAgo.getTime(),todayMax.getTime())
                .inMode(CalendarPickerView.SelectionMode.RANGE)
                .withSelectedDates(initDates);

        calendarPickerView.setOnDateSelectedListener(new CalendarPickerView.OnDateSelectedListener()
        {
            @Override
            public void onDateSelected(Date date)
            {
                //Check if more than one date has been selected
                if(calendarPickerView.getSelectedDates().size() > 1)
                {
                    ((AlertDialog)getDialog()).getButton(AlertDialog.BUTTON_POSITIVE).setVisibility(View.VISIBLE);
                }
                else
                {
                    ((AlertDialog)getDialog()).getButton(AlertDialog.BUTTON_POSITIVE).setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onDateUnselected(Date date) {

            }
        });

        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context)
    {
        super.onAttach(context);
        try {
            dialogListener = (DateRangePickerFragmentDialogListener)getTargetFragment();
        } catch (ClassCastException e)
        {
            throw new ClassCastException(context.toString()
                    + "must implement DateRangePickerFragmentDialogListener");
        }
    }

    public interface DateRangePickerFragmentDialogListener
    {
        void returnDates(String dates);
    }
}
