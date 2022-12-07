package com.ngemeal.ngemeal.ui.profile

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.gson.Gson
import com.ngemeal.ngemeal.Ngemeal
import com.ngemeal.ngemeal.R
import com.ngemeal.ngemeal.databinding.FragmentProfileBinding
import com.ngemeal.ngemeal.model.response.PaginateResponse
import com.ngemeal.ngemeal.model.response.login.User
import com.ngemeal.ngemeal.model.response.transaction.Transaction
import com.ngemeal.ngemeal.ui.auth.AuthActivity

class ProfileFragment : Fragment(), ProfileManagementContract.View {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private var user : User? = null
    var progressDialog : Dialog? = null
    private lateinit var presenter : ProfilePresenter;

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root

        var userString = Ngemeal.getApp().getUser()
        var token = Ngemeal.getApp().getToken()
        this.user = Gson().fromJson(userString, User::class.java)
        Toast.makeText(requireContext(), "token : " + token, Toast.LENGTH_SHORT).show()

        initView()
        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        presenter = ProfilePresenter(this)


        val sectionPagerAdapter = SectionPagerAdapter(
            childFragmentManager
        )
        binding.viewPager.adapter = sectionPagerAdapter
        binding.tabsLayout.setupWithViewPager(binding.viewPager)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initView(){
        this.progressDialog = Dialog(requireContext())
        val dialogLayout  = layoutInflater.inflate(R.layout.dialog_loader, null)

        progressDialog?.let {
            it.setContentView(dialogLayout)
            it.setCancelable(false)
            it.window?.setBackgroundDrawableResource(android.R.color.transparent)
        }

        user?.let {
            Glide.with(requireContext())
                .load(it.profile_photo_url)
                .apply(RequestOptions.circleCropTransform())
                .override(250, 250)
                .into(binding.ivAvatar)
            binding.tvName.text = it.name
            binding.tvEmail.text = it.email

            binding.btnLogout.setOnClickListener {
                presenter.logout()
            }
        }

    }
    override fun onLogoutSuccess() {
        Toast.makeText(requireContext(), "Logout berhasil", Toast.LENGTH_SHORT).show()
        var intent = Intent(requireActivity(), AuthActivity::class.java)
        Ngemeal.getApp().unsetUser()
        Ngemeal.getApp().unsetToken()
        startActivity(intent)
        requireActivity().finish()
    }

    override fun onLogoutFailed(message: String) {
        Toast.makeText(requireContext(),  "logout failed : " + message, Toast.LENGTH_SHORT).show()
    }

    override fun showLoading() {
        progressDialog!!.show()
    }

    override fun dismissLoading() {
        progressDialog!!.dismiss()
    }
}