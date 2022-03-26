package bi.hogi.e_miagefestival;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "=== PIZZA ===";
    public ArrayList<String> jours = new ArrayList<>(), scenes = new ArrayList<>();
    private RecyclerView recycler;
    private String reminder;
    private ArrayList<GroupModel> bands = new ArrayList<>();
    private CardGroup adaptateur;
    ProgressBar progressbar;
    SwipeRefreshLayout swipe_refresh;
    public AppContext app_context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recycler = findViewById(R.id.liste_bands);
        progressbar = findViewById(R.id.progressbar);
        swipe_refresh = findViewById(R.id.swipe_refresh);
        bands = new ArrayList<>();
        adaptateur = new CardGroup(this, bands);
        recycler.setAdapter(adaptateur);
        chargerGroupModels();

        app_context = ((AppContext) this.getApplication());
        app_context.init();

        app_context.bands.observe(this, bands -> {
            adaptateur.notifyDataSetChanged();
        });

        swipe_refresh.setOnRefreshListener(() -> {
            if(bands.size()==0)
                chargerGroupModels();
            else
                swipe_refresh.setRefreshing(false);
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.action_filter) {
            new FormFilter(this).show();
        }
        return super.onOptionsItemSelected(item);
    }
    private void chargerGroupModels() {
        progressbar.setVisibility(View.VISIBLE);
        OkHttpClient client = new OkHttpClient();
        HttpUrl.Builder urlBuilder = HttpUrl.parse(HOST.URL+"/liste").newBuilder();

        String url = urlBuilder.build().toString();
        Request request = new Request.Builder()
            .url(url)
            .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                MainActivity.this.runOnUiThread(() -> {
                    Toast.makeText(MainActivity.this, "Problème de connectivité", Toast.LENGTH_LONG).show();
                    progressbar.setVisibility(View.GONE);
                    swipe_refresh.setRefreshing(false);
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String json = response.body().string();
                try {
                    JSONArray json_items = new JSONObject(json).getJSONArray("data");
                    GroupModel band;
                    String id;
                    for (int i=0; i<json_items.length(); i++){
                        id = (String) json_items.get(i);
                        band = new GroupModel(id);
                        band.artiste = id;
                        bands.add(band);
                    }
                    MainActivity.this.runOnUiThread(() -> {
                        adaptateur.setData(bands);
                        adaptateur.notifyDataSetChanged();
                        fetchMoreInfos(0);
                        progressbar.setVisibility(View.GONE);
                        swipe_refresh.setRefreshing(false);
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void fetchMoreInfos(int i) {
        if (i >= bands.size()) return;
        GroupModel band = bands.get(i);
        if(!band.artiste.equals(band.id)) return;
        OkHttpClient client = new OkHttpClient();
        HttpUrl.Builder urlBuilder = HttpUrl.parse(HOST.URL+"/info/"+band.artiste).newBuilder();

        String url = urlBuilder.build().toString();
        Request request = new Request.Builder().url(url).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) { }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String json = response.body().string();
                try {
                    JSONObject json_object = new JSONObject(json).getJSONObject("data");
                    GroupModel fetched_band = new GroupModel(
                        json_object.getString("artiste"),
                        json_object.getString("texte"),
                        json_object.getString("web"),
                        json_object.getString("image"),
                        json_object.getString("scene"),
                        json_object.getString("jour"),
                        json_object.getString("heure"),
                        json_object.getInt("time")
                    );
                    fetched_band.id = band.id;
                    MainActivity.this.runOnUiThread(() -> {
//                        if(jours.contains(fetched_band.jour))
//                        if(scenes.contains(fetched_band.scene))
                        bands.set(i, fetched_band);
                        adaptateur.setData(bands);
                        adaptateur.notifyItemChanged(i);
                        fetchMoreInfos(i+1);
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void filter(String jours, String scene) {

    }
}