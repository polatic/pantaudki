
package com.polatic.pantaudki.ui;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.polatic.pantaudki.R;
import com.polatic.pantaudki.contact.AboutFragment;
import com.polatic.pantaudki.contact.ContactFragment;
import com.polatic.pantaudki.home.HomeFragment;
import com.polatic.pantaudki.home.SidebarAdapter;
import com.polatic.pantaudki.home.SidebarItem;
import com.polatic.pantaudki.urusan.UrusanFragment;
import com.polatic.pantaudki.utils.Constant;
import com.polatic.pantaudki.utils.UIUtils;

import org.json.JSONException;
import org.json.JSONObject;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.os.Build;

import java.util.ArrayList;
import java.util.List;

public class PantauActivity extends ActionBarActivity {
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;

    private SidebarAdapter sidebarAdapter;
    private List<SidebarItem> sidebarItems;

    private CharSequence mDrawerTitle;
    private CharSequence mTitle;

    private String mSidebarCount;
    private String[] mSidebarTitle;

    private Context mContext;

    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;

    private Fragment mContentFragment;
    private Integer mMenuPosition = 0;

    private IntentFilter mNetworkStateChangedFilter;
    private BroadcastReceiver mNetworkStateIntentReceiver;
    private boolean mAvailable = false;

    private final Integer[] mSidebarImage = {
            R.drawable.sidebar_home, 
            R.drawable.sidebar_setting,
            R.drawable.sidebar_message,
            R.drawable.ic_action_important
    };

