package com.example.app_v1.repositories;

import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.example.app_v1.models.Co2;
import com.example.app_v1.models.Greenhouse;
import com.example.app_v1.models.Humidity;
import com.example.app_v1.models.Measurement;
import com.example.app_v1.models.Temperature;
import com.example.app_v1.utils.DTimeFormatHelper;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

public class Repository
{
    private static Repository instance;

    private static int THREAD_DELAY = 5000; //Thread delay setting

    private MutableLiveData<ArrayList<Greenhouse>> greenhouses = new MutableLiveData<>();
    private ArrayList<Greenhouse> greenhouseArrayList = new ArrayList<>();

    private ArrayList<Measurement> measurementsArrList = new ArrayList<>();
    private ArrayList<Measurement> measurementsInDateRange = new ArrayList<>();
    private MutableLiveData<ArrayList<Measurement>> latestMeasurements = new MutableLiveData<>();

    private ArrayList<Temperature> temperaturesInDateRange = new ArrayList<>();
    private ArrayList<Humidity> humidityInDateRange = new ArrayList<>();
    private ArrayList<Co2> co2InDateRange = new ArrayList<>();

    public static Repository getInstance()
    {
        if(instance == null)
        {
            instance = new Repository();
        }

        return instance;
    }

    public LiveData<ArrayList<Greenhouse>> getGreenhouses()
    {
        return this.greenhouses;
    }

    public LiveData<ArrayList<Measurement>> getLatestMeasurements()
    {
        return this.latestMeasurements;
    }

    public ArrayList<Measurement> getMeasurementsInDateRange(String dateTimeFrom, String dateTimeTo) throws ParseException
    {
        String iso8601dateTimeFrom = DTimeFormatHelper.convertStringDateTimeToISO8601String(dateTimeFrom);
        String iso8601dateTimeTo = DTimeFormatHelper.convertStringDateTimeToISO8601String(dateTimeTo);

        //Call retrofit and pass the formatted timestamps as args

        return this.measurementsInDateRange;
    }

    public ArrayList<Temperature> getTemperaturesInDateRange(String dateTimeFrom, String dateTimeTo) throws ParseException
    {
        //getMeasurementsInDateRange(dateTimeFrom,dateTimeTo);

        temperaturesInDateRange.clear();

        Date dateFrom = DTimeFormatHelper.convertStringToDate(dateTimeFrom);
        Date dateTo = DTimeFormatHelper.convertStringToDate(dateTimeTo);

        //Replace later with measurementsInDateRange;

        for(int i = 0; i < measurementsArrList.size(); i++)
        {
            Date measurementDate = DTimeFormatHelper.convertISO8601stringToDate(measurementsArrList.get(i).getTimeStamp());

            if(measurementDate.compareTo(dateFrom) >= 0 && measurementDate.compareTo(dateTo) <= 0)
            {
                Temperature temperature = new Temperature(measurementsArrList.get(i).getTemperature());
                temperature.setTime(DTimeFormatHelper.getTimeStringFromISO8601Timestamp(measurementsArrList.get(i).getTimeStamp()));
                temperature.setDate(DTimeFormatHelper.getDateStringFromISO8601Timestamp(measurementsArrList.get(i).getTimeStamp()));

                temperaturesInDateRange.add(temperature);
            }
        }

        return this.temperaturesInDateRange;
    }

