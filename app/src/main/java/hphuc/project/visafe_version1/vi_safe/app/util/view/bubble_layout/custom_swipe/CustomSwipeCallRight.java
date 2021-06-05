package hphuc.project.visafe_version1.vi_safe.app.util.view.bubble_layout.custom_swipe;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.view.MotionEvent;
import android.view.View;

import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import hphuc.project.visafe_version1.R;
import hphuc.project.visafe_version1.core.app.util.SwipeControllerActions;
import hphuc.project.visafe_version1.vi_safe.app.Utils;

import static androidx.recyclerview.widget.ItemTouchHelper.ACTION_STATE_SWIPE;
import static androidx.recyclerview.widget.ItemTouchHelper.LEFT;

public class CustomSwipeCallRight extends ItemTouchHelper.Callback {
    private boolean swipeBack = false;

    private ButtonsState buttonShowedState = ButtonsState.GONE;

    private RectF buttonInstance = null;

    private RecyclerView.ViewHolder currentItemViewHolder = null;

    private final SwipeControllerActions buttonsActions;

    private static final float buttonWidth = Utils.convertDpToPx(50f); // 100f cho 2 button

    public CustomSwipeCallRight(SwipeControllerActions buttonsActions, Context context) {
        this.buttonsActions = buttonsActions;
        this.context = context;
    }

    @Override
    public int getMovementFlags(@NotNull RecyclerView recyclerView, @NotNull RecyclerView.ViewHolder viewHolder) {
        return makeMovementFlags(0, LEFT);
    }

    @Override
    public boolean onMove(@NotNull RecyclerView recyclerView, @NotNull RecyclerView.ViewHolder viewHolder, @NotNull RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(@NotNull RecyclerView.ViewHolder viewHolder, int direction) {

    }

    @Override
    public int convertToAbsoluteDirection(int flags, int layoutDirection) {
        if (swipeBack) {
            swipeBack = buttonShowedState != ButtonsState.GONE;
            return 0;
        }
        return super.convertToAbsoluteDirection(flags, layoutDirection);
    }

    @Override
    public void onChildDraw(@NotNull Canvas c, @NotNull RecyclerView recyclerView, @NotNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        if (actionState == ACTION_STATE_SWIPE) {
            if (buttonShowedState != ButtonsState.GONE) {
                if (buttonShowedState == ButtonsState.LEFT_VISIBLE) dX = Math.max(dX, buttonWidth);
                if (buttonShowedState == ButtonsState.RIGHT_VISIBLE)
                    dX = Math.min(dX, -buttonWidth);
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            } else {
                setTouchListener(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
        }

        if (buttonShowedState == ButtonsState.GONE) {
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }
        currentItemViewHolder = viewHolder;
    }

    @SuppressLint("ClickableViewAccessibility")
    private void setTouchListener(final Canvas c, final RecyclerView recyclerView, final RecyclerView.ViewHolder viewHolder, final float dX, final float dY, final int actionState, final boolean isCurrentlyActive) {
        recyclerView.setOnTouchListener((v, event) -> {
            swipeBack = event.getAction() == MotionEvent.ACTION_CANCEL || event.getAction() == MotionEvent.ACTION_UP;
            if (swipeBack) {
                if (dX < -buttonWidth) buttonShowedState = ButtonsState.RIGHT_VISIBLE;
                else if (dX > buttonWidth) buttonShowedState = ButtonsState.LEFT_VISIBLE;

                if (buttonShowedState != ButtonsState.GONE) {
                    setTouchDownListener(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
                    setItemsClickable(recyclerView, false);
                }
            }
            return false;
        });
    }

    @SuppressLint("ClickableViewAccessibility")
    private void setTouchDownListener(final Canvas c, final RecyclerView recyclerView, final RecyclerView.ViewHolder viewHolder, final float dX, final float dY, final int actionState, final boolean isCurrentlyActive) {
        recyclerView.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                setTouchUpListener(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
            return false;
        });
    }

    @SuppressLint("ClickableViewAccessibility")
    private void setTouchUpListener(final Canvas c, final RecyclerView recyclerView, final RecyclerView.ViewHolder viewHolder, final float dX, final float dY, final int actionState, final boolean isCurrentlyActive) {
        recyclerView.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                super.onChildDraw(c, recyclerView, viewHolder, 0F, dY, actionState, isCurrentlyActive);
                recyclerView.setOnTouchListener((v1, event1) -> false);
                setItemsClickable(recyclerView, true);
                swipeBack = false;

                if (buttonsActions != null && buttonInstance != null && buttonInstance.contains(event.getX(), event.getY())) {
                    View view = viewHolder.itemView;
                    if (event.getX() < view.getRight() - buttonWidth / 2 - paddingDrawable &&
                            event.getX() > view.getRight() - buttonWidth) {
                        buttonsActions.onLeftClicked(viewHolder.getBindingAdapterPosition());
                    }
                    if (event.getX() > view.getRight() - buttonWidth - paddingDrawable &&
                            event.getX() <= view.getRight()) {
                        buttonsActions.onRightClicked(viewHolder.getBindingAdapterPosition());
                    }
                }
                buttonShowedState = ButtonsState.GONE;
                currentItemViewHolder = null;
            }
            return false;
        });
    }

