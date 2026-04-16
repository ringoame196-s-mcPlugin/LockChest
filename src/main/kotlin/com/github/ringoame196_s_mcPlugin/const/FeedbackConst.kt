package com.github.ringoame196_s_mcPlugin.const

import com.github.ringoame196_s_mcPlugin.data.model.Feedback
import org.bukkit.ChatColor
import org.bukkit.Sound

object FeedbackConst {
    val PLAYER_ONLY_MESSAGE = "${ChatColor.RED}このコマンドはプレイヤーのみ実行可能です"
    val UNSUPPORTED_BLOCK_MESSAGE = "${ChatColor.RED}このブロックは非対応です"
    val NO_PERMISSION_MESSAGE = "${ChatColor.DARK_RED}権限がありません"
    val ALREADY_LOCK_MESSAGE = "${ChatColor.RED}既にロックがかかっています"
    val LOCK = Feedback(
        "${ChatColor.YELLOW}ロックをかけました",
        Sound.BLOCK_CHEST_LOCKED
    )
    val UNLOCK = Feedback(
        "${ChatColor.YELLOW}ロックを解除しました",
        Sound.BLOCK_CHEST_OPEN
    )
    val NO_LOCK_MESSAGE = "${ChatColor.RED}ロックがかかっていません"
    val WRONG_PASSWORD = Feedback(
        "${ChatColor.RED}パスワードが間違っています",
        Sound.BLOCK_NOTE_BLOCK_BELL
    )
    val NO_OPEN_MESSAGE = "${ChatColor.RED}このブロックを開けることはできません"
    val NO_BREAK = Feedback(
        "${ChatColor.RED}このブロックはロックがかかっているため破壊できません",
        Sound.BLOCK_NOTE_BLOCK_BELL
    )
}
