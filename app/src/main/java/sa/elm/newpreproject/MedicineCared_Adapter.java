package sa.elm.newpreproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class MedicineCared_Adapter extends RecyclerView.Adapter<MedicineCared_Adapter.MyViewHolder> {

    private Context mContext;
    private List<MedicineCardInfo> depList;
    private MedicineCared_Adapter.OnDepListener mOnDepListener;

    // second class
    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView title;
        public ImageView thumbnail;
        OnDepListener onDepListener;

        public MyViewHolder(View view, OnDepListener onDepListener) {

            super(view);
            title = view.findViewById(R.id.medicine_name);
            thumbnail = view.findViewById(R.id.medicine_img);
            view.setOnClickListener(this);
            this.onDepListener = onDepListener;

        } //end MyViewHolder constructor

        @Override
        public void onClick(View view) {
            onDepListener.onDepClick(getAdapterPosition());
        }

    } //end class  MyViewHolder


    public interface OnDepListener {

        void onDepClick(int position);


    } // end interface OnDepListener


    public MedicineCared_Adapter(Context mContext, List<MedicineCardInfo> depList, OnDepListener onDepListener) {
        this.mContext = mContext;
        this.depList = depList;
        this.mOnDepListener = onDepListener;
    } //end DepartmentAdapter constructor

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.medicinecard, parent, false);

        return new MyViewHolder(itemView, mOnDepListener);
    } // end onCreateViewHolder()

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        MedicineCardInfo department = depList.get(position);
        if (MysharedPreferences.getString(mContext, Constants.Keys.APP_LANGUAGE, "en").equals("en")) {
            holder.title.setText(department.getMedicineName());
        } //end if
        holder.title.setText(department.getMedicineName());


        // loading medicine cover using Glide library
        Glide.with(mContext).load(department.getImg()).into(holder.thumbnail);
    } //end onBindViewHolder()

    @Override
    public int getItemCount() {
        return depList.size();
    }//end getItemCount()
}//end MedicineCare_Adapter class
