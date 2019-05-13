package by.naxa.soundrecorder.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;



public class SettingFragment extends Fragment implements
        DirectoryChooserFragment.OnFragmentInteractionListener {
    private View view;
    private Context context;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.menu_main, container, false);
        context = view.getContext();
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Button button = (Button) view.findViewById(R.id.storage);

        final DirectoryChooserConfig config = new DirectoryChooserConfig();
        config.newDirectoryName = "DialogSample";

        final DirectoryChooserFragment mDialog = DirectoryChooserFragment.newInstance(config);

        final Fragment thisfragment = this;

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.setTargetFragment(thisfragment, 0);
                mDialog.show(getFragmentManager(), "DirectoryChooserFragment");
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }


    @Override
    public void onResume() {
        super.onResume();
    }


    @Override
    public void onSelectDirectory(@NonNull String path) {
        Log.d("onSelectDirectory", "path:" + path);
        DirectoryChooserFragment fragment = (DirectoryChooserFragment) getFragmentManager().findFragmentByTag("DirectoryChooserFragment");
        fragment.dismiss();
    }

    @Override
    public void onCancelChooser() {
        DirectoryChooserFragment fragment = (DirectoryChooserFragment) getFragmentManager().findFragmentByTag("DirectoryChooserFragment");
        fragment.dismiss();

    }
}