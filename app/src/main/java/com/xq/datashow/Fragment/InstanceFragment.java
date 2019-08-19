package com.xq.datashow.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xq.datashow.OperationDateActivity;
import com.xq.datashow.R;
import com.xq.datashow.SearchBrandActivity;

public class InstanceFragment extends Fragment {

    View mView;
    private CardView mOperationDataCV, mCarsDataCV;

    public InstanceFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_instance, container, false);

        initViews();

        return mView;
    }

    private void initViews() {
        mOperationDataCV = mView.findViewById(R.id.cv_instance_yunying);
        mOperationDataCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), OperationDateActivity.class));
            }
        });

        mCarsDataCV = mView.findViewById(R.id.cv_instance_qiche);
        mCarsDataCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), SearchBrandActivity.class));
            }
        });
    }

}
