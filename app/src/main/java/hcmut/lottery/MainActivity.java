package hcmut.lottery;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TableRow;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(getString(R.string.url))
                .build();
        client.newCall(request).enqueue(new ControllerCallback(this));


    }

    public void onConnectionFinish(String json) {
        ProgressBar pr4 = (ProgressBar)findViewById(R.id.pr4);
        pr4.setVisibility(View.INVISIBLE);

        this.resultJson = json;
        final MainActivity self = this;
        TableRow [] tr = new  TableRow [8];

        tr[0] = (TableRow) findViewById(R.id.r1);
        tr[1] = (TableRow) findViewById(R.id.r2);
        tr[2] = (TableRow) findViewById(R.id.r3);
        tr[3] = (TableRow) findViewById(R.id.r4);
        tr[4] = (TableRow) findViewById(R.id.r5);
        tr[5] = (TableRow) findViewById(R.id.r6);
        tr[6] = (TableRow) findViewById(R.id.r7);
        tr[7] = (TableRow) findViewById(R.id.r8);

        final String [] province = {
                "AG", "BD", "BL", "BP", "BTH", "CM", "CT", "HCM"
        };
        final String [] name = {
                "An Giang", "Bình Dương", "Bạc Liêu", "Bình Phước", "Bình Thuận",
                "Cà Mau", "Cần Thơ", "Thành Phố HCM"
        };

        for (int i = 0; i < 8; i++) {
            final String prv = province[i];
            final String n = name[i];
            tr[i].setOnClickListener(new TableRow.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(self, DateActivity.class);
                    intent.putExtra("PROVINCE", prv);
                    intent.putExtra("NAME", n);
                    intent.putExtra("RESULT", self.resultJson);
                    startActivity(intent);
                }
            });
        }

    }

    public void onNetworkError() {
        ProgressBar pr4 = (ProgressBar)findViewById(R.id.pr4);
        pr4.setVisibility(View.INVISIBLE);

        new AlertDialog.Builder(this)
                .setTitle("Network error!!!")
                .setMessage("You have to connect the internet to use this app!!!")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {}
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }


    private class ControllerCallback implements Callback {

        private MainActivity parentActivity;

        public ControllerCallback(MainActivity parentActivity) {
            this.parentActivity = parentActivity;
        }

        @Override
        public void onFailure(Call call, IOException e) {
            this.parentActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    //Handle UI here
                    parentActivity.onNetworkError();
                }
            });
        }

        @Override
        public void onResponse(Call call, Response response) throws IOException {
            final String responseJson = response.body().string();
            // UI updating program must run on UIThread
            this.parentActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    //Handle UI here
                    parentActivity.onConnectionFinish(responseJson);
                }
            });

        }
    }
    private String resultJson;
}
