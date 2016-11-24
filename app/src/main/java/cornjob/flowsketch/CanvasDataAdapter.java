package cornjob.flowsketch;

import android.content.Context;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static cornjob.flowsketch.SaveDialogFragment.LOG_TAG;

/**
 * Created by Nguyen on 11/20/2016.
 */

public class CanvasDataAdapter extends RecyclerView.Adapter<CanvasDataAdapter.MyViewHolder> {

    private Context mContext;
    private List<CanvasData> canvasList;
    private static int current_cid;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, date;
        public ImageView thumbnail, overflow;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            date = (TextView) view.findViewById(R.id.date);
           // thumbnail = (ImageView) view.findViewById(R.id.thumbnail);
            overflow = (ImageView) view.findViewById(R.id.overflow);
        }
    }


    public CanvasDataAdapter(Context mContext, ArrayList<CanvasData> canvasList) {
        this.mContext = mContext;
        this.canvasList = canvasList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_view, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        CanvasData canvas = canvasList.get(position);
        holder.title.setText(canvas.getCanvasName());
        holder.date.setText(canvas.getDate());

        current_cid= canvas.getCid();
        // loading album cover using Glide library
       // Glide.with(mContext).load(album.getThumbnail()).into(holder.thumbnail);

        holder.overflow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopupMenu(holder.overflow);
            }
        });
    }

    /**
     * Showing popup menu when tapping on 3 dots
     */
    private void showPopupMenu(View view) {
        // inflate menu
        PopupMenu popup = new PopupMenu(mContext, view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_on_load, popup.getMenu());
        popup.setOnMenuItemClickListener(new MyMenuItemClickListener());
        popup.show();
    }

    /**
     * Click listener for popup menu items
     */
    class MyMenuItemClickListener implements PopupMenu.OnMenuItemClickListener {

        public MyMenuItemClickListener() {
        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.edit_canvas:
                    Toast.makeText(mContext, "Edit of canvas" + current_cid , Toast.LENGTH_SHORT).show();
                    return true;
                case R.id.edit_name:
                    onEditName();
                    Toast.makeText(mContext, "Edit Name", Toast.LENGTH_SHORT).show();
                    return true;
                case R.id.delete_canvas:
                    onDeleteCanvas();
                    Toast.makeText(mContext, "Delete", Toast.LENGTH_SHORT).show();
                    return true;
                default:
            }
            return false;
        }
    }

    @Override
    public int getItemCount() {
        return canvasList.size();
    }

    public void onEdit(){

    }

    public void onEditName(){
    }

    public void onDeleteCanvas(){

    }
}
