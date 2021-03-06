package com.tl.sunshine.data;

import android.content.SearchRecentSuggestionsProvider;

import com.tl.sunshine.BuildConfig;


/**
 * Created by tomtang on 7/05/15.
 */
public class SuggestionProvider extends SearchRecentSuggestionsProvider {
    public static final String AUTHORITY = BuildConfig.APPLICATION_ID + ".search_history_provider";
    public final static int MODE = DATABASE_MODE_QUERIES | DATABASE_MODE_2LINES;

    public SuggestionProvider() {
        setupSuggestions(AUTHORITY, MODE);
    }
}