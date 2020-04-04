package sa.elm.newpreproject;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class medicine_List extends BaseActivity implements MedicineCared_Adapter.OnDepListener {

    String medName, about, DaysBox, PillBox, DoseBox, FrequencyBox, id, txtTime, img;
    private RecyclerView recyclerView;
    MyDatabase mydb;
    Cursor c;

    MedicineCared_Adapter my;
    private MedicineCared_Adapter adapter;
    private List<MedicineCardInfo> al = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main_card);
        recyclerView = findViewById(R.id.recycler_medicine);
        setTitle(R.string.my_medicine_list);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(medicine_List.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(medicine_List.this, 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.HORIZONTAL));





        mydb = new MyDatabase(medicine_List.this);
        c = mydb.getMedicine();

        if (c.getCount() > 0) {
            if (c.moveToFirst()) {
                do {

                    id = c.getString(0);
                    medName = c.getString(1);
                    DoseBox = c.getString(2);
                    FrequencyBox = c.getString(3);
                    txtTime = c.getString(4);
                    DaysBox = c.getString(5);
                    PillBox = c.getString(6);
                    img = c.getString(7);
                    about = c.getString(8);

                    MedicineCardInfo g1 = new MedicineCardInfo(medName, about, DaysBox, PillBox, DoseBox, FrequencyBox, txtTime, img, id);
                    al.add(g1);

                } while (c.moveToNext());

            }

        }

        my = new MedicineCared_Adapter(medicine_List.this, al, this);
        recyclerView.setAdapter(my);






        //    prepareData();

    }//end onCreate()


    @Override
    public void onDepClick(int position) {


        Intent intent = new Intent(medicine_List.this, AddMedicineActivity.class);

        intent.putExtra("Mname", al.get(position).getMedicineName());
        intent.putExtra("editkey",true);
        intent.putExtra("medid", al.get(position).getId());
        intent.putExtra("doseOfpill", al.get(position).getDoseBox());
        intent.putExtra("freq", al.get(position).getFrequencyBox());
        intent.putExtra("time", al.get(position).getTxtTime());
        intent.putExtra("Ndays", al.get(position).getDaysBox());
        intent.putExtra("pill", al.get(position).getPillBox());
        intent.putExtra("img", al.get(position).getImg());
        intent.putExtra("about", al.get(position).getAbout());
        startActivity(intent);
        finish();

     /*   if (MysharedPreferences.getString(this, Constants.Keys.APP_LANGUAGE, "en").equals("en"))
            intent.putExtra(Constants.Keys.TITLE, al.get(position).getMedicineName());*/

    } // end onDepClick()


    //RecyclerView item decoration - give equal margin around grid item
    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        } // end constructor

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                } ///end if
            } //end else
        } //end getItemOffsets()
    } //end class GridSpacingItemDecoration


    // Converting dp to pixel
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    } //end dpToPx



    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    } // end onSupportNavigateUp()

}//end medicine_List class
