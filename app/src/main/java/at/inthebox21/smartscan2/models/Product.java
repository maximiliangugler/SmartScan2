package at.inthebox21.smartscan2.models;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class Product {
    private String depoArtId;
    private String name;
    private List<String> eans = new ArrayList<>();

    public Product(String name, String depoArtId, String ean) {
        this.name = name;
        this.depoArtId = depoArtId;
        this.eans.add(ean);
    }

    public Product(String name, String depoArtId, List<String> eans) {
        this.name = name;
        this.depoArtId = depoArtId;
        this.eans = eans;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDepoArtId() {
        return depoArtId;
    }

    public void setDepoArtId(String depoArtId) {
        this.depoArtId = depoArtId;
    }

    public List<String> getEans() {
        return eans;
    }

    public void setEans(List<String> eans) {
        this.eans = eans;
    }

    public void addEan(String ean) {
        eans.add(ean);
    }

}
