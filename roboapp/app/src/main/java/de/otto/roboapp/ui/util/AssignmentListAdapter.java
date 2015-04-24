package de.otto.roboapp.ui.util;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import de.otto.roboapp.R;
import de.otto.roboapp.RoboAppController;

/**
 * Created by samaeder on 23.03.15.
 */
public class AssignmentListAdapter extends BaseAdapter {
    private Activity activity;
    private RoboAppController roboAppController;

    public AssignmentListAdapter(Activity activity) {
        this.activity = activity;
        roboAppController  = (RoboAppController) activity.getApplicationContext();
    }

    @Override
    public int getCount() {
        return roboAppController.getDataModel().getAssignedPlayer().size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
      LinearLayout linearLayout = new LinearLayout(activity);
        ImageView profil = new ImageView(activity);
        TextView assignedPlayer = new TextView(activity);
        TextView assignedRobo = new TextView(activity);
        TextView seperatorView = new TextView(activity);
        final Button deselectRoboButton = new Button(activity);
        ImageView readyButton = new ImageView(activity);

        final String assignedPlayerName = roboAppController.getDataModel().getAssignedPlayer().get(position).getName();
        final String assignedRoboName = roboAppController.getDataModel().getAssignedRobo().get(position).getName();
        String separator = " @ ";

        assignedPlayer.setText(assignedPlayerName);
        assignedRobo.setText(assignedRoboName);
        seperatorView.setText(separator);
        deselectRoboButton.setText("x");

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(100, LinearLayout.LayoutParams.MATCH_PARENT);
        params.setMargins(40, 10, 40, 10);
        params.weight = 1;
        readyButton.setLayoutParams(params);
        readyButton.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        if (roboAppController.getDataModel().getAssignedPlayer().get(position).isReady()) {
            readyButton.setBackgroundResource(R.drawable.ready_slider);
        } else {
            readyButton.setBackgroundResource(R.drawable.waiting_slider);
        }

        deselectRoboButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                roboAppController.deselect();
            }
        });

        readyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                roboAppController.readyStateChange(!roboAppController.getDataModel().getPlayerByName(roboAppController.getDataModel().getCurrentPlayerName()).isReady());
            }
        });

        linearLayout.addView(assignedPlayer);
        linearLayout.addView(seperatorView);
        linearLayout.addView(assignedRobo);
        linearLayout.addView(readyButton);
        linearLayout.addView(deselectRoboButton);


        return linearLayout;
    }
}
