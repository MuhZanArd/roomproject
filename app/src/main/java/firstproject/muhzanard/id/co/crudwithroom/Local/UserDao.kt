package firstproject.muhzanard.id.co.crudwithroom.Local

import android.arch.persistence.room.*
import firstproject.muhzanard.id.co.crudwithroom.Model.User
import io.reactivex.Flowable

@Dao
interface UserDao {

    // Create
    @get:Query("SELECT * FROM users")
    val allUser: Flowable<List<User>>

    //Read
    @Insert
    fun insertUser(vararg users: User)

    //Updatae
    @Query("Update users SET nama =:nama WHERE id=:id" )
    fun updateUser(nama: String, id: Int)

    //Delte
    @Query ("DELETE FROM users WHERE id=:id")
    fun deleteUser(id: Int)
}