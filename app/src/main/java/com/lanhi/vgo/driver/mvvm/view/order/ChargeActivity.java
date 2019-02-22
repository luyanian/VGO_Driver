package com.lanhi.vgo.driver.mvvm.view.order;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.lanhi.ryon.utils.mutils.LogUtils;
import com.lanhi.vgo.driver.BaseActivity;
import com.lanhi.vgo.driver.R;
import com.paypal.paypalretailsdk.DeviceUpdate;
import com.paypal.paypalretailsdk.FormFactor;
import com.paypal.paypalretailsdk.Invoice;
import com.paypal.paypalretailsdk.PaymentDevice;
import com.paypal.paypalretailsdk.RetailSDK;
import com.paypal.paypalretailsdk.RetailSDKException;
import com.paypal.paypalretailsdk.TransactionBeginOptions;
import com.paypal.paypalretailsdk.TransactionContext;
import com.paypal.paypalretailsdk.TransactionManager;
import com.paypal.paypalretailsdk.TransactionRecord;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ChargeActivity extends BaseActivity{
    private static final String LOG_TAG = ChargeActivity.class.getSimpleName();

    TransactionContext currentTransaction;
    Invoice currentInvoice;
    Invoice invoiceForRefund;

    TextView tvAmount;

    // payment option constants
    public static final String OPTION_AUTH_CAPTURE = "authCapture";
    public static final String OPTION_CARD_READER_PROMPT = "cardReader";
    public static final String OPTION_APP_PROMPT= "appPrompt";
    public static final String OPTION_TIP_ON_READER = "tipReader";
    public static final String OPTION_AMOUNT_TIP = "amountTip";
    public static final String OPTION_QUICK_CHIP_ENABLED = "quickChipEnabled";
    public static final String OPTION_MAGNETIC_SWIPE = "magneticSwipe";
    public static final String OPTION_CHIP = "chip";
    public static final String OPTION_CONTACTLESS = "contactless";
    public static final String OPTION_MANUAL_CARD= "manualCard";
    public static final String OPTION_SECURE_MANUAL= "secureManual";
    public static final String OPTION_TAG= "tag";

    // payment option booleans
    private boolean isAuthCaptureEnabled = false;
    private boolean isCardReaderPromptEnabled = true;
    private boolean isAppPromptEnabled = true;
    private boolean isTippingOnReaderEnabled = false;
    private boolean isAmountBasedTippingEnabled = false;
    private boolean isQuickChipEnabled = false;
    private boolean isMagneticSwipeEnabled = true;
    private boolean isChipEnabled = true;
    private boolean isContactlessEnabled = true;
    private boolean isManualCardEnabled = true;
    private boolean isSecureManualEnabled = true;
    private String tagString = "";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.paypal_charge_activity);
        tvAmount = (TextView) findViewById(R.id.amount);
        tvAmount.setText("1");
    }


    public void onCreateInvoiceClicked() {
        LogUtils.d(LOG_TAG, "onCreateInvoiceClicked");
        String amountText = tvAmount.getText().toString();
        BigDecimal amount = BigDecimal.ZERO;
        if (null != amountText && amountText.length() > 0) {
            amountText = String.format("%.2f", Double.parseDouble(amountText));
            amount = new BigDecimal(amountText);
        }
        LogUtils.d(LOG_TAG, "onCreateInvoiceClicked amount:" + amount);

        currentInvoice = new Invoice(RetailSDK.getMerchant().getCurrency());
        BigDecimal quantity = new BigDecimal(1);
        currentInvoice.addItem("Item", quantity, amount, 1, null);
        // BigDecimal gratuityAmt = new BigDecimal(gratuityField.getText().toString());
        // if(gratuityAmt.intValue() > 0){
        //    invoice.setGratuityAmount(gratuityAmt);
        // }

    }



    public void onCreateTransactionClicked() {
        LogUtils.d(LOG_TAG, "onCreateTransactionClicked");
        RetailSDK.getTransactionManager().createTransaction(currentInvoice, new TransactionManager.TransactionCallback() {
            @Override
            public void transaction(RetailSDKException e, final TransactionContext context) {
                if (e != null) {
                    final String errorTxt = e.toString();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(), "create transaction error: " + errorTxt, Toast.LENGTH_SHORT).show();
                        }
                    });
                }else{
                    currentTransaction = context;
                    onAcceptTransactionClicked();
                }
            }
        });
    }

    public void onAcceptTransactionClicked() {
        LogUtils.d(LOG_TAG, "onAcceptTransactionClicked");
        PaymentDevice activeDevice = RetailSDK.getDeviceManager().getActiveReader();
        if(activeDevice==null){
            Intent intent = new Intent(this,PaypalReaderConnectionActivity.class);
            startActivityForResult(intent,0);
            return;
        }
        DeviceUpdate deviceUpdate = activeDevice.getPendingUpdate();
        if (deviceUpdate != null && deviceUpdate.getIsRequired() && !deviceUpdate.getWasInstalled())
        {
            deviceUpdate.offer(new DeviceUpdate.CompletedCallback()
            {
                @Override
                public void completed(RetailSDKException e, Boolean aBoolean)
                {
                    LogUtils.d(LOG_TAG, "device update completed");
                    ChargeActivity.this.beginPayment();
                }
            });

        }else {
            beginPayment();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        onAcceptTransactionClicked();
    }

    private void beginPayment() {
        currentTransaction.setCompletedHandler(new TransactionContext.TransactionCompletedCallback() {
            @Override
            public void transactionCompleted(RetailSDKException error, TransactionRecord record) {
                ChargeActivity.this.transactionCompleted(error, record);
            }
        });

        TransactionBeginOptions options = new TransactionBeginOptions();
        options.setShowPromptInCardReader(isCardReaderPromptEnabled);
        options.setShowPromptInApp(isAppPromptEnabled);
        options.setIsAuthCapture(isAuthCaptureEnabled);
        options.setAmountBasedTipping(isAmountBasedTippingEnabled);
//        options.setQuickChipEnabled(isQuickChipEnabled);
        options.setTippingOnReaderEnabled(isTippingOnReaderEnabled);
        options.setTag(tagString);
        options.setPreferredFormFactors(getPreferredFormFactors());
        currentTransaction.beginPayment(options);
    }

    void transactionCompleted(RetailSDKException error, final TransactionRecord record) {
        if (error != null) {
            final String errorTxt = error.toString();
//            if (errorTxt.toLowerCase().contains("offline payment enabled")){
//                goToOfflinePayCompleteActivity();
//            }else {
                runOnUiThread(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        Toast.makeText(getApplicationContext(), "transaction error: " + errorTxt, Toast.LENGTH_SHORT).show();
                        //refundButton.setEnabled(false);
                    }
                });
//            }
        } else {
            invoiceForRefund = currentTransaction.getInvoice();
            final String recordTxt =  record.getTransactionNumber();
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
//                    if (isAuthCaptureEnabled) {
//                        goToAuthCaptureActivity(record);
//                    }else {
//                        goToRefundActivity();
//                    }
                    Toast.makeText(getApplicationContext(), String.format("Completed Transaction %s", recordTxt), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }



//    public void goToAuthCaptureActivity(TransactionRecord record){
//        LogUtils.d(LOG_TAG, "goToAuthCaptureActivity");
//        AuthCaptureActivity.invoiceForRefund = invoiceForRefund;
//        Intent intent = new Intent(ChargeActivity.this, AuthCaptureActivity.class);
//        String authId = record.getTransactionNumber();
//        String invoiceId = record.getInvoiceId();
//        BigDecimal amount = currentInvoice.getTotal();
//        LogUtils.d(LOG_TAG, "goToAuthCaptureActivity total: " + amount);
//        intent.putExtra(INTENT_TRANX_TOTAL_AMOUNT, amount);
//        intent.putExtra(INTENT_AUTH_ID, authId);
//        intent.putExtra(INTENT_INVOICE_ID, invoiceId);
//        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        startActivity(intent);
//    }

    public void goToRefundActivity(){
//        LogUtils.d(LOG_TAG, "goToRefundActivity");
//        RefundActivity.invoiceForRefund = invoiceForRefund;
//        Intent refundIntent = new Intent(ChargeActivity.this, RefundActivity.class);
//        BigDecimal amount = currentInvoice.getTotal();
//        LogUtils.d(LOG_TAG, "goToRefundActivity total: " + amount);
//        refundIntent.putExtra(INTENT_TRANX_TOTAL_AMOUNT, amount);
//        refundIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        startActivity(refundIntent);
    }

    public void onChargeClicked(View v) {
        onCreateInvoiceClicked();
        onCreateTransactionClicked();
        onAcceptTransactionClicked();
    }

    public List<FormFactor> getPreferredFormFactors() {
        List<FormFactor> formFactors = new ArrayList<>();
        if (isMagneticSwipeEnabled) {
            formFactors.add(FormFactor.MagneticCardSwipe);
        }
        if (isChipEnabled) {
            formFactors.add(FormFactor.Chip);
        }
        if (isContactlessEnabled) {
            formFactors.add(FormFactor.EmvCertifiedContactless);
        }
        if (isSecureManualEnabled) {
            formFactors.add(FormFactor.SecureManualEntry);
        }
        if (isManualCardEnabled) {
            formFactors.add(FormFactor.ManualCardEntry);
        }

        if (formFactors.size() == 0) {
            formFactors.add(FormFactor.None);
        }
        return formFactors;

    }



    private Bundle getOptionsBundle() {
        Bundle bundle = new Bundle();
        bundle.putBoolean(OPTION_AUTH_CAPTURE,isAuthCaptureEnabled);
        bundle.putBoolean(OPTION_CARD_READER_PROMPT,isCardReaderPromptEnabled);
        bundle.putBoolean(OPTION_APP_PROMPT,isAppPromptEnabled);
        bundle.putBoolean(OPTION_TIP_ON_READER,isTippingOnReaderEnabled);
        bundle.putBoolean(OPTION_AMOUNT_TIP,isAmountBasedTippingEnabled);
        bundle.putBoolean(OPTION_QUICK_CHIP_ENABLED, isQuickChipEnabled);
        bundle.putBoolean(OPTION_MAGNETIC_SWIPE,isMagneticSwipeEnabled);
        bundle.putBoolean(OPTION_CHIP,isChipEnabled);
        bundle.putBoolean(OPTION_CONTACTLESS,isContactlessEnabled);
        bundle.putBoolean(OPTION_MANUAL_CARD,isManualCardEnabled);
        bundle.putBoolean(OPTION_SECURE_MANUAL,isSecureManualEnabled);
        bundle.putString(OPTION_TAG,tagString);
        return bundle;
    }
}