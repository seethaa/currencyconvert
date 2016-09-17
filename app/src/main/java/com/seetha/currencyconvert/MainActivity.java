package com.seetha.currencyconvert;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemSelected;
import models.CurrencyConversion;
import models.Rates;
import network.FixerAPIInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    public static final String BASE_URL = "http://api.fixer.io/";
    public static final String OUTPUT_ROTATION_VALUE = "OUTPUT_ON_ROTATION";

    @BindView(R.id.btnConvert) Button mConvertButton;
    @BindView(R.id.spinnerFrom) Spinner mSpinnerFrom;
    @BindView(R.id.spinnerTo) Spinner mSpinnerTo;
    @BindView(R.id.etInput) EditText mInputEditText;
    @BindView(R.id.tvOutput) TextView mCurrencyOutput;

    private Retrofit mRetrofit;
    private String mBaseCurrency = null;
    private String mOutputCurrency = null;
    private float mInputAmount = 0;
    private String mOutputTextRetrieved;
    private String mOutputTextCalculated;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);


    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putString(OUTPUT_ROTATION_VALUE, mOutputTextCalculated);
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mOutputTextRetrieved = savedInstanceState.getString(OUTPUT_ROTATION_VALUE);
        if (mOutputTextRetrieved !=null){
            mCurrencyOutput.setText(mOutputTextRetrieved);
        }
    }

    @OnItemSelected(R.id.spinnerFrom)
    public void spinnerFromItemSelected(Spinner spinner, int position) {
        mBaseCurrency = spinner.getItemAtPosition(position).toString();
    }

    @OnItemSelected(R.id.spinnerTo)
    public void spinnerToItemSelected(Spinner spinner, int position) {
        mOutputCurrency = spinner.getItemAtPosition(position).toString();

    }

    @OnClick(R.id.btnConvert)
    public void convertAmount(Button button) {

        //Check if input value field is filled
        if (!TextUtils.isEmpty(mInputEditText.getText())){
            mInputAmount = Float.parseFloat(mInputEditText.getText().toString());
        }
        else{
            mInputEditText.setText("0");
        }

        if (mBaseCurrency == null || mOutputCurrency ==null){
            Toast.makeText(getApplicationContext(), "Please input values for all fields.", Toast.LENGTH_LONG).show();
        }

        if (mBaseCurrency != null && mOutputCurrency!=null && !TextUtils.isEmpty(mInputEditText.getText())) {
            getCurrencyConversion();
        }
    }


    private void getCurrencyConversion() {
        mRetrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        FixerAPIInterface service = mRetrofit.create(FixerAPIInterface.class);
        Call<CurrencyConversion> call = service.getCurrencyConversion(mBaseCurrency, mOutputCurrency);
        call.enqueue(new Callback<CurrencyConversion>() {
            @Override
            public void onResponse(Call<CurrencyConversion> call, Response<CurrencyConversion> response) {
                CurrencyConversion currencyConversion = response.body();


                if (mBaseCurrency == mOutputCurrency){
                    mCurrencyOutput.setText(mInputAmount + " " + mOutputCurrency);

                }
                else {
                    Rates rates = currencyConversion.getRates();
                    Float multiplier = rates.getOutputConversion(mOutputCurrency);
                    Float convertedAmount = multiplier * mInputAmount;
                    mOutputTextCalculated = convertedAmount + " " + mOutputCurrency;
                    mCurrencyOutput.setText(mOutputTextCalculated);
                }


            }

            @Override
            public void onFailure(Call<CurrencyConversion> call, Throwable t) {
                // toast error if request failed
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();

            }
        });
    }
}
