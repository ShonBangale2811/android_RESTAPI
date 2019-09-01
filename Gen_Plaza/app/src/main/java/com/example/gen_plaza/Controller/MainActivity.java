package com.example.gen_plaza.Controller;

import android.app.Dialog;
import android.os.Bundle;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.gen_plaza.Model.User;
import com.example.gen_plaza.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;

import com.example.gen_plaza.Adapter.ListAdapter;
import com.squareup.picasso.Downloader;

import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ListAdapter listAdapter;
    private RequestQueue requestQueue;
    ArrayList<User> users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {



        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);





        users = new ArrayList<>();
        requestQueue = Volley.newRequestQueue(this);
            parseJSON();



        recyclerView  = findViewById(R.id.list_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Add a User", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

               open_dialog();


            }
        });
    }

    private void open_dialog() {

        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog);
        dialog.setTitle("Add user");
        int width = (int) (this.getResources().getDisplayMetrics().widthPixels * 0.90);
        dialog.getWindow().setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT);
        final EditText editTextname = (EditText) dialog.findViewById(R.id.add_name);
        final EditText editTextjob = (EditText) dialog.findViewById(R.id.add_job);
        Button submit = dialog.findViewById(R.id.dialog_submit);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name="",job="";
                name = editTextname.getText().toString();
                job = editTextjob.getText().toString();

                add_user(name,job);
                dialog.dismiss();
            }
        });

            dialog.show();

    }

    private void add_user(final String name, final String job) {
        String url = "https://reqres.in/api/users";
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {

                        Log.d("Response", response);
                        Toast.makeText(MainActivity.this, ""+response, Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                       error.printStackTrace();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("name",name);
                params.put("job",job);

                return params;
            }
        };
        requestQueue.add(postRequest);
    }

    public void parseJSON() {


        String url = "https://reqres.in/api/users";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("data");
                            Log.d("length", String.valueOf(jsonArray.length()));
                            for (int i =0; i < jsonArray.length(); i++) {
                                JSONObject data = jsonArray.getJSONObject(i);

                                String imageurl = data.getString("avatar");
                                String fName = data.getString("first_name");
                                String lName = data.getString("last_name");


                                users.add(new User(imageurl, fName, lName));
                            }
                            listAdapter = new ListAdapter(MainActivity.this,users);
                                recyclerView.setAdapter(listAdapter);



                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        requestQueue.add(request);
    }



}
