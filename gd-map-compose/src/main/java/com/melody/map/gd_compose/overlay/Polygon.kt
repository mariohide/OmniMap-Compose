// MIT License
//
// Copyright (c) 2022 被风吹过的夏天
//
// Permission is hereby granted, free of charge, to any person obtaining a copy
// of this software and associated documentation files (the "Software"), to deal
// in the Software without restriction, including without limitation the rights
// to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
// copies of the Software, and to permit persons to whom the Software is
// furnished to do so, subject to the following conditions:
//
// The above copyright notice and this permission notice shall be included in all
// copies or substantial portions of the Software.
//
// THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
// IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
// FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
// AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
// LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
// OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
// SOFTWARE.

package com.melody.map.gd_compose.overlay

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ComposeNode
import androidx.compose.runtime.currentComposer
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import com.amap.api.maps.model.AMapPara
import com.amap.api.maps.model.LatLng
import com.amap.api.maps.model.Polygon
import com.amap.api.maps.model.PolygonOptions
import com.melody.map.gd_compose.MapApplier
import com.melody.map.gd_compose.MapNode
import com.amap.api.maps.model.AMapPara.LineJoinType
import com.melody.map.gd_compose.model.GDMapComposable

internal class PolygonNode(
    val polygon: Polygon
) : MapNode {
    override fun onRemoved() {
        polygon.remove()
    }
}

/**
 * A composable for a polygon on the map.
 *
 * @param points the points comprising the vertices of the polygon
 * @param fillColor the fill color of the polygon
 * @param strokeColor the stroke color of the polygon
 * @param strokeWidth specifies the polygon's stroke width, in display pixels
 * @param visible the visibility of the polygon
 * @param zIndex the z-index of the polygon
 * @param onClick a lambda invoked when the polygon is clicked
 */
@Composable
@GDMapComposable
fun Polygon(
    points: List<LatLng>,
    fillColor: Color = Color.Black,
    strokeColor: Color = Color.Black,
    strokeWidth: Float = 10f,
    visible: Boolean = true,
    zIndex: Float = 0f,
    lineJoinType: LineJoinType = LineJoinType.LineJoinBevel
) {
    val mapApplier = currentComposer.applier as MapApplier?
    ComposeNode<PolygonNode, MapApplier>(
        factory = {
            val polygon = mapApplier?.map?.addPolygon(PolygonOptions().apply  {
                addAll(points)
                fillColor(fillColor.toArgb())
                strokeColor(strokeColor.toArgb())
                strokeWidth(strokeWidth)
                lineJoinType(lineJoinType)
                visible(visible)
                zIndex(zIndex)
            }) ?: error("Error adding polygon")
            PolygonNode(polygon)
        },
        update = {
            set(points) { this.polygon.points = it }
            set(fillColor) { this.polygon.fillColor = it.toArgb() }
            set(strokeColor) { this.polygon.strokeColor = it.toArgb() }
            set(strokeWidth) { this.polygon.strokeWidth = it }
            set(visible) { this.polygon.isVisible = it }
            set(zIndex) { this.polygon.zIndex = it }
        }
    )
}