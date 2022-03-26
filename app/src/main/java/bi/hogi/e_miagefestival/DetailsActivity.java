package bi.hogi.e_miagefestival;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.io.Serializable;

public class DetailsActivity extends AppCompatActivity {

    ImageView img_group_logo;
    TextView txt_artiste, txt_details, txt_website, txt_jour, txt_heure, txt_scene;
    Button btn_make_fav;
    private GroupModel band;
    private AppContext app_context;

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

        band = (GroupModel) getIntent().getSerializableExtra("band");

        txt_artiste.setText(band.artiste);
        txt_details.setText(band.texte);
        txt_website.setText(band.web);
        txt_jour.setText(band.jour);
        txt_heure.setText(band.heure);
        txt_scene.setText(band.scene);
        Glide.with(this).load(HOST.URL+"/"+band.image).into(img_group_logo);

        app_context = ((AppContext) this.getApplication());
        app_context.init();

        if(app_context.inFavorites(band.id)){
            btn_make_fav.setText("supprimer des favoris");
        } else {
            btn_make_fav.setText("ajouter au favoris");
        }
        btn_make_fav.setOnClickListener(view -> {
            if(app_context.inFavorites(band.id)){
                app_context.toggleFavorite(band);
                btn_make_fav.setText("ajouter au favoris");
            } else {
                app_context.toggleFavorite(band);
                btn_make_fav.setText("supprimer des favoris");
            }
        });
    }
}