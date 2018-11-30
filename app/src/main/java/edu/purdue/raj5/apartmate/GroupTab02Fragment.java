package edu.purdue.raj5.apartmate;
import android.support.v4.app.Fragment;
import android.os. Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class GroupTab02Fragment extends Fragment{
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_tab2, container, false);
        initializeTheme();
        return rootView;
    }

    public void getAppTheme(String theme) { //theme is "light" or "dark"

        //call this inside every activity
        RelativeLayout ll = (RelativeLayout) findViewById(R.id.2_tab);
        // The following code is used for theme preferences.
        if (groupTabsActivity.s.equals("dark")) {
            ll.setBackgroundColor(Color.DKGRAY);

        } else {
            ll.setBackgroundColor(Color.WHITE);
        }


    }

    // This method is called in the onCreate. This is used to set theme according to the user's preferences.
    private void initializeTheme() {
        String theme = "";
        try {
            FileInputStream fis = openFileInput("theme");
            Scanner scanner = new Scanner(fis);
            theme = scanner.next();
            scanner.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        if (theme.contains("dark"))
            getAppTheme("dark");

        else
            getAppTheme("light");
    }

}