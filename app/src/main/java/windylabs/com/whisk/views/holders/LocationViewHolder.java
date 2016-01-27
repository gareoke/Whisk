package windylabs.com.whisk.views.holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.InjectView;
import windylabs.com.whisk.R;

/**
 * Created by g.anderson on 1/26/16.
 */
public class LocationViewHolder extends RecyclerView.ViewHolder {
    @InjectView(R.id.location_image) ImageView locationIcon;
    @InjectView(R.id.location_name) TextView locationName;

    public LocationViewHolder(View itemView) {
        super(itemView);

        locationName = (TextView) itemView.findViewById(R.id.location_name);
    }

    public void setLocationName(String locationName){
        this.locationName.setText(locationName);
    }

    public TextView getLocationNameTextView(){
        return this.locationName;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
