package by.naxa.soundrecorder.fragments;

import android.os.Bundle;
import android.os.Environment;
import android.os.FileObserver;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import by.naxa.soundrecorder.R;
import by.naxa.soundrecorder.adapters.FileViewerAdapter;
import by.naxa.soundrecorder.adapters.ItemMoveCallback;
import by.naxa.soundrecorder.adapters.RecyclerViewAdapter;
import by.naxa.soundrecorder.listeners.StartDragListener;
import by.naxa.soundrecorder.util.Paths;

/**
 * Created by Daniel on 12/23/2014.
 */
public class FileViewerFragment extends Fragment implements StartDragListener {
    private static final String LOG_TAG = "FileViewerFragment";

   private FileViewerAdapter mFileViewerAdapter;
    RecyclerView recyclerView;
    RecyclerViewAdapter mAdapter;
    ArrayList<String> stringArrayList = new ArrayList<>();
    ItemTouchHelper touchHelper;

    public static FileViewerFragment newInstance() {
        FileViewerFragment f = new FileViewerFragment();
        Bundle b = new Bundle();
        f.setArguments(b);

        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        observer.startWatching();
    }

    @Override
    public void onDestroy() {
        observer.stopWatching();
        super.onDestroy();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_file_viewer, container, false);





        RecyclerView mRecyclerView = v.findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        //newest to oldest order (database stores from oldest to newest)
        final LinearLayoutManager llm = new LinearLayoutManager(
                getActivity(), RecyclerView.VERTICAL, true);
        llm.setStackFromEnd(true);

        mRecyclerView.setLayoutManager(llm);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());


        mFileViewerAdapter = new FileViewerAdapter(getActivity(), llm);
        mRecyclerView.setAdapter(mFileViewerAdapter);

        return v;
  }


    public void populateRecyclerView() {

        RecyclerViewAdapter mAdapter = new RecyclerViewAdapter( stringArrayList, this );

        ItemTouchHelper.Callback callback =
                new ItemMoveCallback(mAdapter);
        touchHelper  = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(recyclerView);
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mFileViewerAdapter = null;
    }

    private final FileObserver observer =
            new FileObserver(Paths.combine(
                    Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC),
                    Paths.SOUND_RECORDER_FOLDER)) {
                // set up a file observer to watch this directory on sd card
                @Override
                public void onEvent(int event, String file) {
                    if (event == FileObserver.DELETE) {
                        // user deletes a recording file out of the app

                        final String filePath = Paths.combine(
                                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC),
                                Paths.SOUND_RECORDER_FOLDER, file);
                        Log.d(LOG_TAG, "File deleted [" + filePath + "]");

                        // remove file from database and recyclerview
                        mFileViewerAdapter.removeOutOfApp(filePath);
                    }
                }
            };


    @Override
    public void requestDrag(RecyclerView.ViewHolder viewHolder) {
        touchHelper.startDrag(viewHolder);

    }


}




