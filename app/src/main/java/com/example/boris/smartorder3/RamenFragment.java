package com.example.boris.smartorder3;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import javax.xml.transform.Result;
public class RamenFragment extends Fragment {
    RadioGroup rdgDashi,rdgRichness,rdgGarlic,rdgSpicy,rdgTexture;
    CheckBox btnExtraSeaweedYes,btnExtraRiceYes,btnExtraEggYes;
    boolean seaWeed,egg,rice;
    int dashi,richness,garlic,spicy,texture;
    private FragmentActivity activity;
    private FragmentManager fragmentManager;
    private final static String TAG = "RamenUploadFragment";

    public RamenFragment() {

    }

    public static Fragment newInstance() {
        RamenFragment fragment = new RamenFragment();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ramen, container, false);
        customized(view);
        Button btnRamenConfirm = view.findViewById(R.id.btnRamenConfirm);
        btnRamenConfirm.setOnClickListener(confirmListener);
        activity = getActivity();
        fragmentManager = getFragmentManager();

        rdgDashi = view.findViewById(R.id.rdgDashi);
        rdgRichness = view.findViewById(R.id.rdgRichness);
        rdgGarlic = view.findViewById(R.id.rdgGarlic);
        rdgSpicy = view.findViewById(R.id.rdgSpicy);
        rdgTexture = view.findViewById(R.id.rdgTexture);

        btnExtraEggYes = view.findViewById(R.id.btnExtraEggYes);
        btnExtraSeaweedYes = view.findViewById(R.id.btnExtraSeaweedYes);
        btnExtraRiceYes = view.findViewById(R.id.btnExtraRiceYes);


        return view;

    }

    private Button.OnClickListener confirmListener = new Button.OnClickListener() {


        @Override
        public void onClick(View v) {

            switch (v.getId()) {
                case R.id.btnRamenConfirm:


                    new AlertDialog.Builder(getActivity())
                            .setTitle("是否送出？")
                            .setMessage("")
                            .setPositiveButton("確定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    int dashi = rdgDashi.getCheckedRadioButtonId();
                                    int richness = rdgRichness.getCheckedRadioButtonId();
                                    int garlic = rdgGarlic.getCheckedRadioButtonId();
                                    int spicy = rdgSpicy.getCheckedRadioButtonId();
                                    int texture = rdgTexture.getCheckedRadioButtonId();


                                    if (Common.networkConnected(activity)) {
                                        String url = Common.URL + "/RamenServlet";
                                        Ramen ramen = new Ramen(dashi, richness, garlic, spicy, texture, true,true,true);
                                        JsonObject jsonObject = new JsonObject();
                                        jsonObject.addProperty("ramen", new Gson().toJson(ramen));
                                        jsonObject.addProperty("action", "Insert");

                                        jsonObject.addProperty("dashi",dashi);
                                        jsonObject.addProperty("richness",richness);
                                        jsonObject.addProperty("garlic",garlic);
                                        jsonObject.addProperty("spicy",spicy);
                                        jsonObject.addProperty("texture",texture);
                                        jsonObject.addProperty("seaweed",seaWeed);
                                        jsonObject.addProperty("egg",egg);
                                        jsonObject.addProperty("rice",rice);


                                        int count = 0;
                                        try {
                                            String result = new CommonTask(url, jsonObject.toString()).execute().get();
                                            count = Integer.valueOf(result);
                                        } catch (Exception e) {
                                            Log.e(TAG, e.toString());

                                        }
                                        if (count == 0) {
                                            Common.showToast(getActivity(), R.string.msg_InsertFail);
                                        } else {
                                            Common.showToast(getActivity(), R.string.msg_InsertSuccess);
                                        }
                                    } else {
                                        Common.showToast(getActivity(), R.string.msg_NoNetwork);
                                    }



                                }
                            })

                            .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {


                                }
                            })
                            .show();

            }

        }
    };




    public void customized(View view)  {


        RadioGroup rdgDashi = view.findViewById(R.id.rdgDashi);
        rdgDashi.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {


                switch(checkedId) {
                    case R.id.btnDashiLight:


                        dashi = 0;






                            break;

                    case R.id.btnDashiMedium:
                        dashi =1;



                            break;

                    case R.id.btnDashiStrong:
                        dashi =2;



                        break;

                }


            }

        });



        RadioGroup rdgRichness = view.findViewById(R.id.rdgRichness);
        rdgRichness.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {


                switch (checkedId) {

                    case R.id.btnRichnessLight:
                        richness = 0;

                        break;

                    case R.id.btnRichnessMedium:
                        richness = 1;



                    case R.id.btnRichnessStrong:
                        richness = 2;



                }

            }
        });


        RadioGroup rdgGarlic = view.findViewById(R.id.rdgGarlic);
        rdgGarlic.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                switch (checkedId) {

                    case R.id.btnGarlicLight:
                        garlic = 0;

                        break;

                    case R.id.btnGarlicMedium:
                        garlic = 1;

                        break;

                    case R.id.btnGarlicStrong:
                        garlic = 2;

                        break;

                }

            }
        });



        RadioGroup rdgSpicy = view.findViewById(R.id.rdgSpicy);
        rdgSpicy.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                switch (checkedId) {

                    case R.id.btnSpicyLight:
                        spicy = 0;

                        break;

                    case R.id.btnSpicyMedium:
                        spicy =1;

                        break;

                    case R.id.btnSpicyStrong:
                        spicy=2;

                        break;

                }

            }
        });

        RadioGroup rdgTexture = view.findViewById(R.id.rdgTexture);
        rdgTexture.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                switch (checkedId) {

                    case R.id.btnTextureSoft:
                        texture = 0;

                        break;

                    case R.id.btnTextureMedium:
                        texture= 1;

                        break;

                    case R.id.btnTextureFirm:
                        texture = 2;

                        break;

                }

            }
        });



        CheckBox btnExtraSeaweedYes = view.findViewById(R.id.btnExtraSeaweedYes);
        btnExtraSeaweedYes.setOnCheckedChangeListener(new RadioButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {

                    seaWeed = true;

                }


            }
        });

        CheckBox btnExtraEggYes = view.findViewById(R.id.btnExtraEggYes);
        btnExtraEggYes.setOnCheckedChangeListener(new RadioButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {

                    egg = true;

                }


            }
        });

        CheckBox btnExtraRiceYes = view.findViewById(R.id.btnExtraRiceYes);
        btnExtraRiceYes.setOnCheckedChangeListener(new RadioButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {

                    rice = true;

                }
            }
        });




    }

}



