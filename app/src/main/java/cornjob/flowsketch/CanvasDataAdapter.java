package cornjob.flowsketch;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
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

import com.android.volley.Response;

import java.util.ArrayList;
import java.util.List;

import static cornjob.flowsketch.DeleteDialogFragment.LOG_TAG;

/**
 * Created by Nguyen on 11/20/2016.
 */

public class CanvasDataAdapter extends RecyclerView.Adapter<CanvasDataAdapter.MyViewHolder> {

    public interface OnItemClickListener {
        void onClick(View v, int position);
    }

    private OnItemClickListener listener;
    private Context mContext;
    private List<CanvasData> canvasList;
    CanvasData canvas;
    private static int current_cid;
    DeleteDialogFragment deleteFragment;
    LoadFragment loadFragment;




    public class MyViewHolder extends RecyclerView.ViewHolder  implements View.OnClickListener{
        public TextView title, date;
        public int thisid;
        public ImageView thumbnail, overflow;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            date = (TextView) view.findViewById(R.id.date);
           // thumbnail = (ImageView) view.findViewById(R.id.thumbnail);
            overflow = (ImageView) view.findViewById(R.id.overflow);
            view.setOnClickListener(this);
        }



        @Override
        public void onClick(View v) {
            //itemListener.recyclerViewListClicked(v, this.getAdapterPosition());

            int position=getAdapterPosition();
            CanvasData canvas= canvasList.get(position);
            int view_current_cid= canvas.getCid();
            //Toast.makeText(mContext, "view Id canvas" +view_current_cid, Toast.LENGTH_SHORT).show();


        }
    }


    public CanvasDataAdapter(Context mContext, ArrayList<CanvasData> canvasList, OnItemClickListener listener) {
        this.mContext = mContext;
        this.canvasList = canvasList;
        this.listener= listener;
       // this.itemListener = itemListener;
        this.deleteFragment=deleteFragment;
        this.loadFragment=loadFragment;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_view, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        canvas = canvasList.get(position);
        holder.title.setText(canvas.getCanvasName());
        holder.date.setText(canvas.getDate());
        //current_cid= canvas.getCid();
        holder.thisid=canvas.getCid();

        holder.overflow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // current_cid=holder.thisid;
               // showPopupMenu(holder.overflow);
                listener.onClick(view, position);

            }
        });
    }

    /**
     * Showing popup menu when tapping on 3 dots

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
                    Toast.makeText(mContext, "Edit of canvas" +current_cid , Toast.LENGTH_SHORT).show();
                    return true;
                case R.id.edit_name:
                    onEditName();
                    Toast.makeText(mContext, "Edit Name", Toast.LENGTH_SHORT).show();
                    return true;
                case R.id.delete_canvas:
                    onDeleteCanvas();
                    Toast.makeText(mContext, "Deleted", Toast.LENGTH_SHORT).show();
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
        final String TAG = "Delete";
        String URL= "http://flowsketchpi.duckdns.org:8080/sketch_flow/v1/delete/";
        String id= String.valueOf(current_cid);
        URL=URL.concat(id);




    }

    public void onDeleteCanvas() {
        String id = String.valueOf(current_cid);

        Fragment deleteFragment = new DeleteDialogFragment();
        Bundle args = new Bundle();
        args.putString("id", id);
        deleteFragment.setArguments(args);
       // FragmentManager manager = ((Activity)mContext).getFragmemtManager();

        //FragmentTransaction transaction = manager.beginTransaction();
        //transaction.replace(R.id., deleteFragment);
        //transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        //transaction.addToBackStack(null);
        //transaction.commit();


    }

    public void setFragment(DeleteDialogFragment frag){

        //FragmentTransaction transaction = ((MainActivity) mContext).setFragment();
       // transaction.replace(R.id.delete_fragment, frag);
    }
}
