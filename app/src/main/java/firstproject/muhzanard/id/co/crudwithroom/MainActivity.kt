package firstproject.muhzanard.id.co.crudwithroom

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Adapter
import android.widget.ArrayAdapter
import android.widget.Toast
import firstproject.muhzanard.id.co.crudwithroom.Database.UserRepository
import firstproject.muhzanard.id.co.crudwithroom.Local.UserDataSource
import firstproject.muhzanard.id.co.crudwithroom.Local.UserDatabase
import firstproject.muhzanard.id.co.crudwithroom.Model.Form
import firstproject.muhzanard.id.co.crudwithroom.Model.User
import io.reactivex.ObservableOnSubscribe
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Action
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_item_list.*
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {

    private var userRepository: UserRepository? = null
    private var compositeDisposable: CompositeDisposable? = null

    lateinit var adapter: ArrayAdapter<*>
    var userList: MutableList<User> = ArrayList()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_item_list)

        compositeDisposable = CompositeDisposable()

        val userDatabase = UserDatabase.getInstance(this)
        userRepository = UserRepository.getInstance(UserDataSource.getInstance(userDatabase.userDao()))


        val adduser = User()
        adduser.nama = "SANDY"
        insertUser(adduser)

        loadData()


    }

    private fun insertUser(user: User){
        val disposable = io.reactivex.Observable.create(ObservableOnSubscribe<Any> {
            e -> userRepository!!.insertUser()
            e.onComplete()
        })
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(Consumer {
                // succes
            },
                Consumer { throwable ->
                    Toast.makeText(this, "" + throwable.message, Toast.LENGTH_SHORT)
                }, Action {
                    Toast.makeText(this, "Data Berhasil Tersimpan", Toast.LENGTH_SHORT)
                })
        compositeDisposable!!.addAll(disposable)

    }

    private fun onGetAllUserSucces(users: List<User>){
        userList.clear()
        userList.addAll(users)
        adapter.notifyDataSetChanged()
    }

    private fun loadData(){
        val disposable = userRepository!!.allusers
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({users -> showData(users)}){
                throwable -> Toast.makeText(this, ""+throwable.message, Toast.LENGTH_SHORT)
            }
        compositeDisposable!!.add(disposable)

    }

    private fun showData(items: List<User>?){
        list.adapter = UserRecyclerViewAdapter(items, object : UserRecyclerViewAdapter.OnListFragmentInteractionListener{
            override fun onListFragmentInteraction(item: User?) {

                val kirim = Intent(this@MainActivity,Form::class.java)
                kirim.putExtra("kirim_id", item?.id)
                startActivity(kirim)

                val tangkap = intent
                tangkap.getStringExtra("kirim_id")


            }
        })
    }

    override fun onDestroy() {
        compositeDisposable?.clear()
        super.onDestroy()
    }
}
