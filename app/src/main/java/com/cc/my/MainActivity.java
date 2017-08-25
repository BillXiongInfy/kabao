package com.cc.my;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.sunmi.printerhelper.utils.AidlUtil;
import com.xys.libzxing.zxing.activity.CaptureActivity;

import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    private Button mBtnScanner;
    private TextView mTvResult;
    private Button print_json;

    private int record;
    private boolean isBold, isUnderLine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Context context = getApplicationContext();
        AidlUtil.getInstance().connectPrinterService(context);

        initview();
    }

    private void initview() {
        mBtnScanner = (Button) findViewById(R.id.btn_scan);
        mTvResult = (TextView) findViewById(R.id.tv_result);
        print_json = (Button) findViewById(R.id.print_json);

        mBtnScanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(MainActivity.this, CaptureActivity.class), 0);

            }
        });

        print_json.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                print_json_data();

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK){
            Bundle bundle = data.getExtras();
            String result = bundle.getString("result");
            mTvResult.setText(result);
        }

    }

    private void print_json_data(){
        new Thread(){

            @Override

            public void run() {
                Context context = getApplicationContext();
                RequestQueue mQueue = Volley.newRequestQueue(context);
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(mTvResult.getText().toString()+"&device=001", null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                Log.d("TAG", "5aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");

                                try{
                                    int a = response.getInt("errorCode");
                                    Log.d("TAG",""+ a);
                                    if (a == 0){
                                     //   AidlUtil.getInstance().print3Line();
                                     /*   Log.d("TAG", response.getString("userName"));
                                        Log.d("TAG", response.getString("cardId") );
                                        Log.d("TAG", response.getString("id") );
                                        Log.d("TAG", response.getString("cardName") );
                                        Log.d("TAG", response.getString("createUser") );
                                        Log.d("TAG", response.getString("verifyTime") );
                                        Log.d("TAG", response.getString("openid"));
                                        Log.d("TAG", response.getString("verifyOpenid") );
                                        Log.d("TAG", response.getString("createDate") );
                                        Log.d("TAG", response.getString("cardType") );
                                        Log.d("TAG", response.getString("cardNumber") );*/
                                        Log.d("TAG", response.getString("errorCode") );
                                        Log.d("TAG", response.getString("errorDesc"));
                                        print_function("userName     "+":"+response.getString("userName")    );
                                        print_function("cardId       "+":"+response.getString("cardId")      );
                                        print_function("id           "+":"+response.getString("id")          );
                                        print_function("cardName     "+":"+response.getString("cardName")    );
                                        print_function("createUser   "+":"+response.getString("createUser")  );
                                        print_function("verifyTime   "+":"+response.getString("verifyTime")  );
                                        print_function("openid       "+":"+response.getString("openid")      );
                                        print_function("verifyOpenid "+":"+response.getString("verifyOpenid"));
                                        print_function("createDate   "+":"+response.getString("createDate")  );
                                        print_function("cardType     "+":"+response.getString("cardType")    );
                                        print_function("cardNumber   "+":"+response.getString("cardNumber")  );
                                        print_function("errorCode    "+":"+response.getString("errorCode")   );
                                        print_function("errorDesc    "+":"+response.getString("errorDesc")   );
                                        AidlUtil.getInstance().print3Line();
                                    }
                                    else
                                    {
                                        Log.d("TAG", "6aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
                                        mTvResult.setText("已经核销");
                                    }
                                }catch (Exception e){

                                }

                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("TAG", error.getMessage(), error);
                    }
                });
                mQueue.add(jsonObjectRequest);
            }

        }.start();
    }

    private void print_function(String a)
    {
        try{
        a = a+ "\n";
       // isBold = false;
       // isUnderLine = false;
       // float size = 16;
      // Log.d("TAG", a);
      //  String s_utf8 = new String(a.getBytes("ISO8859-1"),"UTF-8");
       // AidlUtil.getInstance().printText(a, size, isBold, isUnderLine);
        Log.d("TAG", a);
         byte[] send = a.getBytes("GB2312");
        AidlUtil.getInstance().sendRawData(send);}
        catch (Exception e)
        {}
    }
}
