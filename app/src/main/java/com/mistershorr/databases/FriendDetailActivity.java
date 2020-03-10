package com.mistershorr.databases;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;

public class FriendDetailActivity extends AppCompatActivity
{
    private EditText name;
    private SeekBar clumsiness;
    private Switch Awesome;
    private SeekBar gymFrequency;
    private RatingBar trustworthiness;
    private EditText moneyOwed;
    private Button save;
    private Friend friend;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_detail);


        wireWidgets();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent lastIntent = getIntent();


        friend = lastIntent.getParcelableExtra(FriendListActivity.EXTRA_FRIEND);

        // make these the defaults
        clumsiness.setProgress(-1);
        gymFrequency.setProgress(-1);
        trustworthiness.setRating(-1);


       if(friend != null) {
           name.setText(friend.getName());
           clumsiness.setProgress(friend.getClumsiness());
           Awesome.setChecked(friend.isAwesome());
           gymFrequency.setProgress((int) (2 * friend.getGymFrequency()));
           trustworthiness.setRating(friend.getTrustworthiness());
           moneyOwed.setText("" + friend.getMoneyOwed());
       }
       else
       {
           friend = new Friend();
           friend.setOwnerId(Backendless.UserService.CurrentUser().getObjectId());
       }

        setListeners();


    }

    public void wireWidgets()
    {
        name = findViewById(R.id.editText_name_detail);
        clumsiness = findViewById(R.id.seekBar_clumsiness_detail);
        Awesome = findViewById(R.id.switch_awesome_detail);
        gymFrequency = findViewById(R.id.seekBar_gym_detail);
        trustworthiness = findViewById(R.id.ratingBar_trust_detail);
        moneyOwed = findViewById(R.id.editText_money_detail);
        save = findViewById(R.id.button_save_detail);
    }
    public void setListeners()
    {
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                if(name.getText().toString() != null && clumsiness.getProgress() != -1 && gymFrequency.getProgress() != -1 && trustworthiness.getRating() != -1 && moneyOwed.getText().toString() != null) {
                    friend.setName(name.getText().toString());
                    friend.setClumsiness(clumsiness.getProgress());
                    friend.setAwesome(Awesome.isChecked());
                    friend.setGymFrequency(gymFrequency.getProgress() * 1.0 / 2);
                    friend.setTrustworthiness((int) trustworthiness.getRating());
                    friend.setMoneyOwed(Double.valueOf(moneyOwed.getText().toString()));
                    Backendless.Persistence.save(friend, new AsyncCallback<Friend>() {
                        @Override
                        public void handleResponse(Friend friend) {
                            Toast.makeText(FriendDetailActivity.this, "Saved!", Toast.LENGTH_LONG).show();
                            Intent targetIntent = new Intent(FriendDetailActivity.this, FriendListActivity.class);


                            startActivity(targetIntent);

                            finish();

                        }

                        @Override
                        public void handleFault(BackendlessFault fault) {
                            Toast.makeText(FriendDetailActivity.this, fault.getDetail(), Toast.LENGTH_LONG).show();
                            Intent targetIntent = new Intent(FriendDetailActivity.this, FriendListActivity.class);


                            startActivity(targetIntent);

                            finish();

                        }
                    });
                }
                else
                {
                    Toast.makeText(FriendDetailActivity.this, "Information Missing!", Toast.LENGTH_LONG).show();

                }
            }
        });
    }
}
