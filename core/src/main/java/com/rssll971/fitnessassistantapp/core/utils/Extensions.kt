/************************************************
 * Created by Ruslan Khvastunov                 *
 * r.khvastunov@gmail.com                       *
 * Copyright (c) 2022                           *
 * All rights reserved.                         *
 *                                              *
 ************************************************/

package com.rssll971.fitnessassistantapp.core.utils

import android.widget.ImageView
import com.bumptech.glide.Glide

fun ImageView.loadImage(path: String){
    Glide.with(context).load(path).into(this)
}