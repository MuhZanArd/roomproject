package firstproject.muhzanard.id.co.crudwithroom.Database

import firstproject.muhzanard.id.co.crudwithroom.Model.User
import io.reactivex.Flowable

interface IUserDataSource {
    val allusers: Flowable<List<User>>
    fun insertUser(vararg users: User)
    fun updateUser(nama: String, id: Int)
    fun deleteUser(id: Int)


}