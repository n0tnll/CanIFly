package com.shv.canifly.presentation.dialogs

import android.content.Context
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import com.shv.canifly.databinding.PermissionDialogBinding

fun permissionDialog(
    context: Context,
    permissionTextProvider: PermissionTextProvider,
    isPermanentlyDeclined: Boolean,
    onOkClick: () -> Unit,
    onGoToAppSettingsClick: () -> Unit
) {
    val dialogView = PermissionDialogBinding.inflate(LayoutInflater.from(context))
    with(dialogView) {
        tvDialogTitle.text = "Permission required"
        tvDialogContent.text = permissionTextProvider.gerDescription(isPermanentlyDeclined)
        tvDialogBtn.text = if (isPermanentlyDeclined) "Grand permission" else "OK"
    }
    val dialog = AlertDialog.Builder(context)
        .setView(dialogView.root)
        .setCancelable(false)
        .create()

    dialogView.tvDialogBtn.setOnClickListener {
        if (isPermanentlyDeclined) {
            onGoToAppSettingsClick()
        } else {
            onOkClick()
        }
        dialog.dismiss()
    }
    dialog.show()
}

interface PermissionTextProvider {
    fun gerDescription(isPermanentlyDeclined: Boolean): String
}

class LocationPermissionTextProvider : PermissionTextProvider {
    override fun gerDescription(isPermanentlyDeclined: Boolean): String {
        return if (isPermanentlyDeclined) {
            "It seems you permanently declined location permission. " +
                    "You can go to the app setting to grant it."
        } else {
            "This app needs access to your location."
        }
    }
}

class NotificationPermissionTextProvider : PermissionTextProvider {
    override fun gerDescription(isPermanentlyDeclined: Boolean): String {
        return if (isPermanentlyDeclined) {
            "It seems you permanently declined notification permission. " +
                    "You can go to the app setting to grant it."
        } else {
            "This app needs access to send you notification that " +
                    "you get weather update on selected date."
        }
    }
}