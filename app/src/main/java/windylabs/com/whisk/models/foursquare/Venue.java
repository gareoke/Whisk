package windylabs.com.whisk.models.foursquare;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by g.anderson on 1/25/16.
 */
public class Venue {
    @SerializedName("name")
    private String name;

    @SerializedName("phone")
    private String phone;

    @SerializedName("location")
    private Location location;

    @SerializedName("categories")
    private ArrayList<Category> categories;

    public Venue(String name, String phone, Location location, ArrayList<Category> categories){
        this.name = name;
        this.phone = phone;
        this.location = location;
        this.categories = categories;
    }

    public String getName(){
        return this.name;
    }

    public Location getLocation(){
        return this.location;
    }
    public ArrayList<Category> getCategories(){
        return this.categories;
    }

}
