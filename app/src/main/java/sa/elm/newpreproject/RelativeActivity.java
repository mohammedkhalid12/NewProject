package sa.elm.newpreproject;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class RelativeActivity extends AppCompatActivity implements Relative_Adapter.OnNoteListener {

    RecyclerView rv;
    String name, number, id;
    MyDatabase mydb;
    Cursor c;
    Relative_Adapter my;
    ArrayList<RelativeInfo> al = new ArrayList<>();
    ImageView add_img;
    TextView empty_notes_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_relative);
        setTitle(R.string.add_reltive);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        rv = findViewById(R.id.rev);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(RelativeActivity.this);
        rv.setLayoutManager(layoutManager);
        add_img = findViewById(R.id.add_img);
        empty_notes_view=findViewById(R.id.empty_notes_view);
        add_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(RelativeActivity.this, AddRelstiveInfoActivity.class);
                startActivity(i);
                finish();

            }
        });

        mydb = new MyDatabase(RelativeActivity.this);
        c = mydb.getData();

        if (c.getCount() > 0) {
            if (c.moveToFirst()) {
                do {
                    id = c.getString(0);
                    name = c.getString(1);
                    number = c.getString(2);
                    RelativeInfo g1 = new RelativeInfo(id, name, number);
                    al.add(g1);

                } while (c.moveToNext());

            }

        }

        my = new Relative_Adapter(RelativeActivity.this, al, this);
        rv.setAdapter(my);


    }// end onCreate()


    @Override
    public void OnNoteClick(final int position) {

        final AlertDialog alertDialog = new AlertDialog.Builder(RelativeActivity.this).create();
        alertDialog.setTitle("What you want to do ?");
        alertDialog.setCancelable(false);
        alertDialog.setMessage("By deleting this, item will permanently be deleted. Are you still want to delete this?");
        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {


            }
        });
        alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                MyDatabase dbHelper = new MyDatabase(RelativeActivity.this);
                dbHelper.removePlace(al.get(position).getId());
                al.remove(position);
                my.notifyDataSetChanged();

            }
        });
        alertDialog.setButton(DialogInterface.BUTTON_NEUTRAL, "Edit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                Intent intent = new Intent(RelativeActivity.this, AddRelstiveInfoActivity.class);

                intent.putExtra("name", al.get(position).getName());
                intent.putExtra("editkey",true);
                intent.putExtra("id", al.get(position).getId());
                intent.putExtra("num", al.get(position).getNumber());
                startActivity(intent);
                finish();
              //  updateNote(id,position);
            }
        });

        alertDialog.show();
        Toast.makeText(RelativeActivity.this, "Long press on position :" + position,
                Toast.LENGTH_LONG).show();
    }
    @Override
    public boolean onSupportNavigateUp () {
        onBackPressed();
        return true;
    } // end onSupportNavigateUp()


}
//end of Class