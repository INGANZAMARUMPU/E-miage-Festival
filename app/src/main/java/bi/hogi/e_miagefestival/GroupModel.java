package bi.hogi.e_miagefestival;

import androidx.lifecycle.MutableLiveData;

import java.io.Serializable;

public class GroupModel implements Serializable {
    public String id;
    public  String artiste = "obtention en cours...";
    public String texte = "obtention en cours...";
    public String web, image, scene, jour, heure;
    public int time;

    public GroupModel(String id) {
        this.id = id;
    }

    public GroupModel(String artiste, String texte, String web, String image, String scene, String jour, String heure, int time) {
        this.artiste = artiste;
        this.texte = texte;
        this.web = web;
        this.image = image;
        this.scene = scene;
        this.jour = jour;
        this.heure = heure;
        this.time = time;
    }

    @Override
    public String toString() {
        return "GroupModel{artiste='" + artiste + "'}";
    }
}
