package bi.hogi.e_miagefestival;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class DetailsActivity extends AppCompatActivity {

    ImageView img_group_logo;
    TextView txt_artiste, txt_details, txt_website, txt_jour, txt_heure, txt_scene;
    Button btn_make_fav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        img_group_logo = findViewById(R.id.img_group_logo);
        txt_artiste = findViewById(R.id.txt_artiste);
        txt_details = findViewById(R.id.txt_details);
        txt_website = findViewById(R.id.txt_website);
        txt_jour = findViewById(R.id.txt_jour);
        txt_heure = findViewById(R.id.txt_heure);
        txt_scene = findViewById(R.id.txt_scene);
        btn_make_fav = findViewById(R.id.btn_make_fav);
    }
}