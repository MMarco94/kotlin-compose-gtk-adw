package io.github.mmarco94.compose.gtk.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ComposeNode
import io.github.mmarco94.compose.GtkApplier
import io.github.mmarco94.compose.GtkComposeNode
import io.github.mmarco94.compose.LeafComposeNode
import io.github.mmarco94.compose.modifier.Modifier
import org.gnome.gdk.Paintable
import org.gnome.gtk.ContentFit
import org.gnome.gtk.Picture
import java.io.File

sealed interface Image {
    data class File(val file: org.gnome.gio.File) : Image
    data class Paintable(val paintable: org.gnome.gdk.Paintable) : Image
}

@Composable
fun Picture(
    image: File,
    modifier: Modifier = Modifier,
    alternativeText: String? = null,
    canShrink: Boolean = true,
    contentFit: ContentFit = ContentFit.CONTAIN,
) {
    Picture(
        image = Image.File(org.gnome.gio.File.newForPath(image.absolutePath)),
        modifier = modifier,
        alternativeText = alternativeText,
        canShrink = canShrink,
        contentFit = contentFit
    )
}

@Composable
fun Picture(
    image: org.gnome.gio.File,
    modifier: Modifier = Modifier,
    alternativeText: String? = null,
    canShrink: Boolean = true,
    contentFit: ContentFit = ContentFit.CONTAIN,
) {
    Picture(
        image = Image.File(image),
        modifier = modifier,
        alternativeText = alternativeText,
        canShrink = canShrink,
        contentFit = contentFit
    )
}


@Composable
fun Picture(
    image: Paintable,
    modifier: Modifier = Modifier,
    alternativeText: String? = null,
    canShrink: Boolean = true,
    contentFit: ContentFit = ContentFit.CONTAIN,
) {
    Picture(
        image = Image.Paintable(image),
        modifier = modifier,
        alternativeText = alternativeText,
        canShrink = canShrink,
        contentFit = contentFit
    )
}

@Composable
fun Picture(
    image: Image?,
    modifier: Modifier = Modifier,
    alternativeText: String? = null,
    canShrink: Boolean = true,
    contentFit: ContentFit = ContentFit.CONTAIN,
) {
    ComposeNode<GtkComposeNode<Picture>, GtkApplier>({
        LeafComposeNode(Picture.builder().build())
    }) {
        set(modifier) { applyModifier(it) }
        set(image) {
            when (it) {
                null -> this.gObject.file = null
                is Image.File -> this.gObject.file = it.file
                is Image.Paintable -> this.gObject.paintable = it.paintable
            }
        }
        set(alternativeText) { this.gObject.alternativeText = it }
        set(canShrink) { this.gObject.canShrink = it }
        set(contentFit) { this.gObject.contentFit = it }
    }
}