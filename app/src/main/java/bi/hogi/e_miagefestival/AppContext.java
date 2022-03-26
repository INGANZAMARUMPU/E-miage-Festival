package bi.hogi.e_miagefestival;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class AppContext extends Application {

    private final String TAG = "=== APP CONTEXT ===";
    public MutableLiveData<ArrayList<String>> bands = new MutableLiveData<>();

    public void init() {
        if(bands.getValue() == null){
            bands.setValue(new ArrayList<>());
        }
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel("emiage", "emiage", NotificationManager.IMPORTANCE_LOW);
            NotificationManager manager = getSystemService((NotificationManager.class));
            manager.createNotificationChannel(channel);
        }
    }

    public boolean inFavorites(String id) {
        for(String fav_id:this.bands.getValue()){
            if(fav_id.equals(id)){
                return true;
            }
        }
        return false;
    }

    public void toggleFavorite(GroupModel band) {
        ArrayList<String> fav_bands = this.bands.getValue();
        if(fav_bands.contains(band.id)){
            fav_bands.remove(band.id);
        } else {
            fav_bands.add(band.id);
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    NotificationCompat.Builder builder = new NotificationCompat.Builder(AppContext.this, "emiage");
                    builder.setContentTitle(band.artiste);
                    builder.setContentText("Un autre concert "+ band.scene +" de \""+ band.artiste + "\" est planifié ce "+ band.jour);
                    builder.setSmallIcon(R.mipmap.ic_launcher);
                    builder.setAutoCancel(true);

                    NotificationManagerCompat manager = NotificationManagerCompat.from(AppContext.this);
                    manager.notify(band.time, builder.build());
                }
            }, band.time*1000);
        }
        this.bands.setValue(fav_bands);
    }
}