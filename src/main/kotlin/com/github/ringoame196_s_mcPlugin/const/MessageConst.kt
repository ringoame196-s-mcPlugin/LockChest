package com.github.ringoame196_s_mcPlugin.const

import org.bukkit.ChatColor

object MessageConst {
    val PLAYER_ONLY_MESSAGE = "${ChatColor.RED}このコマンドはプレイヤーのみ実行可能です"
    val UNSUPPORTED_BLOCK_MESSAGE = "${ChatColor.RED}このブロックは非対応です"
    val NO_LOCK_MESSAGE = "${ChatColor.RED}ロックがかかっていません"
    val UNLOCK_MESSAGE = "${ChatColor.YELLOW}ロックを解除しました"
    val NO_PERMISSION_MESSAGE = "${ChatColor.DARK_RED}権限がありません"
    val ALREADY_LOCK_MESSAGE = "${ChatColor.RED}既にロックがかかっています"
    val LOCK_MESSAGE = "${ChatColor.YELLOW}ロックをかけました"
    val WRONG_PASSWORD_MESSAGE = "${ChatColor.RED}パスワードが間違っています"
    val NO_BREAK_MESSAGE = "${ChatColor.RED}このブロックはロックがかかっているため破壊できません"
    val NO_OPEN_MESSAGE = "${ChatColor.RED}このブロックを開けることはできません"
}
