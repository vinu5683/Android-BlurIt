
# Blur-It 

<H2>Blur-It is an Android project demonstrating an efficient method for background blurring, particularly challenging on Android 11 or earlier devices. 
<br/>The project includes a movable blur view that traverses the constraint layout </b>, along with a seek bar to dynamically adjust the blur radius. Additionally, 
  it addresses and seeks to ignore any UI glitches for a smoother user experience. 🙏🏻 </H2>

<H1>Here is the sample Images</H1>
<hr />
<div>
<img src="sample/Blur video.gif" alt="BlurIt Demo" width="300" height="700">
<img src="sample/Blur Pic.png" alt="BlurIt Demo" width="300" height="700">
</div>
<hr />
<H2>Gif is pixellated
</H2>
<a>
  
</a>[Download BlurIt APK](sample/BlurByVinod.apk)
<hr />
Special credits to 
https://github.com/Dimezis/
<hr />
<H1>Thank You 🙏🏻</H1>


<H1>Update:</H1>
<hr />
Here is the code for Composable equalent

package com.example.facedetection.ui.componants

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Rect
import android.renderscript.Allocation
import android.renderscript.Element
import android.renderscript.RenderScript
import android.renderscript.ScriptIntrinsicBlur
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.core.view.drawToBitmap
import com.example.facedetection.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.math.roundToInt

@Composable
fun DraggableBlurOverlay(blurRadius: Float = 16f) {
    val context = LocalContext.current
    val view    = LocalView.current      // your ComposeView

    // 1️⃣ Hold the “clean” full‐screen bitmap, but *only* after layout
    var originalBmp by remember { mutableStateOf<Bitmap?>(null) }
    DisposableEffect(view) {
        // post runs after measure+layout
        view.post {
            if (originalBmp == null && view.width > 0 && view.height > 0) {
                originalBmp = view.drawToBitmap()
            }
        }
        onDispose { /* nothing to clean up */ }
    }

    // 2️⃣ Drag + blur state
    var dragOffset by remember { mutableStateOf(Offset.Zero) }
    var targetRect by remember { mutableStateOf<Rect?>(null) }
    var blurBmp    by remember { mutableStateOf<ImageBitmap?>(null) }

    Box(Modifier.fillMaxSize()) {
        // background
        Box(
            Modifier
                .size(900.dp)
                .background(Color(0xFF3366FF))
        ) {
            Image(painterResource(R.drawable.abc), contentDescription = null)
        }

        // draggable window
        Box(
            Modifier
                .offset { IntOffset(dragOffset.x.roundToInt(), dragOffset.y.roundToInt()) }
                .size(300.dp)
                .pointerInput(Unit) {
                    detectDragGestures { change, dragAmount ->
                        change.consume()
                        dragOffset += dragAmount
                    }
                }
                .onGloballyPositioned { coords ->
                    coords.localToWindow(Offset.Zero).let { pos ->
                        targetRect = Rect(
                            pos.x.toInt(),
                            pos.y.toInt(),
                            (pos.x + coords.size.width).toInt(),
                            (pos.y + coords.size.height).toInt()
                        )
                    }
                }
        ) {
            blurBmp?.let { bmp ->
                Image(bmp, contentDescription = null, Modifier.fillMaxSize())
            } ?: Box(Modifier.matchParentSize().background(Color.Gray.copy(alpha = .3f)))
        }
    }

    // 3️⃣ When we have both the original & a new drag rect, re‐crop & blur
    targetRect?.let { rect ->
        LaunchedEffect(rect, originalBmp) {
            val full = originalBmp ?: return@LaunchedEffect
            blurBmp = null
            val cropped = withContext(Dispatchers.Default) {
                // clamp in-bounds
                val left = rect.left.coerceIn(0, full.width)
                val top  = rect.top.coerceIn(0, full.height)
                val w    = rect.width().coerceAtMost(full.width - left)
                val h    = rect.height().coerceAtMost(full.height - top)
                Bitmap.createBitmap(full, left, top, w, h)
                    .let { blurBitmap(context, it, blurRadius) }
            }
            blurBmp = cropped.asImageBitmap()
        }
    }
}

// same as before
fun blurBitmap(
    context: Context,
    input: Bitmap,
    radius: Float
): Bitmap {
    val r = radius.coerceIn(0f, 25f)
    val output = Bitmap.createBitmap(input.width, input.height, Bitmap.Config.ARGB_8888)
    val rs = RenderScript.create(context)
    val inAlloc  = Allocation.createFromBitmap(rs, input)
    val outAlloc = Allocation.createTyped(rs, inAlloc.type)
    val script   = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs))
    script.setRadius(r)
    script.setInput(inAlloc)
    script.forEach(outAlloc)
    outAlloc.copyTo(output)
    rs.destroy()
    return output
}


<h1>THANKS AGAIN</h1>

