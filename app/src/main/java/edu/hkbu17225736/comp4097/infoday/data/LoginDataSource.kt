package edu.hkbu17225736.comp4097.infoday.data

import com.google.firebase.auth.FirebaseAuth
import edu.hkbu17225736.comp4097.infoday.data.model.LoggedInUser
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
import java.io.IOException



/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
class LoginDataSource {
    private val mAuth = FirebaseAuth.getInstance() //get back the signalten object

    suspend fun login(username: String, password: String): Result<LoggedInUser> {
        val resultChannel = Channel<Result<LoggedInUser>>() //create a channel
        //sign in with email,
        //signInWithEmailAndPassword
        //add a listener to check if success or fail
        mAuth.signInWithEmailAndPassword(username, password).addOnSuccessListener { authResult ->  //from firebase
            authResult?.user?.let { user ->  //to primative type
                val u = LoggedInUser(user.uid, user.email!!)
                MainScope().launch { resultChannel.send(Result.Success(u)) }  //send the value to this channel
            }
        }.addOnFailureListener {e ->  //Another listener if failure
            MainScope().launch { resultChannel.send(Result.Error(IOException("Error logging in", e))) } //send the value to this channel
        }
        return resultChannel.receive() //return the value to this channel
//        try {
//            // TODO: handle loggedInUser authentication
//            val fakeUser = LoggedInUser(java.util.UUID.randomUUID().toString(), "Jane Doe")
//            return Result.Success(fakeUser)
//        } catch (e: Throwable) {
//            return Result.Error(IOException("Error logging in", e))
//        }

    }

    fun logout() {
        // TODO: revoke authentication
    }
}