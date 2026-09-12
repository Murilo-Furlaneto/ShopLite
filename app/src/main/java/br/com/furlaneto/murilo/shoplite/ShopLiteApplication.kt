package br.com.furlaneto.murilo.shoplite

import android.app.Application
import br.com.furlaneto.murilo.shoplite.BuildConfig
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class ShopLiteApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
            Timber.d("Timber inicializado em modo Debug")
        } else {
            Timber.plant(ReleaseTree())
        }
    }

    /**
     * Árvore de logs para produção.
     * Silencia logs de debug e info, mas permite reportar erros.
     */
    private class ReleaseTree : Timber.Tree() {
        override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
            if (priority == android.util.Log.VERBOSE || priority == android.util.Log.DEBUG) {
                return
            }
            // Aqui você poderia integrar com Crashlytics:
            // Crashlytics.log(priority, tag, message)
        }
    }
}
