package windylabs.com.whisk.models.foursquare;

import com.google.gson.annotations.SerializedName;

/**
 * Created by g.anderson on 1/27/16.
 */
public class Icon {
    @SerializedName("prefix")
    private String prefix;

    @SerializedName("suffix")
    private String suffix;

    private Icon(String prefix, String suffix){
        this.prefix = prefix;
        this.suffix = suffix;
    }

    public String getIconUrl(String size){
        return prefix + size + suffix;
    }
}