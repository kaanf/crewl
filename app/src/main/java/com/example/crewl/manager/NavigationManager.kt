package com.example.crewl.manager

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.NavOptions
import androidx.navigation.Navigator
import com.example.crewl.presentation.fragment.login.SignInFragmentDirections

object NavigationManager {
    /** <--> **/
    val fromSignInToHome = SignInFragmentDirections.actionSignInFragmentToHomeFragment()

    fun NavController.deleteAfterNavigate() = NavOptions.Builder().setPopUpTo(this.currentDestination!!.id, true).build()

    fun NavController.navigateSafely(direction: NavDirections) {
        currentDestination?.getAction(direction.actionId)?.run { navigate(direction) }
    }

    fun NavController.navigateSafely(direction: NavDirections, args: Bundle? = null, navOptions: NavOptions? = null,
                                     navExtras: Navigator.Extras? = null) {
        val action = currentDestination?.getAction(direction.actionId) ?: graph.getAction(direction.actionId)

        if (action != null && currentDestination?.id != action.destinationId)
            navigate(direction.actionId, args, navOptions, navExtras)
    }
}