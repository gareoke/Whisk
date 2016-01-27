package windylabs.com.whisk.utils;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

import windylabs.com.whisk.models.foursquare.Meta;
import windylabs.com.whisk.models.foursquare.ResponseHolder;

/**
 * Created by g.anderson on 1/25/16.
 */
public class FoursquareResponseDeserializer implements JsonDeserializer<Meta> {
    private final static String TAG = FoursquareResponseDeserializer.class.getSimpleName();

    @Override
    public Meta deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        Log.d(TAG, "MainActivity -- " + json.getAsString());

        // Get the "content" element from the parsed JSON
//        JsonElement venues = json.getAsJsonObject().get("response").getAsJsonObject().get("venues");

        // Deserialize it. You use a new instance of Gson to avoid infinite recursion
        // to this deserializer
//        return new Gson().fromJson(venues, ResponseHolder.class);

        JsonElement meta = json.getAsJsonObject().get("meta");

        return new Gson().fromJson(meta, Meta.class);
    }
}