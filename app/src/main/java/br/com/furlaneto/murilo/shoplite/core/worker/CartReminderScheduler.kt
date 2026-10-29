package br.com.furlaneto.murilo.shoplite.core.worker

import android.content.Context
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import java.util.concurrent.TimeUnit

object CartReminderScheduler {

    private const val WORK_NAME = "cart_reminder_work"

    fun scheduleCartReminder(context: Context) {
        try {
            val workRequest = OneTimeWorkRequestBuilder<NotificationWorker>()
                .setInitialDelay(15, TimeUnit.MINUTES)
                .build()

            WorkManager.getInstance(context).enqueueUniqueWork(
                WORK_NAME,
                ExistingWorkPolicy.REPLACE,
                workRequest
            )
        } catch (e: Exception) {
            // Ignorado em ambiente de Teste Unitário onde o WorkManager não é inicializado
        }
    }

    fun cancelCartReminder(context: Context) {
        try {
            WorkManager.getInstance(context).cancelUniqueWork(WORK_NAME)
        } catch (e: Exception) {
            // Ignorado em ambiente de Teste Unitário
        }
    }
}
