/**
 * @author Kaan Fırat
 * Last updated time : 5 September 2022 16:51
 */

package com.example.crewl.utils

/**
 * Lets the UI act on a controlled bound of states that can be defined here
 * <p>
 * @since 1.0
 */
sealed class ViewState<out T : Any> {
    /**
     * Represents UI state where the UI should be showing a loading UX to the user
     * @param isLoading will be true when the loading UX needs to display, false when not
     */
    data class Loading(val isLoading: Boolean) : ViewState<Nothing>()

    /**
     * Represents the UI state where the operation requested by the UI has been completed successfully
     * and the output of type [T] as asked by the UI has been provided to it
     * @param output result object of [T] type representing the fruit of the successful operation
     */
    data class RenderSuccess<out T : Any>(val output: T) : ViewState<T>()

    /**
     * Represents the UI state where the operation requested by the UI has failed to complete
     * either due to a IO issue or a service exception and the same is conveyed back to the UI
     * to be shown the user
     * @param throwable [Throwable] instance containing the root cause of the failure in a [String]
     */
    data class RenderFailure(val throwable: Throwable) : ViewState<Nothing>()

    /**
     *
     */
    data class Empty(val isEmpty: Boolean = true): ViewState<Nothing>()
}
