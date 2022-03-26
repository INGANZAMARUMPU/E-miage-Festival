package bi.hogi.e_miagefestival;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;

public class AppContext extends Application {

    private final String TAG = "=== APP CONTEXT ===";
    public MutableLiveData<ArrayList<String>> bands = new MutableLiveData<>();

    public void init() {
        if(bands.getValue() == null){
            bands.setValue(new ArrayList<>());
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

    public void toggleFavorite(String id) {
        ArrayList<String> fav_bands = this.bands.getValue();
        if(fav_bands.contains(id)){
            fav_bands.remove(id);
        } else {
            fav_bands.add(id);
        }
        this.bands.setValue(fav_bands);
    }
}