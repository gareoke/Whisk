package windylabs.com.whisk.models.foursquare;

import com.google.gson.annotations.SerializedName;

/**
 * Created by g.anderson on 1/26/16.
 */
public class Meta {
    @SerializedName("code")
    private String code;

    @SerializedName("requestId")
    private String requestId;

    public Meta(String code, String requestId){
        this.code = code;
        this.requestId = requestId;
    }

    public String getCode(){
        return this.code;
    }
}