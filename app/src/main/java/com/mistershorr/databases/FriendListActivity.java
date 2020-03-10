package com.mistershorr.databases;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.UserService;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.persistence.DataQueryBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class FriendListActivity extends AppCompatActivity {
    private List<Friend> friendList;
    private ListView friendListView;
    private MenuItem item;
    private FloatingActionButton add;
    ArrayAdapter<Friend> friendListAdapter;



    public static final String EXTRA_FRIEND = "friend";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_list);

        wireWidgets();
        setListeners();

        String userId = Backendless.UserService.CurrentUser().getObjectId();
        String whereClause = "ownerId = " + userId + "'";

        DataQueryBuilder queryBuilder = DataQueryBuilder.create();
        queryBuilder.setWhereClause(whereClause);


        Backendless.Data.of(Friend.class).find(new AsyncCallback<List<Friend>>() {
            @Override
            public void handleResponse(List<Friend> response)
            {
                // make a listview adapter

                // make a friend parcelable
                // when a friend is clicked, it opens the detail activity and loads the info


                friendList = response;
                friendListAdapter = new FriendAdapter(friendList);
                friendListView.setAdapter(friendListAdapter);

                friendListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                        Friend friend = friendList.get(position);

                        Intent targetIntent = new Intent(FriendListActivity.this, FriendDetailActivity.class);

                        targetIntent.putExtra(EXTRA_FRIEND, friend);

                        startActivity(targetIntent);
                        finish();



                    }
                });


            }

            @Override
            public void handleFault(BackendlessFault fault)
            {
                Toast.makeText(FriendListActivity.this, fault.getDetail(), Toast.LENGTH_LONG).show();

            }
        });





    }
    private class FriendAdapter extends ArrayAdapter<Friend>
    {

        private List<Friend> friendList;

        public FriendAdapter(List<Friend> friendList)
        {
            super(FriendListActivity.this, -1, friendList);
            this.friendList = friendList;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = getLayoutInflater();
            if(convertView == null)
            {
                convertView = inflater.inflate(R.layout.item_friend, parent, false);
            }

            TextView textViewName = convertView.findViewById(R.id.textView_name_item);
            TextView textViewClumsiness = convertView.findViewById(R.id.textView_clumsiness_item);
            TextView textViewMoney = convertView.findViewById(R.id.textView_money_item);

            textViewName.setText(friendList.get(position).getName());
            textViewClumsiness.setText(String.valueOf(friendList.get(position).getClumsiness()));
            textViewMoney.setText(String.valueOf(friendList.get(position).getMoneyOwed()));

            return convertView;
        }
    }

    public void wireWidgets()
    {
        friendListView = findViewById(R.id.ListView_friendList_list);
        add = findViewById(R.id.floatingActionButton_add_list);
    }

    public void setListeners()
    {
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent targetIntent = new Intent(FriendListActivity.this, FriendDetailActivity.class);
                startActivity(targetIntent);

            }
        });
    }

}
