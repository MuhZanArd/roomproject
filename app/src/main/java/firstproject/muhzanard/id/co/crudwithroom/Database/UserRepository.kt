package firstproject.muhzanard.id.co.crudwithroom.Database

import firstproject.muhzanard.id.co.crudwithroom.Model.User
import io.reactivex.Flowable

class UserRepository(private val mLocationDataSource: IUserDataSource): IUserDataSource {
    override fun updateUser(nama: String, id: Int) {
        mLocationDataSource.updateUser(nama: String, id: Int) {
            mLocationDataSource.updateUser(nama,id)
        }
    }

    override fun deleteUser(id: Int) {
        mLocationDataSource.deleteUser(id : Int)
        mLocationDataSource.deleteUser(id)
    }

    override val allusers: Flowable<List<User>>
        get() = mLocationDataSource.allusers

    override fun insertUser(vararg users: User) {
        mLocationDataSource.insertUser(*users)
    }



    companion object {
        private var mInstance : UserRepository? = null
        fun getInstance(mLocationDataSource: IUserDataSource): UserRepository{
            if (mInstance == null){
                mInstance = UserRepository(mLocationDataSource)
            }
            return mInstance!!
        }
    }
}