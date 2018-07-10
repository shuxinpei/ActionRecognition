package com.md.View.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.ListView;

import com.md.splashloginsignup.R;

import java.util.ArrayList;
import java.util.Date;

import static android.graphics.Color.rgb;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FreeActionFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FreeActionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */

public class FreeActionFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private Button startAction;
    private Button title_manage;
    private ListView lv;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private long LStartTime;
    private long LEndTime;
    private long LAllTime;
    private FreeActionListViewAdapter adapter;
    //同时减去中间暂停的时间
    private boolean boStart =false;
    //使用Flag进行标识是否已经开始

    private OnFragmentInteractionListener mListener;

    public FreeActionFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FreeActionFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FreeActionFragment newInstance(String param1, String param2) {
        FreeActionFragment fragment = new FreeActionFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_free_action, container, false);
        startAction = (Button) rootView.findViewById(R.id.startAction);
        startAction.setOnClickListener(new startActionBtnListener());
        title_manage =(Button) rootView.findViewById(R.id.title_manage);
        lv = (ListView) rootView.findViewById(R.id.lv);
        //title_manage.contentHorizontalAlignment = UIControlContentHorizontalAlignmentRight;
        //title_manage.titleEdgeInsets = UIEdgeInsetsMake(0, 0, 0, 10);
        adapter = new FreeActionListViewAdapter(FreeActionFragment.this.getContext());
        // listview绑定adapter适配器
        lv.setAdapter(adapter);

        return rootView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
                /**
                 * This interface must be implemented by activities that contain this
                 * fragment to allow an interaction in this fragment to be communicated
                 * to the activity and potentially other fragments contained in that
                 * activity.
                 * <p>
                 * See the Android Training lesson <a href=
                 * "http://developer.android.com/training/basics/fragments/communicating.html"
                 * >Communicating with Other Fragments</a> for more information.
                 */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
    //改变样式的监听器
    public class startActionBtnListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            try{
                if (view == startAction) {
                    if(!boStart){
                        LStartTime = System.currentTimeMillis();
                        startAction.setText("结束运动");
                        startAction.setBackgroundColor(rgb(152,251,152));
                        boStart= true;
                    } else if (boStart) {//暂停运动
                        boStart =false;
                        LEndTime = System.currentTimeMillis();
                        LAllTime = LStartTime -LEndTime;
                        startAction.setText("开始运动");
                        startAction.setBackgroundColor(rgb(255,69,0));
                        //这边需要再调用服务器端代码，回传结果
                        System.out.println(LAllTime+"总运动时间");
                    }
                }
            }catch (Exception ex){
                ex.printStackTrace();
            }
        }
    }
}
