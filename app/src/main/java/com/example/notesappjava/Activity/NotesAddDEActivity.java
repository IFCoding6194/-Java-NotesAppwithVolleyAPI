package com.example.notesappjava.Activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.notesappjava.R;
import com.example.notesappjava.databinding.ActivityNotesAddDeactivityBinding;

import org.json.JSONObject;

public class NotesAddDEActivity extends AppCompatActivity {
    private ActivityNotesAddDeactivityBinding binding;
    private RequestQueue requestQueue;
    private String screenType,id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNotesAddDeactivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        requestQueue = Volley.newRequestQueue(this);

        // Set Status Bar Color
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        getWindow().setStatusBarColor(getResources().getColor(android.R.color.white));

        Bundle bundle =new Bundle();
        bundle = getIntent().getExtras();

        if (bundle != null) {
            screenType = bundle.getString("screen_type");
            if (screenType.equals("Add New Notes")){
                binding.titleTv.setText(screenType);

            }else {
                binding.titleTv.setText(screenType);
                binding.titleEdt.setText(bundle.getString("title"));
                binding.descriptionEdt.setText(bundle.getString("des"));
                id = bundle.getString("id");
                binding.noteDeleteImg.setVisibility(View.VISIBLE);
            }
        }

        binding.noteSaveIng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNewEditNotes();
            }
        });

        binding.backImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        binding.noteDeleteImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteNotes();
            }
        });
    }

    private void deleteNotes(){
        binding.progressBar.setVisibility(View.VISIBLE);
        String url = "https://node-api-408s.onrender.com/api/product/"+id;
        try {
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.DELETE, url, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        binding.progressBar.setVisibility(View.GONE);
                        Toast.makeText(NotesAddDEActivity.this, response.getString("msg"), Toast.LENGTH_SHORT).show();
                        onBackPressed();
                        finish();
                    }catch (Exception e){
                        System.out.println("deleteNotes e : " + e.toString());
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    binding.progressBar.setVisibility(View.GONE);
                    System.out.println("deleteNotes VolleyError : " + error.toString());
                }
            });
            requestQueue.add(jsonObjectRequest);
        }catch (Exception e){
            binding.progressBar.setVisibility(View.GONE);
            System.out.println("deleteNotes e : " + e.toString());
        }
    }

    private void addNewEditNotes(){
        String title = binding.titleEdt.getText().toString().trim();
        String des = binding.descriptionEdt.getText().toString().trim();
        if (TextUtils.isEmpty(title)){
            binding.titleEdt.setError("Enter a Title");
            binding.titleEdt.requestFocus();
        }
        if (TextUtils.isEmpty(des)){
            binding.descriptionEdt.setError("Enter a Description");
            binding.descriptionEdt.requestFocus();
        }

        if (screenType.equals("Add New Notes")) {
            binding.progressBar.setVisibility(View.VISIBLE);
            String url = "https://node-api-408s.onrender.com/api/product";
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("tittle", title.toString());
                jsonObject.put("description", des.toString());
                System.out.println("addNewNotes Request : " + jsonObject.toString() + url);
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObject, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            binding.progressBar.setVisibility(View.GONE);
                            Toast.makeText(NotesAddDEActivity.this, response.getString("msg"), Toast.LENGTH_SHORT).show();
                            onBackPressed();
                            finish();
                        } catch (Exception e) {
                            binding.progressBar.setVisibility(View.GONE);
                            System.out.println("addNewNotes e : " + e.toString());
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        binding.progressBar.setVisibility(View.GONE);
                        System.out.println("addNewNotes VolleyError : " + error.toString());
                    }
                });
                requestQueue.add(jsonObjectRequest);
            } catch (Exception er) {
                binding.progressBar.setVisibility(View.GONE);
                System.out.println("addNewNotes er : " + er.toString());
            }
        }else {
            binding.progressBar.setVisibility(View.VISIBLE);
            String url = "https://node-api-408s.onrender.com/api/product/"+id;
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("tittle", title.toString());
                jsonObject.put("description", des.toString());
                System.out.println("addNewEditNotes Request : " + jsonObject.toString() + url);
                try {
                    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PUT, url, jsonObject, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                binding.progressBar.setVisibility(View.GONE);
                                Toast.makeText(NotesAddDEActivity.this, response.getString("msg"), Toast.LENGTH_SHORT).show();
                                onBackPressed();
                                finish();
                            }catch (Exception e){
                                System.out.println("addNewEditNotes Response e : " + e.toString());
                            }

                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            binding.progressBar.setVisibility(View.GONE);
                            System.out.println("addNewEditNotes VolleyError : " + error.toString());
                        }
                    });
                    requestQueue.add(jsonObjectRequest);
                }catch (Exception err){
                    binding.progressBar.setVisibility(View.GONE);
                    System.out.println("addNewEditNotes err" + err.toString());
                }
            }catch (Exception e){
                binding.progressBar.setVisibility(View.GONE);
                System.out.println("addNewEditNotes e : " + e.toString());
            }
        }
    }
}