package bi.hogi.e_miagefestival;

public class GroupModel {
    public  String artiste, texte, web, image, scene, jour, heure;
    public int time;
    public boolean is_favorite = false;

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
}