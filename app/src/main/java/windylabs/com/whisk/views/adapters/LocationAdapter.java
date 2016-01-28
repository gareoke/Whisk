package windylabs.com.whisk.views.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import windylabs.com.whisk.R;
import windylabs.com.whisk.models.foursquare.Category;
import windylabs.com.whisk.models.foursquare.Venue;
import windylabs.com.whisk.views.holders.LocationViewHolder;

/**
 * Created by g.anderson on 1/26/16.
 */
public class LocationAdapter extends RecyclerView.Adapter<LocationViewHolder>{
    ArrayList<Venue> locationList;

    private Context context; //wont this cause memory leaks?...


    public LocationAdapter(Context context) {
        super();

        this.context = context;
        this.locationList = new ArrayList();
    }

    @Override
    public LocationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.location_item, parent, false);

        TextView tv = (TextView) view.findViewById(R.id.location_name);
        Typeface face=Typeface.createFromAsset(parent.getContext().getAssets(),
                "fonts/Roboto-Bold.ttf");
        tv.setTypeface(face);

        return new LocationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(LocationViewHolder holder, int position) {
        if (locationList.isEmpty())
            return;

        ArrayList<Category> categories = locationList.get(position).getCategories();

        String url = categories!=null && !categories.isEmpty() ? locationList.get(position).getCategories().get(0).getIcon().getIconUrl("64") : "";

        if (!url.isEmpty()){
            Picasso.with(this.context)
                    .load(url)
                    .error(R.drawable.location_default)
                    .into(holder.getIconView());
        }
        holder.setLocationName(locationList.get(position).getName());
        holder.setLocationDetails(locationList.get(position).getLocation().getFormattedLocation());
    }

    @Override
    public void onBindViewHolder(LocationViewHolder holder, int position, List<Object> payloads) {
        super.onBindViewHolder(holder, position, payloads);
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public void setHasStableIds(boolean hasStableIds) {
        super.setHasStableIds(hasStableIds);
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public int getItemCount() {
        return locationList.size();
    }

    @Override
    public void onViewRecycled(LocationViewHolder holder) {
        super.onViewRecycled(holder);
    }

    @Override
    public boolean onFailedToRecycleView(LocationViewHolder holder) {
        return super.onFailedToRecycleView(holder);
    }

    @Override
    public void onViewAttachedToWindow(LocationViewHolder holder) {
        super.onViewAttachedToWindow(holder);
    }

    @Override
    public void onViewDetachedFromWindow(LocationViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
    }

    @Override
    public void registerAdapterDataObserver(RecyclerView.AdapterDataObserver observer) {
        super.registerAdapterDataObserver(observer);
    }

    @Override
    public void unregisterAdapterDataObserver(RecyclerView.AdapterDataObserver observer) {
        super.unregisterAdapterDataObserver(observer);
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
    }

    public void addItem(Venue venue){
        locationList.add(0,venue);
    }
}
