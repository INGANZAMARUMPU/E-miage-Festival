package bi.hogi.e_miagefestival;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

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
    private RecyclerView recycler;
    private String reminder;
    private ArrayList<GroupModel> bands;
    private CardGroup adaptateur;
    ProgressBar progressbar;
    SwipeRefreshLayout swipe_refresh;

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
        swipe_refresh.setOnRefreshListener(() -> {
            chargerGroupModels();
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
            .addHeader("Cookie", reminder)
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
                    JSONObject json_items = new JSONObject(json);
                    JSONObject json_item;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}