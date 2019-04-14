package firstproject.muhzanard.id.co.crudwithroom.Local

import firstproject.muhzanard.id.co.crudwithroom.Database.IUserDataSource
import firstproject.muhzanard.id.co.crudwithroom.Model.User
import io.reactivex.Flowable

class UserDataSource (private val userDao: UserDao): IUserDataSource{
    override fun updateUser(nama: String, id: Int) {
        userDao.updateUser(nama, id)
    }

    override fun deleteUser(id: Int) {
        userDao.deleteUser(id)
    }


    override val allusers: Flowable<List<User>>
        get() = userDao.allUser

    override fun insertUser(vararg users: User) {
        userDao.insertUser(*users)
    }

}