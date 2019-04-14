package firstproject.muhzanard.id.co.crudwithroom.Model

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import firstproject.muhzanard.id.co.crudwithroom.Database.UserRepository
import firstproject.muhzanard.id.co.crudwithroom.R
import io.reactivex.ObservableOnSubscribe
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Action
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers

class Form : AppCompatActivity() {

    private var userRepository: UserRepository? = null
    private var compositeDisposable: CompositeDisposable? = null

    lateinit var adapter: ArrayAdapter<*>
    var userList: MutableList<User> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form)

    }

    private fun insertUser(user: User){
        val disposable = io.reactivex.Observable.create(ObservableOnSubscribe<Any> {
                e -> userRepository!!.insertUser()
            e.onComplete()
        })
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(
                Consumer {
                // succes
            },
                Consumer { throwable ->
                    Toast.makeText(this, "" + throwable.message, Toast.LENGTH_SHORT)
                }, Action {
                    Toast.makeText(this, "Data Berhasil Tersimpan", Toast.LENGTH_SHORT)
                })
        compositeDisposable!!.addAll(disposable)

    }

    override fun onDestroy() {
        compositeDisposable?.clear()
        super.onDestroy()
    }
}
