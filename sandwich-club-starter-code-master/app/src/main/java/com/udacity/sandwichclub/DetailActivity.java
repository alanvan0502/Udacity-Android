package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;
    private TextView mOriginTV;
    private TextView mAlsoKnownTV;
    private TextView mIngredientsTV;
    private TextView mDescriptionTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView imageIV = findViewById(R.id.image_iv);
        mIngredientsTV = findViewById(R.id.ingredients_tv);
        mOriginTV = findViewById(R.id.origin_tv);
        mAlsoKnownTV = findViewById(R.id.also_known_tv);
        mDescriptionTV = findViewById(R.id.description_tv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher_round)
                .into(imageIV);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {
        String description;
        if (!(description = sandwich.getDescription()).equals("")) {
            mDescriptionTV.setText(description);
        }
        mDescriptionTV.append("\n");

        String origin;
        if (!(origin = sandwich.getPlaceOfOrigin()).equals("")) {
            mOriginTV.setText(origin);
        }
        mOriginTV.append("\n");

        List<String> alsoKnownAs;
        if (!(alsoKnownAs = sandwich.getAlsoKnownAs()).isEmpty()) {
            mAlsoKnownTV.setText(TextUtils.join(", ", alsoKnownAs));
        }
        mAlsoKnownTV.append("\n");

        List<String> ingredients;
        if (!(ingredients = sandwich.getIngredients()).isEmpty()) {
            mIngredientsTV.setText(TextUtils.join(", ", ingredients));
        }
        mIngredientsTV.append("\n");
    }
}
