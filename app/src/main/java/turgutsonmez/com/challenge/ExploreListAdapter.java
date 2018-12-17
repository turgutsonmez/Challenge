package turgutsonmez.com.challenge;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import turgutsonmez.com.challenge.Model.Explore.Item_;

public class ExploreListAdapter extends ArrayAdapter<Item_> {

  private LayoutInflater layoutInflater_;



  private static class ViewHolder {
    TextView txt_CafeName;
    TextView txt_Area;
    TextView txt_City;
    TextView txt_Country;
    TextView txt_popup_CafeName;
  }

  public ExploreListAdapter(Context context, int layout, List<Item_> objects) {
    super(context, layout, objects);
    layoutInflater_ = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
  }


  @Override
  public View getView(int position, View convertView, ViewGroup parent) {

    Item_ item_ = getItem(position);

    ViewHolder viewHolder;
    if (convertView == null) {
      // If there's no view to re-use, inflate a brand new view for row
      viewHolder = new ViewHolder();
      LayoutInflater inflater = LayoutInflater.from(getContext());
      convertView = inflater.inflate(R.layout.item_list, parent, false);

      // Lookup view for data population
      viewHolder.txt_Area = (TextView) convertView.findViewById(R.id.txt_Area);
      viewHolder.txt_CafeName = (TextView) convertView.findViewById(R.id.txt_CafeName);
      viewHolder.txt_City = (TextView) convertView.findViewById(R.id.txt_City);
      viewHolder.txt_Country = (TextView) convertView.findViewById(R.id.txt_Country);
      viewHolder.txt_popup_CafeName = convertView.findViewById(R.id.txt_popup_CafeName);

      // Cache the viewHolder object inside the fresh view
      convertView.setTag(viewHolder);
    } else {
      // View is being recycled, retrieve the viewHolder object from tag
      viewHolder = (ViewHolder) convertView.getTag();
    }

    String name = item_.getVenue().getName();
    String area = item_.getVenue().getLocation().getAddress();
    String city = item_.getVenue().getLocation().getCity();
    String country = item_.getVenue().getLocation().getCountry();
    //String popup_CafeName = item_.getVenue().getName();

    viewHolder.txt_CafeName.setText(name);
    viewHolder.txt_Country.setText(country);
    viewHolder.txt_City.setText(city);
    viewHolder.txt_Area.setText(area);
    //viewHolder.txt_popup_CafeName.setText(popup_CafeName);

    return convertView;
  }


}
