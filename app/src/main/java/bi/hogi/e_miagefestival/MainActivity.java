package bi.hogi.e_miagefestival;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "=== PIZZA ===";
    private RecyclerView recycler;
    private String reminder;
    private ArrayList<GroupModel> bands;
    private CardGroup adaptateur;
    ProgressBar progressbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recycler = findViewById(R.id.liste_bands);
        progressbar = findViewById(R.id.progressbar);
        bands = new ArrayList<>();
        adaptateur = new CardGroup(this, bands);
        recycler.setAdapter(adaptateur);
        chargerGroupModels();
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
        }
        return super.onOptionsItemSelected(item);
    }
    private void chargerGroupModels() {
        progressbar.setVisibility(View.VISIBLE);
        OkHttpClient client = new OkHttpClient();
        HttpUrl.Builder urlBuilder = HttpUrl.parse(HOST.URL).newBuilder();

        String url = urlBuilder.build().toString();
        Request request = new Request.Builder()
                .url(url)
                .addHeader("Cookie", reminder)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) { }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String json = response.body().string();
                try {
                    JSONObject json_items = new JSONObject(json);
                    JSONObject json_item;
                    Iterator<String> keys = json_items.keys();
                    GroupModel band;
                    while (keys.hasNext()){
                        String key = keys.next();
                        json_item = json_items.getJSONObject(key);
                        band = new GroupModel(
                                json_item.getDouble("prix"),
                                json_item.getString("ingredients"),
                                json_item.getString("image"),
                                key
                        );
                        bands.add(band);
                    }
                    ListeActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            adaptateur.notifyDataSetChanged();
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}