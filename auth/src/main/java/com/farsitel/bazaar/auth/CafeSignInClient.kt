package com.farsitel.bazaar.auth

import android.app.Activity
import android.content.Intent
import android.net.Uri
import com.farsitel.bazaar.auth.connection.AuthConnection
import com.farsitel.bazaar.auth.model.CafeSignInOptions

class CafeSignInClient {

    lateinit var signInOption: CafeSignInOptions
        internal set

    internal lateinit var activity: Activity

    fun getSignInIntentWithScope(): Intent {
        checkInitializing()
        return getSignInIntentWithScope(signInOption)
    }

    private fun getSignInIntentWithScope(signInOption: CafeSignInOptions) =
        Intent(IAL_ACTION, Uri.parse(IAL_ACTION_URI))
            .apply {
                setPackage("com.farsitel.bazaar")
                putExtra(AuthConnection.PACKAGE_NAME_KEY, activity.packageName)
                putExtra(
                    PERMISSION_SCOPE_KEY,
                    signInOption.getScopes().map { it.ordinal }.toIntArray()
                )
            }


    private fun checkInitializing() {
        if (!::signInOption.isInitialized || !::activity.isInitialized) {
            throw IllegalStateException(
                "You need to create CafeSignInClient with CafeSignIn#getClinet"
            )
        }
    }

    companion object {
        private const val IAL_ACTION = "ir.cafebazaar.intent.action.IAL"
        private const val IAL_ACTION_URI = "bazaar://inapplogin"

        private const val PERMISSION_SCOPE_KEY = "permissionScope"
    }

}