    public ArrayList<Humidity> getHumidityInDateRange(String dateTimeFrom, String dateTimeTo) throws ParseException
    {
        //getMeasurementsInDateRange(dateTimeFrom,dateTimeTo);

        humidityInDateRange.clear();

        Date dateFrom = DTimeFormatHelper.convertStringToDate(dateTimeFrom);
        Date dateTo = DTimeFormatHelper.convertStringToDate(dateTimeTo);

        //Replace later with measurementsInDateRange;

        for(int i = 0; i < measurementsArrList.size(); i++)
        {
            Date measurementDate = DTimeFormatHelper.convertISO8601stringToDate(measurementsArrList.get(i).getTimeStamp());

            if(measurementDate.compareTo(dateFrom) >= 0 && measurementDate.compareTo(dateTo) <= 0)
            {
                Humidity humidity = new Humidity(measurementsArrList.get(i).getHumidity());
                humidity.setTime(DTimeFormatHelper.getTimeStringFromISO8601Timestamp(measurementsArrList.get(i).getTimeStamp()));
                humidity.setDate(DTimeFormatHelper.getDateStringFromISO8601Timestamp(measurementsArrList.get(i).getTimeStamp()));

                humidityInDateRange.add(humidity);
            }
        }

        return this.humidityInDateRange;
    }

    public ArrayList<Co2> getCo2InDateRange(String dateTimeFrom, String dateTimeTo) throws ParseException
    {
        //getMeasurementsInDateRange(dateTimeFrom,dateTimeTo);

        co2InDateRange.clear();

        Date dateFrom = DTimeFormatHelper.convertStringToDate(dateTimeFrom);
        Date dateTo = DTimeFormatHelper.convertStringToDate(dateTimeTo);

        //Replace later with measurementsInDateRange;

        for(int i = 0; i < measurementsArrList.size(); i++)
        {
            Date measurementDate = DTimeFormatHelper.convertISO8601stringToDate(measurementsArrList.get(i).getTimeStamp());

            if(measurementDate.compareTo(dateFrom) >= 0 && measurementDate.compareTo(dateTo) <= 0)
            {
                Co2 co2 = new Co2(measurementsArrList.get(i).getcO2());
                co2.setTime(DTimeFormatHelper.getTimeStringFromISO8601Timestamp(measurementsArrList.get(i).getTimeStamp()));
                co2.setDate(DTimeFormatHelper.getDateStringFromISO8601Timestamp(measurementsArrList.get(i).getTimeStamp()));

                co2InDateRange.add(co2);
            }
        }

        return this.co2InDateRange;
    }

    public void addDummyMeasurements()
    {
        measurementsArrList.clear();

        Measurement mes1 = new Measurement("26.2", "56", "672", "2019-05-05T09:45:00Z");
        Measurement mes2 = new Measurement("24.1", "60", "655", "2019-05-05T09:35:00Z");
        Measurement mes3 = new Measurement("27.2", "57", "675", "2019-05-05T09:25:00Z");
        Measurement mes4 = new Measurement("26.4", "59", "660", "2019-05-05T09:15:00Z");
        Measurement mes5 = new Measurement("24.8", "55", "680", "2019-05-05T09:05:00Z");

        measurementsArrList.add(mes1);
        measurementsArrList.add(mes2);
        measurementsArrList.add(mes3);
        measurementsArrList.add(mes4);
        measurementsArrList.add(mes5);

        latestMeasurements.setValue(measurementsArrList);
    }

    public void addDummyGreenhouses() {

        greenhouseArrayList.clear();

        greenhouseArrayList.add(new Greenhouse(1, "Denmark", "Horsens", "8700", "Kattesund 12A"));
        greenhouseArrayList.add(new Greenhouse(2, "Denmark", "Horsens", "8700", "Kattesund 12A"));
        greenhouseArrayList.add(new Greenhouse(3, "Denmark", "Horsens", "8700", "Kattesund 12A"));
        greenhouseArrayList.add(new Greenhouse(4, "Denmark", "Horsens", "8700", "Kattesund 12A"));

        greenhouses.setValue(greenhouseArrayList);
    }


    //Thread to fetch data from web api
    public void fetchLatestMeasurementsFromApi()
    {
        new Thread(new Runnable() {
            @Override
            public void run()
            {
                try
                {
                    //Call retrofit to get latest measurements

                    //latestMeasurements.setValue(arrayList<Measurement>);

                    Thread.sleep(THREAD_DELAY);
                } catch (InterruptedException ignored){}
            }
        }).start();
    }
}