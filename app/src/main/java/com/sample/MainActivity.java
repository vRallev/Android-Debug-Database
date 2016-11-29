/*
 *
 *  *    Copyright (C) 2016 Amit Shekhar
 *  *    Copyright (C) 2011 Android Open Source Project
 *  *
 *  *    Licensed under the Apache License, Version 2.0 (the "License");
 *  *    you may not use this file except in compliance with the License.
 *  *    You may obtain a copy of the License at
 *  *
 *  *        http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  *    Unless required by applicable law or agreed to in writing, software
 *  *    distributed under the License is distributed on an "AS IS" BASIS,
 *  *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  *    See the License for the specific language governing permissions and
 *  *    limitations under the License.
 *
 */

package com.sample;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;

import com.sample.database.ContactDBHelper;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    @SuppressLint("CommitPrefEdits")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        SharedPreferences prefsOne = getSharedPreferences("countPrefOne", Context.MODE_PRIVATE);
        SharedPreferences prefsTwo = getSharedPreferences("countPrefTwo", Context.MODE_PRIVATE);

        sharedPreferences.edit().putString("testOne", "one").commit();
        sharedPreferences.edit().putString("testTwo", "two").commit();

        prefsOne.edit().putString("testOneNew", "one").commit();

        prefsTwo.edit().putString("testTwoNew", "two").commit();

        String[] databases = new String[]{
                "database1.db",
                new File(getFilesDir(), "database2.db").getAbsolutePath(),
                new File(new File(getFilesDir(), "folder"), "database3.db").getAbsolutePath()
        };

        for (int j = 0; j < databases.length; j++) {
            ContactDBHelper contactDBHelper = new ContactDBHelper(getApplicationContext(), databases[j]);
            if (contactDBHelper.count() == 0) {
                for (int i = 0; i < 100; i++) {
                    String name = "name_" + i;
                    String phone = "phone_" + i;
                    String email = "email_" + i;
                    String street = "street_" + i;
                    String place = "place_" + i;
                    contactDBHelper.insertContact(name, phone, email, street, place, new File(databases[j]).getName());
                }
            }
        }
    }
}
