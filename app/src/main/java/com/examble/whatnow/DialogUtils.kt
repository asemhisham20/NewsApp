package com.examble.whatnow

import android.R.id.message
import android.content.Context
import android.content.DialogInterface
import androidx.appcompat.app.AlertDialog

fun Context.showMessage( message: String,
                    posActionName:String?=null,
                    posAction: DialogInterface.OnClickListener?=null,
                    negActionName:String?=null,
                    negAction: DialogInterface.OnClickListener?=null,
                    ): AlertDialog{
        val dialog = AlertDialog.Builder(this)
        dialog.setMessage(message)
        if(posActionName!=null)
        dialog.setPositiveButton(posActionName,posAction)
if(negActionName!=null)
    dialog.setNegativeButton(negActionName,negAction)

return dialog.show()

    }
