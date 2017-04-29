package hcmut.lottery;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Iterator;

public class ResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);


        Intent thisIntent = getIntent();

        final String name = thisIntent.getStringExtra("NAME");
        final String province = thisIntent.getStringExtra("PROVINCE");
        final String date = thisIntent.getStringExtra("DATE");
        final String result = thisIntent.getStringExtra("RESULT");


        TableLayout tbl = (TableLayout) findViewById(R.id.dl2);
        TableRow tbr;
        TextView tv;

        setTitle(name + " " + date);
        try {
            JSONObject json = new JSONObject(result);
            JSONObject jsonDate = json.getJSONObject(province).getJSONObject(date);


            for (int i = 1; i < 10 ; i++) {
                String j = (i != 9) ? Integer.toString(i) : "DB";
                JSONArray p = jsonDate.getJSONArray(j);

                tv = new TextView(this);
                tv.setTypeface(null, Typeface.BOLD);
                tv.setGravity(Gravity.LEFT);
                tv.setText("Giải " + j);

                tbr = new TableRow(this);
                tbr.addView(tv);
                tbr.setMinimumHeight(TableRow.LayoutParams.WRAP_CONTENT);
                tbr.setMinimumWidth(TableRow.LayoutParams.WRAP_CONTENT);
                tbr.setPadding(30, 30, 30, 30);
                tbr.setGravity(Gravity.LEFT);

                tbl.addView(tbr);

                for (int k = 0; k < p.length(); k++) {
                    String num = p.getString(k);

                    tv = new TextView(this);
                    tv.setTypeface(null, Typeface.BOLD);
                    tv.setGravity(Gravity.RIGHT);
                    tv.setText(num);

                    tbr = new TableRow(this);
                    tbr.addView(tv);
                    tbr.setMinimumHeight(TableRow.LayoutParams.WRAP_CONTENT);
                    tbr.setMinimumWidth(TableRow.LayoutParams.WRAP_CONTENT);
                    tbr.setPadding(30, 30, 30, 30);
                    tbr.setGravity(Gravity.RIGHT);

                    tbl.addView(tbr);
                }

            }



        }
        catch (Exception e){
            e.printStackTrace();
        }



    }
}
