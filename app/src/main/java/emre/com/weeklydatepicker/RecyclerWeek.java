package emre.com.weeklydatepicker;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.joda.time.DateTime;

import java.util.List;

public class RecyclerWeek extends RecyclerView {

    private DateAdapter mAdapter;

    public RecyclerWeek(@NonNull Context context) {
        super(context);

        init();
    }

    public RecyclerWeek(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        init();
    }

    public RecyclerWeek(@NonNull Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        init();
    }

    private void init() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        setLayoutManager(layoutManager);

        SnapHelper snapHelper = new LinearSnapHelper();
        snapHelper.attachToRecyclerView(this);
    }

    public void load(DateTime now, int screenWidth, List<DateModel> dateModelList, boolean isWeekendEnable){
        mAdapter = new DateAdapter(getContext(), dateModelList, now, screenWidth, isWeekendEnable);
        setAdapter(mAdapter);
    }



    public class DateAdapter extends RecyclerView.Adapter<DateViewHolder> {
        private List<DateModel> mData;
        private LayoutInflater mInflater;
        private ItemClickListener mClickListener;

        public int selectedPosition = -1;
        public int lastSelectedPosition = -1;

        int width = 0;
        DateTime now;

        boolean isWeekendEnable;
        int weekendDivider = 7;
        int weekendMargin = 5;

        int weekdaysDivider = 5;
        int weekDaysMargin = 10;

        public DateAdapter(Context context, List<DateModel> data, DateTime now, int width, boolean isWeekendEnable) {
            this.mInflater = LayoutInflater.from(context);
            this.mData = data;
            this.width = width;
            this.now = now;
            this.isWeekendEnable = isWeekendEnable;
        }

        @Override
        public DateViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = mInflater.inflate(R.layout.item_date, parent, false);
            final DateViewHolder holder = new DateViewHolder(view, mClickListener);
            LinearLayout.LayoutParams params;
            if (isWeekendEnable) {
                params = new LinearLayout.LayoutParams(
                        width / weekendDivider - weekendMargin * 2,
                        width / weekendDivider - weekendMargin * 2
                );
                params.setMargins(weekendMargin, weekendMargin, weekendMargin, weekendMargin);
            } else {
                params = new LinearLayout.LayoutParams(
                        width / weekdaysDivider - weekDaysMargin * 2,
                        width / weekdaysDivider - weekDaysMargin * 2
                );
                params.setMargins(weekDaysMargin, weekDaysMargin, weekDaysMargin, weekDaysMargin);
            }
            holder.cardView.setLayoutParams(params);

            holder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    lastSelectedPosition = selectedPosition;
                    selectedPosition = holder.getAdapterPosition();
                    mData.get(selectedPosition).setSelected(!mData.get(selectedPosition).isSelected());
                    if (lastSelectedPosition != -1) {
                        mData.get(lastSelectedPosition).setSelected(!mData.get(lastSelectedPosition).isSelected());
                    }
                    notifyDataSetChanged();
                }
            });

            return holder;
        }

        @Override
        public void onBindViewHolder(DateViewHolder holder, int position) {
            DateModel currentDateModel = mData.get(position);

            holder.dayText.setText(getStringDay(currentDateModel.getDateTime()));
            holder.numberText.setText(getNumericDay(currentDateModel.getDateTime()) + "");

            boolean isToday = currentDateModel.getDateTime().toString().contains(now.toString());

            if (isToday) {
                holder.cardView.setCardBackgroundColor(getContext().getResources().getColor(R.color.nowColor));
                holder.dayText.setTextColor(getContext().getResources().getColor(R.color.white));
                holder.numberText.setTextColor(getContext().getResources().getColor(R.color.white));
            }

            if (mData.get(position).isSelected) {
                holder.cardView.setCardBackgroundColor(getContext().getResources().getColor(R.color.selectedColor));
                holder.dayText.setTextColor(getContext().getResources().getColor(R.color.white));
                holder.numberText.setTextColor(getContext().getResources().getColor(R.color.white));
            } else if (!mData.get(position).isSelected && !isToday) {
                holder.cardView.setCardBackgroundColor(getContext().getResources().getColor(R.color.defaultColor));
                holder.dayText.setTextColor(getContext().getResources().getColor(R.color.gray));
                holder.numberText.setTextColor(getContext().getResources().getColor(R.color.gray));
            }
        }

        @Override
        public int getItemCount() {
            return mData.size();
        }


        public void setClickListener(ItemClickListener itemClickListener) {
            this.mClickListener = itemClickListener;
        }


        public String getStringDay(DateTime dateTime) {
            String shortDay = dateTime.dayOfWeek().getAsShortText();

            return shortDay;
        }

        public int getNumericDay(DateTime dateTime) {
            int numericDay = dateTime.getDayOfMonth();

            return numericDay;
        }
    }

    public class DateViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ItemClickListener mClickListener;

        CardView cardView;
        TextView dayText, numberText;

        DateViewHolder(final View itemView, ItemClickListener mClickListener) {
            super(itemView);

            cardView = itemView.findViewById(R.id.card_view);
            dayText = itemView.findViewById(R.id.day_name_text);
            numberText = itemView.findViewById(R.id.day_number_text);

            this.mClickListener = mClickListener;
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) {
                mClickListener.onItemClick(view, getAdapterPosition());
            }
        }
    }

    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}




