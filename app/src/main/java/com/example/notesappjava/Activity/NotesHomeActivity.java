package com.example.notesappjava.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.notesappjava.R;
import com.example.notesappjava.databinding.ActivityNotesHomeBinding;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class NotesHomeActivity extends AppCompatActivity implements NotesAdapterActivity.OnItemClickListener{
    private ActivityNotesHomeBinding binding;
    private RequestQueue requestQueue;
    private ArrayList<NotesBeanClass> notelist = new ArrayList<>();
    private NotesAdapterActivity notesAdapterActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNotesHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        requestQueue = Volley.newRequestQueue(this);

        // Set Status Bar Color
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        getWindow().setStatusBarColor(getResources().getColor(android.R.color.white));

        setRecyclerView();
        getAllNotes();

        binding.addNotesImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NotesHomeActivity.this,NotesAddDEActivity.class);
                intent.putExtra("screen_type","Add New Notes");
                startActivity(intent);
            }
        });
    }

    private void setRecyclerView(){
        binding.notesRec.setLayoutManager(new GridLayoutManager(this,1));
        notesAdapterActivity = new NotesAdapterActivity(this,notelist);
        binding.notesRec.setAdapter(notesAdapterActivity);

        notesAdapterActivity.setOnItemClickListener(this);
    }

    private void getAllNotes(){
        notelist.clear();
        binding.progressBar.setVisibility(View.VISIBLE);
        String url = "https://node-api-408s.onrender.com/api/product";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    binding.progressBar.setVisibility(View.GONE);
                    System.out.println("getAllNotes Response : " + response.toString());
                    JSONArray jsonArray = response.getJSONArray("notes");
                    for (int i = 0; i < jsonArray.length(); i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        NotesBeanClass notesBeanClass = new NotesBeanClass();
                        notesBeanClass.setTitle(jsonObject.getString("tittle"));
                        notesBeanClass.setDec(jsonObject.getString("description"));
                        notesBeanClass.setId(jsonObject.getString("_id"));
                        notelist.add(notesBeanClass);
                    }
                    notesAdapterActivity.notifyDataSetChanged();
                }catch (Exception e){
                    binding.progressBar.setVisibility(View.GONE);
                    System.out.println("getAllNotes e : " + e.toString());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                binding.progressBar.setVisibility(View.GONE);
                System.out.println("getAllNotes VolleyError : " + error.toString());
            }
        });
        requestQueue.add(jsonObjectRequest);
    }

    @Override
    public void onItemClick(int position) {
        NotesBeanClass notesBeanClass = notelist.get(position);
        Intent intent = new Intent(NotesHomeActivity.this,NotesAddDEActivity.class);
        intent.putExtra("title",notesBeanClass.getTitle());
        intent.putExtra("des",notesBeanClass.getDec());
        intent.putExtra("id",notesBeanClass.getId());
        intent.putExtra("screen_type","Edit & Delete Notes");
        startActivity(intent);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        getAllNotes();
    }
}