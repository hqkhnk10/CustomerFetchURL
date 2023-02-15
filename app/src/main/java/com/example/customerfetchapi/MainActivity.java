package com.example.customerfetchapi;

import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.customerfetchapi.databinding.ActivityMainBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    ArrayList<Customer> userList;
    ArrayAdapter<String> listAdapter;
    Handler mainHandler = new Handler();
    ProgressDialog progressDialog;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initializeUserList();
        new fetchData().run();

        listView.setOnItemClickListener((parent, view, position, id) -> {
            Customer customer = userList.get(position);
            Intent intent = new Intent(this, CustomerDetailActivity.class);
            intent.putExtra("item_name", customer.getName());
            intent.putExtra("item_username", customer.getName());
            intent.putExtra("item_email", customer.getName());
            intent.putExtra("item_address", customer.getName());
            intent.putExtra("item_avatar", customer.getName());

            startActivity(intent);
        });
    }

    private void initializeUserList() {
        userList = new ArrayList<>();
        listAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1,userList);
        binding.userList.setAdapter(listAdapter);
    }

    class fetchData extends  Thread{
        String data = "";

        @Override
        public void run() {

            mainHandler.post(new Runnable() {
                @Override
                public void run() {
                    progressDialog = new ProgressDialog(MainActivity.this);
                    progressDialog.setMessage("Đang lấy dữ liệu");
                    progressDialog.setCancelable(false);
                    progressDialog.show();
                }
            });

            try {
                URL url = new URL("https://lebavui.github.io/jsons/users.json");
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                ;
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    data = data + line;
                }
                if (!data.isEmpty()) {
                    JSONObject jsonobject = new JSONObject(data);
                    System.out.println("jsonobject" + jsonobject);
                    JSONArray users = jsonobject.getJSONArray("Users");
                    userList.clear();
                for (int i = 0;i <users.length();i++){
                    JSONObject json = users.getJSONObject(i) ;
                    Customer customer = new Customer(
                            json.getInt("id"),
                        json.getString("name"),
                        json.getString("username"),
                        json.getString("email"),
                        json.getJSONObject("avatar"),
                        json.getJSONObject("address")
                    );
                    userList.add(customer);
                    }
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            mainHandler.post(new Runnable() {
                @Override
                public void run() {
                    if(progressDialog.isShowing()){
                        progressDialog.dismiss();
                    }
                }
            });
        }
    }
}