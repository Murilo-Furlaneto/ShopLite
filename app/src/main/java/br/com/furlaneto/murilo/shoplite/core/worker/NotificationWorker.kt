package br.com.furlaneto.murilo.shoplite.core.worker

import android.content.Context
import androidx.room3.Room
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import br.com.furlaneto.murilo.shoplite.core.database.AppDatabase
import br.com.furlaneto.murilo.shoplite.core.helper.NotificationHelper
import kotlinx.coroutines.flow.first

class NotificationWorker(
    private val appContext: Context,
    params: WorkerParameters
) : CoroutineWorker(appContext, params) {

    override suspend fun doWork(): Result {
        return try {
            val db = Room.databaseBuilder(
                appContext,
                AppDatabase::class.java,
                "shoplite_database.db"
            ).build()

            val cartItems = db.cartDao().getAllCartItems().first()
            if (cartItems.isNotEmpty()) {
                NotificationHelper.showCartNotification(appContext)
            }
            Result.success()
        } catch (e: Exception) {
            Result.failure()
        }
    }
}
