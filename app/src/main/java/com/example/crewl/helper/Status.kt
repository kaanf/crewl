/**
 * @author Kaan Fırat
 *
 * Last updated time : 3 September 2022 16:53
 */

package com.example.crewl.helper

sealed class Status<out T, out F>(val data : T? = null , val message : F? = null) {
    class Success<T>(data : T) : Status<T, Nothing>(data)
    class Error<T,F>(message : F?, data : T? = null) : Status<T, F>(data , message)
    class Loading<T>(data : T? = null) : Status<T, Nothing>(data)
    class Empty<T>(data : T? = null) : Status<T, Nothing>(data)
}
