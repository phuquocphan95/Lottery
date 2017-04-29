package hcmut.lottery;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

public class DateActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date);

        Intent thisIntent= getIntent();

        final String name = thisIntent.getStringExtra("NAME");
        final String province = thisIntent.getStringExtra("PROVINCE");
        final String result = thisIntent.getStringExtra("RESULT");

        this.setTitle(name);

        TableLayout tbl = (TableLayout) findViewById(R.id.dl1);
        TableRow tbr;
        TextView tv;

        final DateActivity self = this;

        try {
            JSONObject json = new JSONObject(result);
            Iterator iterator = json.getJSONObject(province).keys();
            Boolean background = false;
            while(iterator.hasNext()){
                background = !background;

                final String key = (String)iterator.next();
                tbr = new TableRow(this);

                tv = new TextView(this);
                tv.setText(key);
                tv.setTypeface(null, Typeface.BOLD);

                tbr.addView(tv);
                tbr.setMinimumHeight(TableRow.LayoutParams.WRAP_CONTENT);
                tbr.setMinimumWidth(TableRow.LayoutParams.WRAP_CONTENT);
                tbr.setPadding(30, 30, 30, 30);

                if (background) {
                    tbr.setBackgroundColor(0xFFFFFA99);
                } else {
                    tbr.setBackgroundColor(0xFFECF5FE);
                }

                tbl.addView(tbr);

                tbr.setOnClickListener(new TableRow.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(self, ResultActivity.class);
                        intent.putExtra("PROVINCE", province);
                        intent.putExtra("NAME", name);
                        intent.putExtra("RESULT", result);
                        intent.putExtra("DATE", key);
                        startActivity(intent);
                    }
                });
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }





    }
}
