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
    View parentView;
    @InjectView(R.id.location_icon) ImageView locationIcon;
    @InjectView(R.id.location_name) TextView locationName;
    @InjectView(R.id.location_details) TextView locationDetails;

    public LocationViewHolder(View itemView) {
        super(itemView);

        this.parentView = itemView;

        this.locationIcon = (ImageView) itemView.findViewById(R.id.location_icon);
        this.locationName = (TextView) itemView.findViewById(R.id.location_name);
        this.locationDetails = (TextView) itemView.findViewById(R.id.location_details);
    }

    public void setLocationName(String locationName){
        this.locationName.setText(locationName);
    }

    public void setLocationDetails(String locationDetails){
        this.locationDetails.setText(locationDetails);
    }

    public ImageView getIconView(){
        return this.locationIcon;
    }

    public TextView getLocationNameTextView(){
        return this.locationName;
    }

    public View getParentView(){ return this.parentView; }

    @Override
    public String toString() {
        return super.toString();
    }
}
