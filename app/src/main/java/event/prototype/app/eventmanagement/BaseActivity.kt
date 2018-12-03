package event.prototype.app.eventmanagement

import android.os.Bundle
import android.support.annotation.IdRes
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v7.app.AppCompatActivity
import event.prototype.app.eventmanagement.di.MainComponent


abstract class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Dagger DI
        val app = application as MasterApplication
        configureDependencies(app.getMainComponent())
    }

    protected abstract fun configureDependencies(mainComponent: MainComponent)

    /**
     * Addes the fragment to the given resource
     *
     * @param layoutResource where the fragment will be added
     * @param fragment       to be added
     */
    fun addFragment(@IdRes layoutResource: Int, fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.add(layoutResource, fragment)
        transaction.commit()
    }

    /**
     * Replace the current fragment with new one
     *
     * @param layoutResource id of the container
     * @param fragment       to be replaced
     * @param addToBackStack add transaction to backstack
     */
    fun replaceFragment(@IdRes layoutResource: Int, fragment: Fragment, addToBackStack: Boolean) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(layoutResource, fragment)
        if (addToBackStack)
            transaction.addToBackStack(null)
        else
            supportFragmentManager.popBackStack(0, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        transaction.commitAllowingStateLoss()
    }


    fun addFragment(@IdRes layoutResource: Int, fragment: Fragment, addToBackStack: Boolean) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.add(layoutResource, fragment)
        if (addToBackStack)
            transaction.addToBackStack(null)
        else
            supportFragmentManager.popBackStack(0, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        transaction.commitAllowingStateLoss()
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 0) {
            supportFragmentManager.popBackStack()
        } else {
            super.onBackPressed()
        }
    }
}