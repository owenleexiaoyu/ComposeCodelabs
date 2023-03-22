package life.lixiaoyu.composetetris

import androidx.compose.ui.geometry.Offset


data class Clickable(
    val onMove: (Direction) -> Unit,
    val onRotate: () -> Unit,
    val onRestart: () -> Unit,
    val onPause: () -> Unit,
    val onMute: () -> Unit
)

enum class Direction {
    UP {
        override fun toOffset(): Offset {
            return Offset(0F, 0F)  // 不偏移
        }
    },
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
