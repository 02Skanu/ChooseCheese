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
        // Inflate the layout for this fragment
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
            if (result.contents == null) {
                Toast.makeText(context, "cancelled", Toast.LENGTH_SHORT).show()
            } else {
                Log.d("Fragment", "Scanned from Fragment")
                Toast.makeText(context, "Scanned -> " + result.contents, Toast.LENGTH_SHORT)
                    .show()
                Log.d("Fragment", "$result")

                val sharedPreferences = this.getActivity()?.getSharedPreferences("accessTOKEN",
                    AppCompatActivity.MODE_PRIVATE
                )
                val authToken = sharedPreferences?.getString("accessToken", null)?.let {
                    it
                } ?: ""

                val retrofit = LogicApiClient.getClient(authToken)

                val apiService = retrofit.create(QrApiService::class.java)
                val data = QrData(result.contents) // QR 코드 결과를 사용하여 데이터 객체 생성

                apiService.sendData(data).enqueue(object : Callback<List<ResponseData>> {
                    override fun onResponse(call: Call<List<ResponseData>>, response: Response<List<ResponseData>>) {
                        if (response.isSuccessful) {
                            val responseData = response.body()
                            // Assuming we want to pass the first imageUrl or concatenate all imageUrls into a single string
                            val imageUrls = responseData?.map { it.imageUrl }?.joinToString(",") ?: ""
                            val bundle = bundleOf("imageUrl" to imageUrls)
                            findNavController().navigate(R.id.action_qrFragment_to_homeFragment, bundle)
                        } else {
                            // 에러 처리
                        }
                    }

                    override fun onFailure(call: Call<List<ResponseData>>, t: Throwable) {
                        // 실패 처리
                    }
                })


                val mActivity = activity as MainActivity
                mActivity.finishScan()
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }
}