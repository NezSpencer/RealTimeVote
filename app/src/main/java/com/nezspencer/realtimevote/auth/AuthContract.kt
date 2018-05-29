package com.nezspencer.realtimevote.auth

interface AuthContract {
    interface View {
        fun onActivityRes()

        fun onAuthSuccessful()

        fun onAuthFailure(errorMsg : String)
    }
    interface Presenter {
        fun authWithGoogle()
        fun authWithPhoneNumber()
    }

}