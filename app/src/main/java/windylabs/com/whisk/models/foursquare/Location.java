package windylabs.com.whisk.models.foursquare;

import com.google.gson.annotations.SerializedName;

/**
 * Created by g.anderson on 1/25/16.
 */
public class Location {
    @SerializedName("address")
    private String address;

    @SerializedName("city")
    private String city;

    @SerializedName("state")
    private String state;

    private Location(String address, String city, String state){
        this.address = address;
        this.city = city;
        this.state = state;
    }

    public String getFormattedLocation(){
        return this.address + ", " + this.city + " " + this.state;
    }

    public String getAddress(){
        return this.address;
    }

    public String getCity(){
        return this.city;
    }

    public String getState(){
        return this.state;
    }
}
