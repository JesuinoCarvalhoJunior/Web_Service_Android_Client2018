package com.wsandroid.jcarvalhojr.webserviceandroidclient.Swipe;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

import com.wsandroid.jcarvalhojr.webserviceandroidclient.Adapter.UsuarioAdapter;
import com.wsandroid.jcarvalhojr.webserviceandroidclient.Helpers.getBitmapFactoryHelper;

import com.wsandroid.jcarvalhojr.webserviceandroidclient.Provider.ApplicationContextProvider;
import com.wsandroid.jcarvalhojr.webserviceandroidcliente.R;

import java.util.logging.Handler;

import static com.wsandroid.jcarvalhojr.webserviceandroidclient.Logins.LoginActivity.context;

/**
 * Created by JCarvalhoJr on 11/03/2018.
 */

public class SwipeHelper extends ItemTouchHelper.SimpleCallback {

    UsuarioAdapter usuarioAdapter;
    private Paint paint = new Paint();

    // Context ctx;

    public SwipeHelper(int dragDirs, int swipeDirs) {
        super(dragDirs, swipeDirs);
    }

    // contrutor padrao
    public SwipeHelper(UsuarioAdapter adapter) {
        super(ItemTouchHelper.UP | ItemTouchHelper.DOWN, ItemTouchHelper.LEFT);
        this.usuarioAdapter = adapter;
    }

    /*// contrutor que modifiquei passando context
    public SwipeHelper(UsuarioAdapter adapter, Context context) {
        super(ItemTouchHelper.UP | ItemTouchHelper.DOWN, ItemTouchHelper.LEFT);
        this.usuarioAdapter = adapter;
        this.ctx = context;
    }*/


    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {

        if (viewHolder.getItemViewType() != target.getItemViewType()) {
            return false;
        }
        usuarioAdapter.moveItem(viewHolder.getAdapterPosition(), target.getAdapterPosition());
        return true;
    }

    @Override
    public void onSwiped(final RecyclerView.ViewHolder viewHolder, int direction) {
        final int position = viewHolder.getAdapterPosition();
        if (direction == ItemTouchHelper.LEFT) {
            usuarioAdapter.removeItem(position);
        }

    }

    @Override
    public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder,
                            float dX, float dY, int actionState, boolean isCurrentlyActive) {
        try {

            Bitmap icon;
            if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
                View itemView = viewHolder.itemView;
                float height = (float) itemView.getBottom() - (float) itemView.getTop();
                float width = height / 5;
                viewHolder.itemView.setTranslationX(dX / 5);

                paint.setColor(Color.parseColor("#D32F2F"));
                RectF background = new RectF((float) itemView.getRight() + dX / 5, (float) itemView.getTop(), (float) itemView.getRight(), (float) itemView.getBottom());
                c.drawRect(background, paint);
                icon = getBitmapFactoryHelper.getBitmapFactory(ApplicationContextProvider.getContext(), R.drawable.trash);
                //icon = BitmapFactory.decodeResource(ApplicationContextProvider.getContext().getResources(), R.drawable.ic_action_remove);
                RectF icon_dest = new RectF((float) (itemView.getRight() + dX / 7), (float) itemView.getTop() + width, (float) itemView.getRight() + dX / 20, (float) itemView.getBottom() - width);
                c.drawBitmap(icon, null, icon_dest, paint);
            } else {
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