    public LinearLayout internetLayout;
    private Boolean isRedirectFromActionBarMenu = false;
    private TextView numberTextView;
    private ProgressDialog mProgressDialog;
    boolean isVisible = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pantau_activity);
        // Initialize sharedprefernces and context
        mSharedPreferences = getSharedPreferences(Constant.PREFERENCES_NAME, Context.MODE_PRIVATE);
        mContext = this;

        setupDrawer();

        mSharedPreferences = getSharedPreferences(Constant.PREFERENCES_NAME, Context.MODE_PRIVATE);
        internetLayout = (LinearLayout) findViewById(R.id.layout_home_layout_internet);

        mNetworkStateChangedFilter = new IntentFilter();
        mNetworkStateChangedFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);

        setupBroadcastReceiver();
        selectItem(0);
    }

    /**
     * Show internet connection status on bottom fragment
     */
    private void updateScreen(Boolean isConnecting) {
        if (!mAvailable) {
            internetLayout.setVisibility(View.VISIBLE);
        } else {
            internetLayout.setVisibility(View.GONE);
        }
    }

    /**
     * Set broadcast receiver for network
     */
    private void setupBroadcastReceiver() {
        mNetworkStateIntentReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent.getAction().equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
                    NetworkInfo info = intent
                            .getParcelableExtra(ConnectivityManager.EXTRA_NETWORK_INFO);
                    mAvailable = info.isAvailable();
                    updateScreen(false);

                } else if (intent.getAction().equals(WifiManager.WIFI_STATE_CHANGED_ACTION)) {
                    updateScreen(true);
                }
            }
        };

    }

    @Override
    protected void onResume() {
        super.onResume();

        registerReceiver(mNetworkStateIntentReceiver, mNetworkStateChangedFilter);
        unregisterReceiver(mNetworkStateIntentReceiver);
        registerReceiver(mNetworkStateIntentReceiver, mNetworkStateChangedFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(mNetworkStateIntentReceiver);
    }

    /**
     * Setup menu drawer navigation
     */
    private void setupDrawer() {
        mTitle = mDrawerTitle = getTitle();

        mSidebarTitle = getResources().getStringArray(R.array.sidebar_menu_array);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);

        // set a custom shadow that overlays the main content when the drawer
        // opens
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        
        // enable ActionBar app icon to behave as action to toggle nav drawer
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);

        sidebarItems = new ArrayList<SidebarItem>();
        for (int i = 0; i < mSidebarTitle.length; i++) {
            SidebarItem item;
            if (mSidebarTitle[i].equalsIgnoreCase(getResources().getString(R.string.menu_urusan))) {
                if (numberTextView == null) {
                    isVisible = false;
                    mSidebarCount = "0";
                } else {
                    isVisible = true;
                }
                item = new SidebarItem(mSidebarImage[i], mSidebarTitle[i], mSidebarCount, isVisible);
            } else {
                isVisible = false;
                item = new SidebarItem(mSidebarImage[i], mSidebarTitle[i], mSidebarCount, isVisible);
            }

            sidebarItems.add(item);
        }

        sidebarAdapter = new SidebarAdapter(this, sidebarItems);
        // set up the drawer's list view with items and click listener
        mDrawerList.setAdapter(sidebarAdapter);

        // mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
        mDrawerList.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                // Always hide virtualkeyboard when open navigation to prevent UI
                // conflict
                UIUtils.hideKeyboard(mContext, getWindow());

                mDrawerLayout.setDrawerListener(new DrawerLayout.SimpleDrawerListener() {
                    @Override
                    public void onDrawerClosed(View drawerView) {
                        super.onDrawerClosed(drawerView);
                        mMenuPosition = position;
                        selectItem(position);
                    }

                    @Override
                    public void onDrawerOpened(View drawerView) {
                        UIUtils.hideKeyboard(mContext, getWindow());
                        super.onDrawerOpened(drawerView);
                    }
                });

                mDrawerLayout.closeDrawer(mDrawerList);
            }
        });

        // ActionBarDrawerToggle ties together the the proper interactions
        // between the sliding drawer and the action bar app icon
        mDrawerToggle = new ActionBarDrawerToggle(this, /* host Activity */
                mDrawerLayout, /* DrawerLayout object */
                R.drawable.ic_drawer, /* nav drawer image to replace 'Up' caret */
                R.string.drawer_open, /* "open drawer" description for accessibility */
                R.string.drawer_close /* "close drawer" description for accessibility */
                ) {
            @Override
            public void onDrawerClosed(View view) {
                setActionBarArrowDependingOnFragmentsBackStack();
                getSupportActionBar().setTitle(mTitle);
                 supportInvalidateOptionsMenu(); // creates call to
                // onPrepareOptionsMenu()
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                mDrawerToggle.setDrawerIndicatorEnabled(true);
                getSupportActionBar().setTitle(mDrawerTitle);
                 supportInvalidateOptionsMenu(); // creates call to
//                 onPrepareOptionsMenu()
            }
        };

        mDrawerLayout.setDrawerListener(mDrawerToggle);

        getSupportFragmentManager().addOnBackStackChangedListener(
                new FragmentManager.OnBackStackChangedListener() {
                    @Override
                    public void onBackStackChanged() {
                        setActionBarArrowDependingOnFragmentsBackStack();
                    }
                });
    }

    @Override
    // http://stackoverflow.com/questions/17258020/switching-between-android-navigation-drawer-image-and-up-carat-when-using-fragme
    public void onBackPressed() {
        super.onBackPressed();
        // turn on the Navigation Drawer image; this is called in the
        // LowerLevelFragments
        mDrawerToggle.setDrawerIndicatorEnabled(true);

    }

    /**
     * Set back arrow ActionBar based on Fragment stacks At first place, there
     * always one fragment counted as top stack So we add back arrow if fragment
     * count more than zero
     */
    private void setActionBarArrowDependingOnFragmentsBackStack() {
        int backStackEntryCount = getSupportFragmentManager().getBackStackEntryCount();
        // Disable drawer indicator if fragment in backstack more than 0
        boolean shouldEnableDrawerIndicator = true;
        if (backStackEntryCount > 0) {
            shouldEnableDrawerIndicator = false;
        }

        mDrawerToggle.setDrawerIndicatorEnabled(shouldEnableDrawerIndicator);
    }

    /**
     * Create options menu
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        setActionBarMenu(menu);

        return true;
    }

    private void setActionBarMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.pantau, menu);

//        MenuItem badgeItem = menu.findItem(R.id.action_cart);
//        View view = MenuItemCompat.getActionView(badgeItem);
//
//        if (view != null) {
//            numberTextView = (TextView) view.findViewById(R.id.actionbar_notification_textview);
//            ImageView cartView = (ImageView) view.findViewById(R.id.actionbar_cart_image);
//            cartView.setOnClickListener(new OnClickListener() {
//
//                @Override
//                public void onClick(View v) {
//                    isRedirectFromActionBarMenu = true;
//                    // do stuff here
//                    selectItem(1);
//                }
//            });
//
//        }
    }

    /* Called whenever we call invalidateOptionsMenu() */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // If the nav drawer is open, hide action items related to the content
        // view
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Pass the event to ActionBarDrawerToggle, if it returns
        // true, then it has handled the app icon touch event
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        } else if (item.getItemId() == android.R.id.home
                && getSupportFragmentManager().popBackStackImmediate()) {
            return true;
        }

        // Handle action buttons
        switch (item.getItemId()) {
            
        }
        return true;
    }

    /**
     * Load fragment based on selected navigation drawers
     * 
     * @param position
     */
    private void selectItem(int position) {
        // update the main content by replacing fragments
        Fragment selectedContentFragment = new HomeFragment();
        
        if (position == 1) {
            // update the main content by replacing fragments
            selectedContentFragment = new UrusanFragment();
        } else if (position == 2) {
            // update the main content by replacing fragments
            selectedContentFragment = new ContactFragment();
        } else if (position == 3) {
            // update the main content by replacing fragments
            selectedContentFragment = new AboutFragment();
        }
        

        if (mContentFragment != null || isRedirectFromActionBarMenu) {
            // make sure transactions are finished before reading backstack
            // count
            getSupportFragmentManager().executePendingTransactions();
            getSupportFragmentManager().beginTransaction()
            // .setCustomAnimations(R.anim.frag_slide_in,
            // R.anim.frag_slide_out)
            .hide(mContentFragment).commit();
            // FragmentManager fm = getSupportFragmentManager();
            // for(int i = 0; i < fm.getBackStackEntryCount(); ++i) {
            // fm.popBackStack();
            // }

            isRedirectFromActionBarMenu = false;
        }

        // Set bundle extras to new
        Bundle args = new Bundle();
        args.putInt(Constant.FRAGMENT_NUMBER, position);
        selectedContentFragment.setArguments(args);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.popenter, R.anim.popexit);
        transaction.replace(R.id.content_frame, selectedContentFragment);
        transaction.commit();

        mContentFragment = selectedContentFragment;

        // update selected item and title, then close the drawer
        mDrawerList.setItemChecked(position, true);
        setTitle(mSidebarTitle[position]);
        mDrawerLayout.closeDrawer(mDrawerList);

        // Hide arrow home as up
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        // Update current fragment position
        mMenuPosition = position;
    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getSupportActionBar().setTitle(mTitle);
    }

    /**
     * When using the ActionBarDrawerToggle, you must call it during
     * onPostCreate() and onConfigurationChanged()...
     */

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggls
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

}

