package co.edu.uniandes.miswmobile.vinilosapp

import androidx.test.espresso.IdlingResource
import java.util.concurrent.atomic.AtomicBoolean

class SimpleIdlingResource(private val resourceName: String) : IdlingResource {

    private val isIdle = AtomicBoolean(true)

    @Volatile private var resourceCallback: IdlingResource.ResourceCallback? = null

    override fun getName(): String {
        return resourceName
    }

    override fun registerIdleTransitionCallback(callback: IdlingResource.ResourceCallback?) {
        this.resourceCallback = resourceCallback
    }

    override fun isIdleNow(): Boolean {
        return isIdle.get()
    }

    fun setIdle(isIdleNow: Boolean) {
        if (isIdleNow == isIdle.get()) return
        isIdle.set(isIdleNow)
        if (isIdleNow) {
            resourceCallback?.onTransitionToIdle()
        }
    }
}