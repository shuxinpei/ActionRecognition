package com.md.View.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.md.View.ArmActivity;
import com.md.View.HipActivity;
import com.md.View.SplashActivity;
import com.md.View.ViewComponent.StartActionButton;
import com.md.splashloginsignup.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SpecifyActionFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SpecifyActionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SpecifyActionFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private LinearLayout arm_layout;
    private LinearLayout back_layout;
    private LinearLayout chest_layout;
    private LinearLayout hip_layout;
    private LinearLayout leg_layout;
    private LinearLayout shoulders_layout;
    private LinearLayout abdomen_layout;
    private View  rootView;
    private OnFragmentInteractionListener mListener;

    public SpecifyActionFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SpecifyActionFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SpecifyActionFragment newInstance(String param1, String param2) {
        SpecifyActionFragment fragment = new SpecifyActionFragment();
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
        rootView =  inflater.inflate(R.layout.fragment_specify_action, container, false);
        arm_layout = rootView.findViewById(R.id.arm_layout);
        back_layout = rootView.findViewById(R.id.back_layout);
        chest_layout = rootView.findViewById(R.id.chest_layout);
        hip_layout = rootView.findViewById(R.id.hip_layout);
        leg_layout = rootView.findViewById(R.id.leg_layout);
        shoulders_layout = rootView.findViewById(R.id.shoulders_layout);
        abdomen_layout = rootView.findViewById(R.id.abdomen_layout);
        arm_layout.setOnClickListener(new layoutONclickListener());
        back_layout.setOnClickListener(new layoutONclickListener());
        chest_layout.setOnClickListener(new layoutONclickListener());
        hip_layout.setOnClickListener(new layoutONclickListener());
        leg_layout.setOnClickListener(new layoutONclickListener());
        shoulders_layout.setOnClickListener(new layoutONclickListener());
        abdomen_layout.setOnClickListener(new layoutONclickListener());

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
    class layoutONclickListener implements View.OnClickListener{
        @Override
        public void onClick(View view) {
                if(view == arm_layout){
                //跳转
                    startActivity(new Intent(rootView.getContext(), ArmActivity.class));
                }else if(view == back_layout){
                    startActivity(new Intent(rootView.getContext(), ArmActivity.class));
                }else if(view == chest_layout){
                    startActivity(new Intent(rootView.getContext(), ArmActivity.class));
                }else if(view == hip_layout){
                    startActivity(new Intent(rootView.getContext(), HipActivity.class));
                }else if(view == leg_layout){
                    startActivity(new Intent(rootView.getContext(), HipActivity.class));
                }else if(view == abdomen_layout){
                    startActivity(new Intent(rootView.getContext(), HipActivity.class));
                }else if(view == shoulders_layout){
                    startActivity(new Intent(rootView.getContext(), ArmActivity.class));
                }
        }
    }
}
