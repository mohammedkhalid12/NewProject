package sa.elm.newpreproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Relative_Adapter extends RecyclerView.Adapter<Relative_Adapter.Myclass> {
    Context context;

    ArrayList<RelativeInfo> al;
    private OnNoteListener mOnNoteListener;

    public Relative_Adapter(Context context, ArrayList<RelativeInfo> al, OnNoteListener onNoteListene) {
        this.context = context;
        this.al = al;

        this.mOnNoteListener = onNoteListene;

    }

    @NonNull
    @Override
    public Myclass onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.relative_info, parent, false);

        return new Relative_Adapter.Myclass(v, mOnNoteListener);
    }

    @Override
    public void onBindViewHolder(@NonNull Myclass holder, int position) {
        RelativeInfo gl = al.get(position);
        holder.tv_name.setText(gl.getName());
        holder.tv_number.setText(gl.getNumber());
    }

    @Override
    public int getItemCount() {
        return al.size();
    }

    public class Myclass extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tv_name, tv_number;
        public OnNoteListener onNoteListener;

        public Myclass(@NonNull View itemView, OnNoteListener onNoteListener) {
            super(itemView);
            tv_name = itemView.findViewById(R.id.tv_name);
            tv_number = itemView.findViewById(R.id.tv_number);
            itemView.setOnClickListener(this);
            this.onNoteListener = onNoteListener;

        }

        @Override
        public void onClick(View view) {
            onNoteListener.OnNoteClick(getAdapterPosition());
        }
    }

    public interface OnNoteListener {
        void OnNoteClick(int position);
    }//end OnNoteListener()


}


