package windylabs.com.whisk.models.foursquare;

import com.google.gson.annotations.SerializedName;

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

    public Venue(String name, String phone, Location location){
        this.name = name;
        this.phone = phone;
        this.location = location;
    }

    public String getName(){
        return this.name;
    }

    public Location getLocation(){
        return this.location;
    }
}
