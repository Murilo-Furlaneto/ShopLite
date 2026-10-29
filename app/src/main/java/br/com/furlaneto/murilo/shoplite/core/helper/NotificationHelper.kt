package br.com.furlaneto.murilo.shoplite.core.helper

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import br.com.furlaneto.murilo.shoplite.MainActivity
import br.com.furlaneto.murilo.shoplite.R

object NotificationHelper {

    private const val CHANNEL_ID = "cart_reminders"
    private const val CHANNEL_NAME = "Lembretes do carrinho"
    private const val NOTIFICATION_ID = 1001

    // 1. Cria o canal de notificações
    fun createNotificationChannel(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = "Notificações sobre produtos no carrinho"
            }

            val notificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE)
                        as NotificationManager

            notificationManager.createNotificationChannel(channel)
        }
    }

    // 2. Constrói e exibe a notificação
    fun showCartNotification(context: Context) {

        // No Android 13+, a permissão precisa estar concedida.
        if (
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU &&
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }

        // Define a Activity que será aberta ao tocar na notificação.
        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or
                    Intent.FLAG_ACTIVITY_SINGLE_TOP
        }

        val pendingIntent = PendingIntent.getActivity(
            context,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or
                    PendingIntent.FLAG_IMMUTABLE
        )

        val notification = NotificationCompat.Builder(
            context,
            CHANNEL_ID
        )
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle("Seu carrinho te espera! 🛒")
            .setContentText(
                "Você deixou produtos no carrinho. " +
                        "Que tal finalizar sua compra?"
            )
            .setStyle(
                NotificationCompat.BigTextStyle()
                    .bigText(
                        "Você deixou produtos no carrinho. " +
                                "Volte ao aplicativo e finalize sua compra!"
                    )
            )
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()

        NotificationManagerCompat.from(context)
            .notify(NOTIFICATION_ID, notification)
    }
}
