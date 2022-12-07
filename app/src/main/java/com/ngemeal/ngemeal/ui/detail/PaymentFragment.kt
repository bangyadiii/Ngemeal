package com.ngemeal.ngemeal.ui.detail

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.FragmentContainer
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.midtrans.sdk.corekit.core.MidtransSDK
import com.midtrans.sdk.corekit.core.UIKitCustomSetting
import com.midtrans.sdk.corekit.core.themes.CustomColorTheme
import com.midtrans.sdk.corekit.models.snap.TransactionResult
import com.midtrans.sdk.uikit.SdkUIFlowBuilder
import com.ngemeal.ngemeal.BuildConfig
import com.ngemeal.ngemeal.Ngemeal
import com.ngemeal.ngemeal.R
import com.ngemeal.ngemeal.databinding.FragmentPaymentBinding
import com.ngemeal.ngemeal.model.response.checkout.CheckoutResponse
import com.ngemeal.ngemeal.model.response.home.Data
import com.ngemeal.ngemeal.model.response.login.User
import com.ngemeal.ngemeal.utils.Helpers.formatPrice


class PaymentFragment : Fragment(), PaymentContract.View {

    private var _binding: FragmentPaymentBinding? = null
    private val binding get() = _binding!!
    private val data : PaymentFragmentArgs by navArgs()
    private var totalHarga : Int? = null
    var progressDialog : Dialog? = null
    private lateinit var presenter : PaymentPresenter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPaymentBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        this.progressDialog = Dialog(requireContext())
        val dialogLayout  = layoutInflater.inflate(R.layout.dialog_loader, null)

        progressDialog?.let {
            it.setContentView(dialogLayout)
            it.setCancelable(false)
            it.window?.setBackgroundDrawableResource(android.R.color.transparent)
        }

        (activity as DetailActivity).toolbarPayment()
        data?.data?.let {
            initView(it)
        }
        presenter = PaymentPresenter(this)
    }

    private fun initView(it : Data){
        Glide.with(requireContext())
            .load(it.images?.get(0)?.imageUrl)
            .into(binding.ivPoster)
        binding.tvNama.text = it.name

        if(!it.price?.toString().isNullOrEmpty()) {
            binding.tvHarga.formatPrice(it.price.toString())
            var pajak = it.price?.div(10)
            binding.tvTax.formatPrice(pajak.toString())
            totalHarga = it.price!! + pajak!! + 5000 // driver
            binding.tvTotal.formatPrice(totalHarga.toString())
        }
        else {
            binding.tvTax.text = "IDR 0"
            binding.tvHarga.text = "IDR 0"
            binding.tvTax.text = "IDR 0"
            binding.tvTotal.text = "IDR 0"
        }

        var userJson = Ngemeal.getApp().getUser()
        var user  = Gson().fromJson(userJson, User::class.java)

        binding.tvNama.text = user?.name
        binding.tvNomer.text = user?.phone_number
        binding.tvNomer.text = user?.phone_number
        binding.tvAlamat.text = user?.address
        binding.tvNorum.text = user?.house_number
        binding.tvKota.text = user?.city

        binding.btnCheckout.setOnClickListener {
            presenter.getCheckout(
                data.data?.id!!,
                1, /** quantity 1 */
                it
            )
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCheckoutSuccess(checkoutResponse: CheckoutResponse, view : View) {

        initMidtransSDK(view)

        var snapToken = checkoutResponse.snapToken ?: checkoutResponse.mdSnapToken
        Toast.makeText(requireContext(), snapToken, Toast.LENGTH_SHORT).show()
        var uiSetting = UIKitCustomSetting()
        uiSetting.isSkipCustomerDetailsPages = true
        uiSetting.isEnabledAnimation = true
        uiSetting.isSaveCardChecked = true

        var mdInstance = MidtransSDK.getInstance()
        mdInstance.uiKitCustomSetting = uiSetting
        mdInstance.startPaymentUiFlow(requireContext(), snapToken!!)
    }



    override fun onCheckoutFailed(message: String) {
        Toast.makeText(context, message + ", checkout gagal" , Toast.LENGTH_SHORT).show()
    }

    override fun showLoading() {
        progressDialog?.show()
    }

    override fun dismissLoading() {
        progressDialog?.dismiss()
    }

    private fun initMidtransSDK(view : View){
        SdkUIFlowBuilder.init()
            .setClientKey(BuildConfig.MIDTRANS_CLIENT_KEY)
            .setContext(requireActivity())
            .setTransactionFinishedCallback {
                if(it.response != null) {
                    if (it.status == TransactionResult.STATUS_SUCCESS) {
                        Toast.makeText(requireContext(),  "payment success : " + it.response.paymentCode , Toast.LENGTH_SHORT)
                            .show()
                        var transaction = requireActivity().supportFragmentManager.beginTransaction()
                        transaction.add(R.id.detailHostFragment, PaymentSuccessFragment())
                        transaction.addToBackStack(null)
                        transaction.commitAllowingStateLoss()
//                        val navHostFragment = requireActivity().supportFragmentManager.findFragmentById(R.id.detailHostFragment) as NavHostFragment
//                        val navController = navHostFragment.findNavController()
//                        navController.navigate(R.id.action_payment_success)
                    } else if (it.status == TransactionResult.STATUS_PENDING) {
                        Toast.makeText(requireContext(), "PENDING", Toast.LENGTH_SHORT).show()
                    } else if (it.status == TransactionResult.STATUS_FAILED) {
                        Toast.makeText(requireContext(), "FAILED", Toast.LENGTH_SHORT).show()
                    }
                }
                else if(it.isTransactionCanceled) {
                    Toast.makeText(requireContext(), "TRANSACTION CANCELLED", Toast.LENGTH_SHORT).show()
                }else {
                    if(it.status.equals(TransactionResult.STATUS_INVALID, true)){
                        Toast.makeText(requireContext(), "TRANSACTION INVALID", Toast.LENGTH_SHORT).show()
                    }else{
                        Toast.makeText(requireContext(), "TRANSACTION FINISH with FAILURE", Toast.LENGTH_SHORT).show()
                    }
                }

            }
            .setMerchantBaseUrl(BuildConfig.BASE_URL)
            .enableLog(true)
            .setColorTheme(CustomColorTheme("#FFC700", "#E91E68", "#000"))
            .setLanguage("id")
            .buildSDK()

    }
}