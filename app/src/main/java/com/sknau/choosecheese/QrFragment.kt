package com.sknau.choosecheese

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.google.zxing.integration.android.IntentIntegrator
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class QrFragment : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_qr, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val intentIntegrator = IntentIntegrator.forSupportFragment(this)
        intentIntegrator.setBeepEnabled(false)
        intentIntegrator.setCameraId(0)
        intentIntegrator.setPrompt("Place a code on the angle")
        intentIntegrator.setOrientationLocked(false)
        intentIntegrator.setBarcodeImageEnabled(false)
        intentIntegrator.initiateScan()
    }

    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (result != null) {

            Log.d("QrFragment", "Scanned from Fragment: ${result.contents}")

            val sharedPreferences = this.getActivity()
                ?.getSharedPreferences("accessTOKEN", AppCompatActivity.MODE_PRIVATE)
            val authToken = sharedPreferences?.getString("accessToken", null)?.let {
                it
            } ?: ""

            val retrofit = LogicApiClient.getClient(authToken)
            val apiService = retrofit.create(QrApiService::class.java)
            val data = QrData(result.contents)

            apiService.sendData(data).enqueue(object : Callback<Void> {
                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    if (response.isSuccessful) {
                        Log.d("QrFragment", "Data sent successfully")
                        val bundle = bundleOf("imageUrl" to result.contents)
                        findNavController().navigate(R.id.action_qrFragment_to_homeFragment, bundle)
                        Log.d("QrFragment", "Navigating to HomeFragment")
                    } else {
                        Log.e(
                            "Response Error",
                            "Code: ${response.code()}, Message: ${response.message()}"
                        )
                    }
                }

                override fun onFailure(call: Call<Void>, t: Throwable) {
                    Log.e("Network Error", "Connection failed", t)
                }
            })

            val mActivity = activity as MainActivity
            mActivity.finishScan()

        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }
}
