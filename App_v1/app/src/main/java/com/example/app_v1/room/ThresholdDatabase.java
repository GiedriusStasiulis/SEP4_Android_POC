package com.example.app_v1.room;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.app_v1.models.Threshold;

@Database(entities = {Threshold.class}, version = 1, exportSchema = false)
public abstract class ThresholdDatabase extends RoomDatabase {
    private static ThresholdDatabase instance;

    public abstract ThresholdDAO thresholdsDAO();

    public static synchronized ThresholdDatabase getInstance(Context context){
        if(instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(), ThresholdDatabase.class, "threshold_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();

        }
        return instance;

    }
    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void>{
        private ThresholdDAO thresholdDAO;

        private PopulateDbAsyncTask(ThresholdDatabase db){
            thresholdDAO = db.thresholdsDAO();
        }
        @Override
        protected Void doInBackground(Void... voids) {
            thresholdDAO.insert(new Threshold("temperature","10.5","40.8"));
            thresholdDAO.insert(new Threshold("humidity","200","400"));
            thresholdDAO.insert(new Threshold("co2","400","800"));
            return null;
        }
    }
}
