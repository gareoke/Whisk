package windylabs.com.whisk.models.foursquare;

import com.google.gson.annotations.SerializedName;

/**
 * Created by g.anderson on 1/27/16.
 */
public class Category {
    @SerializedName("icon")
    private Icon icon;

    private Category(Icon icon){
        this.icon = icon;
    }

    public Icon getIcon(){
        return this.icon;
    }
}
