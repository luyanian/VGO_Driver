package com.lanhi.vgo.driver.mvvm.view.order;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import com.lanhi.ryon.utils.mutils.LogUtils;
import com.lanhi.ryon.utils.mutils.ToastUtils;
import com.lanhi.vgo.driver.BaseActivity;
import com.lanhi.vgo.driver.R;
import com.paypal.paypalretailsdk.DeviceManager;
import com.paypal.paypalretailsdk.PaymentDevice;
import com.paypal.paypalretailsdk.RetailSDK;
import com.paypal.paypalretailsdk.RetailSDKException;

public class PaypalReaderConnectionActivity extends BaseActivity {

  private static final String LOG_TAG = PaypalReaderConnectionActivity.class.getSimpleName();
  public static final String INTENT_STRING_EMV_READER = "EMV_READER";
  public static final String INTENT_STRING_AUDIO_JACK_READER = "AUDIO_JACK_READER";
  private ProgressBar progress;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.paypal_reader_connection_activity);
    progress = findViewById(R.id.progress);

  }


  public void onFindAndConnectClicked(View view) {
    RetailSDK.getDeviceManager().searchAndConnect(new DeviceManager.ConnectionCallback() {
      @Override
      public void connection(final RetailSDKException error, final PaymentDevice cardReader) {
        runOnUiThread(new Runnable() {
          @Override
          public void run() {
            if (error == null) {
              //Toast.makeText(getApplicationContext(), "Connected to card reader" + cardReader.getId(), Toast.LENGTH_SHORT).show();
              onReaderConnected(cardReader);
            } else {
              LogUtils.e(LOG_TAG, "Connection to a reader failed with error: " + error);
              ToastUtils.showShort("Card reader connection error: " + error.getMessage());
            }
          }
        });
      }
    });

  }


  public void onConnectToLastClicked(View view) {
    RetailSDK.getDeviceManager().connectToLastActiveReader(new DeviceManager.ConnectionCallback() {
      @Override
      public void connection(final RetailSDKException error, final PaymentDevice cardReader) {
        runOnUiThread(new Runnable() {
          @Override
          public void run() {
            if (error == null && cardReader != null) {
              //Toast.makeText(getApplicationContext(), "Connected to last active device " + cardReader.getId(), Toast.LENGTH_SHORT).show();
              onReaderConnected(cardReader);
            } else if (error != null) {
              ToastUtils.showShort("Connection to a reader failed with error: " + error);
              LogUtils.e(LOG_TAG, "Connection to a reader failed with error: " + error);
            } else {
              ToastUtils.showShort("Could not find the last card reader to connect to");
              LogUtils.d(LOG_TAG, "Could not find the last card reader to connect to");
            }
          }
        });
      }
    });
  }

  private void onReaderConnected(PaymentDevice cardReader) {
    finish();
//    LogUtils.d(LOG_TAG, "Connected to device " + cardReader.getId());
//    final TextView readerIdTxt = (TextView) findViewById(R.id.textReaderId);
//    readerIdTxt.setText(getString(R.string.connected) + " " + cardReader.getId());
//
//    final LinearLayout runTxnButtonContainer = (LinearLayout) findViewById(R.id.run_txn_btn_container);
//    runTxnButtonContainer.setVisibility(View.VISIBLE);

  }

  public void onRunTransactionClicked(View view) {
//    Intent transactionIntent = new Intent(ReaderConnectionActivity.this, ChargeActivity.class);
//    startActivity(transactionIntent);
  }


  public void onAutoConnectClicked(View view) {

    showProgressBar();
    String lastKnowReader = RetailSDK.getDeviceManager().getLastActiveBluetoothReader();
    RetailSDK.getDeviceManager().scanAndAutoConnectToBluetoothReader(lastKnowReader, new DeviceManager.ConnectionCallback() {
      @Override
      public void connection(final RetailSDKException error, final PaymentDevice cardReader) {
        runOnUiThread(new Runnable() {
          @Override
          public void run() {
            hideProgressBarShowButton();
            if (error == null && cardReader != null) {
              //Toast.makeText(getApplicationContext(), "Connected to last active device " + cardReader.getId(), Toast.LENGTH_SHORT).show();
              onReaderConnected(cardReader);
            } else if (error != null) {
              ToastUtils.showShort("Connection to a reader failed with error: " + error);
              LogUtils.e(LOG_TAG, "Connection to a reader failed with error: " + error);
            } else {
              ToastUtils.showShort("Could not find the last card reader to connect to");
              LogUtils.d(LOG_TAG, "Could not find the last card reader to connect to");
            }
          }
        });
      }
    });
  }

  public void showProgressBar()
  {
//    button.setVisibility(GONE);
//    tick.setVisibility(GONE);
    progress.setVisibility(View.VISIBLE);
  }

  public void hideProgressBarShowButton(){
    progress.setVisibility(View.GONE);
//    tick.setVisibility(GONE);
//    button.setVisibility(VISIBLE);
  }
}
