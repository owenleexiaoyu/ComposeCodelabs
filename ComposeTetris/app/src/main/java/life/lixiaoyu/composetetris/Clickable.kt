package life.lixiaoyu.composetetris

import androidx.compose.ui.geometry.Offset


data class Clickable(
    val onMove: (Direction) -> Unit,
    val onDrop: () -> Unit,
    val onRotate: () -> Unit,
    val onRestart: () -> Unit,
    val onPause: () -> Unit,
    val onMute: () -> Unit
)

enum class Direction {
    LEFT {
        override fun toOffset(): Offset {
            return Offset(-1F, 0F)
        }
    },
    RIGHT {
        override fun toOffset(): Offset {
            return Offset(1F, 0F)
        }
    },
    DOWN {
        override fun toOffset(): Offset {
            return Offset(0F, 1F)
        }
    };
    abstract fun toOffset(): Offset
}
