package com.ngemeal.ngemeal.ui.order.detailorders

import android.app.Dialog
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.midtrans.sdk.corekit.core.MidtransSDK
import com.midtrans.sdk.corekit.core.UIKitCustomSetting
import com.midtrans.sdk.corekit.core.themes.CustomColorTheme
import com.midtrans.sdk.corekit.models.snap.TransactionResult
import com.midtrans.sdk.uikit.SdkUIFlowBuilder
import com.ngemeal.ngemeal.BuildConfig
import com.ngemeal.ngemeal.R
import com.ngemeal.ngemeal.databinding.FragmentOrderDetailBinding
import com.ngemeal.ngemeal.model.response.transaction.Transaction
import com.ngemeal.ngemeal.ui.MainActivity
import com.ngemeal.ngemeal.ui.detail.PaymentSuccessFragment
import com.ngemeal.ngemeal.ui.order.OrderFragment
import com.ngemeal.ngemeal.utils.Helpers.formatPrice


class OrderDetailFragment : Fragment(), OrdersDetailContract.View {
    private var _binding : FragmentOrderDetailBinding? = null
    private val binding get() = _binding!!
    var progressDialog: Dialog? = null
    lateinit var presenter : OrdersDetailPresenter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentOrderDetailBinding.inflate(layoutInflater)
        val view : View = binding.root
        return view
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        arguments?.let {
            var data : Transaction? = null
            if (Build.VERSION.SDK_INT >= 33) {
                data = it.getParcelable("data", Transaction::class.java)
            } else {
                data = it.getParcelable<Transaction>("data")
            }
            initView(data)
        }
        initView()
    }

    private fun initView(data: Transaction?) {
        data?.let {
            binding.tvTitle.text = it.food?.name
            binding.tvQuantity.text = it.quantity.toString()
            if (!data?.food?.price.toString().isNullOrEmpty()) {
                var totalTax = data?.food?.price?.div(10)
                binding.tvTax.formatPrice(totalTax.toString())

                binding.tvHarga.formatPrice(it.food?.price.toString())
                binding.tvPrice.formatPrice(it.total.toString())
                var total = data?.food?.price!! + totalTax!! + 10000
                binding.tvTotal.formatPrice(total.toString())
            } else {
                binding.tvPrice.text = "IDR. 0"
                binding.tvTax.text = "IDR. 0"
                binding.tvTotal.text = "IDR. 0"
            }

            binding.tvNama.text = it.user?.name
            binding.tvPhone.text = it.user?.phone_number
            binding.tvAddress.text = it.user?.address
            binding.tvCity.text = it.user?.city
            binding.tvOrderStatus.text = it.trxStatus


            if (it.trxStatus.equals("FAILED", true)) {
                binding.btnCancelled.visibility = View.VISIBLE
                binding.constraintLayout3.visibility = View.VISIBLE
                binding.tvPending.text = "Failed"
            } else if (it.trxStatus.equals("SUCCESS", true)) {
                binding.btnCancelled.visibility = View.INVISIBLE
                binding.constraintLayout3.visibility = View.VISIBLE
                binding.tvPending.text = "Paid"
            } else if (it.trxStatus.equals("PENDING", true)) {
                binding.btnCancelled.visibility = View.VISIBLE
                binding.btnCancelled.text = "Pay Now"
                binding.constraintLayout3.visibility = View.VISIBLE
                binding.tvPending.text = "Pending"
            }
            Glide.with(requireActivity())
                .load(it.food?.images?.get(0)?.imageUrl)
                .apply(
                    RequestOptions().placeholder(R.drawable.iv_sample_product)
                        .error(R.drawable.iv_sample_product)
                )
                .into(binding.ivPoster)

            binding.btnCancelled.setOnClickListener {
                if (binding.btnCancelled.text.equals("Pay Now")) {
                    initMidtransSDK(it)
                    var snapToken = data.snapToken ?: data.mdSnapToken
                    var uiSetting = UIKitCustomSetting()
                    uiSetting.isSkipCustomerDetailsPages = true
                    uiSetting.isEnabledAnimation = true
                    uiSetting.isSaveCardChecked = true

                    var mdInstance = MidtransSDK.getInstance()
                    mdInstance.uiKitCustomSetting = uiSetting
                    mdInstance.startPaymentUiFlow(requireContext(), snapToken!!)
                } else {
                    presenter.getUpdateTransaction(it.id.toString(), "CANCELLED")

                }
            }
        }


    }
    private fun initView() {
        binding.iniToolbar1.toolbar.title = "Pembayaran"
        binding.iniToolbar1.toolbar.subtitle = "Self reward buat kamu"
        binding.iniToolbar1.toolbar.navigationIcon  = resources.getDrawable(R.drawable.ic_arrow_back_000,null)
        binding.iniToolbar1.toolbar.setNavigationOnClickListener { (activity as MainActivity).onBackPressed() }

        progressDialog = Dialog(requireContext())
        val dialogLayout = layoutInflater.inflate(R.layout.dialog_loader, null)

        progressDialog?.let {
            it.setContentView(dialogLayout)
            it.setCancelable(false)
            it.window?.setBackgroundDrawableResource(android.R.color.transparent)
        }
    }

    override fun onUpdateTransactionSuccess(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    override fun onUpdateTransactionFailed(message: String) {
        Toast.makeText(requireContext(), "update failed :" + message, Toast.LENGTH_SHORT).show()
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
                        transaction.add(R.id.detailHostFragment, OrderFragment())
                        transaction.addToBackStack(null)
                        transaction.commitAllowingStateLoss()

//                        val navController = Navigation.findNavController(view)
//                        navController.navigate(R.id.action_fragmentDetailOrders_to_navigation_order)
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