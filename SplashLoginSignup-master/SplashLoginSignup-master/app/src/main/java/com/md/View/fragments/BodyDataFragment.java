package com.md.View.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.support.design.widget.TextInputEditText;

import com.md.View.LeftViewActivity;
import com.md.View.MainActivity;
import com.md.splashloginsignup.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link BodyDataFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link BodyDataFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
//身体数据设置
public class BodyDataFragment extends android.app.Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private Button submit;
    private Button back;

    private TextInputEditText height;
    private TextInputEditText weight;
    private TextInputEditText age;
    private TextInputEditText sex;

    private  View view;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public BodyDataFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BodyDataFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BodyDataFragment newInstance(String param1, String param2) {
        BodyDataFragment fragment = new BodyDataFragment();
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
        view = inflater.inflate(R.layout.fragment_body_data, container, false);
        submit = (Button)view.findViewById(R.id.submit);
        back = (Button)view.findViewById(R.id.back);

        height = (TextInputEditText)view.findViewById(R.id.height);
        weight = (TextInputEditText)view.findViewById(R.id.weight);
        age = (TextInputEditText)view.findViewById(R.id.age);
        sex = (TextInputEditText)view.findViewById(R.id.sex);

        submit.setOnClickListener(new Mlistener());
        back.setOnClickListener(new Mlistener());
        return view;
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
    class Mlistener implements View.OnClickListener{

        @Override
        public void onClick(View view) {
            Intent intent = new Intent();
            if(view == submit){
                //跳转之前进行数据传输,提交用户数据
                intent = new Intent(view.getContext(), MainActivity.class);
            }else if(view == back){
                intent = new Intent(view.getContext(), MainActivity.class);
            }
            startActivity(intent);
        }
    }
}