    private void setItemsClickable(RecyclerView recyclerView, boolean isClickable) {
        for (int i = 0; i < recyclerView.getChildCount(); ++i) {
            recyclerView.getChildAt(i).setClickable(isClickable);
        }
    }

    Context context;
    int paddingDrawable = (int) Utils.convertDpToPx(12f);

    private void drawButtons(Canvas c, RecyclerView.ViewHolder viewHolder, Drawable drawable, Drawable drawable2) {
        float corners = Utils.convertDpToPx(4f);
        View itemView = viewHolder.itemView;
        Paint p = new Paint();

        // vẽ lại màu của item
        RectF itemAbove = new RectF(itemView.getLeft(),
                itemView.getTop(),
                itemView.getRight() - buttonWidth,
                itemView.getBottom());

        p.setColor(ActivityCompat.getColor(context, R.color.color_F2F2F2));
        c.drawRoundRect(itemAbove, corners, corners, p);
        c.save();

        int sizeNeumorphirm = (int) Utils.convertDpToPx(12f);
        // vẽ 2 cái icon
        RectF rightButton = new RectF(itemView.getLeft(),
                itemView.getTop(),
                itemView.getRight(),
                itemView.getBottom());

        p.setColor(ActivityCompat.getColor(context, R.color.color_f9fafc));
        c.drawRoundRect(rightButton, corners, corners, p);


        // draw icon remove
        drawDrawable(drawable, c,
                (int) ((int) rightButton.right - buttonWidth / 2 - paddingDrawable), // /4 cho 2 button
                (int) rightButton.centerY() - paddingDrawable,
                p);

//        // draw icon edit
//        p.setColor(ActivityCompat.getColor(context, R.color.color_f9fafc));
//        drawDrawable(drawable2, c,
//                (int) (itemView.getRight() - buttonWidth),
//                (int) (rightButton.centerY() - paddingDrawable),
//                p);
//
//        // draw line
//        p.setColor(ActivityCompat.getColor(context, R.color.color_ebebeb));
//        p.setStrokeWidth(2f);
//        c.drawLine(itemView.getRight() - buttonWidth / 2 - paddingDrawable,
//                itemView.getTop(),
//                itemView.getRight() - buttonWidth / 2 - paddingDrawable,
//                itemView.getBottom(),
//                p);

        buttonInstance = null;
        if (buttonShowedState == ButtonsState.RIGHT_VISIBLE) {
            buttonInstance = rightButton;
        }
    }

    private void drawDrawable(Drawable draw, Canvas c, int left, int top, Paint p) {
        int sizeDrawable = (int) Utils.convertDpToPx(24f);
        Bitmap bitmap = convertToBitmap(draw, sizeDrawable, sizeDrawable);
        c.drawBitmap(bitmap, left, top, p);
    }

    public Bitmap convertToBitmap(Drawable drawable, int widthPixels, int heightPixels) {
        Bitmap mutableBitmap = Bitmap.createBitmap(widthPixels, heightPixels, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(mutableBitmap);
        drawable.setBounds(0,
                0,
                widthPixels,
                heightPixels);
        drawable.draw(canvas);
        return mutableBitmap;
    }

    private void drawText(String text, Canvas c, RectF button, Paint p) {
        float textSize = 60;
        p.setColor(Color.WHITE);
        p.setAntiAlias(true);
        p.setTextSize(textSize);

        float textWidth = p.measureText(text);
        c.drawText(text, button.centerX() - (textWidth / 2), button.centerY() + (textSize / 2), p);
    }

    public void onDraw(Canvas c, Drawable drawable, Drawable drawable2) {
        if (currentItemViewHolder != null) {
            drawButtons(c, currentItemViewHolder, drawable, drawable2);
        }
    }
}
